package main.java;

public class Book {
    private String id;
    private String title;
    private int copiesAvailable;

    public Book(String id, String title, int copiesAvailable) {
        this.id = id;
        this.title = title;
        this.copiesAvailable = copiesAvailable;
    }

    // Getters

    public String getId() {
        return id;
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public String getTitle() {
        return title;
    }

    // Setters

    public void setCopiesAvailable(int copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
