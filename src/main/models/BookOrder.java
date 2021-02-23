package main.models;

import java.time.LocalDate;

public class BookOrder {
    private int orderId;    //do we need orderId?
    private Book book;
    private Reader reader;
    private LocalDate orderDate;
    private LocalDate returnByDate;
    private BookOrderStatus orderStatus;

    public BookOrder(int orderId, Book book, Reader reader, LocalDate orderDate, LocalDate returnByDate, BookOrderStatus orderStatus) {
        this.orderId = orderId;
        this.book = book;
        this.reader = reader;
        this.orderDate = orderDate;
        this.returnByDate = returnByDate;
        this.orderStatus = orderStatus;
    }

    public BookOrder() {
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getReturnByDate() {
        return returnByDate;
    }

    public void setReturnByDate(LocalDate returnByDate) {
        this.returnByDate = returnByDate;
    }

    public BookOrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(BookOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BookOrder(Book book, Reader reader, LocalDate orderDate, LocalDate returnByDate, BookOrderStatus orderStatus) {
        this.book = book;
        this.reader = reader;
        this.orderDate = orderDate;
        this.returnByDate = returnByDate;
        this.orderStatus = orderStatus;
    }
}
