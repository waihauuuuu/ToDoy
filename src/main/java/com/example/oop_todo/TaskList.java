package com.example.oop_todo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Comparator;

public class TaskList implements Persistable{
    public static ArrayList<Task> tasks = new ArrayList<>();
    private final String filename = "src/main/resources/com/example/oop_todo/text_file/tasks.txt";

    // Constructor
    public TaskList() {
        load();
    }

    // Implementing the Persistable interface methods
    @Override
    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename , false))) {
            for (Task task : tasks) {
                bw.write(task.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {
        // load through all data in text file
        tasks = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String dataLine;
            while ((dataLine = br.readLine()) != null) {
                // Skip empty lines or lines that do not have the expected format
                if (!dataLine.trim().isEmpty() && dataLine.split(";").length == 6) { // Assuming 5 fields: id, title, description, dueDate, isPriority
                    try {
                        //System.out.println(dataLine);           /* Use for debug the data in text file */
                        tasks.add(Task.fromString(dataLine));
                    } catch (Exception e) {
                        System.err.println("Skipping invalid line: " + dataLine);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Additional methods for task list manipulation
    // Generate a unique ID for a new task
    private long generateUniqueID() {
        if (tasks.isEmpty()) {
            return 1; // Start IDs from 1 if no tasks exist
        } else {
            // Find the maximum ID and add 1 to it to ensure uniqueness
            return tasks.stream()
                    .max(Comparator.comparingLong(Task::getId))
                    .map(task -> task.getId() + 1)
                    .orElse(1L);
        }
    }

    public void addTask(String title, String description, LocalDate dueDate, boolean isPriority) {
        // Load tasks from file to ensure we have the latest list and to compute the next unique ID accurately
        load();
        long uniqueId = generateUniqueID(); // Generate a unique ID
        Task newTask = new Task(uniqueId, title, description, dueDate, isPriority);
        tasks.add(newTask);

        // write the new task into text file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            bw.write(newTask.toString());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //NOT USED YET
    public void updateTask(long id, String title, String description, LocalDate dueDate, boolean isPriority) {
        // Find the task by ID
        load();
        for (Task task : tasks) {
            if (task.getId() == id) {
                // Update task properties
                task.setTitle(title);
                task.setDescription(description);
                task.setDueDate(dueDate);
                task.setPriority(isPriority);
                task.setStatusActive(); // set back the task status to active once updated
                break; // Exit the loop once the task is found and updated
            }
        }
        save(); // Save the updated tasks list to the file
    }

    public void completeTask(long id) {
        // Find the task by ID
        for (Task task : tasks) {
            if (task.getId() == id && task.getStatus().equals("active")) { // have same id and its status is active
                // Update task status
                task.setStatusCompleted(); // set to complete as checked
                break; // Exit the loop once the task is found and updated
            }
        }
        save(); // Save the updated tasks list to the file
    }

    public void moveToTrash(long id) {
        // Find the task by ID
        for (Task task : tasks) {
            if (task.getId() == id) {
                // Update task status
                task.setStatusTrashed(); // set to complete as checked
                break; // Exit the loop once the task is found and updated
            }
        }
        save(); // Save the updated tasks list to the file
    }

    public void recoverFromTrash(long id) {
        // Find the task by ID
        for (Task task : tasks) {
            if (task.getId() == id) {
                // Update task status
                task.setStatusActive(); // set to complete as checked
                break; // Exit the loop once the task is found and updated
            }
        }
        save(); // Save the updated tasks list to the file
    }

    //NOT USED YET
    public Task getTaskById(long id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null; // Return null or throw an exception if the task is not found
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        save();
    }

    public static ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
}
