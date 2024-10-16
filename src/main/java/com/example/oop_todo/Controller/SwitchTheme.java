package com.example.oop_todo.Controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SwitchTheme extends Controller {
    // Constructor that parameterised with all the control's object in main_page.fxml
    public SwitchTheme(Label lblTitle, Button btnToday, Button btnImportant, Button btnPrevious,
                       Button btnSomeday, Button btnTrash, Button btnClear,
                       Button btnMode, Button btnAddTask, AnchorPane parentPane, VBox vboxSide,
                       VBox vboxAppBar, ImageView myImageView, ProgressBar progressBar,
                       VBox vboxTaskContainer, ScrollPane sclTaskList)
    {
        super.lblTitle = lblTitle;
        super.btnToday = btnToday;
        super.btnImportant = btnImportant;
        super.btnPrevious = btnPrevious;
        super.btnSomeday = btnSomeday;
        super.btnTrash = btnTrash;
        super.btnClear = btnClear;
        super.btnMode = btnMode;
        super.btnAddTask = btnAddTask;
        super.parentPane = parentPane;
        super.vboxSide = vboxSide;
        super.vboxAppBar = vboxAppBar;
        super.myImageView = myImageView;
        super.progressBar = progressBar;
        super.vboxTaskContainer = vboxTaskContainer;
        super.sclTaskList = sclTaskList;
    }

    // Method to handle the switching to Light Theme on main_page.fxml
    @FXML
    public void setLightTheme(){
        List<Button> buttons = Arrays.asList(btnToday, btnImportant, btnPrevious, btnSomeday, btnTrash);
        String[] imagePaths = {
                "/com/example/oop_todo/icons/light_mode/today.png",
                "/com/example/oop_todo/icons/light_mode/important.png",
                "/com/example/oop_todo/icons/light_mode/previous.png",
                "/com/example/oop_todo/icons/light_mode/someday.png",
                "/com/example/oop_todo/icons/light_mode/trash.png",
        };

        int counter = 0;
        for (Button btn : buttons){
            btn.setStyle("-fx-background-color: " + _LightMode.getButtonColor());
            double fontSize = btn.getFont().getSize();

            if (fontSize > 20) { // the current focused sidebar button ( 22.5 font size )
                btn.setTextFill(Color.web(_LightMode.getSecondaryFontColor()));
            } else {
                btn.setTextFill(Color.web(_LightMode.getTertiaryFontColor()));
            }
            setImageForButton(btn, imagePaths[counter]);
            counter++;
        }

        btnClear.setStyle("-fx-background-color: " + _LightMode.getButtonColor());
        btnClear.setTextFill(Color.web(_LightMode.getSecondaryFontColor()));
        btnAddTask.setStyle("-fx-background-color: " + _LightMode.getButtonColor());
        btnAddTask.setTextFill(Color.web(_LightMode.getIconColor()));
        lblTitle.setTextFill(Color.web(_LightMode.getPrimaryFontColor()));

        // Assuming 'sclTaskList' is your ScrollPane and 'vboxTaskContainer' is your VBox
        ObservableList<String> scrollPaneStyleClasses = sclTaskList.getStyleClass();
        ObservableList<String> vboxStyleClasses = vboxTaskContainer.getStyleClass();

        // Remove existing style classes
        scrollPaneStyleClasses.removeIf(styleClass -> styleClass.startsWith("scl-"));
        vboxStyleClasses.removeIf(styleClass -> styleClass.startsWith("vbox-"));

        // Add new style classes based on the current mode
        scrollPaneStyleClasses.add("scl-light-mode");
        vboxStyleClasses.add("vbox-light-mode");

        // change the icon of mode button
        btnMode.setStyle("-fx-background-color: " + _LightMode.getAnchorPaneColor());
        setImageForButton(btnMode, "/com/example/oop_todo/icons/light_mode/dark(in_light).png");

        parentPane.setStyle("-fx-background-color: " + _LightMode.getAnchorPaneColor());
        vboxSide.setStyle("-fx-background-color: " + _LightMode.getSideBarColor());

        vboxAppBar.setStyle(
                "-fx-background-color: " + _LightMode.getAppBarColor() + ";" +
                "-fx-background-radius: 12 12 0 0 ;" +
                "-fx-border-radius: 12 12 0 0 ;"
        );

        progressBar.lookup(".track").setStyle("-fx-background-color: " + _LightMode.getProgressBar_TrackColor());
    }

    // Method to handle the switching to Dark Theme on main_page.fxml
    @FXML
    public void setDarkTheme(){
        List<Button> buttons = Arrays.asList(btnToday, btnImportant, btnPrevious, btnSomeday, btnTrash);
        String[] imagePaths = {
                "/com/example/oop_todo/icons/dark_mode/today(dark).png",
                "/com/example/oop_todo/icons/dark_mode/important(dark).png",
                "/com/example/oop_todo/icons/dark_mode/previous(dark).png",
                "/com/example/oop_todo/icons/dark_mode/someday(dark).png",
                "/com/example/oop_todo/icons/dark_mode/trash(dark).png",
        };

        int counter = 0;
        for (Button btn : buttons){
            btn.setStyle("-fx-background-color: " + _DarkMode.getButtonColor());
            double fontSize = btn.getFont().getSize(); // get the current font size of the button object

            if (fontSize > 20) { // the current focused sidebar button ( 22.5 font size )
                btn.setTextFill(Color.web(_DarkMode.getSecondaryFontColor()));
            } else {
                btn.setTextFill(Color.web(_DarkMode.getTertiaryFontColor()));
            }
            setImageForButton(btn, imagePaths[counter]); // user define method
            counter++;
        }

        btnClear.setStyle("-fx-background-color: " + _DarkMode.getButtonColor());
        btnClear.setTextFill(Color.web(_DarkMode.getSecondaryFontColor()));
        btnAddTask.setStyle("-fx-background-color: " + _DarkMode.getButtonColor());
        btnAddTask.setTextFill(Color.web(_DarkMode.getIconColor()));
        lblTitle.setTextFill(Color.web(_DarkMode.getPrimaryFontColor()));

        // Assuming 'sclTaskList' is your ScrollPane and 'vboxTaskContainer' is your VBox
        ObservableList<String> scrollPaneStyleClasses = sclTaskList.getStyleClass();
        ObservableList<String> vboxStyleClasses = vboxTaskContainer.getStyleClass();

        // Remove existing style classes
        scrollPaneStyleClasses.removeIf(styleClass -> styleClass.startsWith("scl-"));
        vboxStyleClasses.removeIf(styleClass -> styleClass.startsWith("vbox-"));

        // Add new style classes based on the current mode
        scrollPaneStyleClasses.add("scl-dark-mode");
        vboxStyleClasses.add("vbox-dark-mode");

        // change the icon of mode button
        btnMode.setStyle("-fx-background-color: " + _DarkMode.getAnchorPaneColor());
        setImageForButton(btnMode, "/com/example/oop_todo/icons/dark_mode/light(in_dark).png");

        parentPane.setStyle("-fx-background-color: " + _DarkMode.getAnchorPaneColor());
        vboxSide.setStyle(
                "-fx-background-color: " + _DarkMode.getSideBarColor() + ";" +
                "-fx-background-radius: 12 12 0 0;" +
                "-fx-border-radius: 12 12 0 0;"
        );

        vboxAppBar.setStyle("-fx-background-color: " + _DarkMode.getAppBarColor());

        progressBar.lookup(".track").setStyle("-fx-background-color: " + _DarkMode.getProgressBar_TrackColor());

    }

    // Method to handle the switching of button icon's directory
    private void setImageForButton(Button button, String imagePath) {
        try {
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(30); // Adjust the width as needed
            imageView.setFitHeight(30); // Adjust the height as needed
            button.setGraphic(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
