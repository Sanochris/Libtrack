package controllers;

import models.Book;
import services.BorrowService;
import utils.InputValidator;

import java.util.Scanner;

public class BorrowController {

    private final BorrowService borrowService;
    private final Scanner scanner;

    public BorrowController(BorrowService borrowService, Scanner scanner) {
        if (borrowService == null || scanner == null) {
            throw new IllegalArgumentException("Borrow service and scanner are required.");
        }
        this.borrowService = borrowService;
        this.scanner = scanner;
    }

    public void borrowBook() {
        System.out.println("\n--- Borrow Book ---");
        try {
            int bookId = readBookId();
            String borrowerName = InputValidator.readString(scanner, "Borrower's name: ");
            Book book = borrowService.borrowBook(bookId, borrowerName);
            System.out.println("'" + book.getTitle() + "' borrowed successfully by " + book.getBorrowerName() + ".");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void returnBook() {
        System.out.println("\n--- Return Book ---");
        try {
            int bookId = readBookId();
            Book book = borrowService.returnBook(bookId);
            System.out.println("'" + book.getTitle() + "' returned successfully and is now available.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private int readBookId() {
        String input = InputValidator.readString(scanner, "Book ID: ");
        try {
            int id = Integer.parseInt(input);
            if (id <= 0) {
                throw new NumberFormatException();
            }
            return id;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Book ID must be a positive whole number.");
        }
    }
}
