package utils;

import java.util.Scanner;

public class InputValidator {

    public static String readString(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static String readNonEmpty(Scanner scanner, String prompt) {
        while (true) {
            String value = readString(scanner, prompt);
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty. Please try again.");
        }
    }

    public static int readPositiveInt(Scanner scanner, String prompt) {
        while (true) {
            String input = readString(scanner, prompt);
            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
                System.out.println("Please enter a positive whole number.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }
}
