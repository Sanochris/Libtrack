# LibTrack — Book Library Management System

LibTrack is a console-based library application for managing physical and digital books. It supports adding, listing and searching books, borrowing and returning books, and viewing a library report. The application uses one shared `Library` instance so changes made by one service are visible to the others.

## Features

- Add physical and digital books with unique IDs.
- List all books and search by title, author or genre.
- Borrow a book by ID after validating the ID, availability and borrower's name.
- Return a borrowed book, restoring its availability and clearing the borrower name.
- Report total, physical and digital books, counts for every book status, and the most common genre among currently borrowed books.

## Project structure

```text
src/
├── Main.java
├── enums/        BookType, BookStatus, SearchField
├── models/       Book, PhysicalBook, DigitalBook, Library
├── services/     BookService, BorrowService, ReportService, FileService
├── controllers/  BookController, BorrowController, ReportController, MenuController
└── utils/        InputValidator
```

## Compile and run

From this folder, compile all Java source files and launch the application:

```sh
javac -d out src/*/*.java src/Main.java
java -cp out Main
```

The `Main` class should create one `Library` and pass it to each service. Borrowing and returning update each book's status and borrower name in that shared library. A report's most borrowed genre is calculated from books whose status is currently `BORROWED`; if there are no borrowed books, the report displays `None`.
