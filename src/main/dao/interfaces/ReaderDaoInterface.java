package main.dao.interfaces;

import main.models.Reader;
import java.util.List;

public interface ReaderDaoInterface extends CrudDaoInterface<Reader> {
    public List<Reader> getAllReaders();
}




//boolean methods return true if the operation was successful
/* CREATE: receives a value object of Reader class and sends its values to the SQL database
 * READ: receives [reader_id], goes to the database, then creates and returns a value object of Reader class
 * UPDATE: receives a value object of Reader class with reader_id and other fields,
 * goes to the database and overwrites things
 * DELETE: receives reader_id, goes to the database and deletes a row with that id
 * DELETE should be impossible if the reader has ACTIVE orders
 */

//    public boolean addReader(Reader valueObject);   //CRETE
//    public Reader getReaderInfo(int reader_id);     //READ
//    public boolean editReader(Reader valueObject);  //UPDATE
//    public boolean removeReader(int reader_id);     //DELETE