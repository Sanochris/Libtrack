package services;

import enums.BookStatus;
import enums.BookType;
import models.Book;
import models.Library;
import models.LibraryReport;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class ReportService {

    private final Library library;

    public ReportService(Library library) {
        if (library == null) {
            throw new IllegalArgumentException("Library cannot be null.");
        }
        this.library = library;
    }

    public LibraryReport generateReport() {
        int physicalBooks = 0;
        int digitalBooks = 0;
        Map<BookStatus, Integer> statusCounts = new EnumMap<>(BookStatus.class);
        for (BookStatus status : BookStatus.values()) {
            statusCounts.put(status, 0);
        }
        Map<String, Integer> borrowedGenres = new HashMap<>();

        for (Book book : library.getBooks()) {
            if (book.getType() == BookType.PHYSICAL) {
                physicalBooks++;
            } else {
                digitalBooks++;
            }

            statusCounts.put(book.getStatus(), statusCounts.get(book.getStatus()) + 1);

            if (book.getStatus() == BookStatus.BORROWED) {
                String genre = book.getGenre() == null || book.getGenre().trim().isEmpty()
                        ? "Unknown" : book.getGenre().trim();
                borrowedGenres.put(genre, borrowedGenres.getOrDefault(genre, 0) + 1);
            }
        }

        String mostBorrowedGenre = null;
        int mostBorrowedGenreCount = 0;
        for (Map.Entry<String, Integer> entry : borrowedGenres.entrySet()) {
            if (entry.getValue() > mostBorrowedGenreCount) {
                mostBorrowedGenre = entry.getKey();
                mostBorrowedGenreCount = entry.getValue();
            }
        }

        return new LibraryReport(library.size(), physicalBooks, digitalBooks,
                statusCounts, mostBorrowedGenre, mostBorrowedGenreCount);
    }
}
