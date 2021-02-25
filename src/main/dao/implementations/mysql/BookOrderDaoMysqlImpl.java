package main.dao.implementations.mysql;

import main.dao.interfaces.BookDaoInterface;
import main.dao.interfaces.BookOrderDaoInterface;
import main.dao.interfaces.ReaderDaoInterface;
import main.models.Book;
import main.models.BookOrder;
import main.models.BookOrderStatus;
import main.models.Reader;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class BookOrderDaoMysqlImpl implements BookOrderDaoInterface {

    private Connection sqlConnection = null;

    public BookOrderDaoMysqlImpl(Connection connectionUpdate) {
        this.sqlConnection = connectionUpdate;
    }

    @Override
    public List<BookOrder> getAllOrders() {
        String getAllOrdersQuery = "SELECT * FROM taken_books;";
        Statement statement = null;
        ResultSet resultSet = null;
        List<BookOrder> orders = new ArrayList<>();
        try {
            statement = sqlConnection.createStatement();
            resultSet = statement.executeQuery(getAllOrdersQuery);
            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");

                int bookId = resultSet.getInt("book_id");
                BookDaoInterface bookDaoImplementation = new BookDaoMysqlImpl(sqlConnection);
                Book book = bookDaoImplementation.getInfo(bookId);

                int readerId = resultSet.getInt("reader_id");
                ReaderDaoInterface readerDaoImplementation = new ReaderDaoMysqlImpl(sqlConnection);
                Reader reader = readerDaoImplementation.getInfo(readerId);

                LocalDate orderDate = resultSet.getTimestamp("order_date").toLocalDateTime().toLocalDate();
                LocalDate returnByDate = resultSet.getTimestamp("return_by").toLocalDateTime().toLocalDate();
                BookOrderStatus orderStatus = BookOrderStatus.valueOf(resultSet.getString("order_status"));

                BookOrder order = new BookOrder(orderId, book, reader, orderDate, returnByDate, orderStatus);
                orders.add(order);
            }
        } catch (SQLException e) {
            System.out.println(e.getSQLState() + "\n" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) statement.close();
                //if (sqlConnection != null) sqlConnection.close();
            } catch (SQLException e) {
                System.out.println(e.getSQLState() + "\n" + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return orders;
    }

    @Override
    public Integer add(BookOrder bookOrder) {
        String addOrderQuery = "INSERT INTO taken_books (book_id, reader_id, order_date, return_by, order_status) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;
        Integer generatedId = null;
        try {
            preparedStatement = sqlConnection.prepareStatement(addOrderQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, bookOrder.getBook().getBookId());
            preparedStatement.setInt(2, bookOrder.getReader().getReaderId());
            preparedStatement.setDate(3, Date.valueOf(bookOrder.getOrderDate()));
            preparedStatement.setDate(4, Date.valueOf(bookOrder.getReturnByDate()));
            preparedStatement.setString(5, bookOrder.getOrderStatus().name());
            generatedId = preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            generatedId = generatedKeys.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getSQLState() + "\n" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (generatedId == null)
                    throw new SQLException("The add method in DAO for BookOrder failed to get the generated id");
                if (preparedStatement != null) preparedStatement.close();
                //if (sqlConnection != null) sqlConnection.close();
            } catch (SQLException e) {
                System.out.println(e.getSQLState() + "\n" + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return generatedId;
    }

    @Override
    public BookOrder getInfo(int orderId) {
        String getOrderInfoQuery = "SELECT * FROM taken_books WHERE order_id = ?;";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        BookOrder theOrder = null;
        try {
            preparedStatement = sqlConnection.prepareStatement(getOrderInfoQuery);
            preparedStatement.setInt(1, orderId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int bookId = resultSet.getInt("book_id");
                BookDaoInterface bookDaoImplementation = new BookDaoMysqlImpl(sqlConnection);
                Book book = bookDaoImplementation.getInfo(bookId);

                int readerId = resultSet.getInt("reader_id");
                ReaderDaoInterface readerDaoImplementation = new ReaderDaoMysqlImpl(sqlConnection);
                Reader reader = readerDaoImplementation.getInfo(readerId);

                LocalDate orderDate = resultSet.getTimestamp("order_date").toLocalDateTime().toLocalDate();
                LocalDate returnByDate = resultSet.getTimestamp("return_by").toLocalDateTime().toLocalDate();
                BookOrderStatus orderStatus = BookOrderStatus.valueOf(resultSet.getString("order_status"));

                theOrder = new BookOrder(orderId, book, reader, orderDate, returnByDate, orderStatus);
            }
        } catch (SQLException e) {
            System.out.println(e.getSQLState() + "\n" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (theOrder == null)
                    throw new SQLException("getInfo method in BookOrder DAO implementation failed to obtain order details");
                if (preparedStatement != null) preparedStatement.close();
                //if (sqlConnection != null) sqlConnection.close();
            } catch (SQLException e) {
                System.out.println(e.getSQLState() + "\n" + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return theOrder;
    }

    @Override
    public boolean edit(BookOrder fixedBookOrder) {
        PreparedStatement preparedStatement = null;
        //ResultSet resultSet = null;
        String editOrderQuery = "UPDATE taken_books SET book_id = ?, reader_id = ?, order_date = ?, return_by = ?, order_status = ? WHERE order_id = ?;";
        int executeUpdateResult = 0;
        try {
            preparedStatement = sqlConnection.prepareStatement(editOrderQuery);
            preparedStatement.setInt(1, fixedBookOrder.getBook().getBookId());
            preparedStatement.setInt(2, fixedBookOrder.getReader().getReaderId());
            preparedStatement.setDate(3, Date.valueOf(fixedBookOrder.getOrderDate()));
            preparedStatement.setDate(4, Date.valueOf(fixedBookOrder.getReturnByDate()));
            preparedStatement.setString(5, String.valueOf(fixedBookOrder.getOrderStatus()));
            preparedStatement.setInt(6, fixedBookOrder.getOrderId());
            executeUpdateResult = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getSQLState() + "\n" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                //if (sqlConnection != null) sqlConnection.close();
            } catch (SQLException e) {
                System.out.println(e.getSQLState() + "\n" + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return executeUpdateResult != 0;
    }

    @Override
    public boolean remove(int orderId) {
        PreparedStatement preparedStatement = null;
        int executeUpdateResult = 0;
        String removeOrderQuery = "DELETE FROM taken_books WHERE order_id = ?;";
        try {
            preparedStatement = sqlConnection.prepareStatement(removeOrderQuery);
            preparedStatement.setInt(1, orderId);
            executeUpdateResult = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getSQLState() + "\n" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                //if (sqlConnection != null) sqlConnection.close();
            } catch (SQLException e) {
                System.out.println(e.getSQLState() + "\n" + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return executeUpdateResult != 0;
    }

    @Override
    public boolean makeReturned(int orderId) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String setStatusQuery = "UPDATE taken_books SET order_status = ? WHERE order_id = ?;";
        int executeUpdateResult = 0;
        try {
            preparedStatement = sqlConnection.prepareStatement(setStatusQuery);
            preparedStatement.setString(1, BookOrderStatus.RETURNED.name());
            preparedStatement.setInt(2, orderId);
            executeUpdateResult = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getSQLState() + "\n" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                //if (sqlConnection != null) sqlConnection.close();
            } catch (SQLException e) {
                System.out.println(e.getSQLState() + "\n" + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return executeUpdateResult != 0;

    }

    @Override
    public boolean deleteReturned() {
        PreparedStatement preparedStatement = null;
        int executeUpdateResult = 0;
        String removeReturnedQuery = "DELETE FROM taken_books WHERE order_status = 'RETURNED';";
        try {
            preparedStatement = sqlConnection.prepareStatement(removeReturnedQuery);
            executeUpdateResult = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getSQLState() + "\n" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                //if (sqlConnection != null) sqlConnection.close();
            } catch (SQLException e) {
                System.out.println(e.getSQLState() + "\n" + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return executeUpdateResult != 0;
    }

    @Override
    public List<BookOrder> getOverdueOrders() {
        LocalDate today = LocalDate.now();
        List<BookOrder> allOrders = getAllOrders();
        ArrayList<BookOrder> overdueOrders = new ArrayList();
        for (BookOrder order : allOrders) {
            if (today.isAfter(order.getReturnByDate()))
                overdueOrders.add(order);
        }
        return overdueOrders;
    }

    @Override
    public int getCopiesInStock(int bookId){
        String getStockQuery = "SELECT copies_in_stock FROM books WHERE book_id = ?;";
        PreparedStatement preparedStatement = null;
        int copiesInStock = Integer.MIN_VALUE;
        ResultSet resultSet = null;
        try {
            preparedStatement = sqlConnection.prepareStatement(getStockQuery);
            preparedStatement.setInt(1, bookId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                copiesInStock = resultSet.getInt("copies_in_stock");
            }
        } catch (SQLException e) {
            System.out.println(e.getSQLState() + "\n" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                System.out.println(e.getSQLState() + "\n" + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return copiesInStock;

    }

    @Override
    public boolean decrementCopiesInStock(int bookId) {
        int copiesInStock = getCopiesInStock(bookId);
        int newCopiesInStock = --copiesInStock;
        String setCopiesInStock = "UPDATE books SET copies_in_stock = ? WHERE book_id = ?;";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = sqlConnection.prepareStatement(setCopiesInStock);
            preparedStatement.setInt(1, newCopiesInStock);
            preparedStatement.setInt(2, bookId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getSQLState() + "\n" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                System.out.println(e.getSQLState() + "\n" + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newCopiesInStock == getCopiesInStock(bookId);
    }

    @Override
    public boolean incrementCopiesInStock(int bookId) {
        int copiesInStock = getCopiesInStock(bookId);
        int newCopiesInStock = ++copiesInStock;
        String setCopiesInStock = "UPDATE books SET copies_in_stock = ? WHERE book_id = ?;";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = sqlConnection.prepareStatement(setCopiesInStock);
            preparedStatement.setInt(1, newCopiesInStock);
            preparedStatement.setInt(2, bookId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getSQLState() + "\n" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                System.out.println(e.getSQLState() + "\n" + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return newCopiesInStock == getCopiesInStock(bookId);
    }



}
