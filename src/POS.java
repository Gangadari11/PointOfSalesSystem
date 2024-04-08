import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class POS {
    private Scanner scanner = new Scanner(System.in);
    private ItemDatabase itemDatabase = new ItemDatabase();
    private ArrayList<String> registeredCustomers = new ArrayList<>();
    private Bill currentBill = null;

    public static void main(String[] args) {
        POS posSystem = new POS(); // Create instance of the POS
        posSystem.loadRegisteredCustomers(); // Load customers on startup

        while (true) {
            posSystem.showMenu();
            int choice = posSystem.scanner.nextInt();
            posSystem.scanner.nextLine(); // Consume newline

            if (choice == 0) {
                System.out.println("Exiting the system...");
                break; // Exit the loop
            }

            posSystem.handleMenuChoice(choice);
        }
    }

    private void showMenu() {
        System.out.println("\n***** Super-Saving POS *****");
        System.out.println("1. Start New Bill");
        System.out.println("2. Add Item to Existing Bill");
        System.out.println("3. Process Bill");
        System.out.println("4. Save Pending Bill");
        System.out.println("5. Load Pending Bill");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                currentBill = startNewBill();
                break;
            case 2:
                if (currentBill == null) {
                    System.out.println("Error: No active bill. Please start a new bill first.");
                } else {
                    addItemToBill(currentBill);
                }
                break;
            case 3:
                if (currentBill == null) {
                    System.out.println("Error: No active bill.");
                } else {
                    processBill(currentBill);
                    currentBill = null;  // Reset the bill
                }
                break;
            case 4:
                if (currentBill == null) {
                    System.out.println("Error: No active bill.");
                } else {
                    savePendingBill(currentBill);
                }
                break;
            case 5:
                currentBill = loadPendingBill();
                if (currentBill != null) {
                    System.out.println("Pending bill loaded.");
                }
                break;
            default:
                System.out.println("Invalid Choice");
        }
    }

    // Methods
    public Bill startNewBill() {
        System.out.print("Enter Cashier Name: ");
        String cashierName = scanner.nextLine();
        System.out.print("Enter Branch: ");
        String branch = scanner.nextLine();
        System.out.print("Enter Customer Name or Phone Number : ");
        String customerIdentifier = scanner.nextLine();

        Bill bill = new Bill(cashierName, branch, "");

        if (!customerIdentifier.isEmpty() && registeredCustomers.contains(customerIdentifier)) {
            bill.setIsCustomerRegistered(true);
            bill.setCustomerName(customerIdentifier); // Set the name here
        }

        return bill;
    }

    public GroceryItem getItemDetails() throws ItemCodeNotFound {
        while (true) {
            System.out.print("Enter Item Code: ");
            String itemCode = scanner.nextLine();

            GroceryItem item = fetchItemFromDatabase(itemCode);

            if (item != null) {
                return item;
            } else {
                throw new ItemCodeNotFound("Item with code " + itemCode + " not found");
            }
        }
    }

    public void addItemToBill(Bill bill) {
        try {
            GroceryItem item = getItemDetails();
            System.out.print("Enter Quantity: ");
            int quantity = scanner.nextInt();

            System.out.print("Enter Discount (Percentage): ");
            double discount = scanner.nextDouble();

            BillItem billItem = new BillItem(item, quantity, discount);
            bill.addItem(billItem);
            bill.calculateTotals();

        } catch (ItemCodeNotFound e) {
            System.err.println("Error: Item not found with the provided code.");
            boolean retry = true;
            while (retry) {
                System.out.print("Try again? (y/n): ");
                if (scanner.nextLine().equalsIgnoreCase("y")) {
                    try {
                        GroceryItem item = getItemDetails();
                        item = getItemDetails();
                        retry = false;
                    } catch (ItemCodeNotFound ex) {
                        System.err.println("Error: Item not found.");
                    }
                } else {
                    retry = false; // Exit the loop
                }
            }
        }
    }

    public void processBill(Bill bill) {
        bill.setDate(new Date());
        bill.setTime(new Date());
        System.out.println(bill.toString());
    }

    public void savePendingBill(Bill bill) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("pending_bill.ser"))) {
            out.writeObject(bill);
            System.out.println("Bill saved as pending.");
        } catch (IOException e) {
            System.err.println("Error saving bill: " + e.getMessage());
        }
    }

    public Bill loadPendingBill() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("pending_bill.ser"))) {
            return (Bill) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading pending bill: " + e.getMessage());
            return null;
        }
    }


    private GroceryItem fetchItemFromDatabase(String itemCode) {
        return itemDatabase.getItem(itemCode);
    }

    private void loadRegisteredCustomers() {
        try (Scanner fileScanner = new Scanner(new File("registered_customers.txt"))) {
            while (fileScanner.hasNextLine()) {
                registeredCustomers.add(fileScanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: registered_customers.txt not found.");
        }
    }

}
