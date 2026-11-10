import java.util.Scanner;

public class SmartStockApp {

    private static final Scanner sc = new Scanner(System.in);
    private static final StockManager manager = new StockManager();

    public static void main(String[] args) {

        int choice;

        System.out.println("======================================");
        System.out.println("     SMARTSTOCK MANAGEMENT SYSTEM");
        System.out.println("======================================");

        do {
            displayMenu();
            choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    addProduct();
                    break;

                case 2:
                    manager.viewProducts();
                    break;

                case 3:
                    searchProduct();
                    break;

                case 4:
                    updateProduct();
                    break;

                case 5:
                    int deleteId = readInt("Enter Product ID to delete: ");
                    manager.deleteProduct(deleteId);
                    break;

                case 6:
                    int addId = readInt("Enter Product ID: ");
                    int addQuantity = readInt("Enter quantity to add: ");
                    manager.addStock(addId, addQuantity);
                    break;

                case 7:
                    int removeId = readInt("Enter Product ID: ");
                    int removeQuantity =
                            readInt("Enter quantity to remove: ");
                    manager.removeStock(removeId, removeQuantity);
                    break;

                case 8:
                    manager.showLowStock();
                    break;

                case 9:
                    manager.showSummary();
                    break;

                case 10:
                    manager.showTransactions();
                    break;

                case 11:
                    System.out.println("Starting backup in background...");
                    manager.createBackup();
                    break;

                case 12:
                    System.out.println("Thank you for using SmartStock!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 12);

        sc.close();
    }

    private static void displayMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1.  Add New Product");
        System.out.println("2.  View All Products");
        System.out.println("3.  Search Product");
        System.out.println("4.  Update Product");
        System.out.println("5.  Delete Product");
        System.out.println("6.  Add Stock");
        System.out.println("7.  Remove Stock");
        System.out.println("8.  View Low Stock Alerts");
        System.out.println("9.  View Inventory Summary");
        System.out.println("10. View Stock Movement History");
        System.out.println("11. Create Data Backup");
        System.out.println("12. Exit");
        System.out.println("===============================");
    }

    private static void addProduct() {

        System.out.println("\n--- ADD NEW PRODUCT ---");

        int id = readInt("Enter Product ID: ");
        String name = readText("Enter Product Name: ");
        String category = readText("Enter Category: ");
        int quantity = readInt("Enter Quantity: ");
        double price = readDouble("Enter Price: ");
        int minimumStock = readInt("Enter Minimum Stock Level: ");

        if (id <= 0 || name.isBlank() || category.isBlank()
                || quantity < 0 || price <= 0 || minimumStock < 0) {
            System.out.println("Invalid details. Please try again.");
            return;
        }

        System.out.println("1. Standard Product");
        System.out.println("2. Perishable Product");

        int type = readInt("Select Product Type: ");

        if (type == 1) {

            Product product = new StandardProduct(
                    id, name, category, quantity, price, minimumStock
            );

            manager.addProduct(product);

        } else if (type == 2) {

            String expiryDate = readText("Enter Expiry Date (DD-MM-YYYY): ");

            Product product = new PerishableProduct(
                    id, name, category, quantity, price,
                    minimumStock, expiryDate
            );

            manager.addProduct(product);

        } else {
            System.out.println("Invalid product type.");
        }
    }

    private static void searchProduct() {

        int id = readInt("Enter Product ID to search: ");

        Product product = manager.searchProduct(id);

        if (product == null) {
            System.out.println("Product not found.");
        } else {
            System.out.println("\nProduct found:");
            product.displayDetails();
        }
    }

    private static void updateProduct() {

        int id = readInt("Enter Product ID to update: ");

        Product product = manager.searchProduct(id);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("Current product details:");
        product.displayDetails();

        String name = readText("Enter New Product Name: ");
        double price = readDouble("Enter New Price: ");
        int minimumStock = readInt("Enter New Minimum Stock Level: ");

        manager.updateProduct(id, name, price, minimumStock);
    }

    private static String readText(String message) {

        while (true) {
            System.out.print(message);
            String value = sc.nextLine().trim();

            if (!value.isEmpty()
                    && !value.contains("|")
                    && !value.contains("\n")
                    && !value.contains("\r")) {
                return value;
            }

            System.out.println("Enter a valid value without the | symbol.");
        }
    }

    private static int readInt(String message) {

        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(sc.nextLine().trim());

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a whole number.");
            }
        }
    }

    private static double readDouble(String message) {

        while (true) {
            try {
                System.out.print(message);
                double value = Double.parseDouble(sc.nextLine().trim());

                if (Double.isFinite(value)) {
                    return value;
                }

                System.out.println("Enter a valid finite number.");

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a valid amount.");
            }
        }
    }
}
