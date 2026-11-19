package services;

import enums.BookStatus;
import models.Book;

public class BorrowService {

    private final BookService bookService;

    public BorrowService(BookService bookService) {
        if (bookService == null) {
            throw new IllegalArgumentException("BookService cannot be null.");
        }
        this.bookService = bookService;
    }

    public Book borrowBook(int bookId, String borrowerName) {
        String name = borrowerName == null ? "" : borrowerName.trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Borrower's name cannot be empty.");
        }

        Book book = bookService.findById(bookId);
        if (book == null) {
            throw new IllegalArgumentException("No book found with ID " + bookId + ".");
        }
        if (book.getStatus() == BookStatus.BORROWED) {
            throw new IllegalArgumentException("'" + book.getTitle() + "' is already borrowed.");
        }
        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new IllegalArgumentException("'" + book.getTitle() + "' is not available to borrow.");
        }

        book.setBorrowerName(name);
        book.setStatus(BookStatus.BORROWED);
        return book;
    }

    public Book returnBook(int bookId) {
        Book book = bookService.findById(bookId);
        if (book == null) {
            throw new IllegalArgumentException("No book found with ID " + bookId + ".");
        }
        if (book.getStatus() != BookStatus.BORROWED) {
            throw new IllegalArgumentException("'" + book.getTitle() + "' is not currently borrowed.");
        }

        book.setStatus(BookStatus.AVAILABLE);
        book.setBorrowerName(null);
        return book;
    }
}
