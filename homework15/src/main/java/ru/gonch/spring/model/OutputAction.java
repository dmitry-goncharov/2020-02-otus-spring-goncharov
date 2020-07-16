package ru.gonch.spring.model;

public class OutputAction {
    private long timestamp;
    private long userId;
    private long bookId;

    private String userName;
    private String bookName;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @Override
    public String toString() {
        return "OutputAction{" +
                "timestamp=" + timestamp +
                ", userId=" + userId +
                ", bookId=" + bookId +
                ", userName='" + userName + '\'' +
                ", bookName='" + bookName + '\'' +
                '}';
    }
}
