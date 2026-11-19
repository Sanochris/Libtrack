package controllers;

import enums.BookType;
import enums.SearchField;
import models.Book;
import models.DigitalBook;
import models.PhysicalBook;
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

    public void addBook() {
        System.out.println("\n--- Add Book ---");

        String title = readRequiredField(scanner, "Title: ", "Title cannot be empty.");

        String author = readRequiredField(scanner, "Author: ", "Author cannot be empty.");

        String isbn;
        while (true) {
            isbn = readRequiredField(scanner, "ISBN: ", "ISBN cannot be empty.");
            if (!bookService.isbnExists(isbn)) {
                break;
            }
            System.out.println("Error: A book with ISBN '" + isbn + "' already exists. Please enter a different ISBN.");
        }

        String genre = InputValidator.readString(scanner, "Genre (e.g. Fiction, Science, History): ");

        String yearInput;
        while (true) {
            yearInput = InputValidator.readString(scanner, "Year Published: ");
            if (isValidYear(yearInput)) {
                break;
            }
            System.out.println("Error: Year Published must be a valid number. Please try again.");
        }

        BookType type = readBookType();

        String detail;
        if (type == BookType.PHYSICAL) {
            detail = InputValidator.readString(scanner, "Shelf location (e.g. A3, B12): ");
        } else {
            detail = InputValidator.readString(scanner, "File format (e.g. PDF, EPUB, MOBI): ");
        }

        try {
            Book book = bookService.addBook(title, author, isbn, genre, yearInput, type, detail);
            System.out.println("Book added successfully! ID: " + book.getId() + " (" + book.getTitle() + ")");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage() + " The book was not added.");
        }
    }

    private String readRequiredField(Scanner scanner, String prompt, String errorMessage) {
        while (true) {
            String value = InputValidator.readString(scanner, prompt);
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Error: " + errorMessage);
        }
    }

    private boolean isValidYear(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        try {
            int year = Integer.parseInt(input.trim());
            return year > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private BookType readBookType() {
        while (true) {
            String input = InputValidator.readString(scanner, "Type (Physical/Digital): ");
            for (BookType type : BookType.values()) {
                if (type.name().equalsIgnoreCase(input)) {
                    return type;
                }
            }
            System.out.println("Invalid type. Please enter Physical or Digital.");
        }
    }

    public void viewAllBooks() {
        System.out.println("\n--- All Books ---");
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("The library is empty. Add a book first.");
            return;
        }
        printTable(books);
    }

    public void searchBooks() {
        System.out.println("\n--- Search Books ---");
        System.out.println("Search by:");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Genre");

        String choice = InputValidator.readString(scanner, "Enter your choice: ");
        SearchField field;
        switch (choice) {
            case "1": field = SearchField.TITLE;  break;
            case "2": field = SearchField.AUTHOR; break;
            case "3": field = SearchField.GENRE;  break;
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

    private void printTable(List<Book> books) {
        String line = "-".repeat(101);
        System.out.println(line);
        System.out.printf(ROW_FORMAT, "ID", "Title", "Author", "Genre", "Type", "Status", "Shelf/Format");
        System.out.println(line);
        for (Book book : books) {
            String typeSpecific;
            if (book instanceof PhysicalBook) {
                typeSpecific = ((PhysicalBook) book).getShelfLocation();
            } else {
                typeSpecific = ((DigitalBook) book).getFileFormat();
            }
            System.out.printf(ROW_FORMAT,
                    book.getId(),
                    fit(book.getTitle(), 24),
                    fit(book.getAuthor(), 20),
                    fit(book.getGenre(), 14),
                    book.getType().name(),
                    book.getStatus().name(),
                    fit(typeSpecific, 14));
        }
        System.out.println(line);
    }

    private String fit(String text, int width) {
        if (text.length() <= width) {
            return text;
        }
        return text.substring(0, width - 3) + "...";
    }
}
