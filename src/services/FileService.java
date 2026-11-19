package services;

import enums.BookStatus;
import enums.BookType;
import models.Book;
import models.DigitalBook;
import models.Library;
import models.PhysicalBook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileService {

    private static final String FILE_NAME = "library_data.txt";
    private static final String DELIMITER = "|";
    private static final String DELIMITER_REGEX = "\\|";

    private static String resolveFilePath() {
        String classLocation = FileService.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath();

        File classDir = new File(classLocation);
        File projectRoot = classDir.getParentFile().getParentFile().getParentFile();
        return new File(projectRoot, FILE_NAME).getAbsolutePath();
    }

    public void saveBooks(Library library) {
        String filePath = resolveFilePath();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Book book : library.getBooks()) {
                writer.write(formatBook(book));
                writer.newLine();
            }
            System.out.println("Library saved to: " + filePath);
            System.out.println("Saved " + library.size() + " book(s) successfully.");
        } catch (IOException e) {
            System.out.println("Warning: Could not save library data. " + e.getMessage());
        }
    }

    public void loadBooks(Library library) {
        String filePath = resolveFilePath();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int loaded = 0;
            int skipped = 0;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                Book book = parseLine(line);
                if (book != null) {
                    library.addBook(book);
                    loaded++;
                } else {
                    skipped++;
                }
            }

            System.out.println("Loaded " + loaded + " book(s) from: " + filePath);
            if (skipped > 0) {
                System.out.println("Warning: " + skipped + " record(s) could not be read and were skipped.");
            }

        } catch (FileNotFoundException e) {
            System.out.println("No saved data found. Starting with an empty library.");
        } catch (IOException e) {
            System.out.println("Warning: Could not read saved data. Starting with an empty library.");
        }
    }

    private String formatBook(Book book) {
        String typeSpecific;
        if (book instanceof PhysicalBook) {
            typeSpecific = ((PhysicalBook) book).getShelfLocation();
        } else {
            typeSpecific = ((DigitalBook) book).getFileFormat();
        }

        String borrower = book.getBorrowerName() == null ? "" : book.getBorrowerName();

        return String.join(DELIMITER,
                String.valueOf(book.getId()),
                book.getType().name(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getGenre(),
                String.valueOf(book.getYearPublished()),
                typeSpecific,
                book.getStatus().name(),
                borrower
        );
    }

    private Book parseLine(String line) {
        String[] parts = line.split(DELIMITER_REGEX, -1);
        if (parts.length != 10) {
            return null;
        }

        try {
            int id            = Integer.parseInt(parts[0].trim());
            BookType type     = BookType.valueOf(parts[1].trim().toUpperCase());
            String title      = parts[2].trim();
            String author     = parts[3].trim();
            String isbn       = parts[4].trim();
            String genre      = parts[5].trim();
            int year          = Integer.parseInt(parts[6].trim());
            String specific   = parts[7].trim();
            BookStatus status = BookStatus.valueOf(parts[8].trim().toUpperCase());
            String borrower   = parts[9].trim();
            String borrowerName = borrower.isEmpty() ? null : borrower;

            if (title.isEmpty() || isbn.isEmpty()) {
                return null;
            }

            if (type == BookType.PHYSICAL) {
                return new PhysicalBook(id, title, author, isbn, genre, year, specific, status, borrowerName);
            } else {
                return new DigitalBook(id, title, author, isbn, genre, year, specific, status, borrowerName);
            }

        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
