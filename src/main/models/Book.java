package main.models;

public class Book {
    private int bookId;
    private String title;
    private String authors;
    private int year;
    private String topic;
    private int totalCopies;

    public Book() {
    }

    public Book(int bookId, String title, String authors, int year, String topic, int totalCopies) {
        this.bookId = bookId;
        this.title = title;
        this.authors = authors;
        this.year = year;
        this.topic = topic;
        this.totalCopies = totalCopies;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public Book(String title, String authors, int year, String topic, int totalCopies) {
        this.title = title;
        this.authors = authors;
        this.year = year;
        this.topic = topic;
        this.totalCopies = totalCopies;
    }

    public int getBookId() {
        return bookId;
    }

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


}
