package main.models;

public class Reader {
    private int reader_id;      //null. Do we need it here?
    private String name;

    public Reader(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
