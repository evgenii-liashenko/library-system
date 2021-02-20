package main.command_line_ui;

class Menu {
    public static void printMenu(){
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
                        "\t4. Edit an existing order\n" + "\t5. Return an order\n" + "\t6. List overdue orders\n" +
                        "4. Other >\n" +
                        "\t1. About Library System");
    }
    public static void about(){
        System.out.println("Library System\nVersion 0.1");
        System.out.println("Lorem ipsum dolor sit amet, consectetur adipiscing elit. \n" +
                "Nam molestie augue nibh, ut blandit quam egestas eu. " +
                "\nUt non mauris non mi eleifend pellentesque. " );
    }
}
