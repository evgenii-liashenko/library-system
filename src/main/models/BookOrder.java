package main.models;

import java.time.LocalDate;

public class BookOrder {
    //do we need orderId here?
    private Book book;
    private Reader reader;
    private LocalDate orderDate;
    private LocalDate returnByDate;
    private BookOrderStatus orderStatus;

}
