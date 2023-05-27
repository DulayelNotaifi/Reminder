package com.example.notification;

public class Event {
    private int id;
    private String name;
    private String type;
    private String date;
    private String time;
    private String priority;
    private String notes;

    public Event(int id, String name, String type, String date, String time, String priority, String notes) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.date = date;
        this.time = time;
        this.priority = priority;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPriority() {
        return priority;
    }

    public String getNotes() {
        return notes;
    }
}