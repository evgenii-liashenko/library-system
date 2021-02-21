package main.services;

import main.dao.implementations.mysql.ConnectionManager;
import main.dao.implementations.mysql.ReaderDaoMysqlImpl;
import main.dao.interfaces.ReaderDaoInterface;
import main.models.Reader;

import java.util.Scanner;


public class ReaderService {
    //this method exchanges data with the commandline layer
    public static void addReaderUiExchange(){
        System.out.println("Enter full name:");
        String name = null;
        Scanner nameScanner = new Scanner(System.in);
        while (name == null){
            name = nameScanner.nextLine();
        }
        nameScanner.close();
        String uiMessage = (addReaderDaoExchange(name))? ("Successfull operation") : ("Operation failed");
        System.out.println(uiMessage);
    }

    //this method exchanges data with the DAO layer
    public static boolean addReaderDaoExchange(String name){
        ConnectionManager sqlDataBase = new ConnectionManager();
        ReaderDaoInterface readerDaoImplementation = new ReaderDaoMysqlImpl(sqlDataBase.openConnection());
        boolean successfulOperation = readerDaoImplementation.add(new Reader(name));
        sqlDataBase.closeConnection();
        return successfulOperation;
    }
}
