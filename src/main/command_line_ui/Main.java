package main.command_line_ui;

public class Main {

    public static void main(String[] args) {
        System.out.println("\nEnter two digit operation code from the menu below:");
        System.out.println(
                "1. Books >\n" +
                    "\t1. List all books\n" + "\t2. Add a new book\n" + "\t3. Get book description\n" +
                        "\t4. Edit an existing book\n" + "\t5. Remove a book from the library\n" +
                "2. Readers >\n" +
                    "\t1. List all readers\n" + "\t2. Add a new reader\n" + "\t3. Get reader info\n" +
                        "\t4. Edit an existing reader\n" + "\t5. Remove a reader from the library\n" +
                "3. Orders >\n" +
                    "\t1. List all orders\n" + "\t2. Place a new order\n" + "\t3. Get order info\n" +
                        "\t4. Edit an existing order\n" + "\t5. Return an order\n" + "\t6. List overdue orders\n" +
                "4. Other >\n" +
                    "\t1. About Library System");
    }
}
