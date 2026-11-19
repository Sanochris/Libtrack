package models;

import enums.BookStatus;
import enums.BookType;

public abstract class Book {

    private final int id;
    private final String title;
    private final String author;
    private final String isbn;
    private final String genre;
    private final int yearPublished;
    private final BookType type;
    private BookStatus status;
    private String borrowerName;

    protected Book(int id, String title, String author, String isbn, String genre,
                   int yearPublished, BookType type) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.yearPublished = yearPublished;
        this.type = type;
        this.status = BookStatus.AVAILABLE;
        this.borrowerName = null;
    }

    protected Book(int id, String title, String author, String isbn, String genre,
                   int yearPublished, BookType type, BookStatus status, String borrowerName) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.yearPublished = yearPublished;
        this.type = type;
        this.status = status;
        this.borrowerName = borrowerName;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getGenre() {
        return genre;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public BookType getType() {
        return type;
    }

    public BookStatus getStatus() {
        return status;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }
}
