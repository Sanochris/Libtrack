package services;

import enums.BookType;
import enums.SearchField;
import models.Book;
import models.DigitalBook;
import models.Library;
import models.PhysicalBook;

import java.util.ArrayList;
import java.util.List;

public class BookService {

    private static final String DEFAULT_GENRE = "Unknown";

    private final Library library;
    private int nextId = 1;

    public BookService(Library library) {
        this.library = library;
        syncNextId();
    }

    public Book addBook(String title, String author, String isbn, String genre,
                        String yearText, BookType type, String typeDetail) {

        title = clean(title);
        author = clean(author);
        isbn = clean(isbn);
        genre = clean(genre);
        typeDetail = clean(typeDetail);

        if (title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        if (author.isEmpty()) {
            throw new IllegalArgumentException("Author cannot be empty.");
        }
        if (isbn.isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be empty.");
        }
        if (isbnExists(isbn)) {
            throw new IllegalArgumentException("A book with ISBN '" + isbn + "' already exists.");
        }

        int year = parseYear(yearText);

        if (type == null) {
            throw new IllegalArgumentException("Type must be Physical or Digital.");
        }
        if (genre.isEmpty()) {
            genre = DEFAULT_GENRE;
        }

        int id = nextId++;

        Book book;
        if (type == BookType.PHYSICAL) {
            if (typeDetail.isEmpty()) {
                throw new IllegalArgumentException("Shelf location cannot be empty.");
            }
            book = new PhysicalBook(id, title, author, isbn, genre, year, typeDetail.toUpperCase());
        } else {
            if (typeDetail.isEmpty()) {
                throw new IllegalArgumentException("File format cannot be empty.");
            }
            book = new DigitalBook(id, title, author, isbn, genre, year, typeDetail.toUpperCase());
        }

        library.addBook(book);
        return book;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(library.getBooks());
    }

    public List<Book> searchBooks(SearchField field, String keyword) {
        String needle = clean(keyword).toLowerCase();
        List<Book> matches = new ArrayList<>();
        for (Book book : library.getBooks()) {
            if (needle.isEmpty() || getField(book, field).toLowerCase().contains(needle)) {
                matches.add(book);
            }
        }
        return matches;
    }

    public Book findById(int id) {
        for (Book book : library.getBooks()) {
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
        for (Book book : library.getBooks()) {
            if (book.getIsbn().equalsIgnoreCase(wanted)) {
                return true;
            }
        }
        return false;
    }

    private void syncNextId() {
        for (Book book : library.getBooks()) {
            if (book.getId() >= nextId) {
                nextId = book.getId() + 1;
            }
        }
    }

    private String getField(Book book, SearchField field) {
        switch (field) {
            case TITLE:  return book.getTitle();
            case AUTHOR: return book.getAuthor();
            case GENRE:  return book.getGenre();
            default:     return "";
        }
    }

    private int parseYear(String yearText) {
        String text = clean(yearText);
        int year;
        try {
            year = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Year Published must be a valid number.");
        }
        if (year <= 0) {
            throw new IllegalArgumentException("Year Published must be a positive number.");
        }
        return year;
    }

    private String clean(String text) {
        return text == null ? "" : text.trim();
    }
}
