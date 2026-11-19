package controllers;

import enums.BookStatus;
import models.Book;
import services.BookService;
import services.BorrowService;
import utils.InputValidator;

import java.util.Scanner;

public class BorrowController {

    private final BorrowService borrowService;
    private final BookService bookService;
    private final Scanner scanner;

    public BorrowController(BorrowService borrowService, BookService bookService, Scanner scanner) {
        this.borrowService = borrowService;
        this.bookService = bookService;
        this.scanner = scanner;
    }

    public void borrowBook() {
        System.out.println("\n--- Borrow Book ---");

        int bookId = readBookId();

        Book book = bookService.findById(bookId);
        if (book == null) {
            System.out.println("Error: No book found with ID " + bookId + ".");
            return;
        }
        if (book.getStatus() != BookStatus.AVAILABLE) {
            System.out.print("Error: '" + book.getTitle() + "' cannot be borrowed. Status: " + book.getStatus().name());
            if (book.getStatus() == BookStatus.BORROWED && book.getBorrowerName() != null) {
                System.out.print(", currently borrowed by: " + book.getBorrowerName());
            }
            System.out.println(".");
            return;
        }

        String borrowerName;
        while (true) {
            borrowerName = InputValidator.readString(scanner, "Borrower's name: ");
            if (!borrowerName.isEmpty()) {
                break;
            }
            System.out.println("Error: Borrower's name cannot be empty.");
        }

        Book borrowed = borrowService.borrowBook(bookId, borrowerName);
        System.out.println("'" + borrowed.getTitle() + "' borrowed successfully by " + borrowed.getBorrowerName() + ".");
    }

    public void returnBook() {
        System.out.println("\n--- Return Book ---");

        int bookId = readBookId();

        Book book = bookService.findById(bookId);
        if (book == null) {
            System.out.println("Error: No book found with ID " + bookId + ".");
            return;
        }
        if (book.getStatus() != BookStatus.BORROWED) {
            System.out.println("Error: '" + book.getTitle() + "' cannot be returned. Current status: " + book.getStatus().name() + ".");
            return;
        }

        Book returned = borrowService.returnBook(bookId);
        System.out.println("'" + returned.getTitle() + "' returned successfully and is now available.");
    }

    private int readBookId() {
        while (true) {
            String input = InputValidator.readString(scanner, "Book ID: ");
            try {
                int id = Integer.parseInt(input);
                if (id > 0) {
                    return id;
                }
                System.out.println("Error: Book ID must be a positive whole number.");
            } catch (NumberFormatException e) {
                System.out.println("Error: Book ID must be a positive whole number.");
            }
        }
    }
}
