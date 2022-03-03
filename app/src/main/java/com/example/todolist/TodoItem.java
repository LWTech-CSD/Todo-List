package com.example.todolist;

public class TodoItem {
    private int id;
    private String text;
    private String date;
    private boolean done;

    public TodoItem(int id, String text, String date, boolean done) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.done = done;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isDone() {
        return done;
    }

    public int getId() {
        return id;
    }
}
