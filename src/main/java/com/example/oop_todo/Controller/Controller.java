package com.example.oop_todo.Controller;

import com.example.oop_todo.Task;
import com.example.oop_todo.TaskList;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Controller {
    //Create objects
    @FXML
    protected ProgressBar progressBar;

    @FXML
    protected Label lblProgress;

    @FXML
    protected VBox vboxAppBar;

    @FXML
    protected VBox vboxSide;

    @FXML
    protected AnchorPane parentPane;

    @FXML
    protected Label lblTitle;

    @FXML
    protected Button btnClose;

    @FXML
    protected Button btnMax;

    @FXML
    protected Button btnMin;

    @FXML
    protected Button btnToday = new Button();

    @FXML
    protected Button btnImportant = new Button();

    @FXML
    protected Button btnPrevious = new Button();

    @FXML
    protected Button btnSomeday = new Button();

    @FXML
    protected Button btnTrash = new Button();

    @FXML
    protected Button btnAddTask;

    @FXML
    protected Button btnClear;

    @FXML
    protected Button btnMode;
    @FXML
    protected ImageView myImageView;

    @FXML
    protected VBox vboxTaskContainer = new VBox();

    @FXML
    protected ScrollPane sclTaskList;

    @FXML
    private ImageView imgClose;

    @FXML
    private ImageView imgMax;

    @FXML
    private ImageView imgMin;

    TaskList taskList = new TaskList();

    public static ArrayList<TaskBarController> taskControllers;

    // set the color scheme of light and dark theme
    protected final LightModeColorScheme _LightMode = new LightModeColorScheme();
    protected final DarkModeColorScheme _DarkMode = new DarkModeColorScheme();

    // Set the default to Light Theme
    protected static boolean isLightMode = true;

    protected static Controller instance; // create an instance of Controller class

    private SwitchTheme st;

    public static String currentFocusedPage = "today";

    private TaskBarController taskBarController;

    private Add_Check_Update_Delete_TaskController fc;

    // set the initial coordinate (x,y) of form
    private static double x;
    private static double y = 0;

    public static Stage mainStage;

    // Invoke the method once main_page.fxml have been loaded in Main.java
    public void initialize(Stage stage) throws IOException {
        // initialize the effect method for all the button in the program
        SideBarButton_Effect(btnToday);
        SideBarButton_Effect(btnImportant);
        SideBarButton_Effect(btnPrevious);
        SideBarButton_Effect(btnSomeday);
        SideBarButton_Effect(btnTrash);
        AppBarButton_Effect(btnClose);
        AppBarButton_Effect(btnMin);
        AppBarButton_Effect(btnMax);
        AddNewTaskButton_Effect(btnAddTask);
        SwitchModeButton_Effect(btnMode);
        ClearButton_Effect(btnClear);

        mainStage = stage;
        taskBarController = new TaskBarController();

        mainStage.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) { // Check if the main scene is focused
                System.err.println("Main Page is focused again");
                try {
                    if (MessageBoxController.currTaskBarBtnYesResponse){ // if true
                        taskBarController.loadTrashOnTaskBar(TaskBarController.currentDeletingId);
                        taskBarController.saveTrashOnTaskBar();
                        MessageBoxController.currTaskBarBtnYesResponse = false;
                    }

                    if (MessageBoxController.currMainPageBtnClearResponse){ // if true
                        loadTrash();
                        saveTrash();
                        MessageBoxController.currMainPageBtnClearResponse = false;
                    }
                    reloadMainPageState(currentFocusedPage);
                } catch (IOException e) {
                    e.printStackTrace(); // Handle the exception appropriately
                }
            }
        });

        taskList = new TaskList();

        vboxTaskContainer.setPadding(new javafx.geometry.Insets(10));
        displayTasks(currentFocusedPage);

        // put all the control that affected by the switchTheme method
        st = new SwitchTheme(lblTitle, btnToday, btnImportant, btnPrevious, btnSomeday, btnTrash, btnClear,
                btnMode, btnAddTask, parentPane, vboxSide, vboxAppBar, myImageView, progressBar,
                vboxTaskContainer, sclTaskList);
        fc = new Add_Check_Update_Delete_TaskController(btnToday, btnImportant, btnPrevious,
                btnSomeday, btnTrash);
    }

    // Method to switch the color theme on main_page.fxml
    @FXML
    private void onClickSwitchMode(ActionEvent event){
        isLightMode = !isLightMode;
        // Selection statement for switching theme
        if (isLightMode){ // light mode theme
            st.setLightTheme();
            for (TaskBarController tc : taskControllers){
                tc.InheritMainColorTheme();
            }
        } else { // dark mode theme
            st.setDarkTheme();
            for (TaskBarController tc : taskControllers){
                tc.InheritMainColorTheme();
            }
        }
    }

    // Method to return the instance of Controller.java
    public static Controller getInstance() {
        // Public static method to access the singleton instance
        if (instance == null) {
            instance = new Controller(); // Create the instance if it's null
        }
        return instance;
    }

    // Method to return the existing vboxTaskContainer object
    public VBox getVboxTaskContainer() {
        // Method to get the VBox instance
        return vboxTaskContainer;
    }

    // Method to handle hover effect of App Bar's circles button
    protected void AppBarButton_Effect(Button button) {
        button.setOnMouseEntered(event -> {
            if (button == btnClose) {
                imgClose.setVisible(true);
            } else if (button == btnMin) {
                imgMin.setVisible(true);
            } else {
                imgMax.setVisible(true);
            }
        });

        button.setOnMouseExited(event -> {
            if (button == btnClose) {
                imgClose.setVisible(false);
            } else if (button == btnMin) {
                imgMin.setVisible(false);
            } else {
                imgMax.setVisible(false);
            }
        });
    }

    // Method to handle hover effect for btnAddTask
    private void AddNewTaskButton_Effect(Button button){
        button.setOnMouseEntered(event -> { // Hover
            if (isLightMode)
                button.setStyle("-fx-background-color: " + _LightMode.getButtonHoverColor() + ";");
            else
                button.setStyle("-fx-background-color: " + _DarkMode.getButtonHoverColor() + ";");
        });
        button.setOnMouseExited(event -> {
            if (isLightMode)
                button.setStyle("-fx-background-color: " + _LightMode.getButtonColor() + ";");
            else // dark mode
                button.setStyle("-fx-background-color: " + _DarkMode.getButtonColor() + ";");
        });
    }

    private void SwitchModeButton_Effect(Button button){
        button.setOnMouseEntered(event -> { // Hover
            try {
                if (isLightMode){ // light mode - black background, white icon
                    button.setStyle("-fx-background-color: " + _DarkMode.getAnchorPaneColor() + ";");
                    setImageForButton(button, "/com/example/oop_todo/icons/light_mode/dark(in_dark).png", 30);
                }
                if(!isLightMode){ // dark mode - white background, black icon
                    button.setStyle("-fx-background-color: " + _LightMode.getAnchorPaneColor() + ";");
                    setImageForButton(button, "/com/example/oop_todo/icons/dark_mode/light(in_light).png", 30);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        button.setOnMouseExited(event -> {
            try{
                if (isLightMode){ // light mode - white background, black icon
                    button.setStyle("-fx-background-color: " + _LightMode.getAnchorPaneColor() + ";");
                    setImageForButton(button, "/com/example/oop_todo/icons/light_mode/dark(in_light).png",30);
                }
                if(!isLightMode){ // dark mode - black background, white icon
                    button.setStyle("-fx-background-color: " + _DarkMode.getAnchorPaneColor() + ";");
                    setImageForButton(button, "/com/example/oop_todo/icons/dark_mode/light(in_dark).png", 30);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    private void ClearButton_Effect(Button button){
        button.setOnMouseEntered(event -> { // Hover
            button.setStyle("-fx-background-color: " + _LightMode.getIconColor() + ";");
            button.setTextFill(Color.web("#DBDBDB"));
            setImageForButton(button, "/com/example/oop_todo/icons/clear(hover).png", 40);
        });
        button.setOnMouseExited(event -> {
            setImageForButton(button, "/com/example/oop_todo/icons/clear.png", 40);

            if (isLightMode){
                button.setStyle("-fx-background-color: " + _LightMode.getButtonColor() + ";");
                button.setTextFill(Color.web(_LightMode.getSecondaryFontColor()));
            }
            else{ // dark mode
                button.setStyle("-fx-background-color: " + _DarkMode.getButtonColor() + ";");
                button.setTextFill(Color.web(_DarkMode.getSecondaryFontColor()));
            }
        });
    }

    private void setImageForButton(Button button, String imagePath, double size) {
        try {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(size); // Adjust the width as needed
            imageView.setFitHeight(size); // Adjust the height as needed
            button.setGraphic(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to display the addNewTask.fxml form
    @FXML
    private void onClickAddNewTask(ActionEvent event) throws IOException{
        Add_Check_Update_Delete_TaskController.openType = "add";
        createForm("");
    }

    public void createForm(String taskData) throws IOException {
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource("/com/example/oop_todo/fxml/addNewTask.fxml"));
        Parent parent = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);

        Add_Check_Update_Delete_TaskController newFormController = loader.getController();

        if (Add_Check_Update_Delete_TaskController.openType.equals("add")){
            newFormController.setAddNewTaskButtonVisible(true);
        }else{ // openType == update
            newFormController.setUpdateButtonVisible(true);
            newFormController.setLblAppTitle("Update Task");
            String[] parts = taskData.split(";"); //  split the task data get from other form

            newFormController.setLblUniqueID(parts[0]);
            newFormController.setTxtTitle(parts[1]);
            newFormController.setTxtDescription(parts[2]);
            newFormController.setDpDueDate(LocalDate.parse(parts[3]));
            newFormController.setChkPriority(Boolean.parseBoolean(parts[4]));
        }

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

    // Method to handle button hover effects and change the style
    private void SideBarButton_Effect(Button button) { //when mouse enter and exit --> color changed
        button.setOnMouseEntered(event -> { //Hover
            if (isLightMode)
                button.setStyle("-fx-background-color: " + _LightMode.getButtonHoverColor());
            else
                button.setStyle("-fx-background-color: " + _DarkMode.getButtonHoverColor());
        });
        button.setOnMouseExited(event -> {
            if (isLightMode)
                button.setStyle("-fx-background-color: " + _LightMode.getButtonColor());
            else // dark mode
                button.setStyle("-fx-background-color: " + _DarkMode.getButtonColor());
        });
    }

    @FXML
    private void onClickSideBarButton(ActionEvent event){
        // Reset all the button properties
        ResetButton(btnToday);
        ResetButton(btnImportant);
        ResetButton(btnPrevious);
        ResetButton(btnSomeday);
        ResetButton(btnTrash);

        Button button = (Button) event.getSource();
        String buttonId = button.getId();

        //make the text indent --> when button clicked
        button.setGraphicTextGap(25);

        // change font size, color of focused button
        button.setFont(Font.font("Inter Semi Bold", FontWeight.BOLD, 22));

        // check the current color theme
        if (isLightMode)
            button.setTextFill(Color.web(_LightMode.getSecondaryFontColor()));
        else // dark mode
            button.setTextFill(Color.web(_DarkMode.getSecondaryFontColor()));

        // default state
        progressBar.setVisible(true);
        lblProgress.setVisible(true);
        btnClear.setVisible(false);

        if (buttonId.equals("btnToday")){
            lblTitle.setText("Today");
            lblTitle.setFont(Font.font("System", FontWeight.BOLD, 40));
            setCurrentFocusedPage("today");
        }
        else if (buttonId.equals("btnImportant")) {
            lblTitle.setText("Important");
            lblTitle.setFont(Font.font("System", FontWeight.BOLD, 40));
            setCurrentFocusedPage("important");
        }
        else if (buttonId.equals("btnPrevious")) {
            lblTitle.setText("Previous");
            lblTitle.setFont(Font.font("System", FontWeight.BOLD, 40));
            setCurrentFocusedPage("previous");
        }
        else if (buttonId.equals("btnSomeday")) {
            lblTitle.setText("Someday");
            lblTitle.setFont(Font.font("System", FontWeight.BOLD, 40));
            setCurrentFocusedPage("someday");
        }
        else if (buttonId.equals("btnTrash")) {
            lblTitle.setText("Trash");
            lblTitle.setFont(Font.font("System", FontWeight.BOLD, 40));
            setCurrentFocusedPage("trash");
            // hide the progress bar and show the clear button
            progressBar.setVisible(false);
            lblProgress.setVisible(false);
            btnClear.setVisible(true);
        }
    }

    public void setCurrentFocusedPage(String page){
        currentFocusedPage = page;
        try {
            displayTasks(currentFocusedPage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Method to reset the style properties for all side menu buttons
    private void ResetButton(Button button) {
        //All button in one list
        List<Button> buttons = Arrays.asList(btnToday, btnImportant, btnPrevious, btnSomeday, btnTrash);

        for (Button multipleButton : buttons) {
            //make the text not indent
            button.setGraphicTextGap(4);

            //reset the font size and colour
            button.setFont(Font.font("System", FontWeight.BOLD, 20));
            button.setTextFill(Color.web(_LightMode.getTertiaryFontColor()));
        }
    }

    public void displayTasks(String currPage) throws IOException {
        // Retrieve the existing Task Object List
        ArrayList<Task> task_list = TaskList.tasks;
        taskControllers = new ArrayList<>(); // create new array list to store the controller instance of each task bar

        if (task_list.isEmpty()) {
            System.out.println("it is empty...");
            vboxTaskContainer.getChildren().clear();
            return;
        }

        vboxTaskContainer.getChildren().clear();

        double progress = 0.0f;
        double totalCount = 0.0f;
        double completeCount = 0.0f;
        // display tasks according to the filter
        for (Task taskData : task_list) {
            if (!taskData.getStatus().equals("trashed")) {
                switch (currPage) {
                    case "today":
                        if (taskData.getDueDate().isEqual(LocalDate.now())) {
                            totalCount++;
                            if (taskData.getStatus().equals("completed")){
                                completeCount++;
                            }
                            insertTaskBarControl(taskData);
                        }
                        break;

                    case "important":
                        if (taskData.getPriority()) { // true
                            totalCount++;
                            if (taskData.getStatus().equals("completed")){
                                completeCount++;
                            }
                            insertTaskBarControl(taskData);
                        }
                        break;

                    case "previous":
                        if (taskData.getDueDate().isBefore(LocalDate.now())) {
                            totalCount++;
                            if (taskData.getStatus().equals("completed")){
                                completeCount++;
                            }
                            insertTaskBarControl(taskData);
                        }
                        break;

                    case "someday":
                        if (taskData.getDueDate().isAfter(LocalDate.now())) {
                            totalCount++;
                            if (taskData.getStatus().equals("completed")){
                                completeCount++;
                            }
                            insertTaskBarControl(taskData);
                        }
                        break;
                }
            } else {
                if (currPage.equals("trash")) {
                    insertTaskBarControl(taskData);
                }
            }
        }
        progress = completeCount / totalCount;

        // Set progress to 0 if it's null
        if (Double.isNaN(progress) || Double.isInfinite(progress)) {
            progress = 0.0;
        }

        System.out.println("totalCount: " + completeCount);
        System.out.println("completeCount: " + completeCount);
        System.out.println("completeCount / totalCount: " + progress);
        progressBar.setProgress(progress);
        String rounded = String.format("%.1f", (progress * 100));
        lblProgress.setText(rounded + "%");
    }

    // Method to handle the insertion of task bar control into the ScrollPane
    private void insertTaskBarControl(Task taskData) throws IOException {
        // System.out.println(taskData.getTitle());                 /* Use for debug the task list's element */

        // Load the taskBar control from taskBar_control.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oop_todo/fxml/taskBar_control.fxml"));
        // set the root control (Pane) of Custom Control to be the loaded UI
        Pane taskBar = loader.load();

        // Access the CustomController instance
        TaskBarController taskController = loader.getController();

        taskController.setLblUniqueID(taskData.getId());
        taskController.setLblTitle(taskData.getTitle());
        taskController.setLblDueDate(taskData.getDueDate());
        taskController.InheritMainColorTheme();

        // Add current controller instance into the dynamic array
        taskControllers.add(taskController);

        // Set spacing for each taskBar before added into the vbox in Main_Page
        vboxTaskContainer.setSpacing(10);

        // Add the taskBar to the VBox
        vboxTaskContainer.getChildren().add(taskBar);
    }

    public void reloadMainPageState(String currPage) throws IOException {
        if (vboxTaskContainer == null) {
            System.err.println("Error: vboxTaskContainer is null.");
            return; // Exit the method or handle the null case appropriately
        }
        setCurrentFocusedPage(currentFocusedPage);
        System.err.println("    Reloading...    ");
    }

    // Method to handle the termination of the program
    @FXML
    private void onClickTerminateProgram(ActionEvent event) {
        Platform.exit(); // terminate the whole program
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
        msgBoxController.setOpenButton("btnClear");
        msgBoxController.setLblAppTitle("Alert: Delete ALL Tasks");
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
    private void onClickButtonClear(ActionEvent event) throws IOException {
        String text = "Are you sure to permanently delete ALL the trashed tasks?";
        createAlertMessageBox(text);
    }

    private void loadTrash() {
        TaskList.tasks = new ArrayList<>();
        String filename = "src/main/resources/com/example/oop_todo/text_file/tasks.txt";
        String dataLine;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while ((dataLine = br.readLine()) != null) {
                // Skip empty lines or lines that do not have the expected format
                if (!dataLine.trim().isEmpty() && dataLine.split(";").length == 6) { // Assuming 5 fields: id, title, description, dueDate, isPriority
                    String[] parts = dataLine.split(";");
                    String status = parts[5].trim(); // Accessing the sixth part after split
                    if (!status.equals("TRASHED")) {
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

    private void saveTrash() {
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