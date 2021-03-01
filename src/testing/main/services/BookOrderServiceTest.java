package main.services;
//TODO make separate test suites in testng.xml

import main.models.Book;
import main.models.BookOrder;
import main.models.BookOrderStatus;
import main.models.Reader;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static main.command_line_ui.DatabaseSetupScript.setUpDatabase;
import static main.services.BookOrderService.*;
import static main.services.BookService.*;
import static main.services.ReaderService.*;


public class BookOrderServiceTest {

    @BeforeTest
    public void preparationsBookOrder() {
        setUpDatabase();
        System.out.println("The database setup script has finished\n");
    }

    @Test
    public void testListAllOrdersDaoExchange() {
        System.out.println("Testing listing all orders");
        System.out.println("Testing listing all books");

        int orderCount = listAllOrdersDaoExchange().size();
        int firstId = listAllOrdersDaoExchange().get(0).getOrderId();
        String firstBook = getOrderByIdDaoExchange(firstId).getBook().getTitle();
        String firstReader = getOrderByIdDaoExchange(firstId).getReader().getName();
        int lastId = listAllOrdersDaoExchange().get(orderCount - 1).getOrderId();
        String lastBook = listAllOrdersDaoExchange().get(orderCount - 1).getBook().getTitle();
        String lastReader = listAllOrdersDaoExchange().get(orderCount - 1).getReader().getName();

        Assert.assertEquals(firstId, 1);
        Assert.assertEquals(firstBook, "Head First Java, 2nd Edition");
        Assert.assertEquals(firstReader, "Jane Doe");
        Assert.assertEquals(lastId, 10);
        Assert.assertEquals(lastBook, "The Definitive Book of Body Language: How to read others’ attitudes by their gestures");
        Assert.assertEquals(lastReader, "Jill Roberts");
    }

    @Test(dependsOnMethods = "testListAllOrdersDaoExchange")
    public void testAddOrderDaoExchange() {
        System.out.println("Testing placing an order");

        int orderCountBefore = listAllOrdersDaoExchange().size();
        Book bookToOrder = getBookByIdDaoExchange(5);
        Reader customer = getReaderByIdDaoExchange(3);
        LocalDate theOrderDate = LocalDate.now().minusWeeks(1);
        LocalDate theReturnDate = LocalDate.now().plusMonths(2);
        BookOrder orderToPlace = new BookOrder(bookToOrder, customer, theReturnDate, theOrderDate);

        Integer generatedId = addOrderDaoExchange(orderToPlace);
        int orderCountAfter = listAllOrdersDaoExchange().size();
        BookOrder actualOrder = getOrderByIdDaoExchange(generatedId);

        Assert.assertEquals(orderCountBefore + 1, orderCountAfter);
        Assert.assertEquals(orderToPlace.getBook().getBookId(), actualOrder.getBook().getBookId());
        Assert.assertEquals(orderToPlace.getReader().getReaderId(), actualOrder.getReader().getReaderId());
        Assert.assertEquals(orderToPlace.getOrderDate(), actualOrder.getOrderDate());
        Assert.assertEquals(orderToPlace.getReturnByDate(), actualOrder.getReturnByDate());

    }

    @Test
    public void testGetOrderByIdDaoExchange() {
        System.out.println("Testing getting order information");
        LocalDate today = LocalDate.now();

        BookOrder theOrder = getOrderByIdDaoExchange(1);
        Assert.assertEquals(theOrder.getOrderId(), 1);
        Assert.assertEquals(theOrder.getBook().getBookId(), 1);
        Assert.assertEquals(theOrder.getReader().getReaderId(), 1);
        Assert.assertEquals(theOrder.getOrderStatus(), BookOrderStatus.ACTIVE);
        Assert.assertEquals(theOrder.getOrderDate().toString(), today.toString());
        Assert.assertEquals(theOrder.getReturnByDate().toString(), today.plusMonths(6).toString());
    }

    @Test
    public void testEditBookOrderDaoExchange() {
        System.out.println("Testing order editing");
        int orderToEditId = 2;
        Book fixedBook = getBookByIdDaoExchange(6);
        Reader fixedReader = getReaderByIdDaoExchange(7);
        LocalDate newOrderDate = LocalDate.now().minusWeeks(10);
        LocalDate newReturnDate = LocalDate.now().minusWeeks(5);
        BookOrderStatus newOrderStatus = BookOrderStatus.RETURNED;
        BookOrder fixedOrder = new BookOrder(orderToEditId, fixedBook, fixedReader, newOrderDate, newReturnDate, newOrderStatus);

        editBookOrderDaoExchange(fixedOrder);
        BookOrder obtainedOrder = getOrderByIdDaoExchange(orderToEditId);

        Assert.assertEquals(obtainedOrder.getOrderId(), fixedOrder.getOrderId());
        Assert.assertEquals(obtainedOrder.getBook().getBookId(), fixedOrder.getBook().getBookId());
        Assert.assertEquals(obtainedOrder.getReader().getReaderId(), fixedOrder.getReader().getReaderId());
        Assert.assertEquals(obtainedOrder.getOrderDate(), fixedOrder.getOrderDate());
        Assert.assertEquals(obtainedOrder.getReturnByDate(), fixedOrder.getReturnByDate());
        Assert.assertEquals(obtainedOrder.getOrderStatus(), fixedOrder.getOrderStatus());
    }

    @Test
    public void testReturnBookDaoExchange() {
        System.out.println("Testing returning an order");
        int testId = 3;

        returnBookDaoExchange(testId);
        BookOrder testOrder = getOrderByIdDaoExchange(testId);
        Assert.assertEquals(testOrder.getOrderStatus(), BookOrderStatus.RETURNED);
    }

    @Test
    public void testDeleteReturnedOrdersDaoExchange() {
        System.out.println("Testing deleting returned orders");
        int testId = 8;
        int orderCountBefore = listAllOrdersDaoExchange().size();

        returnBookDaoExchange(testId);
        deleteReturnedOrdersDaoExchange();
        int orderCountAfter = listAllOrdersDaoExchange().size();
        Assert.assertEquals(orderCountBefore, orderCountAfter + 1);

        getOrderByIdDaoExchange(testId);
    }

    @Test
    public void testListOverdueOrdersDaoExchange() {
        System.out.println("Testing listing overdue orders");
        int overdueCountBefore = listOverdueOrdersDaoExchange().size();
        int testId = 6;
        BookOrder activeOrder = getOrderByIdDaoExchange(testId);
        LocalDate yesterday = LocalDate.now().minusDays(1);
        BookOrder overdueOrder = new BookOrder(
                activeOrder.getOrderId(),
                activeOrder.getBook(),
                activeOrder.getReader(),
                activeOrder.getOrderDate(),
                yesterday,
                activeOrder.getOrderStatus()
        );

        editBookOrderDaoExchange(overdueOrder);
        int overdueCountAfter = listOverdueOrdersDaoExchange().size();

        Assert.assertEquals(overdueCountBefore + 1, overdueCountAfter);

    }
}