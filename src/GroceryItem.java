import java.util.Date;

public class GroceryItem {
    private String itemCode;
    private String name;
    private double price;
    private double weightSize;
    private String manufacturingDate;
    private String expiryDate;
    private String manufacturer;

    // Grocery Item constructor
    public GroceryItem(String itemCode, String name, double price, double weightSize,
                       String manufacturingDate, String expiryDate, String manufacturer) {
        this.itemCode = itemCode;
        this.name = name;
        this.price = price;
        this.weightSize = weightSize;
        this.manufacturingDate = manufacturingDate;
        this.expiryDate = expiryDate;
        this.manufacturer = manufacturer;
    }

    // Getters and Setters
    public String getItemCode() {
        return itemCode;
    }

    public String getName() {
        return this.name;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public double getPrice() {
        return this.price;
    }
}
