package main.services;
//TODO make separate test suites in testng.xml
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static main.command_line_ui.DatabaseSetupScript.setUpDatabase;
import static org.testng.Assert.*;

public class BookOrderServiceTest {

//    @BeforeMethod
//    public void sendTestDataToDatabase(){
//        setUpDatabase();
//    }

    @Test
    public void testListAllOrdersDaoExchange() {
    }

    @Test
    public void testAddOrderDaoExchange() {
    }

    @Test
    public void testGetOrderByIdDaoExchange() {
    }

    @Test
    public void testEditBookOrderDaoExchange() {
    }

    @Test
    public void testReturnBookDaoExchange() {
    }

    @Test
    public void testDeleteReturnedOrdersDaoExchange() {
    }

    @Test
    public void testListOverdueOrdersDaoExchange() {
    }
}