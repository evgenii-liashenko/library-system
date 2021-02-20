package main.dao.interfaces;

public interface CrudDaoInterface<MODEL, ID> {
    public boolean add(MODEL valueObject);  //CREATE
    public MODEL getInfo(ID model_id);      //READ
    public boolean edit(MODEL valueObject); //UPDATE
    public boolean remove(ID model_id);     //DELETE
}
