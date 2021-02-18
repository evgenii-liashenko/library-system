package testing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnectionTest2 {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/library_system",
                    "root",
                    "solarwinds123");
            if (conn != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }
        }
        catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (conn != null)
                    conn.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
