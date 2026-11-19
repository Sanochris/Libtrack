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
    private BookStatus status;
    private String borrowerName; // null when nobody has the book

    /** Constructor for a brand-new book: status AVAILABLE, no borrower. */
    protected Book(int id, String title, String author, String isbn, String genre, int yearPublished) {
        this(id, title, author, isbn, genre, yearPublished, BookStatus.AVAILABLE, null);
    }

    /** Full constructor, used when restoring a book from the saved file. */
    protected Book(int id, String title, String author, String isbn, String genre, int yearPublished,
                   BookStatus status, String borrowerName) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.yearPublished = yearPublished;
        this.status = status;
        this.borrowerName = borrowerName;
    }

    
    public abstract BookType getType();

   
    public abstract String getTypeSpecificValue();

   
    public abstract String getTypeSpecificLabel();

    // ---- Getters ----

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

    public BookStatus getStatus() {
        return status;
    }


    public String getBorrowerName() {
        return borrowerName;
    }

    // ---- Setters (only the fields that change after creation) ----

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public boolean isAvailable() {
        return status == BookStatus.AVAILABLE;
    }

    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + "', author='" + author + "', type=" + getType()
                + ", status=" + status + "}";
    }
}