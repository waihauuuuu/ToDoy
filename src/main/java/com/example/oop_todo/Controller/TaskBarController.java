package com.example.oop_todo.Controller;

import com.example.oop_todo.Task;
import com.example.oop_todo.TaskList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TaskBarController extends Controller{
    @FXML
    private Button btnChecked;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnMoveTrash;

    @FXML
    private Button btnRecover;

    @FXML
    private Label lblDueDate;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblUniqueID;

    @FXML
    private Pane rootPane;

    @FXML
    private VBox vboxTaskBar;

    VBox taskParentContainer;

    DropShadow dropShadow = new DropShadow();

    Task target_task;

    TaskList taskList;

    // Accessing global variable
    private Controller mainPageController;

    // Global variables
    public static String currentDeletingId;

    private double x;
    private double y = 0;

    public void initialize(){ // called the function once addNewTask.fxml have been loaded
        btnChecked.setStyle("-fx-background-color: transparent;");
        btnEdit.setStyle("-fx-background-color: transparent;");
        btnMoveTrash.setStyle("-fx-background-color: transparent;");
        btnRecover.setStyle("-fx-background-color: transparent;");
        btnDelete.setStyle("-fx-background-color: transparent;");

        mainPageController = getInstance();
    }

    public void InheritMainColorTheme(){
        taskList = new TaskList();
        System.out.println(lblUniqueID.getText());
        target_task = taskList.getTaskById(Long.parseLong(lblUniqueID.getText()));
        String currStatus = target_task.getStatus();

        if (isLightMode){
            setLightTheme(currStatus);
        }else{
            setDarkTheme(currStatus);
        }
    }

    public void setLightTheme(String status) {
        setDefault("light");

        switch (status) {
            case "active" -> {
                lblTitle.setTextFill(Color.web(_LightMode.getSecondaryFontColor()));

                if (target_task.getDueDate().isBefore(LocalDate.now())) { // overdue in previous
                    lblDueDate.setTextFill(Color.web("#f20000")); // red font
                } else {
                    lblDueDate.setTextFill(Color.web(_LightMode.getIconColor()));
                }
            }
            case "completed" -> {
                lblTitle.setTextFill(Color.web("#b7b7b7"));
                lblDueDate.setTextFill(Color.web("#b7b7b7"));
                btnEdit.setVisible(false);
                btnMoveTrash.setVisible(true);
                btnDelete.setVisible(false);
                btnRecover.setVisible(false);
                //btnChecked.setDisable(true); // disable the check button from user
                setImageForButton(btnChecked, "/com/example/oop_todo/icons/check.png"); // change the check icon
            }
            case "trashed" -> {
                lblTitle.setTextFill(Color.web(_LightMode.getSecondaryFontColor()));
                lblDueDate.setTextFill(Color.web(_LightMode.getSecondaryFontColor()));
                btnChecked.setVisible(false);
                btnMoveTrash.setVisible(false);
                btnEdit.setVisible(false);
                btnRecover.setVisible(true);
                btnDelete.setVisible(true);
            }
            default -> setDefault("light");
        }
    }

    public void setDarkTheme(String status) {
        setDefault("dark");
        switch (status) {
            case "active" -> {
                lblTitle.setTextFill(Color.web(_DarkMode.getPrimaryFontColor()));
                if (target_task.getDueDate().isBefore(LocalDate.now())) { // overdue in previous
                    lblDueDate.setTextFill(Color.web("#ff605c")); // red font
                } else {
                    lblDueDate.setTextFill(Color.web(_DarkMode.getIconColor()));
                }
            }
            case "completed" -> {
                lblTitle.setTextFill(Color.web(_LightMode.getTertiaryFontColor()));
                lblDueDate.setTextFill(Color.web(_LightMode.getTertiaryFontColor()));
                btnEdit.setVisible(false);
                btnMoveTrash.setVisible(true);
                btnRecover.setVisible(false);
                btnDelete.setVisible(false);
                //btnChecked.setDisable(true); // disable the check button from user
                setImageForButton(btnChecked, "/com/example/oop_todo/icons/check.png"); // change the check icon
            }
            case "trashed" -> {
                lblTitle.setTextFill(Color.web(_DarkMode.getPrimaryFontColor()));
                lblDueDate.setTextFill(Color.web(_DarkMode.getPrimaryFontColor()));
                btnChecked.setVisible(false);
                btnMoveTrash.setVisible(false);
                btnEdit.setVisible(false);
                btnRecover.setVisible(true);
                btnDelete.setVisible(true);
            }
            default -> setDefault("dark");
        }
    }

    private void setDefault(String mode){
        btnChecked.setVisible(true);
        btnEdit.setVisible(true);
        btnMoveTrash.setVisible(true);
        btnRecover.setVisible(false);
        btnDelete.setVisible(false);

        if (mode.equals("light")){
            vboxTaskBar.setStyle(
                    "-fx-background-color:" +  _LightMode.getAnchorPaneColor() + ";" +
                    "-fx-border-color: " + _LightMode.getAppBarColor() + ";" +
                    "-fx-border-radius: 10;" +
                    "-fx-background-radius: 10;"
            );

            changeDropShadowColor(Color.web("#DDDDDD"));
            lblTitle.setTextFill(Color.web(_LightMode.getSecondaryFontColor()));
            lblDueDate.setTextFill(Color.web(_LightMode.getIconColor()));
            setImageForButton(btnChecked, "/com/example/oop_todo/icons/light_mode/uncheck-light.png");
            setImageForButton(btnEdit, "/com/example/oop_todo/icons/light_mode/edit-light.png");
            setImageForButton(btnMoveTrash, "/com/example/oop_todo/icons/light_mode/trash-light.png");
            setImageForButton(btnDelete, "/com/example/oop_todo/icons/light_mode/delete-light.png");
            setImageForButton(btnRecover, "/com/example/oop_todo/icons/light_mode/recover-light.png");

        }else{ // dark mode default
            vboxTaskBar.setStyle(
                    "-fx-background-color: " + _DarkMode.getAnchorPaneColor() + ";" +
                    "-fx-border-color: " + _DarkMode.getAppBarColor() + ";" +
                    "-fx-border-radius: 10;" +
                    "-fx-background-radius: 10;"
            );
            changeDropShadowColor(Color.web("#282525"));
            lblTitle.setTextFill(Color.web(_DarkMode.getPrimaryFontColor()));
            lblDueDate.setTextFill(Color.web(_DarkMode.getIconColor()));
            setImageForButton(btnChecked, "/com/example/oop_todo/icons/dark_mode/uncheck-dark.png");
            setImageForButton(btnEdit, "/com/example/oop_todo/icons/dark_mode/edit-dark.png");
            setImageForButton(btnMoveTrash, "/com/example/oop_todo/icons/dark_mode/trash-dark.png");
            setImageForButton(btnDelete, "/com/example/oop_todo/icons/dark_mode/delete-dark.png");
            setImageForButton(btnRecover, "/com/example/oop_todo/icons/dark_mode/recover-dark.png");

        }
    }

    // Handle the change of image for button
    private void setImageForButton(Button button, String imagePath) {
        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(36); // Set the width of the image view
            imageView.setFitHeight(36); // Set the height of the image view
            imageView.setTranslateX(-12.5);
            button.setGraphic(imageView); // Set the image view as the graphic for the button
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeDropShadowColor (Color color) {
        DropShadow dropShadow = (DropShadow) rootPane.getEffect();
        dropShadow.setColor(color);
        // Apply the drop shadow effect to the button
        rootPane.setEffect(dropShadow);
    }

    public void setLblUniqueID(long UniqueID){
        lblUniqueID.setText(String.valueOf(UniqueID));
    }

    public void setLblTitle(String title){
        lblTitle.setText(title);
    }

    public void setLblDueDate(LocalDate DueDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Define the date format
        String dateString = DueDate.format(formatter);
        lblDueDate.setText(dateString);
    }

    public String getLblUniqueID(){
        return lblUniqueID.getText();
    }

    public String getLblTitle(){
        return lblTitle.getText();
    }

    public String getLblDueDate(){
        return lblDueDate.getText();
    }

    // Method to get handle on the check event
    @FXML
    void onClickCheckButton(ActionEvent event) throws IOException {
        if (lblUniqueID.getText() != null){
            taskList.completeTask(Long.parseLong(lblUniqueID.getText()));
            createAnimationPane();
            InheritMainColorTheme();
        }
    }

    private void createAnimationPane() throws IOException {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oop_todo/fxml/CheckAnimation.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);
        // Show the stage
        stage.show();

        // Create a timeline to close the stage after 2 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            stage.close(); // Close the stage after 2 seconds
        }));
        timeline.play();
    }


    @FXML
    void onClickEditButton(ActionEvent event) throws IOException {
        Add_Check_Update_Delete_TaskController.openType = "update";
        if (lblUniqueID.getText() != null){
            String taskData = (taskList.getTaskById(Long.parseLong(lblUniqueID.getText()))).toString();
            mainPageController.createForm(taskData);
            InheritMainColorTheme();
        }
    }

    @FXML
    void onClickTrashButton(ActionEvent event) throws IOException {
        // get the unique id of the task
        if (lblUniqueID.getText() != null){
            taskList.moveToTrash(Long.parseLong(lblUniqueID.getText()));
            // call the messagebox and get response
            String title = taskList.getTaskById(Long.parseLong(lblUniqueID.getText())).getTitle();
            // call the messagebox and get response
            String text = "Hi! I'm ToDoy!\nYou have remove the task: [" + title + "].\nYou have a chance to recover it from Trash Page!";
            createReminderMessageBox("Reminder: Remove Task", text);
        }
    }

    @FXML
    void onClickRecoverButton(ActionEvent event) throws IOException {
        // get the unique id of the task
        if (lblUniqueID.getText() != null){
            taskList.recoverFromTrash(Long.parseLong(lblUniqueID.getText()));
            String title = taskList.getTaskById(Long.parseLong(lblUniqueID.getText())).getTitle();
            // call the messagebox and get response
            String text = "Hi! I'm ToDoy!\nYou have recovered the task: [" + title + "].";
            createReminderMessageBox("Reminder: Recover Task", text);
        }
    }
    private void createReminderMessageBox(String title, String description) throws IOException {
        FXMLLoader loader = new FXMLLoader(TaskBarController.class.getResource("/com/example/oop_todo/fxml/customMessageBox.fxml"));
        Parent parent = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);

        MessageBoxController msgBoxController = loader.getController();
        msgBoxController.setLblAppTitle(title);
        msgBoxController.setLblDescription(description);

        //move around
        parent.setOnMousePressed(evt ->{
            x = evt.getSceneX();
            y = evt.getSceneY();
        });

        parent.setOnMouseDragged(evt ->{
            stage.setX(evt.getScreenX()- x);
            stage.setY(evt.getScreenY()- y);
        });

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    private void createAlertMessageBox(String description) throws IOException {
        FXMLLoader loader = new FXMLLoader(TaskBarController.class.getResource("/com/example/oop_todo/fxml/customMessageBox.fxml"));
        Parent parent = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);

        MessageBoxController msgBoxController = loader.getController();
        msgBoxController.getBtnOK().setVisible(false);
        msgBoxController.getBtnYes().setVisible(true);
        msgBoxController.getBtnNo().setVisible(true);
        msgBoxController.setOpenButton("btnDelete");
        msgBoxController.setLblAppTitle("Alert: Delete Task");
        msgBoxController.setLblDescription(description);

        //move around
        parent.setOnMousePressed(evt ->{
            x = evt.getSceneX();
            y = evt.getSceneY();
        });

        parent.setOnMouseDragged(evt ->{
            stage.setX(evt.getScreenX()- x);
            stage.setY(evt.getScreenY()- y);
        });

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    @FXML
    void onClickDeleteButton(ActionEvent event) throws IOException {
        // get the unique id of the task
        if (lblUniqueID.getText() != null){
            String title = taskList.getTaskById(Long.parseLong(lblUniqueID.getText())).getTitle();
            // call the messagebox and get response
            String text = "Are you sure to permanently delete the task: [" + title + "]?";
            createAlertMessageBox(text);
            currentDeletingId = lblUniqueID.getText();
        }
    }

    public void loadTrashOnTaskBar(String labelUniqueID) {
        TaskList.tasks = new ArrayList<>();
        String filename = "src/main/resources/com/example/oop_todo/text_file/tasks.txt";
        String dataLine;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ((dataLine = br.readLine()) != null) {
                // Skip empty lines or lines that do not have the expected format
                if (!dataLine.trim().isEmpty() && dataLine.split(";").length == 6) { // Assuming 5 fields: id, title, description, dueDate, isPriority
                    String[] parts = dataLine.split(";");
                    String id = parts[0].trim(); // Accessing the sixth part after split
                    if (!id.equals(labelUniqueID)) {
                        System.out.println("not deleting: " + dataLine);           /* Use for debug the data in text file */
                        TaskList.tasks.add(Task.fromString(dataLine));
                    } else {
                        System.out.println("deleting: " + dataLine);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveTrashOnTaskBar() {
        String filename = "src/main/resources/com/example/oop_todo/text_file/tasks.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename , false))) {
            for (Task task : TaskList.tasks) {
                bw.write(task.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
