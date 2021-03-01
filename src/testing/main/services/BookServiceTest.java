package main.services;

import main.models.Book;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static main.command_line_ui.DatabaseSetupScript.setUpDatabase;
import static main.services.BookService.*;

public class BookServiceTest {

    @BeforeTest
    public void preparationsBook() {
        setUpDatabase();
        System.out.println("The database setup script has finished\n");
    }

    @Test
    public void testListAllBooksDaoExchange() {
        System.out.println("Testing listing all books");
        int bookCount = listAllBooksDaoExchange().size();
        int firstId = listAllBooksDaoExchange().get(0).getBookId();
        String firstTitle = getBookByIdDaoExchange(firstId).getTitle();
        int lastId = listAllBooksDaoExchange().get(bookCount - 1).getBookId();
        String lastTitle = getBookByIdDaoExchange(lastId).getTitle();

        Assert.assertEquals(firstId, 1);
        Assert.assertEquals(firstTitle, "Head First Java, 2nd Edition");
        Assert.assertEquals(lastId, 10);
        Assert.assertEquals(lastTitle, "The Definitive Book of Body Language: How to read others’ attitudes by their gestures");
    }

    @Test(dependsOnMethods = "testListAllBooksDaoExchange")
    public void testAddBookDaoExchange() {
        System.out.println("testing adding books");
        String theTitle = "Design of Rotating Electrical Machines";
        String theAuthors = "Juha Pyrhonen, Tapani Jokinen, Valeria Hrabovcova";
        int theYear = 2013;
        String theTopic = "Electrical Engineering";
        int theTotalCopies = 4;
        Book bookToAdd = new Book(theTitle, theAuthors, theYear, theTopic, theTotalCopies);
        int lastIdBefore = listAllBooksDaoExchange().get(listAllBooksDaoExchange().size() - 1).getBookId();
        int lastIdAfter = addBookDaoExchange(bookToAdd);
        Book addedBook = getBookByIdDaoExchange(lastIdAfter);

        Assert.assertEquals(lastIdBefore + 1, lastIdAfter);
        Assert.assertEquals(addedBook.getBookId(), lastIdBefore + 1);
        Assert.assertEquals(addedBook.getTitle(), theTitle);
        Assert.assertEquals(addedBook.getAuthors(), theAuthors);
        Assert.assertEquals(addedBook.getTopic(), theTopic);
        Assert.assertEquals(addedBook.getYear(), theYear);
        Assert.assertEquals(addedBook.getTotalCopies(), theTotalCopies);
    }

    @Test
    public void testGetBookByIdDaoExchange() {
        System.out.println("testing getting book description");
        Book obtainedBook = getBookByIdDaoExchange(1);
        Assert.assertEquals(obtainedBook.getTitle(), "Head First Java, 2nd Edition");
        Assert.assertEquals(obtainedBook.getAuthors(), "Kathy Sierra");
        Assert.assertEquals(obtainedBook.getYear(), 2005);
        Assert.assertEquals(obtainedBook.getTopic(), "Programming");
        Assert.assertEquals(obtainedBook.getTotalCopies(), 5);
    }

    @Test
    public void testEditBookDaoExchange() {
        System.out.println("Testing book editing");
        int testId = 9;
        String fixedTitle = "Corrected book title";
        String fixedAuthors = "Corrected authors";
        int fixedYear = 2017;
        String fixedTopic = "New topic for the book";
        int fixedNumberOfCopies = 7;
        Book fixedBook = new Book(testId, fixedTitle, fixedAuthors, fixedYear, fixedTopic, fixedNumberOfCopies);

        editBookDaoExchange(fixedBook);
        Book modifiedBook = getBookByIdDaoExchange(testId);

        Assert.assertEquals(modifiedBook.getBookId(), testId);
        Assert.assertEquals(modifiedBook.getTitle(), fixedTitle);
        Assert.assertEquals(modifiedBook.getAuthors(), fixedAuthors);
        Assert.assertEquals(modifiedBook.getYear(), fixedYear);
        Assert.assertEquals(modifiedBook.getTopic(), fixedTopic);
        Assert.assertEquals(modifiedBook.getTotalCopies(), fixedNumberOfCopies);

    }

    @Test(dependsOnMethods = "testAddBookDaoExchange")
    public void testRemoveBookDaoExchange() {
        System.out.println("Testing book removal");
        int bookCountBefore = listAllBooksDaoExchange().size();
        int lastIdBefore = listAllBooksDaoExchange().get(bookCountBefore - 1).getBookId();

        removeBookDaoExchange(lastIdBefore);
        int bookCountAfter = listAllBooksDaoExchange().size();
        int lastIdAfter = listAllBooksDaoExchange().get(bookCountAfter - 1).getBookId();
        String emptyRecordEXpected = getBookByIdDaoExchange(lastIdBefore).getTitle();

        Assert.assertEquals(bookCountBefore, bookCountAfter + 1);
        Assert.assertEquals(emptyRecordEXpected, "");
    }
}