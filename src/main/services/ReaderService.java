package main.services;

import main.dao.implementations.mysql.ConnectionManager;
import main.dao.implementations.mysql.ReaderDaoMysqlImpl;
import main.dao.interfaces.ReaderDaoInterface;
import main.models.Reader;

import java.util.List;

import static main.command_line_ui.Menu.*;


public class ReaderService {
    //this method exchanges data with the commandline layer
    public static void addReaderUiExchange()  {
        String name = inputTextFromUser("Enter full name:");
        Integer generatedId = addReaderDaoExchange(name);
        String uiMessage = "Reader " + name + " has been added to the library and assigned id " + generatedId.toString();
        System.out.println(uiMessage);
    }

    //this method exchanges data with the DAO layer
    public static Integer addReaderDaoExchange(String name)  {
        ConnectionManager sqlDataBase = new ConnectionManager();
        ReaderDaoInterface readerDaoImplementation = new ReaderDaoMysqlImpl(sqlDataBase.openConnection());
        Integer generatedId = readerDaoImplementation.add(new Reader(name));
        sqlDataBase.closeConnection();
        return generatedId;
    }



    public static void getReaderByIdUiExchange() {
        int readerId = inputNumberFromUser("Enter reader id:");
        Reader theReader = getReaderByIdDaoExchange(readerId);
        String uiMessage = (theReader.getName().equals("")) ?
                ("Reader not found") : ("Reader with id " + readerId + " has full name " + theReader.getName());
        System.out.println(uiMessage);
    }

    public static Reader getReaderByIdDaoExchange(int readerId) {
        ConnectionManager sql = new ConnectionManager();
        ReaderDaoInterface readerDaoImplementation = new ReaderDaoMysqlImpl(sql.openConnection());
        return readerDaoImplementation.getInfo(readerId);
    }


    public static void editReaderUiExchange() {
        //gathering data to build a new value object with correct values
        int readerId = inputNumberFromUser("Enter the id of the reader to edit:");
        String newName = inputTextFromUser("Enter a new name for that reader:");

        boolean successfulOperation = editReaderDaoExchange(new Reader(readerId, newName));
        String uiMessage = successfulOperation ? ("Name for the reader with id "
                + readerId + " has been set to " + newName) : "Operation failed";
        System.out.println(uiMessage);
    }

    public static boolean editReaderDaoExchange(Reader fixedReader) {
        int readerId = fixedReader.getReaderId();
        String newName = fixedReader.getName();
        ConnectionManager sql = new ConnectionManager();
        ReaderDaoInterface readerDaoImplementation = new ReaderDaoMysqlImpl(sql.openConnection());
        boolean successfulOperation = readerDaoImplementation.edit(new Reader(readerId, newName));
        return successfulOperation;
    }


    public static void removeReaderUiExchange() {
        int readerId = inputNumberFromUser("Enter the id of the reader to remove:");
        String userName = getReaderByIdDaoExchange(readerId).getName();
        ConnectionManager sql = new ConnectionManager();
        ReaderDaoInterface readerDaoImplementation = new ReaderDaoMysqlImpl(sql.openConnection());
        boolean successfulOperation = readerDaoImplementation.remove(readerId);
        String uiMessage = successfulOperation ? ("Reader " + userName + " has been removed from the library") :
                ("Operation failed. Make sure " + userName + " has no active orders");  //TODO Make this actially work so that only the users who have ACTIVE orders cannot be removed
        System.out.println(uiMessage);
    }

    public static boolean removeReaderDaoExchange(int readerId) {
        ConnectionManager sql = new ConnectionManager();
        ReaderDaoInterface readerDaoImplementation = new ReaderDaoMysqlImpl(sql.openConnection());
        boolean successfulOperation = readerDaoImplementation.remove(readerId);
        return successfulOperation;
    }


    public static void listAllReadersUiExchange() {
        List<Reader> readers = listAllReadersDaoExchange();
        for (Reader reader : readers) {
            System.out.println(reader.getReaderId() + "[id]" + "\t\t" + reader.getName() + "[Full name]");
        }
    }

    public static List<Reader> listAllReadersDaoExchange() {
        ConnectionManager sql = new ConnectionManager();
        ReaderDaoInterface readerDaoImplementation = new ReaderDaoMysqlImpl(sql.openConnection());
        return readerDaoImplementation.getAllReaders();
    }
}

