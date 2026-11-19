package controllers;

import enums.BookStatus;
import services.ReportService;

public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        if (reportService == null) {
            throw new IllegalArgumentException("Report service is required.");
        }
        this.reportService = reportService;
    }

    public void showReport() {
        ReportService.LibraryReport report = reportService.generateReport();
        System.out.println("\n--- Library Report ---");
        System.out.println("Total books: " + report.getTotalBooks());
        System.out.println("Physical books: " + report.getPhysicalBooks());
        System.out.println("Digital books: " + report.getDigitalBooks());
        System.out.println("\nBook statuses:");
        for (BookStatus status : BookStatus.values()) {
            System.out.println("  " + formatStatus(status) + ": " + report.getStatusCount(status));
        }

        System.out.println();
        if (report.getMostBorrowedGenre() == null) {
            System.out.println("Most borrowed genre: None (no books are currently borrowed)");
        } else {
            System.out.println("Most borrowed genre (currently borrowed books): "
                    + report.getMostBorrowedGenre() + " (" + report.getMostBorrowedGenreCount() + ")");
        }
    }

    public void displayReport() {
        showReport();
    }

    private String formatStatus(BookStatus status) {
        String name = status.name().toLowerCase();
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}
