public class StandardProduct extends Product {

    public StandardProduct(int id, String name, String category,
                           int quantity, double price, int minimumStock) {
        super(id, name, category, quantity, price, minimumStock);
    }

    @Override
    public String getProductType() {
        return "Standard";
    }

    @Override
    public String getExtraDetails() {
        return "No expiry date";
    }

    @Override
    public String toFileString() {
        return getProductId() + "|" + getProductName() + "|"
                + getCategory() + "|" + getQuantity() + "|"
                + getPrice() + "|" + getMinimumStock()
                + "|STANDARD|NA";
    }
}