package ru.gonch.spring.model;

public class InputAction {
    private long timestamp;
    private ActionType type;
    private long userId;
    private long bookId;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "InputAction{" +
                "timestamp=" + timestamp +
                ", type=" + type +
                ", userId=" + userId +
                ", bookId=" + bookId +
                '}';
    }
}