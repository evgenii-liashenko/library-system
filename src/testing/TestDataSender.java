package testing;

import main.models.Book;
import main.models.Reader;

import java.sql.*;
import java.util.ArrayList;

public class TestDataSender {
    public static void main(String[] args) {
        Connection testConnection = null;
        Statement testStatement = null;
        PreparedStatement testPS = null;
        ResultSet testResSet = null;
        try {
            //1. Database setup
            //1.1 Creating a connection to a local MySQL server
            testConnection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306",
                    "root",
                    "solarwinds123");
            String conStatus = (testConnection != null) ? ("Successful connection") : ("Connection failed :(");
            System.out.println(conStatus);

            //1.2 Creating a database
            testStatement = testConnection.createStatement();
            testStatement.execute("DROP DATABASE IF EXISTS library_system;");
            testStatement.execute("CREATE DATABASE library_system;");
            testStatement.execute("USE library_system;");

            //1.3 Creating empty tables
            String createReadersTable = "CREATE TABLE readers (reader_id INT NOT NULL AUTO_INCREMENT" +
                    " PRIMARY KEY, name TINYTEXT);";
            String createBooksTable = "CREATE TABLE books (\n" +
                    "book_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                    "title TINYTEXT, authors TINYTEXT, year INT,\n" +
                    "topic TINYTEXT, total_copies INT, available_copies INT);";
            String createTakenBooksTable = "CREATE TABLE taken_books (\n" +
                    "book_id INT NOT NULL, FOREIGN KEY (book_id) REFERENCES books (book_id),\n" +
                    "reader_id INT NOT NULL, FOREIGN KEY (reader_id) REFERENCES readers (reader_id),\n" +
                    "PRIMARY KEY (book_id, reader_id), order_date DATE, return_by DATE);";
            testStatement.execute(createReadersTable);
            testStatement.execute(createBooksTable);
            testStatement.execute(createTakenBooksTable);


            //2. Preparing test data
            // Readers
            String[] names = new String[]{"Jane Doe", "John Doe", "John Appleseed", "Вася Пупкин",
                    "Jack Sparrow", "Tyler Durden", "Harley Quinn", "Tommy Vercetti", "Elliot Reid", "Jill Roberts"};
            ArrayList<Reader> readers = new ArrayList<Reader>();
            for (int i = 0; i < names.length; i++) {
                readers.add(new Reader(names[i]));
            }
            // Books
            ArrayList<Book> books = new ArrayList<Book>();
            books.add(new Book("Head First Java, 2nd Edition", "Kathy Sierra", 2005, "Programming", 5, 5));
            books.add(new Book("Тестирование программного обеспечения Базовый курс Базовый курс (2-е издание)", "Святослав Куликов", 2020, "QA", 5, 5));
            books.add(new Book("Работа с MySQL, MS SQL Server и Oracle в примерах Практическое пособие для программистов и тестировщиков", "Святослав Куликов", 2019, "QA", 5, 5));
            books.add(new Book("How Google Tests Software", "James A. Whittaker, Jason A. Joseph, Jeff Carollo", 2012, "Programming", 5, 5));
            books.add(new Book("Clean Code", "Robert Cecil Martin", 2008, "Programming", 5, 5));
            books.add(new Book("A Practitioner’s Guide to Software Test Design", "Lee Copeland", 2004, "QA", 5, 5));
            books.add(new Book("Тестирование dot com", "Роман Савин", 2007, "QA", 5, 5));
            books.add(new Book("Lessons Learned in Software Testing: A Context-Driven Approach", "Cem Kaner, James Bach, Bret Pettichord", 2002, "QA", 5, 5));
            books.add(new Book("English Grammar in Use, 5th Edition", "Raymond Murphy", 2019, "Grammar", 5, 5));
            books.add(new Book("The Definitive Book of Body Language: How to read others’ attitudes by their gestures", "Allan Pease, Barbara Pease", 2004, "Social Sciences", 5, 5));


            //3. Uploading the test data
            // Sending readers to the database
            String addName = "INSERT INTO readers (name) VALUES (?);";
            testPS = testConnection.prepareStatement(addName);
            for (Reader reader : readers) {
                testPS.setString(1, reader.getName());
                testPS.execute();
            }
            // Sending books to the database
            String addBook = "INSERT INTO books (title, authors, year, topic, total_copies, available_copies) VALUES (?, ?, ?, ?, ?, ?)";
            testPS = testConnection.prepareStatement(addBook);
            for (Book book : books) {
                testPS.setString(1, book.getTitle());
                testPS.setString(2, book.getAuthors());
                testPS.setInt(3, book.getYear());
                testPS.setString(4, book.getTopic());
                testPS.setInt(5, book.getTotalCopies());
                testPS.setInt(6, book.getAvailableCopies());
                testPS.execute();
            }


        } catch (SQLException e) {
            System.out.println(e.getSQLState());
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (testConnection != null)
                    testConnection.close();
                if (testStatement != null)
                    testStatement.close();
                if (testResSet != null)
                    testResSet.close();
                if (testPS != null)
                    testPS.close();
            } catch (SQLException e) {
                System.out.println(e.getSQLState());
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
