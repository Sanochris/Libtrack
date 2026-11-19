package models;

import enums.BookStatus;
import enums.BookType;

public class PhysicalBook extends Book {

    private final String shelfLocation;

    public PhysicalBook(int id, String title, String author, String isbn, String genre,
                        int yearPublished, String shelfLocation) {
        super(id, title, author, isbn, genre, yearPublished, BookType.PHYSICAL);
        this.shelfLocation = shelfLocation;
    }

    public PhysicalBook(int id, String title, String author, String isbn, String genre,
                        int yearPublished, String shelfLocation, BookStatus status, String borrowerName) {
        super(id, title, author, isbn, genre, yearPublished, BookType.PHYSICAL, status, borrowerName);
        this.shelfLocation = shelfLocation;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }
}
