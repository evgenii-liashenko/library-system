package main.dao.interfaces;


import java.sql.SQLException;

public interface CrudDaoInterface<ModelType, ResponseType> {
    public ResponseType add(ModelType valueObject) throws SQLException;   //CREATE
    public ModelType getInfo(int model_id);      //READ
    public boolean edit(ModelType valueObject);  //UPDATE
    public boolean remove(int model_id);     //DELETE
}



//public interface CrudDaoInterface<MODEL> {
//    public boolean add(MODEL valueObject);   //CREATE
//    public MODEL getInfo(int model_id);      //READ
//    public boolean edit(MODEL valueObject);  //UPDATE
//    public boolean remove(int model_id);     //DELETE
//}