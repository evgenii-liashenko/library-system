package main.services;

import main.dao.implementations.mysql.BookDaoMysqlImpl;
import main.dao.implementations.mysql.BookOrderDaoMysqlImpl;
import main.dao.implementations.mysql.ConnectionManager;
import main.dao.interfaces.BookDaoInterface;
import main.dao.interfaces.BookOrderDaoInterface;
import main.models.Book;
import main.models.BookOrder;
import main.models.BookOrderStatus;

import java.time.LocalDate;
import java.util.List;

import static main.command_line_ui.Menu.inputNumberFromUser;
import static main.services.BookService.getBookByIdDaoExchange;
import static main.services.ReaderService.getReaderByIdDaoExchange;

public class BookOrderService {

    public static void listAllOrdersUiExchange() {
        List<BookOrder> bookOrders = listAllOrdersDaoExchange();
        for (BookOrder order : bookOrders) {
            System.out.println(
                    order.getOrderId() + "[id]\t" + order.getBook().getTitle() + "[Book]\t" + order.getReader().getName() + "[Reader]\t"
            + order.getOrderDate() + "[Date]\t" + order.getReturnByDate() + "[To be returned by]\t" + order.getOrderStatus() + "[Status]");
        }
    }
    public static List<BookOrder> listAllOrdersDaoExchange() {
        ConnectionManager sql = new ConnectionManager();
        BookOrderDaoInterface bookOrderDaoImplementation = new BookOrderDaoMysqlImpl(sql.openConnection());
        return bookOrderDaoImplementation.getAllOrders();
    }


    public static void addOrderUiExchange() {       //TODO decrement the available copies, block if there aren't any
        int bookId = inputNumberFromUser("Enter book id:");
        int readerId = inputNumberFromUser("Enter reader id:");
        LocalDate orderDate = LocalDate.now();
        int duration = inputNumberFromUser("Enter loan period in months:");
        LocalDate returnDate = orderDate.plusMonths(duration);
        BookOrder newOrder = new BookOrder(getBookByIdDaoExchange(bookId), getReaderByIdDaoExchange(readerId), orderDate, returnDate, BookOrderStatus.ACTIVE);
        Integer generatedId = addOrderDaoExchange(newOrder);
        String uiMessage = "A new order has been created and assigned id " + generatedId.toString();
        System.out.println(uiMessage);
    }

    public static Integer addOrderDaoExchange(BookOrder newOrder) {
        ConnectionManager sql = new ConnectionManager();
        BookOrderDaoInterface bookOrderDaoImplementation = new BookOrderDaoMysqlImpl(sql.openConnection());
        Integer generatedId = bookOrderDaoImplementation.add(newOrder);
        return generatedId;
    }

//
//    public static void getBookByIdUiExchange() {
//        int bookId = inputNumberFromUser("Enter book id:");
//        Book theBook = getBookByIdDaoExchange(bookId);
//        String uiMessage = theBook.getBookId() + "[id]\t" + theBook.getTitle() + "[Title]\t" + theBook.getAuthors() + "[Authours]\t" +
//                theBook.getYear() + "[Year]\t" + theBook.getTopic() + "[Topic]\t" + theBook.getTotalCopies() + "[Total copies]";
//        System.out.println(uiMessage);
//    }
//    public static Book getBookByIdDaoExchange(int bookId) {
//        ConnectionManager sql = new ConnectionManager();
//        BookDaoInterface bookDaoImplementation = new BookDaoMysqlImpl(sql.openConnection());
//        return bookDaoImplementation.getInfo(bookId);
//    }
//
//
//    public static void editBookUiExchange() {
//        int bookId = inputNumberFromUser("Enter the id of the book to edit:");
//        String newTitle = inputTextFromUser("Enter new title:");
//        String newAuthors = inputTextFromUser("Enter new authors:");
//        int newYear = inputNumberFromUser("Enter new year:");
//        String newTopic = inputTextFromUser("Enter new topic:");
//        int newTotalCopies = inputNumberFromUser("Enter new amount of copies:");
//        Book fixedBook = new Book(bookId, newTitle, newAuthors, newYear, newTopic, newTotalCopies);
//        boolean successfulOperation = editBookDaoExchange(fixedBook);
//        String newDescription = fixedBook.getTitle() + "[Title]\t" + fixedBook.getAuthors() + "[Authours]\t" +
//                fixedBook.getYear() + "[Year]\t" + fixedBook.getTopic() + "[Topic]\t" + fixedBook.getTotalCopies() + "[Total copies]";
//        String uiMessage = successfulOperation ? ("Description for the book with id " + bookId + " has been set to " + newDescription) : "Operation failed";
//        System.out.println(uiMessage);
//    }
//    public static boolean editBookDaoExchange(Book fixedBook) {
//        ConnectionManager sql = new ConnectionManager();
//        BookDaoInterface bookDaoImplementation = new BookDaoMysqlImpl(sql.openConnection());
//        boolean successfulOperation = bookDaoImplementation.edit(fixedBook);
//        return successfulOperation;
//    }
//
//
//    public static void removeBookUiExchange() {
//        int bookId = inputNumberFromUser("Enter the id of the book to remove:");
//        String title = getBookByIdDaoExchange(bookId).getTitle();
//        ConnectionManager sql = new ConnectionManager();
//        BookDaoInterface bookDaoImplementation = new BookDaoMysqlImpl(sql.openConnection());
//        boolean successfulOperation = bookDaoImplementation.remove(bookId);
//        String uiMessage = successfulOperation ? ("Book " + title + " has been removed from the library") :
//                ("Operation failed. Make sure " + title + " is not present in any active orders");  //TODO Make this actually work, so that only the books that are in ACTIVE orders cannot be removed
//        System.out.println(uiMessage);
//    }
//    public static boolean removeBookDaoExchange(int bookId) {
//        ConnectionManager sql = new ConnectionManager();
//        BookDaoInterface bookDaoImplementation = new BookDaoMysqlImpl(sql.openConnection());
//        boolean successfulOperation = bookDaoImplementation.remove(bookId);
//        return successfulOperation;
//    }


}
