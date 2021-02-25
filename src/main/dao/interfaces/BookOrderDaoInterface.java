package main.dao.interfaces;

import main.models.BookOrder;
import java.util.List;

public interface BookOrderDaoInterface extends CrudDaoInterface<BookOrder, Integer> {
    public List<BookOrder> getAllOrders();
    public boolean makeReturned(int orderId);
    public boolean deleteReturned();
    public List<BookOrder> getOverdueOrders();
}