package main.dao.implementations.mysql;
import java.sql.*;
public class ConnectionManager {
    public Connection openConnection() {
        Connection sqlConnection = null;
        Statement sqlStatement = null;
        try {
            sqlConnection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "solarwinds123");
            sqlStatement = sqlConnection.createStatement();
            sqlStatement.execute("USE library_system;");
            if (sqlStatement != null) sqlStatement.close();
        } catch (SQLException e) {
            System.out.println(e.getSQLState() + "\n" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sqlConnection;
    }
    public Connection sqlConnection = openConnection();
    public void closeConnection() {
        try {
            if (sqlConnection != null) sqlConnection.close();
        } catch (SQLException e) {
            System.out.println(e.getSQLState() + "\n" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void connectionStatus(){
        String message = (sqlConnection == null)? ("Connection terminated or never existed") : ("Open connection");
        System.out.println(message);
    }
}

//import java.sql.Connection;
//        import java.sql.DriverManager;
//        import java.sql.SQLException;
//        import java.sql.Statement;
