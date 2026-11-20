package models;

import enums.BookStatus;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class LibraryReport {

    private final int totalBooks;
    private final int physicalBooks;
    private final int digitalBooks;
    private final Map<BookStatus, Integer> statusCounts;
    private final String mostBorrowedGenre;
    private final int mostBorrowedGenreCount;

    public LibraryReport(int totalBooks, int physicalBooks, int digitalBooks,
                         Map<BookStatus, Integer> statusCounts,
                         String mostBorrowedGenre, int mostBorrowedGenreCount) {
        this.totalBooks = totalBooks;
        this.physicalBooks = physicalBooks;
        this.digitalBooks = digitalBooks;
        this.statusCounts = statusCounts != null
                ? Collections.unmodifiableMap(new EnumMap<>(statusCounts))
                : Collections.emptyMap();
        this.mostBorrowedGenre = mostBorrowedGenre;
        this.mostBorrowedGenreCount = mostBorrowedGenreCount;
    }

    public int getTotalBooks() {
        return totalBooks;
    }

    public int getPhysicalBooks() {
        return physicalBooks;
    }

    public int getDigitalBooks() {
        return digitalBooks;
    }

    public int getStatusCount(BookStatus status) {
        return statusCounts.getOrDefault(status, 0);
    }

    public Map<BookStatus, Integer> getStatusCounts() {
        return statusCounts;
    }

    public String getMostBorrowedGenre() {
        return mostBorrowedGenre;
    }

    public int getMostBorrowedGenreCount() {
        return mostBorrowedGenreCount;
    }
}
