package com.example.oop_todo;
import java.time.LocalDate;

public class Task {
    private long id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private boolean isPriority;
    private Status status;
    public enum Status{
        ACTIVE, COMPLETED, TRASHED
    }

    // Constructor
    public Task(long id, String title, String description, LocalDate dueDate, boolean isPriority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isPriority = isPriority;
        this.status = Status.ACTIVE; // Default
    }

    public Task(long id, String title, String description, LocalDate dueDate, boolean isPriority, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.isPriority = isPriority;

        if (status.equals("ACTIVE")){
            this.status = Status.ACTIVE;
        } else if (status.equals("COMPLETED")){
            this.status = Status.COMPLETED;
        } else {
            this.status = Status.TRASHED;
        }
    }

    // Getters and setters for encapsulation
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public boolean getPriority() {
        return isPriority;
    }
    public void setPriority(boolean priority) {
        isPriority = priority;
    }
    public void setStatusActive() {
        this.status = Status.ACTIVE;
    }
    public void setStatusCompleted() {
        this.status = Status.COMPLETED;
    }
    public void setStatusTrashed() {
        this.status = Status.TRASHED;
    }
    public String getStatus() {
        if(status == Status.ACTIVE){
            return "active";
        }

        if(status == Status.COMPLETED){
            return "completed";
        }

        if(status == Status.TRASHED){
            return "trashed";
        }
        return null;
    }

    // Method to convert a task to a string for file saving
    @Override
    public String toString() {
        return id + ";" + title + ";" + description + ";" + dueDate + ";" + isPriority + ";" + status;
    }

    // Static method to create a task from a string
    public static Task fromString(String taskData) {
        String[] parts = taskData.split(";");
        // Assuming the ID is the first part of the stored data
        long id = Long.parseLong(parts[0]);
        return new Task(id, parts[1], parts[2], LocalDate.parse(parts[3]), Boolean.parseBoolean(parts[4]), parts[5]);
    }
}
