package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Holds the collection of books and hands out unique IDs.
 * It only stores and looks up books; business rules live in the services.
 */
public class Library {

    private final List<Book> books = new ArrayList<>();
    private int nextId = 1;

    /** @return the next unused book ID (does not consume it until a book is added). */
    public int generateNextId() {
        return nextId;
    }

    /**
     * Adds a book. If its ID is >= the next ID (e.g. a book loaded from the file),
     * the counter moves past it, so new books never reuse an existing ID.
     */
    public void addBook(Book book) {
        books.add(book);
        if (book.getId() >= nextId) {
            nextId = book.getId() + 1;
        }
    }

    /** @return a read-only view of all books (use addBook to add). */
    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    /** @return the book with this ID, or null if none exists. */
    public Book findById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    
    public boolean isbnExists(String isbn) {
        if (isbn == null) {
            return false;
        }
        String wanted = isbn.trim();
        for (Book book : books) {
            if (book.getIsbn().equalsIgnoreCase(wanted)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return books.size();
    }

    public boolean isEmpty() {
        return books.isEmpty();
    }
}