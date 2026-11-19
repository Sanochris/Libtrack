package utils;

import java.util.Scanner;

/**
 * PLACEHOLDER written by Person 1 so BookController compiles and can be tested.
 * Person 3 owns this class: replace it with the full version, but keep
 * the method below (same name and signature) so BookController keeps working.
 */
public class InputValidator {

    /** Prints the prompt and returns what the user typed, with surrounding spaces removed. */
    public static String readString(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
