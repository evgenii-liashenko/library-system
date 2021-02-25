package main.command_line_ui;

import java.time.LocalDate;
import java.util.Scanner;

public class Menu {
    public static void printMenu() {
        System.out.println(
                        "\nLibrary System:\n" +
                        "1. Books >\n" +
                        "\t1. List all books\n" + "\t2. Add a new book\n" + "\t3. Get book description\n" +
                        "\t4. Edit an existing book\n" + "\t5. Remove a book from the library\n" +
                        "2. Readers >\n" +
                        "\t1. List all readers\n" + "\t2. Add a new reader\n" + "\t3. Get reader information\n" +
                        "\t4. Edit an existing reader\n" + "\t5. Remove a reader from the library\n" +
                        "3. Orders >\n" +
                        "\t1. List all orders\n" + "\t2. Place a new order\n" + "\t3. Get order details\n" +
                        "\t4. Edit an existing order\n" + "\t5. Return an order\n" +
                                "\t6. Delete all returned orders from order history\n" + "\t7. List overdue orders\n" +
                        "4. Other >\n" +
                        "\t1. Run database setup script\n" +
                        "\t2. About Library System");
    }

    public static void about() {
        System.out.println("Library System\nVersion 0.1\nDeveloped by an EPAM student Evgenii Liashenko");
    }

    public static int inputNumberFromUser(String message) {
        System.out.println(message);
        int id = Integer.MIN_VALUE;
        Scanner idScanner = new Scanner(System.in);
        while (id == Integer.MIN_VALUE) {
            id = idScanner.nextInt();
        }
        return id;
    }

    public static String inputTextFromUser(String message) {
        System.out.println(message);
        String name = null;
        Scanner nameScanner = new Scanner(System.in);
        while (name == null) {
            name = nameScanner.nextLine();
        }
        return name;
    }

    public static LocalDate inputDateFromUser(String message) {
        System.out.println(message);
        String dateString = null;
        Scanner nameScanner = new Scanner(System.in);
        while (dateString == null) {
            dateString = nameScanner.nextLine();
        }
        LocalDate theDate = LocalDate.parse(dateString);
        return theDate;
    }

}
