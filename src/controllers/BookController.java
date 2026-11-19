package controllers;

import enums.BookType;
import enums.SearchField;
import models.Book;
import services.BookService;
import utils.InputValidator;

import java.util.List;
import java.util.Scanner;


public class BookController {

    private static final String ROW_FORMAT = "%-4s %-24s %-20s %-14s %-9s %-10s %-14s%n";

    private final BookService bookService;
    private final Scanner scanner;

    public BookController(BookService bookService, Scanner scanner) {
        this.bookService = bookService;
        this.scanner = scanner;
    }

    // ---------- Menu option 1 ----------

    public void addBook() {
        System.out.println("\n--- Add Book ---");

        String title = InputValidator.readString(scanner, "Title: ");
        String author = InputValidator.readString(scanner, "Author: ");
        String isbn = InputValidator.readString(scanner, "ISBN: ");
        String genre = InputValidator.readString(scanner, "Genre (e.g. Fiction, Science, History): ");
        String year = InputValidator.readString(scanner, "Year Published: ");
        BookType type = readBookType();

        String detail;
        if (type == BookType.PHYSICAL) {
            detail = InputValidator.readString(scanner, "Shelf location (e.g. A3, B12): ");
        } else {
            detail = InputValidator.readString(scanner, "File format (e.g. PDF, EPUB, MOBI): ");
        }

        try {
            Book book = bookService.addBook(title, author, isbn, genre, year, type, detail);
            System.out.println("Book added successfully! ID: " + book.getId() + " (" + book.getTitle() + ")");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage() + " The book was not added.");
        }
    }

    /** Re-prompts until the user types Physical or Digital. */
    private BookType readBookType() {
        while (true) {
            String input = InputValidator.readString(scanner, "Type (Physical/Digital): ");
            BookType type = BookType.fromString(input);
            if (type != null) {
                return type;
            }
            System.out.println("Invalid type. Please enter Physical or Digital.");
        }
    }

    // ---------- Menu option 2 ----------

    public void viewAllBooks() {
        System.out.println("\n--- All Books ---");
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("The library is empty. Add a book first.");
            return;
        }
        printTable(books);
    }

    // ---------- Menu option 3 ----------

    public void searchBooks() {
        System.out.println("\n--- Search Books ---");
        System.out.println("Search by:");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Genre");

        String choice = InputValidator.readString(scanner, "Enter your choice: ");
        SearchField field;
        switch (choice) {
            case "1":
                field = SearchField.TITLE;
                break;
            case "2":
                field = SearchField.AUTHOR;
                break;
            case "3":
                field = SearchField.GENRE;
                break;
            default:
                System.out.println("Invalid choice. Please enter 1, 2 or 3.");
                return;
        }

        String keyword = InputValidator.readString(scanner, "Enter keyword (leave empty for all books): ");
        List<Book> results = bookService.searchBooks(field, keyword);

        if (results.isEmpty()) {
            System.out.println("No books found matching '" + keyword + "'.");
            return;
        }
        System.out.println(results.size() + " book(s) found:");
        printTable(results);
    }

    // ---------- Table output ----------

    private void printTable(List<Book> books) {
        String line = "-".repeat(101);
        System.out.println(line);
        System.out.printf(ROW_FORMAT, "ID", "Title", "Author", "Genre", "Type", "Status", "Shelf/Format");
        System.out.println(line);
        for (Book book : books) {
            System.out.printf(ROW_FORMAT,
                    book.getId(),
                    fit(book.getTitle(), 24),
                    fit(book.getAuthor(), 20),
                    fit(book.getGenre(), 14),
                    book.getType(),
                    book.getStatus(),
                    fit(book.getTypeSpecificValue(), 14));
        }
        System.out.println(line);
    }

    /** Shortens text with "..." so long values do not break the table columns. */
    private String fit(String text, int width) {
        if (text.length() <= width) {
            return text;
        }
        return text.substring(0, width - 3) + "...";
    }
}