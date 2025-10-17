package main.java;

public class Reservation {
    private String userId;
    private String bookId;

    public Reservation(String userId, String bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }

    // Getters

    public String getBookId() {
        return bookId;
    }

    public String getUserId() {
        return userId;
    }

    // Setters

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
