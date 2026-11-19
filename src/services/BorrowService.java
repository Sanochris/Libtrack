package services;

import enums.BookStatus;
import models.Book;
import models.Library;

public class BorrowService {

    private final Library library;

    public BorrowService(Library library) {
        if (library == null) {
            throw new IllegalArgumentException("Library cannot be null.");
        }
        this.library = library;
    }

    public Book borrowBook(int bookId, String borrowerName) {
        String name = borrowerName == null ? "" : borrowerName.trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Borrower's name cannot be empty.");
        }

        Book book = library.findById(bookId);
        if (book == null) {
            throw new IllegalArgumentException("No book found with ID " + bookId + ".");
        }
        if (!book.isAvailable()) {
            if (book.getStatus() == BookStatus.BORROWED) {
                throw new IllegalArgumentException("'" + book.getTitle() + "' is already borrowed.");
            }
            throw new IllegalArgumentException("'" + book.getTitle() + "' is not available to borrow.");
        }

        book.setBorrowerName(name);
        book.setStatus(BookStatus.BORROWED);
        return book;
    }

    public Book returnBook(int bookId) {
        Book book = library.findById(bookId);
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
