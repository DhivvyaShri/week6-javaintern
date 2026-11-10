import java.util.List;

public interface StockOperation {
    void addProduct(Product product);

    void viewProducts();

    Product searchProduct(int id);

    void updateProduct(int id, String name,
                       double price, int minimumStock);

    void deleteProduct(int id);

    List<Product> getAllProducts();
}