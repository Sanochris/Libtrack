package services;

import enums.BookStatus;
import enums.BookType;
import models.Book;
import models.Library;

import java.util.Collections;
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
            } else if (book.getType() == BookType.DIGITAL) {
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
            if (entry.getValue() > mostBorrowedGenreCount
                    || (entry.getValue() == mostBorrowedGenreCount
                    && (mostBorrowedGenre == null || entry.getKey().compareToIgnoreCase(mostBorrowedGenre) < 0))) {
                mostBorrowedGenre = entry.getKey();
                mostBorrowedGenreCount = entry.getValue();
            }
        }

        return new LibraryReport(library.size(), physicalBooks, digitalBooks, statusCounts,
                mostBorrowedGenre, mostBorrowedGenreCount);
    }

    public static final class LibraryReport {
        private final int totalBooks;
        private final int physicalBooks;
        private final int digitalBooks;
        private final Map<BookStatus, Integer> statusCounts;
        private final String mostBorrowedGenre;
        private final int mostBorrowedGenreCount;

        private LibraryReport(int totalBooks, int physicalBooks, int digitalBooks,
                              Map<BookStatus, Integer> statusCounts,
                              String mostBorrowedGenre, int mostBorrowedGenreCount) {
            this.totalBooks = totalBooks;
            this.physicalBooks = physicalBooks;
            this.digitalBooks = digitalBooks;
            this.statusCounts = Collections.unmodifiableMap(new EnumMap<>(statusCounts));
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

        public Map<BookStatus, Integer> getStatusCounts() {
            return statusCounts;
        }

        public int getStatusCount(BookStatus status) {
            return statusCounts.getOrDefault(status, 0);
        }

        public String getMostBorrowedGenre() {
            return mostBorrowedGenre;
        }

        public int getMostBorrowedGenreCount() {
            return mostBorrowedGenreCount;
        }
    }
}
