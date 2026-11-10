public class PerishableProduct extends Product {
    private String expiryDate;

    public PerishableProduct(int id, String name, String category,
                             int quantity, double price,
                             int minimumStock, String expiryDate) {
        super(id, name, category, quantity, price, minimumStock);
        this.expiryDate = expiryDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String getProductType() {
        return "Perishable";
    }

    @Override
    public String getExtraDetails() {
        return "Expiry Date: " + expiryDate;
    }

    @Override
    public String toFileString() {
        return getProductId() + "|" + getProductName() + "|"
                + getCategory() + "|" + getQuantity() + "|"
                + getPrice() + "|" + getMinimumStock()
                + "|PERISHABLE|" + expiryDate;
    }
}