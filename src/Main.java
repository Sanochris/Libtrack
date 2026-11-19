import controllers.BookController;
import models.Library;
import services.BookService;

import java.util.Scanner;

/**
 * TEMPORARY Main written by Person 1 to run and test Add / View / Search.
 * Person 3 will replace this with the real Main and MenuController.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Library library = new Library();
        BookService bookService = new BookService(library);
        BookController bookController = new BookController(bookService, scanner);

        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("==============================");
            System.out.println(" LIBTRACK v1.0 (Person 1 test)");
            System.out.println("==============================");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Books");
            System.out.println("0. Exit");
            System.out.println("==============================");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    bookController.addBook();
                    break;
                case "2":
                    bookController.viewAllBooks();
                    break;
                case "3":
                    bookController.searchBooks();
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}