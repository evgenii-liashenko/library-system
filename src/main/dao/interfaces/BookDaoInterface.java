package main.dao.interfaces;

import main.models.Book;
import java.util.List;

public interface BookDaoInterface extends CrudDaoInterface<Book, Integer> {
    public List<Book> getAllBooks();
}
