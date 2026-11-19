package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Library {

    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public int size() {
        return books.size();
    }

    public boolean isEmpty() {
        return books.isEmpty();
    }
}
