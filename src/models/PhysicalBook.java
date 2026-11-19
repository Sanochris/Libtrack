package models;

import enums.BookStatus;
import enums.BookType;


public class PhysicalBook extends Book {

    private final String shelfLocation; // e.g. A3, B12

   
    public PhysicalBook(int id, String title, String author, String isbn, String genre,
                        int yearPublished, String shelfLocation) {
        super(id, title, author, isbn, genre, yearPublished);
        this.shelfLocation = shelfLocation;
    }

    
    public PhysicalBook(int id, String title, String author, String isbn, String genre,
                        int yearPublished, String shelfLocation, BookStatus status, String borrowerName) {
        super(id, title, author, isbn, genre, yearPublished, status, borrowerName);
        this.shelfLocation = shelfLocation;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    @Override
    public BookType getType() {
        return BookType.PHYSICAL;
    }

    @Override
    public String getTypeSpecificValue() {
        return shelfLocation;
    }

    @Override
    public String getTypeSpecificLabel() {
        return "Shelf";
    }
}