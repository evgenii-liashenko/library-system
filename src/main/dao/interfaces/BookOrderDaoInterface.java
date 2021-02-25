package main.dao.interfaces;

import main.models.BookOrder;
import java.util.List;

public interface BookOrderDaoInterface extends CrudDaoInterface<BookOrder, Integer> {
    public List<BookOrder> getAllOrders();
    public boolean setStatusToReturned(int orderId);
}



/*
 * CREATE: takes a value object of BookOrder class and sends the data from it to the database
 * READ: takes [order_id] and returns a corresponding value object of BookOrder class
 * UPDATE: takes a value object of BookOrder class (it contains order_id), then overwrites the
 * corresponding entry in the database with the values from the value object
 * DELETE: takes [order_id] and sets the enum in SQL database to RETURNED. It does not actually delete anything
 */
//    public boolean placeOrder(BookOrder valueObject);   //CREATE
//    public BookOrder getOrderDetails(int order_id);     //READ
//    public boolean editOrder(BookOrder valueObject);    //UPDATE
//    public boolean returnOrder(int order_id);           //DELETE    Well, sort of.