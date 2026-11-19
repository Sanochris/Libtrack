# LibTrack — Book Library Management System

Group project (3 members). This folder contains Person 1's part:
enums, models, BookService and BookController.

## Structure

```
src/
├── Main.java                  (Person 3 - not included yet)
├── enums/       BookType, BookStatus, SearchField
├── models/      Book (abstract), PhysicalBook, DigitalBook, Library
├── services/    BookService  (Person 2: BorrowService, ReportService; Person 3: FileService)
├── controllers/ BookController (Person 2: BorrowController, ReportController; Person 3: MenuController)
└── utils/       InputValidator (placeholder - Person 3 replaces it, keep readString(Scanner, String))
```

## Compile

From this folder, once Main.java exists:

```
javac -d out src/*/*.java src/Main.java
java -cp out Main
```

## Notes for teammates

- Create ONE `Library` in Main and pass it to every service.
- Loading from file: use the constructors that take `status` and `borrowerName`, then `library.addBook(book)`. This keeps the ID counter correct.
- Borrow/return: `library.findById(id)`, `book.setStatus(...)`, `book.setBorrowerName(...)`.
- A blank genre is stored as "Unknown".
