package controllers;

import utils.InputValidator;

import java.util.Scanner;

public class MenuController {

    private final BookController bookController;
    private final BorrowController borrowController;
    private final ReportController reportController;
    private final Scanner scanner;

    private boolean running = true;
    private boolean saveRequested = false;

    public MenuController(BookController bookController,
                          BorrowController borrowController,
                          ReportController reportController,
                          Scanner scanner) {
        this.bookController = bookController;
        this.borrowController = borrowController;
        this.reportController = reportController;
        this.scanner = scanner;
    }

    public void start() {
        while (running) {
            printMenu();
            String choice = InputValidator.readString(scanner, "Enter your choice: ");
            handleChoice(choice);
        }
    }

    public boolean isSaveRequested() {
        return saveRequested;
    }

    private void printMenu() {
        System.out.println();
        System.out.println("============================================================");
        System.out.println("                   LIBTRACK - Library Manager               ");
        System.out.println("============================================================");
        System.out.println("  1. Add Book");
        System.out.println("  2. View All Books");
        System.out.println("  3. Search Books");
        System.out.println("  4. Borrow Book");
        System.out.println("  5. Return Book");
        System.out.println("  6. View Report");
        System.out.println("  7. Save & Exit");
        System.out.println("============================================================");
    }

    private void handleChoice(String choice) {
        switch (choice) {
            case "1":
                bookController.addBook();
                break;
            case "2":
                bookController.viewAllBooks();
                break;
            case "3":
                bookController.searchBooks();
                break;
            case "4":
                borrowController.borrowBook();
                break;
            case "5":
                borrowController.returnBook();
                break;
            case "6":
                reportController.showReport();
                break;
            case "7":
                saveRequested = true;
                running = false;
                break;
            default:
                System.out.println("Invalid choice. Please enter a number from 1 to 7.");
        }
    }
}
