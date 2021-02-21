package main.models;

public class Reader {
    private int readerId;      //null. Do we need it here?
    private String name;

    public Reader(String name) {
        this.name = name;
    }

    public Reader(int readerId, String name) {
        this.readerId = readerId;
        this.name = name;
    }

    public int getReaderId() {
        return readerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
