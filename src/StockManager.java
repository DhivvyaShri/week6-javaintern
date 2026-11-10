import java.io.*;
import java.nio.file.*;
import java.util.*;

public class StockManager implements StockOperation {

    private HashMap<Integer, Product> products = new HashMap<>();
    private HashSet<String> categories = new HashSet<>();
    private ArrayList<String> transactions = new ArrayList<>();

    private final String PRODUCT_FILE = "data/products.txt";
    private final String TRANSACTION_FILE = "data/transactions.txt";

    public StockManager() {
        loadProducts();
        loadTransactions();
    }

    @Override
    public void addProduct(Product product) {
        if (products.containsKey(product.getProductId())) {
            System.out.println("Error: Product ID already exists.");
            return;
        }

        products.put(product.getProductId(), product);
        categories.add(product.getCategory());

        transactions.add("Added product: " + product.getProductName());
        saveProducts();
        saveTransactions();

        System.out.println("Product added successfully!");
    }

    @Override
    public void viewProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        for (Product product : products.values()) {
            product.displayDetails();
        }
    }

    @Override
    public Product searchProduct(int id) {
        return products.get(id);
    }

    @Override
    public void updateProduct(int id, String name,
                              double price, int minimumStock) {
        Product product = products.get(id);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        if (name == null || name.trim().isEmpty()
                || price <= 0 || minimumStock < 0) {
            System.out.println("Invalid product details.");
            return;
        }

        product.setProductName(name);
        product.setPrice(price);
        product.setMinimumStock(minimumStock);

        transactions.add("Updated product ID: " + id);

        saveProducts();
        saveTransactions();

        System.out.println("Product updated successfully!");
    }

    @Override
    public void deleteProduct(int id) {
        Product removed = products.remove(id);

        if (removed == null) {
            System.out.println("Product not found.");
            return;
        }

        transactions.add("Deleted product: " + removed.getProductName());

        rebuildCategories();
        saveProducts();
        saveTransactions();

        System.out.println("Product deleted successfully!");
    }

    public void addStock(int id, int amount) {
        Product product = products.get(id);

        if (product == null || amount <= 0) {
            System.out.println("Invalid product ID or quantity.");
            return;
        }

        product.setQuantity(product.getQuantity() + amount);

        transactions.add("Added " + amount + " units to "
                + product.getProductName());

        saveProducts();
        saveTransactions();

        System.out.println("Stock added successfully!");
    }

    public void removeStock(int id, int amount) {
        Product product = products.get(id);

        if (product == null || amount <= 0) {
            System.out.println("Invalid product ID or quantity.");
            return;
        }

        if (amount > product.getQuantity()) {
            System.out.println("Insufficient stock!");
            return;
        }

        product.setQuantity(product.getQuantity() - amount);

        transactions.add("Removed " + amount + " units from "
                + product.getProductName());

        saveProducts();
        saveTransactions();

        System.out.println("Stock removed successfully!");
    }

    public void showLowStock() {
        boolean found = false;

        System.out.println("\n--- LOW STOCK ALERTS ---");

        for (Product product : products.values()) {
            if (product.getQuantity() <= product.getMinimumStock()) {
                System.out.println(product.getProductName()
                        + " | Quantity: " + product.getQuantity()
                        + " | Status: " + product.getStockStatus());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No low-stock products.");
        }
    }

    public void showSummary() {
        int totalProducts = products.size();
        int totalQuantity = 0;
        double totalValue = 0;

        for (Product product : products.values()) {
            totalQuantity += product.getQuantity();
            totalValue += product.getTotalValue();
        }

        System.out.println("\n--- INVENTORY SUMMARY ---");
        System.out.println("Number of Products : " + totalProducts);
        System.out.println("Total Stock Units  : " + totalQuantity);
        System.out.println("Inventory Value    : Rs." + totalValue);
        System.out.println("Unique Categories  : " + categories.size());
        System.out.println("Unique Categories  : " + categories);
    }

    public void showTransactions() {
        System.out.println("\n--- STOCK MOVEMENT HISTORY ---");

        if (transactions.isEmpty()) {
            System.out.println("No transaction history.");
            return;
        }

        for (String transaction : transactions) {
            System.out.println(transaction);
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    private void rebuildCategories() {
        categories.clear();

        for (Product product : products.values()) {
            categories.add(product.getCategory());
        }
    }

    private void loadProducts() {
        try {
            Files.createDirectories(Paths.get("data"));

            File file = new File(PRODUCT_FILE);

            if (!file.exists()) {
                file.createNewFile();
                return;
            }

            try (BufferedReader reader =
                         new BufferedReader(new FileReader(file))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    try {
                        String[] data = line.split("\\|", -1);

                        if (data.length != 8) {
                            continue;
                        }

                        int id = Integer.parseInt(data[0]);
                        String name = data[1];
                        String category = data[2];
                        int quantity = Integer.parseInt(data[3]);
                        double price = Double.parseDouble(data[4]);
                        int minimumStock = Integer.parseInt(data[5]);

                        if (id <= 0 || name.isBlank()
                                || category.isBlank() || quantity < 0
                                || price <= 0 || minimumStock < 0
                                || products.containsKey(id)) {
                            continue;
                        }

                        Product product;

                        if (data[6].equals("PERISHABLE")) {
                            product = new PerishableProduct(
                                    id, name, category, quantity,
                                    price, minimumStock, data[7]);
                        } else if (data[6].equals("STANDARD")) {
                            product = new StandardProduct(
                                    id, name, category, quantity,
                                    price, minimumStock);
                        } else {
                            continue;
                        }

                        products.put(id, product);
                        categories.add(category);

                    } catch (NumberFormatException e) {
                        System.out.println("Skipping invalid product record.");
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
    }

    private void saveProducts() {
        try {
            Files.createDirectories(Paths.get("data"));

            try (BufferedWriter writer =
                         new BufferedWriter(new FileWriter(PRODUCT_FILE))) {

                for (Product product : products.values()) {
                    writer.write(product.toFileString());
                    writer.newLine();
                }
            }

        } catch (IOException e) {
            System.out.println("Error saving products: " + e.getMessage());
        }
    }

    private void loadTransactions() {
        try {
            File file = new File(TRANSACTION_FILE);

            if (!file.exists()) {
                file.createNewFile();
                return;
            }

            try (BufferedReader reader =
                         new BufferedReader(new FileReader(file))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    transactions.add(line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
    }

    private void saveTransactions() {
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(TRANSACTION_FILE))) {

            for (String transaction : transactions) {
                writer.write(transaction);
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    public void createBackup() {
        Thread backupThread = new Thread(() -> {
            try {
                Files.createDirectories(Paths.get("backups"));

                String timestamp = java.time.LocalDateTime.now()
                        .toString().replace(":", "-");

                Files.copy(
                        Paths.get(PRODUCT_FILE),
                        Paths.get("backups/products_" + timestamp + ".txt"),
                        StandardCopyOption.REPLACE_EXISTING
                );

                Files.copy(
                        Paths.get(TRANSACTION_FILE),
                        Paths.get("backups/transactions_" + timestamp + ".txt"),
                        StandardCopyOption.REPLACE_EXISTING
                );

                System.out.println("\nBackup completed successfully!");

            } catch (IOException e) {
                System.out.println("Backup failed: " + e.getMessage());
            }
        });

        backupThread.start();
    }
}