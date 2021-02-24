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
                if (sqlConnection != null) sqlConnection.close();
            } catch (SQLException e) {
                System.out.println(e.getSQLState() + "\n" + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return orders;
    }

    @Override   //TODO Make it work
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
            generatedId = generatedKeys.getInt(1);      //TODO make it work. it returns null

        } catch (SQLException e) {
            System.out.println(e.getSQLState() + "\n" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (generatedId == null) throw new SQLException("The add method in DAO for BookOrder failed to get the generated id");
                if (preparedStatement != null) preparedStatement.close();
                if (sqlConnection != null) sqlConnection.close();
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
        BookOrder theOrder = null;      //todo throw an exception if null to be returned
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
                if (preparedStatement != null) preparedStatement.close();
                if (sqlConnection != null) sqlConnection.close();
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
        ResultSet resultSet = null;

//        int OrderId = fixedBookOrder.getOrderId();
//        int newBookId = fixedBookOrder.getBook().getBookId();
//        int newReaderId = fixedBookOrder.getReader().getReaderId();
//        LocalDate newOrderDate = fixedBookOrder.getOrderDate();
//        LocalDate newReturnByDate = fixedBookOrder.getReturnByDate();
//        BookOrderStatus newOrderStatus = fixedBookOrder.getOrderStatus();

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
        }catch (SQLException e) {
            System.out.println(e.getSQLState() + "\n" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (sqlConnection != null) sqlConnection.close();
            } catch (SQLException e) {
                System.out.println(e.getSQLState() + "\n" + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return executeUpdateResult != 0;
    }

    @Override       //TODO set the enum to RETURNED instead of deleting or write a separate method for that
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
                if (sqlConnection != null) sqlConnection.close();
            } catch (SQLException e) {
                System.out.println(e.getSQLState() + "\n" + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return executeUpdateResult != 0;
    }


}
