import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class ItemDatabase {
    private Map<String, GroceryItem> items;

    public ItemDatabase() {
        items = new HashMap<>();
        populateItemDatabase();
    }

    private void populateItemDatabase() {
        // Sample items
        items.put("19984", new GroceryItem("19984", "Donut", 200, 1.0, "2024.03.19", "2024.03.20", "P&S"));
        items.put("28921", new GroceryItem("28921", "M&M", 3200, 0.5, "2023.03.12","2023.05.12", "123COMPANY"));
        items.put("75186", new GroceryItem("75186", "SunCrush", 150, 175,"2024.03.15","2024.06.15", "PK BEVERAGES"));
        items.put("84524", new GroceryItem("84524", "Toothbrush", 100, 1.0, "2024.03.2","2024.12.12", "B2C"));
        items.put("56842", new GroceryItem("56842", "Toothpaste", 500, 1.0, "2024.13.08", "2024.06.08", "PASTE MASTERS"));
        items.put("64218", new GroceryItem("64218", "Soap", 200, 1.0, "2024.03.10" ,"2024.07.10", "WINLIGHT"));
        items.put("27643", new GroceryItem("27643", "Kiwi", 200, 100.0,"2024.03.20", "2024.04.20","HPOKINS VIBE"));
        items.put("94628", new GroceryItem("94628", "FreshMilk", 530, 1.0,  "2024.03.20","2024.03.27" ,"AMBEWELA"));
        items.put("59278", new GroceryItem("59278", "Shampo", 750, 1.0, "2024.03.15","2024.06.15", "WINLIGHT"));
        items.put("27469", new GroceryItem("27469", "Bread", 150, 1.0, "2024.03.19","2024.03.20", "PERERA BAKERS"));
        items.put("78435", new GroceryItem("78435", "Chocolate Biscuits", 500, 1.0, "2024.03.02","2024.07.02", "Valibun"));
        items.put("48731", new GroceryItem("48731", "cocacola", 280, 1.0,"2024.03.01","2024.08.01", "ABC Foods"));


    }

    public GroceryItem getItem(String itemCode) {
        return items.get(itemCode);
    }
}
