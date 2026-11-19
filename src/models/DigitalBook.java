package models;

import enums.BookStatus;
import enums.BookType;

public class DigitalBook extends Book {

    private final String fileFormat;

    public DigitalBook(int id, String title, String author, String isbn, String genre,
                       int yearPublished, String fileFormat) {
        super(id, title, author, isbn, genre, yearPublished, BookType.DIGITAL);
        this.fileFormat = fileFormat;
    }

    public DigitalBook(int id, String title, String author, String isbn, String genre,
                       int yearPublished, String fileFormat, BookStatus status, String borrowerName) {
        super(id, title, author, isbn, genre, yearPublished, BookType.DIGITAL, status, borrowerName);
        this.fileFormat = fileFormat;
    }

    public String getFileFormat() {
        return fileFormat;
    }
}
