package main.command_line_ui;

import main.models.Book;
import main.models.BookOrder;
import main.models.BookOrderStatus;
import main.models.Reader;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSetupScript {

    public static void setUpDatabase() {
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            //1.1 Creating a connection to a local MySQL server
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306",
                    "root",
                    "solarwinds123");
            String conStatus = (connection != null) ? ("Successful connection") : ("Connection failed :(");
            System.out.println(conStatus);


            //1.2 Creating a database
            statement = connection.createStatement();
            statement.execute("DROP DATABASE IF EXISTS library_system;");
            if (statement.executeUpdate("CREATE DATABASE library_system;") == 1)
                System.out.println("Database library_system has been created");;
            statement.execute("USE library_system;");


            //1.3 Creating empty tables
            String createReadersTable = "CREATE TABLE readers (reader_id INT NOT NULL AUTO_INCREMENT" +
                    " PRIMARY KEY, name TINYTEXT);";
            String createBooksTable = "CREATE TABLE books (\n" +
                    "book_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                    "title TINYTEXT, authors TINYTEXT, year INT,\n" +
                    "topic TINYTEXT, total_copies INT, copies_in_stock INT);";
            String createTakenBooksTableWithId = "CREATE TABLE taken_books (\n" +
                    "order_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                    "book_id INT NOT NULL, FOREIGN KEY (book_id) REFERENCES books (book_id),\n" +
                    "reader_id INT NOT NULL, FOREIGN KEY (reader_id) REFERENCES readers (reader_id),\n" +
                    "order_date DATE, return_by DATE," +
                    "order_status ENUM(\"ACTIVE\", \"RETURNED\") NOT NULL DEFAULT \"ACTIVE\");";
//            String createTakenBooksTableNoId = "CREATE TABLE taken_books (\n" +
//                    "book_id INT NOT NULL, FOREIGN KEY (book_id) REFERENCES books (book_id),\n" +
//                    "reader_id INT NOT NULL, FOREIGN KEY (reader_id) REFERENCES readers (reader_id),\n" +
//                    "PRIMARY KEY (book_id, reader_id), order_date DATE, return_by DATE," +
//                    "order_status ENUM(\"ACTIVE\", \"RETURNED\") NOT NULL DEFAULT \"ACTIVE\");";
            statement.execute(createReadersTable);
            statement.execute(createBooksTable);
            statement.execute(createTakenBooksTableWithId);



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
            books.add(new Book("Head First Java, 2nd Edition", "Kathy Sierra", 2005, "Programming", 5));
            books.add(new Book("Тестирование программного обеспечения Базовый курс Базовый курс (2-е издание)", "Святослав Куликов", 2020, "QA", 5));
            books.add(new Book("Работа с MySQL, MS SQL Server и Oracle в примерах Практическое пособие для программистов и тестировщиков", "Святослав Куликов", 2019, "QA", 5));
            books.add(new Book("How Google Tests Software", "James A. Whittaker, Jason A. Joseph, Jeff Carollo", 2012, "Programming", 5));
            books.add(new Book("Clean Code", "Robert Cecil Martin", 2008, "Programming", 5));
            books.add(new Book("A Practitioner’s Guide to Software Test Design", "Lee Copeland", 2004, "QA", 5));
            books.add(new Book("Тестирование dot com", "Роман Савин", 2007, "QA", 5));
            books.add(new Book("Lessons Learned in Software Testing: A Context-Driven Approach", "Cem Kaner, James Bach, Bret Pettichord", 2002, "QA", 5));
            books.add(new Book("English Grammar in Use, 5th Edition", "Raymond Murphy", 2019, "Grammar", 5));
            books.add(new Book("The Definitive Book of Body Language: How to read others’ attitudes by their gestures", "Allan Pease, Barbara Pease", 2004, "Social Sciences", 5));
            //Orders
            LocalDate today = LocalDate.now();
            LocalDate returnDate = today.plusMonths(6);
            List<BookOrder> orders = new ArrayList<>();
            for (int i = 0; i < readers.size(); i++) {
                BookOrder order = new BookOrder(books.get(i), readers.get(i), today, returnDate, BookOrderStatus.ACTIVE);
                orders.add(order);
            }
            //TODO gotta add boodId and readerId to orders




            //3. Uploading the test data
            // Adding readers to the database
            String addReader = "INSERT INTO readers (name) VALUES (?);";
            preparedStatement = connection.prepareStatement(addReader, Statement.RETURN_GENERATED_KEYS);
            for (Reader reader : readers) {
                preparedStatement.setString(1, reader.getName());
                preparedStatement.execute();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                while (generatedKeys.next()){
                    int readerId = generatedKeys.getInt(1);
                    reader.setReaderId(readerId);
                }
            }


            // Adding books to the database
            String addBook = "INSERT INTO books (title, authors, year, topic, total_copies, copies_in_stock) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(addBook, Statement.RETURN_GENERATED_KEYS);
            for (Book book : books) {
                preparedStatement.setString(1, book.getTitle());
                preparedStatement.setString(2, book.getAuthors());
                preparedStatement.setInt(3, book.getYear());
                preparedStatement.setString(4, book.getTopic());
                preparedStatement.setInt(5, book.getTotalCopies());
                preparedStatement.setInt(6, book.getTotalCopies());
                preparedStatement.execute();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                while (generatedKeys.next()){
                    int bookId = generatedKeys.getInt(1);
                    book.setBookId(bookId);
                }
            }
            // Adding orders to the database
            String addOrder = "INSERT INTO taken_books (book_id, reader_id, order_date, return_by, order_status) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(addOrder);
            for (BookOrder order: orders) {
                preparedStatement.setInt(1, order.getBook().getBookId());   //TODO where will id come from?
                preparedStatement.setInt(2, order.getReader().getReaderId());
                preparedStatement.setDate(3, Date.valueOf(today));
                preparedStatement.setDate(4, Date.valueOf(returnDate));
                preparedStatement.setString(5, order.getOrderStatus().name());
                preparedStatement.execute();
            }


        } catch (SQLException e) {
            System.out.println(e.getSQLState() + "\n" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
                if (statement != null) statement.close();
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
            } catch (SQLException e) {
                System.out.println(e.getSQLState() + "\n" + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
