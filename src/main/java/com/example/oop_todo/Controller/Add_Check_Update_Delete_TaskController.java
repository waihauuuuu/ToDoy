package com.example.oop_todo.Controller;

import com.example.oop_todo.TaskList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class Add_Check_Update_Delete_TaskController extends Controller{
    @FXML
    private Button btnCancel;

    @FXML
    private Button btnOK;

    @FXML
    private Button btnUpdate;

    @FXML
    private CheckBox chkPriority;

    @FXML
    private DatePicker dpDueDate;

    @FXML
    private Label lblAppTitle;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblDueDate;

    @FXML
    private Label lblTaskTitle;

    @FXML
    private Label lblRequireDueDate;

    @FXML
    private Label lblRequireTaskTitle;

    @FXML
    private Label lblUniqueID;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtTitle;

    @FXML
    private Button btnCloseOnForm;

    @FXML
    private AnchorPane parent;

    @FXML
    private VBox vboxFormAppBar;

    @FXML
    private ImageView imgCloseOnForm;

    //Global Variable
    public static String openType = "add";  // open form type (default is add)

    // Accessing global variable
    private Controller mainPageController;

    // set the color scheme of light and dark theme
    private final LightModeColorScheme _LightMode = new LightModeColorScheme();
    private final DarkModeColorScheme _DarkMode = new DarkModeColorScheme();

    public Add_Check_Update_Delete_TaskController(Button btnToday, Button btnImportant,
                                                  Button btnPrevious, Button btnSomeday,
                                                  Button btnTrash)
    {
        super.btnToday = btnToday;
        super.btnImportant = btnImportant;
        super.btnPrevious = btnPrevious;
        super.btnSomeday = btnSomeday;
        super.btnTrash = btnTrash;
    }

    public Add_Check_Update_Delete_TaskController(){}

    // Method handle the initial properties once the fxml being load
    public void initialize(){ // called the function once addNewTask.fxml have been loaded
        InheritMainPageTheme();

        mainPageController = Controller.getInstance();
        VBox taskContainer = getVboxTaskContainer();
        // Debugging output
        System.out.println("mainPageController: " + mainPageController);
        System.out.println("vboxTaskContainer: " + taskContainer);

        if(taskContainer == null){
            System.err.println("The task container still empty...");
        }
        CloseButton_Effect(btnCloseOnForm);
    }

    // Method to inherit the current Color Theme from main_page.fxml
    private void InheritMainPageTheme(){
        if (isLightMode){
            setLightTheme();
        }else{
            setDarkTheme();
        }
    }

    @FXML
    public void setLblAppTitle(String appTitle) {
        lblAppTitle.setText(appTitle);
    }

    @FXML
    public void setLblUniqueID(String uniqueID) {
        lblUniqueID.setText(uniqueID);
    }

    public void setTxtTitle(String title) {
        txtTitle.setText(title);
    }

    @FXML
    public void setDpDueDate(LocalDate dueDate) {
        dpDueDate.setValue(dueDate);
    }

    @FXML
    public void setTxtDescription(String description) {
        txtDescription.setText(description);
    }

    @FXML
    public void setChkPriority(boolean isPriority) {
        chkPriority.setSelected(isPriority);
    }

    @FXML
    public void setUpdateButtonVisible(boolean bool){
        if (bool){
            btnUpdate.setVisible(true);
            btnOK.setVisible(false);
        }
    }

    @FXML
    public void setAddNewTaskButtonVisible(boolean bool){
        if (bool){
            btnUpdate.setVisible(false);
            btnOK.setVisible(true);
        }
    }

    private void CloseButton_Effect(Button button){
        button.setOnMouseEntered(event -> {
            imgCloseOnForm.setVisible(true);
        });
        button.setOnMouseExited(event -> {
            imgCloseOnForm.setVisible(false);
        });
    }

    // Method to handle switch of form to Light Theme
    private void setLightTheme(){
        vboxFormAppBar.setStyle(
            "-fx-background-radius: 12 12 0 0;"+
            "-fx-border-radius: 12 12 0 0;" +
           "-fx-background-color: " + _LightMode.getAppBarColor()
        );

        parent.setStyle(
            "-fx-background-color: " + _LightMode.getAnchorPaneColor() + ";" +
            "-fx-background-radius: 12;" +
            "-fx-border-radius: 12;"
        );

        txtTitle.setStyle(
                "-fx-background-color: " + _LightMode.getFieldColor() + ";" +
                "-fx-text-fill: " + _LightMode.getSecondaryFontColor() + ";"
        );

        txtDescription.setStyle(
                "-fx-control-inner-background: " + _LightMode.getFieldColor() + ";" +
                "-fx-text-fill: " + _LightMode.getSecondaryFontColor() + ";" +
                "-fx-border-color: " + _LightMode.getAnchorPaneColor()
        );

        btnCancel.setStyle("-fx-background-color: " + _LightMode.getFieldColor());
        btnCancel.setTextFill(Color.web(_LightMode.getPrimaryFontColor()));

        lblTaskTitle.setTextFill(Color.web(_LightMode.getPrimaryFontColor()));
        lblDescription.setTextFill(Color.web(_LightMode.getPrimaryFontColor()));
        lblDueDate.setTextFill(Color.web(_LightMode.getPrimaryFontColor()));
        lblRequireTaskTitle.setTextFill(Color.web("#f20000"));
        lblRequireDueDate.setTextFill(Color.web("#f20000"));

        chkPriority.setTextFill(Color.web(_LightMode.getPrimaryFontColor()));
        parent.getStyleClass().clear();
        chkPriority.getStyleClass().add("light-mode");

        dpDueDate.setStyle("-fx-control-inner-background: "+ _LightMode.getFieldColor());
        parent.getStyleClass().clear();
        parent.getStyleClass().add("dp-light-mode");
        Region arrowButton = (Region) dpDueDate.lookup(".arrow-button");
        // set the color of calendar icon background
        if (arrowButton != null) {
            arrowButton.setStyle("-fx-background-color: " + _LightMode.getProgressBar_TrackColor());

            // Apply styles to the DatePicker's calendar icon
            Region arrow = (Region) arrowButton.lookup(".arrow");
            if (arrow != null) {
                arrow.setStyle( "-fx-background-color: #837C7C;");
            }
        }
    }

    // Method to handle switch of form to Dark Theme
    private void setDarkTheme(){
        vboxFormAppBar.setStyle(
                "-fx-background-radius: 12 12 0 0;" +
                "-fx-border-radius: 12 12 0 0;" +
                "-fx-background-color: " + _DarkMode.getAppBarColor()
        );

        parent.setStyle(
                "-fx-background-color: " + _DarkMode.getAnchorPaneColor() + ";" +
                "-fx-background-radius: 12;" +
                "-fx-border-radius: 12;"
        );

        txtTitle.setStyle(
                "-fx-background-color: " + _DarkMode.getFieldColor() + ";" +
                "-fx-text-fill: " + _DarkMode.getSecondaryFontColor()
        );

        txtDescription.setStyle(
                "-fx-control-inner-background: " + _DarkMode.getFieldColor() + ";" +
                "-fx-text-fill: " + _DarkMode.getSecondaryFontColor() + ";" +
                "-fx-background-color: " + _DarkMode.getAnchorPaneColor() +";"
        );

        btnCancel.setStyle("-fx-background-color: " + _DarkMode.getFieldColor());
        btnCancel.setTextFill(Color.web(_DarkMode.getPrimaryFontColor()));

        lblTaskTitle.setTextFill(Color.web(_DarkMode.getPrimaryFontColor()));
        lblDescription.setTextFill(Color.web(_DarkMode.getPrimaryFontColor()));
        lblDueDate.setTextFill(Color.web(_DarkMode.getPrimaryFontColor()));
        lblRequireTaskTitle.setTextFill(Color.web("#ff605c"));
        lblRequireDueDate.setTextFill(Color.web("#ff605c"));

        chkPriority.setTextFill(Color.web(_DarkMode.getPrimaryFontColor()));
        parent.getStyleClass().clear();
        chkPriority.getStyleClass().add("dark-mode");

        dpDueDate.setStyle("-fx-control-inner-background: "+ _DarkMode.getFieldColor());
        parent.getStyleClass().clear();
        parent.getStyleClass().add("dp-dark-mode");
        Region arrowButton = (Region) dpDueDate.lookup(".arrow-button");
        // set the color of calendar icon background
        if (arrowButton != null) {
            arrowButton.setStyle("-fx-background-color: " + _DarkMode.getProgressBar_TrackColor());

            // Apply styles to the DatePicker's calendar icon
            Region arrow = (Region) arrowButton.lookup(".arrow");
            if (arrow != null) {
                arrow.setStyle( "-fx-background-color: #FFFFFF;");
            }
        }
    }

    // Method to handle the close of form
    @FXML
    private void onCloseButtonClick(ActionEvent event) {
        closeStage();
    }

    private void closeStage(){
        // Get the stage associated with the button. Close the dialog or update the view as needed
        Stage stage = (Stage) btnCloseOnForm.getScene().getWindow();
        // Close the stage
        stage.close();
    }

    private TaskList taskList;
    // Method to set TaskList, call this from the main application controller
    public void setTaskList(String filename) {
        this.taskList = new TaskList();
    }

    // This method is called when the "Add New" button is clicked
    @FXML
    private void handleAddTaskAction(ActionEvent event) throws IOException {
        // Check the validation of file directory
        try{
            String FILE_PATH = "src/main/resources/com/example/oop_todo/text_file/tasks.txt";
            setTaskList(FILE_PATH);
        } catch(Exception e){
            System.out.println("ERROR HERE");
        }

        // check if title and due date have input
        if(txtTitle.getText().isEmpty()){ // title have no input
            lblRequireTaskTitle.setVisible(true);
            return;
        }
        lblRequireTaskTitle.setVisible(false);

        if (dpDueDate.getValue() == null){ // due date have no input
            lblRequireDueDate.setVisible(true);
            return;
        }
        lblRequireDueDate.setVisible(false);

        if(txtDescription.getText().isEmpty()){
            txtDescription.setText("No Description...");
        }

        // Get the task details from the form
        String title = txtTitle.getText();
        String description = txtDescription.getText();
        LocalDate dueDate = dpDueDate.getValue();
        boolean isPriority = chkPriority.isSelected();

        if (this.taskList == null) {
            // TaskList has not been initialized, handle this case appropriately
            System.err.println("TaskList is not initialized.");
        }
        else {
            // Add the new task to the task list which will create a new Task object with a unique ID
            taskList.addTask(title, description, dueDate, isPriority);

            // Optionally, you can clear the form fields after adding the task
            txtTitle.clear();
            txtDescription.clear();
            dpDueDate.setValue(null); // or set to a default value
            chkPriority.setSelected(false);

            closeStage();
        }
    }

    @FXML
    void handleUpdateTaskAction(ActionEvent event) throws IOException {
        // Check the validation of file directory
        try{
            String FILE_PATH = "src/main/resources/com/example/oop_todo/text_file/tasks.txt";
            setTaskList(FILE_PATH);
        } catch(Exception e){
            System.out.println("ERROR HERE");
        }

        // check if title and due date have input
        if(txtTitle.getText().isEmpty()){ // title have no input
            lblRequireTaskTitle.setVisible(true);
            return;
        }
        lblRequireTaskTitle.setVisible(false);

        if (dpDueDate.getValue() == null){ // due date have no input
            lblRequireDueDate.setVisible(true);
            return;
        }
        lblRequireDueDate.setVisible(false);

        if(txtDescription.getText().isEmpty()){
            txtDescription.setText("No Description...");
        }

        // Get the task details from the form
        long id = Long.parseLong(lblUniqueID.getText());
        String title = txtTitle.getText();
        String description = txtDescription.getText();
        LocalDate dueDate = dpDueDate.getValue();
        boolean isPriority = chkPriority.isSelected();

        if (this.taskList == null) {
            // TaskList has not been initialized, handle this case appropriately
            System.err.println("TaskList is not initialized.");
        }
        else {
            // Add the new task to the task list which will create a new Task object with a unique ID
            taskList.updateTask(id, title, description, dueDate, isPriority);

            // Optionally, you can clear the form fields after adding the task
            lblUniqueID.setText("");
            txtTitle.clear();
            txtDescription.clear();
            dpDueDate.setValue(null); // or set to a default value
            chkPriority.setSelected(false);

            closeStage();
        }
    }
}

