package main.dao.interfaces;

public interface CrudDaoInterface<MODEL> {
    public boolean add(MODEL valueObject);   //CREATE
    public MODEL getInfo(int model_id);      //READ
    public boolean edit(MODEL valueObject);  //UPDATE
    public boolean remove(int model_id);     //DELETE
}
