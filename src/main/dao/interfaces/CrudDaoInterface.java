package main.dao.interfaces;

public interface CrudDaoInterface<ModelType, ResponseType> {
    public ResponseType add(ModelType valueObject);   //CREATE
    public ModelType getInfo(int model_id);      //READ
    public boolean edit(ModelType valueObject);  //UPDATE
    public boolean remove(int model_id);     //DELETE
}

