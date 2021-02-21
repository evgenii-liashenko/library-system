package main.dao.implementations.mysql;

import main.dao.interfaces.ReaderDaoInterface;
import main.models.Reader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReaderDaoMysqlImpl implements ReaderDaoInterface {
    private Connection sqlConnection = null;

    public ReaderDaoMysqlImpl(Connection connectionUpdate) {
        this.sqlConnection = connectionUpdate;
    }

    @Override
    public boolean add(Reader reader) {
        String addReaderQuery = "INSERT INTO readers (name) VALUES (?);";
        PreparedStatement preparedStatement = null;
        int executeUpdateResult = 0;
        try {
            preparedStatement = sqlConnection.prepareStatement(addReaderQuery);
            preparedStatement.setString(1, reader.getName());
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


    //TO BE DEVELOPED
    public Reader getInfo(int reader_id) {       //or
        Reader reader = new Reader("");
        return reader;
    }

    //TO BE DEVELOPED
    public boolean edit(Reader reader) {
        return true;
    }

    //TO BE DEVELOPED
    public boolean remove(int reader_id) {
        return true;
    }

    //TO BE DEVELOPED
    public ArrayList<Reader> getAllReaders() {
        return new ArrayList<Reader>();
    }

}
