package main.dao.implementations.mysql;

import main.dao.interfaces.BookDaoInterface;
import main.models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BookDaoMysqlImpl implements BookDaoInterface {

    private Connection sqlConnection = null;
    public BookDaoMysqlImpl(Connection connectionUpdate) {
        this.sqlConnection = connectionUpdate;
    }

    @Override
    public List<Book> getAllBooks(){
        String getAllBooksQuery = "SELECT * FROM books;";
        Statement statement = null;
        ResultSet resultSet = null;
        List<Book> books = new ArrayList<>();
        try {
            statement = sqlConnection.createStatement();
            resultSet = statement.executeQuery(getAllBooksQuery);
            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getInt("book_id"),
                        resultSet.getString("title"),
                        resultSet.getString("authors"),
                        resultSet.getInt("year"),
                        resultSet.getString("topic"),
                        resultSet.getInt("total_copies"));
                books.add(book);
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
        return books;
    }

    @Override
    public Integer add(Book book) throws SQLException {     //TODO return the generated id instead of the boolean
        String addBookQuery = "INSERT INTO books (title, authors, year, topic, total_copies, copies_in_stock) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;
        Integer generatedId = null;
        try {
            preparedStatement = sqlConnection.prepareStatement(addBookQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthors());
            preparedStatement.setInt(3, book.getYear());
            preparedStatement.setString(4, book.getTopic());
            preparedStatement.setInt(5, book.getTotalCopies());
            preparedStatement.setInt(6, book.getTotalCopies());
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
                if (preparedStatement != null) preparedStatement.close();
                if (sqlConnection != null) sqlConnection.close();
            } catch (SQLException e) {
                System.out.println(e.getSQLState() + "\n" + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (generatedId == null)
            throw new SQLException("DAO add method for Book failed to obtain the generated id");
        return generatedId;
    }

    @Override
    public Book getInfo(int book_id){
        String getBookInfoQuery = "SELECT * FROM books WHERE reader_id = ?;";
        PreparedStatement preparedStatement = null;
        String title = "";
        String authors = "";
        int year = Integer.MIN_VALUE;
        String topic = "";
        int totalCopies = Integer.MIN_VALUE;
        ResultSet resultSet = null;
        try {
            preparedStatement = sqlConnection.prepareStatement(getBookInfoQuery);
            preparedStatement.setInt(1, book_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                title = resultSet.getString("title");
                authors = resultSet.getString("authors");
                year = resultSet.getInt(year);
                topic = resultSet.getString("topic");
                totalCopies = resultSet.getInt("total_copies");
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

        return new Book(book_id, title, authors, year, topic, totalCopies);
    }

    @Override
    public boolean edit(Book fixedBook) {
        int bookId = fixedBook.getBookId();

        String newTitle = fixedBook.getTitle();
        String newAuthors = fixedBook.getAuthors();
        int newYear = fixedBook.getYear();
        String newTopic = fixedBook.getTopic();
        int newTotalCopies = fixedBook.getTotalCopies();

        String updateBookQuery = "UPDATE books SET title = ?, authors = ?, year = ?, topic = ?, total_copies = ? WHERE book_id = ?;";
        PreparedStatement preparedStatement = null;
        int executeUpdateResult = 0;
        try {
            preparedStatement = sqlConnection.prepareStatement(updateBookQuery);
            preparedStatement.setString(1, newTitle);
            preparedStatement.setString(2, newAuthors);
            preparedStatement.setInt(3, newYear);
            preparedStatement.setString(4, newTopic);
            preparedStatement.setInt(5, newTotalCopies);
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

    @Override
    public boolean remove(int bookId) {
        PreparedStatement preparedStatement = null;
        int executeUpdateResult = 0;
        String removeBookQuery = "DELETE FROM books WHERE book_id = ?;";
        try {
            preparedStatement = sqlConnection.prepareStatement(removeBookQuery);
            preparedStatement.setInt(1, bookId);
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
