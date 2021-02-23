package main.dao.implementations.mysql;

import main.dao.interfaces.ReaderDaoInterface;
import main.models.Reader;

import java.sql.*;
import java.util.ArrayList;


public class ReaderDaoMysqlImpl implements ReaderDaoInterface {

    private Connection sqlConnection = null;

    public ReaderDaoMysqlImpl(Connection connectionUpdate) {
        this.sqlConnection = connectionUpdate;
    }


    //@Override
    public Integer add(Reader reader) throws SQLException {     //TODO return the generated id instead of the boolean
        String addReaderQuery = "INSERT INTO readers (name) VALUES (?);";
        PreparedStatement preparedStatement = null;
        Integer generatedId = null;
        try {
            preparedStatement = sqlConnection.prepareStatement(addReaderQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, reader.getName());
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
            throw new SQLException("DAO add method for Reader failed to obtain the generated id");
        return generatedId;
    }


    @Override
    public Reader getInfo(int reader_id) {
        String getReaderInfo = "SELECT * FROM library_system.readers WHERE reader_id = ?;";
        PreparedStatement preparedStatement = null;
        String fullName = "";
        ResultSet resultSet = null;
        try {
            preparedStatement = sqlConnection.prepareStatement(getReaderInfo);
            preparedStatement.setInt(1, reader_id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                fullName = resultSet.getString("name");
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

        return new Reader(fullName);
    }


    @Override
    public boolean edit(Reader fixedReader) {
        int readerId = fixedReader.getReaderId();
        String newName = fixedReader.getName();
        String updateNameQuery = "UPDATE readers SET name = ? WHERE reader_id = ?;";
        PreparedStatement preparedStatement = null;
        int executeUpdateResult = 0;
        try {
            preparedStatement = sqlConnection.prepareStatement(updateNameQuery);
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, readerId);
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
    public boolean remove(int readerId) {
        PreparedStatement preparedStatement = null;
        int executeUpdateResult = 0;
        String removeReaderQuery = "DELETE FROM library_system.readers WHERE reader_id = ?;";
        try {
            preparedStatement = sqlConnection.prepareStatement(removeReaderQuery);
            preparedStatement.setInt(1, readerId);
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
    public ArrayList<Reader> getAllReaders() {
        String getAllReadersQuery = "SELECT * FROM library_system.readers;";
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<Reader> readers = new ArrayList<Reader>();
        try {
            statement = sqlConnection.createStatement();
            resultSet = statement.executeQuery(getAllReadersQuery);
            while (resultSet.next()) {
                Reader reader = new Reader(resultSet.getInt("reader_id"), resultSet.getString("name"));
                readers.add(reader);
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
        return readers;
    }


}
