import controllers.BookController;
import controllers.BorrowController;
import controllers.MenuController;
import controllers.ReportController;
import models.Library;
import services.BookService;
import services.BorrowService;
import services.FileService;
import services.ReportService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Library library = new Library();
        FileService fileService = new FileService();

        System.out.println("============================================================");
        System.out.println("             Welcome to LIBTRACK - Library Manager          ");
        System.out.println("============================================================");

        fileService.loadBooks(library);

        BookService bookService = new BookService(library);
        BorrowService borrowService = new BorrowService(bookService);
        ReportService reportService = new ReportService(library);

        BookController bookController = new BookController(bookService, scanner);
        BorrowController borrowController = new BorrowController(borrowService, bookService, scanner);
        ReportController reportController = new ReportController(reportService);

        MenuController menuController = new MenuController(
                bookController, borrowController, reportController, scanner
        );

        menuController.start();

        if (menuController.isSaveRequested()) {
            fileService.saveBooks(library);
        }

        System.out.println();
        System.out.println("Thank you for using LIBTRACK. Goodbye!");
        scanner.close();
    }
}
