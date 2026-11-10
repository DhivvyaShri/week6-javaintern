public abstract class Product {
    private int productId;
    private String productName;
    private String category;
    private int quantity;
    private double price;
    private int minimumStock;

    public Product(int productId, String productName, String category,
                   int quantity, double price, int minimumStock) {
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.minimumStock = minimumStock;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public int getMinimumStock() {
        return minimumStock;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setMinimumStock(int minimumStock) {
        this.minimumStock = minimumStock;
    }

    public String getStockStatus() {
        if (quantity == 0) {
            return "OUT OF STOCK";
        } else if (quantity <= minimumStock) {
            return "LOW STOCK";
        }
        return "IN STOCK";
    }

    public double getTotalValue() {
        return quantity * price;
    }

    public abstract String getProductType();

    public abstract String getExtraDetails();

    public void displayDetails() {
        System.out.println("------------------------------------");
        System.out.println("Product ID     : " + productId);
        System.out.println("Product Name   : " + productName);
        System.out.println("Category       : " + category);
        System.out.println("Product Type   : " + getProductType());
        System.out.println("Quantity       : " + quantity);
        System.out.println("Price          : Rs." + price);
        System.out.println("Stock Status   : " + getStockStatus());
        System.out.println("Minimum Stock  : " + minimumStock);
        System.out.println("Extra Details  : " + getExtraDetails());
        System.out.println("Total Value    : Rs." + getTotalValue());
        System.out.println("------------------------------------");
    }

    public abstract String toFileString();
}