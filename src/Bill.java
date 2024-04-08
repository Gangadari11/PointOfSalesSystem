import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class Bill implements Serializable {
    private String cashierName;
    private String branch;
    private String customerName;
    private ArrayList<BillItem> items;  //List of items in the bill
    private double totalDiscount; 
    private double totalPrice;  
    private Date date;      //Date when the bill was printed
    private Date time;  // Time when the bill was printed
    private boolean isCustomerRegistered;   // Flag indicating if the customer is registered

    public Bill(String cashierName, String branch, String customerName) {
        this.cashierName = cashierName;
        this.branch = branch;
        this.customerName = customerName;
        this.items = new ArrayList<>();
        this.totalDiscount = 0.0;
        this.totalPrice = 0.0;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public ArrayList<BillItem> getItems() {
        return items;
    }


    // Add an item to the bill
    public void addItem(BillItem item) {
        items.add(item);
        // Recalculate totalPrice and totalDiscount (explained later)
    }


    // Calculate total discount and total price of the bill
    public void calculateTotals() {
        totalPrice = 0.0;
        totalDiscount = 0.0;

        for (BillItem item : items) {
            totalPrice += item.getNetPrice();
            totalDiscount += (item.getItem().getPrice() * item.getQuantity() * item.getDiscount() / 100);
        }
    }


    // Set whether the customer is registered
    public void setIsCustomerRegistered(boolean isCustomerRegistered) {
        this.isCustomerRegistered = isCustomerRegistered;
    }


    // Set customer name
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    //Generate Bill
    @Override
    public String toString() {
        StringBuilder billOutput = new StringBuilder("\n***** BILL *****\n");
        billOutput.append("Cashier: ").append(cashierName).append("\n");
        billOutput.append("Branch: ").append(branch).append("\n");

        if (isCustomerRegistered) {
            billOutput.append("Customer: ").append(customerName).append("\n");
        }

        billOutput.append("Date: ").append(formatDate(date)).append("\n");
        billOutput.append("Time: ").append(formatTime(time)).append("\n");

        billOutput.append("\n----- Items -----\n");
        billOutput.append(String.format("%-15s %-10s %-10s %-10s\n", "Item", "Price", "Qty", "Net Price")); // Table header
        for (BillItem item : items) {
            billOutput.append(String.format("%-15s %-10.2f %-10d %-10.2f\n",
                    item.getItem().getName(),
                    item.getItem().getPrice(),
                    item.getQuantity(),
                    item.getNetPrice()));
        }

        billOutput.append(String.format("\nTotal Discount:  %.2f\n", totalDiscount));
        billOutput.append(String.format("Total Price:     %.2f\n", totalPrice));

        return billOutput.toString();
    }

    // Helper functions for formatting date and time
    private String formatDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }

    private String formatTime(Date time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        return formatter.format(time);
    }

}