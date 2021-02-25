package main.dao.interfaces;
import main.models.Reader;
import java.util.List;

public interface ReaderDaoInterface extends CrudDaoInterface<Reader, Integer> {
    public List<Reader> getAllReaders();
}
