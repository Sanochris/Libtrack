package models;

import enums.BookStatus;
import enums.BookType;


public class DigitalBook extends Book {

    private final String fileFormat; // e.g. PDF, EPUB, MOBI

   
    public DigitalBook(int id, String title, String author, String isbn, String genre,
                       int yearPublished, String fileFormat) {
        super(id, title, author, isbn, genre, yearPublished);
        this.fileFormat = fileFormat;
    }

    public DigitalBook(int id, String title, String author, String isbn, String genre,
                       int yearPublished, String fileFormat, BookStatus status, String borrowerName) {
        super(id, title, author, isbn, genre, yearPublished, status, borrowerName);
        this.fileFormat = fileFormat;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    @Override
    public BookType getType() {
        return BookType.DIGITAL;
    }

    @Override
    public String getTypeSpecificValue() {
        return fileFormat;
    }

    @Override
    public String getTypeSpecificLabel() {
        return "Format";
    }
}