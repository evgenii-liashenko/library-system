package main.dao.implementations.mysql;

import main.dao.interfaces.BookOrderDaoInterface;
import main.models.BookOrder;

import java.sql.*;
import java.util.ArrayList;



//public class BookOrderDaoMysqlImpl implements BookOrderDaoInterface {
//
//    private Connection sqlConnection = null;
//    public BookOrderDaoMysqlImpl(Connection connectionUpdate) {
//        this.sqlConnection = connectionUpdate;
//    }
//
//
//    //ToDo
//    public ArrayList<BookOrder> getAllOrders(){
//        String getAllOrdersQuery = "SELECT * FROM taken_books;";
//        Statement statement = null;
//        ResultSet resultSet = null;
//        ArrayList<BookOrder> orders = new ArrayList<>();
//        try {
//            statement = sqlConnection.createStatement();
//            resultSet = statement.executeQuery(getAllOrdersQuery);
//            while (resultSet.next()){
//                //BookOrder order = new BookOrder(resultSet.getInt(""))     //TODO pass Book and Reader objects,
//                // which apparently to be greated right here using the id and getInfo methods
//            }
//        }
//        catch (SQLException e) {
//            System.out.println(e.getSQLState() + "\n" + e.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (statement != null) statement.close();
//                if (sqlConnection != null) sqlConnection.close();
//            } catch (SQLException e) {
//                System.out.println(e.getSQLState() + "\n" + e.getMessage());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        return orders;
//    }
//
//    //ToDo
//    public boolean add(BookOrder bookOrder) {
//        String addOrderQuery = "INSERT INTO taken_books (order_id, book_id, reader_id, order_date, return_by, order_status) VALUES (?, ?, ?, ?, ?, ?);";
//        PreparedStatement preparedStatement = null;
//        int executeUpdateResult = 0;
//        try {
//            preparedStatement = sqlConnection.prepareStatement(addOrderQuery);
////            preparedStatement.setString(1, BookOrder);
////            executeUpdateResult = preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getSQLState() + "\n" + e.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (preparedStatement != null) preparedStatement.close();
//                if (sqlConnection != null) sqlConnection.close();
//            } catch (SQLException e) {
//                System.out.println(e.getSQLState() + "\n" + e.getMessage());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return executeUpdateResult != 0;
//    }
//
//
//    //ToDo
//    public BookOrder getInfo(int reader_id) {
//        String getReaderInfo = "SELECT * FROM library_system.readers WHERE reader_id = ?;";
//        PreparedStatement preparedStatement = null;
//        String fullName = "";
//        ResultSet resultSet = null;
//        try {
//            preparedStatement = sqlConnection.prepareStatement(getReaderInfo);
//            preparedStatement.setInt(1, reader_id);
//            resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                fullName = resultSet.getString("name");
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getSQLState() + "\n" + e.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (preparedStatement != null) preparedStatement.close();
//                if (sqlConnection != null) sqlConnection.close();
//            } catch (SQLException e) {
//                System.out.println(e.getSQLState() + "\n" + e.getMessage());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        return new BookOrder();
//    }
//
//
//    //ToDo
//    public boolean edit(BookOrder bookOrder) {
//
//        int executeUpdateResult = 0;
//
//        return executeUpdateResult != 0;
//    }
//
//
//    //ToDo
//    public boolean remove(int readerId) {
//        PreparedStatement preparedStatement = null;
//        int executeUpdateResult = 0;
//        String removeReaderQuery = "DELETE FROM library_system.readers WHERE reader_id = ?;";
//        try {
//            preparedStatement = sqlConnection.prepareStatement(removeReaderQuery);
//            preparedStatement.setInt(1, readerId);
//            executeUpdateResult = preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println(e.getSQLState() + "\n" + e.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (preparedStatement != null) preparedStatement.close();
//                if (sqlConnection != null) sqlConnection.close();
//            } catch (SQLException e) {
//                System.out.println(e.getSQLState() + "\n" + e.getMessage());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return executeUpdateResult != 0;
//    }
//
//
//}
