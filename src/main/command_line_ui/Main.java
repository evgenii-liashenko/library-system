package main.command_line_ui;

import java.util.Scanner;

import static main.command_line_ui.DatabaseSetupScript.setUpDatabase;
import static main.command_line_ui.Menu.*;
import static main.services.BookService.*;
import static main.services.BookOrderService.*;
import static main.services.ReaderService.*;

public class Main {

    public static void main(String[] args)  {
        printMenu();
        System.out.println("\nEnter two digit operation code from the menu above");
        System.out.println("(e.g. 23 for Readers > Get reader information):");
        Scanner menuScanner = new Scanner(System.in);

        while (menuScanner.hasNextInt()) {
            int operationCode = menuScanner.nextInt();
            switch (operationCode) {
                case 0:
                    printMenu();
                    break;
                case 11:
                    listAllBooksUiExchange();
                    break;
                case 12:
                    addBookUiExchange();
                    break;
                case 13:
                    getBookByIdUiExchange();
                    break;
                case 14:
                    editBookUiExchange();
                    break;
                case 15:
                    removeBookUiExchange();
                    break;
                case 21:
                    listAllReadersUiExchange();
                    break;
                case 22:
                    addReaderUiExchange();
                    break;
                case 23:
                    getReaderByIdUiExchange();
                    break;
                case 24:
                    editReaderUiExchange();
                    break;
                case 25:
                    removeReaderUiExchange();
                    break;
                case 31:
                    listAllOrdersUiExchange();
                    break;
                case 32:
                    addOrderUiExchange();
                    break;
                case 41:
                    setUpDatabase();
                    break;
                case 42:
                    about();
                    break;
                default:
                    System.out.println("The feature is under development or incorrect operation code");
                    break;
            }
            System.out.println("\nEnter operation code. To display the menu again, enter 0:");
        }

        menuScanner.close();
    }
}
