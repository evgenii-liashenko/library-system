package main.command_line_ui;

import java.util.Scanner;

public class UserInput {
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
}
