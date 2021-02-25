package main.services;

import main.dao.implementations.mysql.BookDaoMysqlImpl;
import main.dao.implementations.mysql.ConnectionManager;
import main.dao.interfaces.BookDaoInterface;
import main.models.Book;

import java.util.List;

import static main.command_line_ui.Menu.*;

public class BookService {
    public static void listAllBooksUiExchange() {
        List<Book> books = listAllBooksDaoExchange();
        for (Book book : books) {
            System.out.println(book.getBookId() + "[id]\t" + book.getTitle() + "[Title]\t" + book.getAuthors() + "[Authours]\t" +
                    book.getYear() + "[Year]\t" + book.getTopic() + "[Topic]\t" + book.getTotalCopies() + "[Total copies]");
        }
    }
    public static List<Book> listAllBooksDaoExchange() {
        ConnectionManager sql = new ConnectionManager();
        BookDaoInterface bookDaoImplementation = new BookDaoMysqlImpl(sql.openConnection());
        return bookDaoImplementation.getAllBooks();
    }


    public static void addBookUiExchange() {
        String title = inputTextFromUser("Enter title:");
        String authors = inputTextFromUser("Enter authors:");
        int year = inputNumberFromUser("Enter year:");
        String topic = inputTextFromUser("Enter topic:");
        int totalCopies = inputNumberFromUser("Enter the amount of copies:");
        Book newBook = new Book(title, authors, year, topic, totalCopies);
        Integer generatedId = addBookDaoExchange(newBook);
        String uiMessage = "Book [" + title + "] has been added to the library and assigned id " + generatedId.toString();
        System.out.println(uiMessage);
    }
    public static Integer addBookDaoExchange(Book newBook)  {
        ConnectionManager sql = new ConnectionManager();
        BookDaoInterface bookDaoImplementation = new BookDaoMysqlImpl(sql.openConnection());
        Integer generatedId = bookDaoImplementation.add(newBook);
        return generatedId;
    }

    
    public static void getBookByIdUiExchange() {
        int bookId = inputNumberFromUser("Enter book id:");
        Book theBook = getBookByIdDaoExchange(bookId);
        String uiMessage = theBook.getBookId() + "[id]\t" + theBook.getTitle() + "[Title]\t" + theBook.getAuthors() + "[Authours]\t" +
                theBook.getYear() + "[Year]\t" + theBook.getTopic() + "[Topic]\t" + theBook.getTotalCopies() + "[Total copies]";
        System.out.println(uiMessage);
    }
    public static Book getBookByIdDaoExchange(int bookId) {
        ConnectionManager sql = new ConnectionManager();
        BookDaoInterface bookDaoImplementation = new BookDaoMysqlImpl(sql.openConnection());
        return bookDaoImplementation.getInfo(bookId);
    }       //TODO get available copies as well

    
    public static void editBookUiExchange() {
        int bookId = inputNumberFromUser("Enter the id of the book to edit:");
        String newTitle = inputTextFromUser("Enter new title:");
        String newAuthors = inputTextFromUser("Enter new authors:");
        int newYear = inputNumberFromUser("Enter new year:");
        String newTopic = inputTextFromUser("Enter new topic:");
        int newTotalCopies = inputNumberFromUser("Enter new amount of copies:");
        Book fixedBook = new Book(bookId, newTitle, newAuthors, newYear, newTopic, newTotalCopies);
        boolean successfulOperation = editBookDaoExchange(fixedBook);
        String newDescription = fixedBook.getTitle() + "[Title]\t" + fixedBook.getAuthors() + "[Authours]\t" +
                fixedBook.getYear() + "[Year]\t" + fixedBook.getTopic() + "[Topic]\t" + fixedBook.getTotalCopies() + "[Total copies]";
        String uiMessage = successfulOperation ? ("Description for the book with id " + bookId + " has been set to " + newDescription) : "Operation failed";
        System.out.println(uiMessage);
    }
    public static boolean editBookDaoExchange(Book fixedBook) {
        ConnectionManager sql = new ConnectionManager();
        BookDaoInterface bookDaoImplementation = new BookDaoMysqlImpl(sql.openConnection());
        boolean successfulOperation = bookDaoImplementation.edit(fixedBook);
        return successfulOperation;
    }


    public static void removeBookUiExchange() {
        int bookId = inputNumberFromUser("Enter the id of the book to remove:");
        String title = getBookByIdDaoExchange(bookId).getTitle();
        ConnectionManager sql = new ConnectionManager();
        BookDaoInterface bookDaoImplementation = new BookDaoMysqlImpl(sql.openConnection());
        boolean successfulOperation = bookDaoImplementation.remove(bookId);
        String uiMessage = successfulOperation ? ("Book [" + title + "] has been removed from the library") :
                ("Operation failed. The book [" + title + "] is present in one or more orders. Use operations 35 and 36, then try again");
        System.out.println(uiMessage);
    }
    public static boolean removeBookDaoExchange(int bookId) {
        ConnectionManager sql = new ConnectionManager();
        BookDaoInterface bookDaoImplementation = new BookDaoMysqlImpl(sql.openConnection());
        boolean successfulOperation = bookDaoImplementation.remove(bookId);
        return successfulOperation;
    }



}
