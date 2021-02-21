package main.dao.interfaces;

import main.models.Book;
import java.util.List;

public interface BookDaoInterface {
    public List<Book> getAllBooks();
}




/*
 * CREATE: receives a value object of Book class and sends its values to the SQL database
 * READ: takes [book_id] or incomplete value object of Book class,
 * then returns a corresponding value object of Book class
 * UPDATE: receives a value object of Book class with book_id and other fields,
 *  goes to the database and overwrites the columns
 * DELETE: receives book_id, goes to the database and deletes a row with that id
 */
//    public boolean addBook(Book valueObject);       //CRETE
//    public Book getBookDescription(int book_id);    //READ
//    public boolean editBook(Book valueObject);      //UPDATE
//    public boolean removeBook(int book_id);         //DELETE