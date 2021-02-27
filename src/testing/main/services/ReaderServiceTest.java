package main.services;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


//todo where is the xml file?

public class ReaderServiceTest {


    @BeforeClass
    public void connectToServer() {
//        try {
//            sqlConnection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306", "root", "solarwinds123");
//            statement = sqlConnection.createStatement();
//            statement.execute("USE library_system;");
//            if (sqlConnection != null) System.out.println("Connected to the library_system database");
//        } catch (
//                SQLException e) {
//            System.out.println(e.getSQLState() + "\n" + e.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        ConnectionManager MySqlDatabase = new ConnectionManager();
    }

    @AfterClass
    public void disconnectFromServer() {
//        if (sqlConnection != null) {
//            try {
//                System.out.println("Closing the connection...");
//                if (sqlConnection != null) sqlConnection.close();
//                if (statement != null) statement.close();
//            } catch (
//                    SQLException e) {
//                System.out.println(e.getSQLState() + "\n" + e.getMessage());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }



//    @Test(description = "This test verifies adding new readers to the library system",
//            groups = "Reader functionality", dataProvider = "dataProviderAddReader")
//    public void testAddReaderDaoExchange() {
//
//
//    }
//
//    @DataProvider (name = "dataProviderAddReader")
//    public Object[][] dataProviderAddReader() {
//        return new Object[][] { {"first set of data"}, {"second set of data"} };
//    }


    @Test
    public void testGetReaderByIdDaoExchange() {
        System.out.println("testing getting reader information");
    }

    @Test
    public void testEditReaderDaoExchange() {
        System.out.println("testing reader editing");
    }

    @Test
    public void testRemoveReaderDaoExchange() {
        System.out.println("testing reader removal");
    }

    @Test
    public void testListAllReadersDaoExchange() {
        System.out.println("testing listing all readers");
    }
}