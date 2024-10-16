package com.example.oop_todo.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MessageBoxController extends Controller{

    @FXML
    private Pane PaneAppBar;

    @FXML
    private Pane PaneBase;

    @FXML
    private Button btnCloseReminder;

    @FXML
    private Button btnOK;

    @FXML
    private Button btnYes;

    @FXML
    private Button btnNo;

    @FXML
    private ImageView imgCloseOnForm;

    @FXML
    private Label lblAppTitle;

    @FXML
    private Label lblDescription;

    public static Boolean currTaskBarBtnYesResponse = false;

    public static Boolean currMainPageBtnClearResponse = false;

    private String openButton;

    @FXML
    private AnchorPane rootPane;

    public void initialize(){
        InheritMainPageTheme();
        CloseButton_Effect(btnCloseReminder);
    }

    public void setOpenButton(String openButton){
        this.openButton = openButton;
    }

    // Method to inherit the current Color Theme from main_page.fxml
    private void InheritMainPageTheme(){
        if (isLightMode){
            setLightTheme();
        }else{
            setDarkTheme();
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

    private void setLightTheme() {
        PaneBase.setStyle(
                "-fx-background-radius:12;" +
                "-fx-border-radius:12;"+
                "-fx-background-color:" + _LightMode.getAnchorPaneColor() + ";"
        );

        PaneAppBar.setStyle(
                "-fx-background-radius:12 12 0 0;" +
                "-fx-border-radius:12 12 0 0;" +
                "-fx-background-color:" + _LightMode.getAppBarColor() + ";"
        );

        lblDescription.setTextFill(Color.web(_LightMode.getPrimaryFontColor()));
    }

    private void setDarkTheme() {
        PaneBase.setStyle(
                "-fx-background-radius:12;" +
                "-fx-border-radius:12;"+
                "-fx-background-color:" + _DarkMode.getAnchorPaneColor() + ";"
        );

        PaneAppBar.setStyle(
                "-fx-background-radius:12 12 0 0;" +
                "-fx-border-radius:12 12 0 0;" +
                "-fx-background-color:" + _DarkMode.getAppBarColor()+ ";"
        );
        lblDescription.setTextFill(Color.web(_DarkMode.getPrimaryFontColor()));
    }

    public void setLblAppTitle(String description) {
        lblAppTitle.setText(description);
    }

    public void setLblDescription(String description) {
        lblDescription.setText(description);
    }

    public Button getBtnNo() {
        return btnNo;
    }

    public Button getBtnOK() {
        return btnOK;
    }

    public Button getBtnYes() {
        return btnYes;
    }

    @FXML
    private void onCloseButtonClick(ActionEvent event) {
        currTaskBarBtnYesResponse = false;
        currMainPageBtnClearResponse = false;
        closeStage();
    }

    private void closeStage(){
        // Get the stage associated with the button. Close the dialog or update the view as needed
        Stage stage = (Stage) btnCloseReminder.getScene().getWindow();
        // Close the stage
        stage.close();
    }

    @FXML
    void onClickButtonYes(ActionEvent event) {
        if (openButton.equals("btnDelete")){
            currTaskBarBtnYesResponse = true;
        }

        if (openButton.equals("btnClear")){
            currMainPageBtnClearResponse = true;
        }
        closeStage();
    }

}
