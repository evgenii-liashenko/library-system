package main.command_line_ui;
import java.util.Scanner;
import static main.command_line_ui.Menu.*;

public class Main {

    public static void main(String[] args) {
        printMenu();
        System.out.println("\nEnter two digit operation code from the menu above:");
        Scanner menuScanner = new Scanner(System.in);

        while (menuScanner.hasNextInt()) {
            int operationCode = menuScanner.nextInt();
            //System.out.println();

            switch (operationCode) {
                case 0:
                    printMenu();
                    break;
                case 41:
                    about();
                    break;
                default:
                    System.out.println("The feature is under development or incorrect operation code.");
                    break;
            }

            System.out.println("\nEnter operation code. To display the menu again, enter 0:");
        }

        menuScanner.close();
    }
}
