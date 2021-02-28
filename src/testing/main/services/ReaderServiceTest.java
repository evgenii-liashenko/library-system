package main.services;

import main.models.Reader;
import org.junit.BeforeClass;
import org.testng.Assert;
import org.testng.annotations.*;


import static main.command_line_ui.DatabaseSetupScript.setUpDatabase;
import static main.services.ReaderService.*;


public class ReaderServiceTest {

    @BeforeTest
    public void preparationsReader(){
        setUpDatabase();
        System.out.println("The database setup script has finished\n");
    }

    @Test(dependsOnMethods = {"testListAllReadersDaoExchange"})
    public void testAddReaderDaoExchange(){
        //Arrange
        String nameToAdd = "John Dorian";
        int readerCountBefore = listAllReadersDaoExchange().size();
        int lastIdBefore = listAllReadersDaoExchange().get(readerCountBefore - 1).getReaderId();

        //Act
        int generatedId = addReaderDaoExchange(nameToAdd);
        String nameAdded = getReaderByIdDaoExchange(generatedId).getName();
        int readerCountAfter = listAllReadersDaoExchange().size();

        //Assert
        Assert.assertEquals(readerCountBefore + 1, readerCountAfter);
        Assert.assertEquals(nameAdded, nameToAdd);
        Assert.assertEquals(lastIdBefore + 1, generatedId);

    }

    @Test
    public void testGetReaderByIdDaoExchange() {
        System.out.println("testing getting reader information");
        Reader obtainedReader = getReaderByIdDaoExchange(1);
        String obtainedName = obtainedReader.getName();
        Assert.assertEquals(obtainedName, "Jane Doe");
    }

    @Test
    public void testEditReaderDaoExchange() {
        System.out.println("testing reader editing");
        String nameUpdate = "Christopher Turk";
        int testId = 2;
        Reader fixedReader = new Reader(testId, nameUpdate);
        editReaderDaoExchange(fixedReader);
        Assert.assertEquals(getReaderByIdDaoExchange(testId).getName(), nameUpdate);
    }

    @Test(dependsOnMethods = {"testAddReaderDaoExchange"})
    public void testRemoveReaderDaoExchange() {
        System.out.println("testing reader removal");
        int readerCountBefore = listAllReadersDaoExchange().size();
        int lastIdBefore = listAllReadersDaoExchange().get(readerCountBefore - 1).getReaderId();
        String expectedResponseAfter = "";

        removeReaderDaoExchange(lastIdBefore);
        int readerCountAfter = listAllReadersDaoExchange().size();
        int lastIdAfter = listAllReadersDaoExchange().get(readerCountAfter - 1).getReaderId();
        String emptyRecordExpected = getReaderByIdDaoExchange(lastIdBefore).getName();

        Assert.assertEquals(readerCountBefore, readerCountAfter + 1);
        Assert.assertEquals(emptyRecordExpected, "");

    }

    @Test
    public void testListAllReadersDaoExchange() {
        System.out.println("testing listing all readers");
        int readerCount = listAllReadersDaoExchange().size();
        int firstId = listAllReadersDaoExchange().get(0).getReaderId();
        String firstUserName = getReaderByIdDaoExchange(firstId).getName();
        int lastId = listAllReadersDaoExchange().get(readerCount - 1).getReaderId();
        String lastUserName = getReaderByIdDaoExchange(lastId).getName();

        String userNameId5 = getReaderByIdDaoExchange(5).getName();

        Assert.assertEquals(lastId, 10);
        Assert.assertEquals(firstId, 1);
        Assert.assertEquals(firstUserName, "Jane Doe");
        Assert.assertEquals(userNameId5, "Jack Sparrow");
        Assert.assertEquals(lastUserName, "Jill Roberts");

    }
}