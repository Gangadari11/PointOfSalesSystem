public class BillItem {
    private GroceryItem item;
    private int quantity;
    private double discount;

    public BillItem(GroceryItem item, int quantity, double discount) {
        this.item = item;
        this.quantity = quantity;
        this.discount = discount;
    }

    public GroceryItem getItem() {
        return item;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public double getDiscount() {
        return this.discount;
    }

    public double getNetPrice() {
        double itemPrice = item.getPrice() * quantity;
        double discountAmount = itemPrice * (discount / 100.0);
        return itemPrice - discountAmount;
    }



}
