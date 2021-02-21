package main.models;

public class Book {
    private int book_id;        //null. Do we need it here?
    private String title;
    private String authors;
    private int year;
    private String topic;
    private int totalCopies;
    //private int availableCopies;






    public Book(String title, String authors, int year, String topic, int totalCopies, int availableCopies) {
        this.title = title;
        this.authors = authors;
        this.year = year;
        this.topic = topic;
        this.totalCopies = totalCopies;
      //  this.availableCopies = availableCopies;
    }

//    public int getBook_id() {
//        return book_id;
//    }
//
//    public void setBook_id(int book_id) {
//        this.book_id = book_id;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

//    public int getAvailableCopies() {
//        return availableCopies;
//    }

//    public void setAvailableCopies(int availableCopies) {
//        this.availableCopies = availableCopies;
//    }
}
