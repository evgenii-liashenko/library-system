package main.services;

import main.dao.implementations.mysql.ConnectionManager;
import main.dao.implementations.mysql.ReaderDaoMysqlImpl;
import main.dao.interfaces.ReaderDaoInterface;
import main.models.Reader;

import java.util.Scanner;

import static main.command_line_ui.UserInput.*;


public class ReaderService {
    //this method exchanges data with the commandline layer
    public static void addReaderUiExchange() {
        String name = inputTextFromUser("Enter full name:");
        String uiMessage = (addReaderDaoExchange(name)) ? ("Successful operation") : ("Operation failed");
        System.out.println(uiMessage);
    }

    //this method exchanges data with the DAO layer
    public static boolean addReaderDaoExchange(String name) {
        ConnectionManager sqlDataBase = new ConnectionManager();
        ReaderDaoInterface readerDaoImplementation = new ReaderDaoMysqlImpl(sqlDataBase.openConnection());
        boolean successfulOperation = readerDaoImplementation.add(new Reader(name));
        sqlDataBase.closeConnection();
        return successfulOperation;
    }


    public static void getReaderByIdUiExchange() {
        int reader_id = inputNumberFromUser("Enter reader id:");
        Reader theReader = getReaderByIdDaoExchange(reader_id);
        String uiMessage = (theReader.getName().equals("")) ?
                ("Reader not found") : ("Reader with id " + reader_id + " has full name " + theReader.getName());
        System.out.println(uiMessage);
    }

    public static Reader getReaderByIdDaoExchange(int reader_id) {
        ConnectionManager sql = new ConnectionManager();
        ReaderDaoInterface readerDaoImplementation = new ReaderDaoMysqlImpl(sql.openConnection());
        return readerDaoImplementation.getInfo(reader_id);
    }


    public static void editReaderUiExchange() {
        //gathering data to build a new value object with correct values
        int readerId = inputNumberFromUser("Enter the id of the reader to edit:");
        String newName = inputTextFromUser("Enter a new name for that reader:");

        boolean successfulOperation = editReaderDaoExchange(new Reader(readerId, newName));
        String uiMessage = successfulOperation? ("Name for the reader with id "
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


}

