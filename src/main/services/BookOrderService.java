package main.services;

import main.dao.implementations.mysql.BookOrderDaoMysqlImpl;
import main.dao.implementations.mysql.ConnectionManager;
import main.dao.interfaces.BookOrderDaoInterface;
import main.models.BookOrder;
import main.models.BookOrderStatus;

import java.time.LocalDate;
import java.util.List;

import static main.command_line_ui.Menu.*;
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

    public static void addOrderUiExchange() {
        //Some business logic here: decrement the copies_in_stock for the book when the order is placed, block the operation if there aren't any.
        int bookId = inputNumberFromUser("Enter book id:");
        if (isBookInStock(bookId)) {
            int readerId = inputNumberFromUser("Enter reader id:");
            LocalDate orderDate = LocalDate.now();
            int duration = inputNumberFromUser("Enter loan period in months:");
            LocalDate returnDate = orderDate.plusMonths(duration);
            BookOrder newOrder = new BookOrder(getBookByIdDaoExchange(bookId), getReaderByIdDaoExchange(readerId), orderDate, returnDate, BookOrderStatus.ACTIVE);
            Integer generatedId = addOrderDaoExchange(newOrder);
            String uiMessage = "A new order for a book [" +newOrder.getBook().getTitle() + "] and reader " + newOrder.getReader().getName() +" has been created and assigned id " + generatedId.toString();
            System.out.println(uiMessage);
        }
        else
            System.out.println("Operation cannot be performed. The book [" + getBookByIdDaoExchange(bookId).getTitle() + "] is out of stock");
    }
    public static Integer addOrderDaoExchange(BookOrder newOrder) {
        ConnectionManager sql = new ConnectionManager();
        BookOrderDaoInterface bookOrderDaoImplementation = new BookOrderDaoMysqlImpl(sql.openConnection());
        Integer generatedId = bookOrderDaoImplementation.add(newOrder);
        bookOrderDaoImplementation.decrementCopiesInStock(newOrder.getBook().getBookId());
        return generatedId;
    }


    public static void getOrderByIdUiExchange() {
        int orderId = inputNumberFromUser("Enter order id:");
        BookOrder theOrder = getOrderByIdDaoExchange(orderId);
        if (theOrder == null)
            System.out.println("Order not found");
        else {
            String uiMessage = theOrder.getOrderId() + "[id]\t" + theOrder.getBook().getTitle() + "[Book]\t" + theOrder.getReader().getName() + "[Reader]\t"
                    + theOrder.getOrderDate() + "[Date]\t" + theOrder.getReturnByDate() + "[To be returned by]\t" + theOrder.getOrderStatus() + "[Status]";
            System.out.println(uiMessage);
        }
    }
    public static BookOrder getOrderByIdDaoExchange(int orderId) {
        ConnectionManager sql = new ConnectionManager();
        BookOrderDaoInterface bookOrderDaoImplementation = new BookOrderDaoMysqlImpl(sql.openConnection());
        return bookOrderDaoImplementation.getInfo(orderId);
    }


    public static void editOrderUiExchange() {
        int orderId = inputNumberFromUser("Enter the id of the order to edit:");
        int newBookId = inputNumberFromUser("Enter new book id:");
        int newReaderId = inputNumberFromUser("Enter new reader id:");
        LocalDate newOrderDate = inputDateFromUser("Enter new order date (YYYY-MM-DD):");
        LocalDate newReturnDate = inputDateFromUser("Enter new return date (YYYY-MM-DD):");
        BookOrderStatus newOrderStatus = BookOrderStatus.valueOf(inputTextFromUser("Enter new order status (RETURNED or ACTIVE):"));
        BookOrder fixedOrder = new BookOrder(orderId, getBookByIdDaoExchange(newBookId), getReaderByIdDaoExchange(newReaderId), newOrderDate, newReturnDate, newOrderStatus);
        boolean successfulOperation = editBookOrderDaoExchange(fixedOrder);
        String newDescription = fixedOrder.getBook().getTitle() + "[Book]\t" + fixedOrder.getReader().getName() +
                "[Reader]\t" + fixedOrder.getOrderDate() + "[Order date]\t" + fixedOrder.getReturnByDate() + "[Return date]\t" + fixedOrder.getOrderStatus() + "[Status]";
        String uiMessage = successfulOperation ? ("Details for the order with id " + orderId + " have been updated to:\n" + newDescription) : "Operation failed";
        System.out.println(uiMessage);
    }
    public static boolean editBookOrderDaoExchange(BookOrder fixedOrder) {
        ConnectionManager sql = new ConnectionManager();
        BookOrderDaoInterface bookOrderDaoImplementation = new BookOrderDaoMysqlImpl(sql.openConnection());
        boolean successfulOperation = bookOrderDaoImplementation.edit(fixedOrder);
        return successfulOperation;
    }


    public static void returnBookUiExchange() {
        int orderId = inputNumberFromUser("Enter order id:");
        ConnectionManager sql = new ConnectionManager();
        BookOrderDaoInterface bookOrderDaoImplementation = new BookOrderDaoMysqlImpl(sql.openConnection());
        boolean successfulOperation = returnBookDaoExchange(orderId);
        String uiMessage = successfulOperation ? ("Book [" + getOrderByIdDaoExchange(orderId).getBook().getTitle() +
                "] has been returned to the library from reader " + getOrderByIdDaoExchange(orderId).getReader().getName()) : ("Operation failed");
        System.out.println(uiMessage);
    }
    public static boolean returnBookDaoExchange(int orderId) {
        ConnectionManager sql = new ConnectionManager();
        BookOrderDaoInterface bookOrderDaoImplementation = new BookOrderDaoMysqlImpl(sql.openConnection());
        boolean successfulOperation = bookOrderDaoImplementation.makeReturned(orderId);
        bookOrderDaoImplementation.incrementCopiesInStock(getOrderByIdDaoExchange(orderId).getBook().getBookId());
        return successfulOperation;
    }


    public static void deleteReturnedOrdersUiExchange(){
        String uiMessage = deleteReturnedOrdersDaoExchange()? "Returned orders have been removed from order history" : "Operation failed";
        System.out.println(uiMessage);
    }
    public static boolean deleteReturnedOrdersDaoExchange(){
        ConnectionManager sql = new ConnectionManager();
        BookOrderDaoInterface bookOrderDaoImplementation = new BookOrderDaoMysqlImpl(sql.openConnection());
        boolean successfulOperation = bookOrderDaoImplementation.deleteReturned();
        return successfulOperation;
    }


    public static void listOverdueOrdersUiExchange(){
        List<BookOrder> overdueOrders = listOverdueOrdersDaoExchange();
        for (BookOrder order : overdueOrders) {
            System.out.println(
                    order.getOrderId() + "[id]\t" + order.getBook().getTitle() + "[Book]\t" + order.getReader().getName() + "[Reader]\t"
                            + order.getOrderDate() + "[Date]\t" + order.getReturnByDate() + "[To be returned by]\t" + order.getOrderStatus() + "[Status]");
        }
    }
    public static List<BookOrder> listOverdueOrdersDaoExchange(){
        ConnectionManager sql = new ConnectionManager();
        BookOrderDaoInterface bookOrderDaoImplementation = new BookOrderDaoMysqlImpl(sql.openConnection());
        return bookOrderDaoImplementation.getOverdueOrders();
    }

    public static boolean isBookInStock(int bookId){
        ConnectionManager sql = new ConnectionManager();
        BookOrderDaoInterface bookOrderDaoImplementation = new BookOrderDaoMysqlImpl(sql.openConnection());
        return bookOrderDaoImplementation.getCopiesInStock(bookId) > 0;
    }

}
