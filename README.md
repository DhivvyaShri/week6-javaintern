# SmartStock – Inventory & Stock Alert Management System

## Project Description

SmartStock is a console-based Java application designed to
manage product inventory. It allows users to add, view, search,
update, and delete products while monitoring stock levels.

The application also provides low-stock alerts, inventory
valuation, stock movement history, and background data backup.

## Features

- Add new products
- View all products
- Search products using Product ID
- Update product details
- Delete products
- Add and remove stock
- Low-stock alerts
- Inventory summary
- Stock movement history
- Background data backup
- File-based data persistence
- Standard and perishable product support

## Technologies Used

- Java 17+
- JDK
- VS Code / IntelliJ IDEA
- Git
- GitHub

## Java Concepts Used

- Classes and Objects
- Constructors and this keyword
- Encapsulation
- Inheritance
- Polymorphism
- Abstraction
- Interfaces
- Exception Handling
- ArrayList
- HashSet
- HashMap
- File Handling
- Multithreading

## How to Run the Project

1. Install JDK 17 or later.
2. Open the project folder in VS Code or a terminal.
3. Compile the Java files:

   javac -d out src/*.java

4. Run the application:

   java -cp out SmartStockApp

## Data Storage

Product information is stored in:

data/products.txt

Stock movement history is stored in:

data/transactions.txt

Backup files are generated in:

backups/

## Sample Output

SMARTSTOCK MANAGEMENT SYSTEM

1. Add New Product
2. View All Products
3. Search Product
4. Update Product
5. Delete Product
6. Add Stock
7. Remove Stock
8. View Low Stock Alerts
9. View Inventory Summary
10. View Stock Movement History
11. Create Data Backup
12. Exit

## Future Improvements

- Add a graphical user interface.
- Connect the application to MySQL.
- Add user login and authentication.
- Generate inventory reports in PDF.
- Add automatic expiry-date alerts.
- Develop a web-based inventory dashboard.