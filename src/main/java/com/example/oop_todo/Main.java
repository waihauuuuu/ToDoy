package com.example.oop_todo;

import com.example.oop_todo.Controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Main extends Application {
    double x, y = 0;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oop_todo/fxml/main_page.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);

        // Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/oop_todo/fxml/main_page.fxml")));
        // Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);

        // initializing the title and icon
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/example/oop_todo/icons/ToDoy(puppy).png")));
        stage.getIcons().add(icon);
        stage.setTitle("ToDoy");

        // Initialize the controller after the FXML is loaded and the scene is set
        Controller controller = loader.getController();
        controller.initialize(stage);

        // move around
        parent.setOnMousePressed(evt ->{
            x = evt.getSceneX();
            y = evt.getSceneY();

        });
        parent.setOnMouseDragged(evt ->{
            stage.setX(evt.getScreenX()- x);
            stage.setY(evt.getScreenY()- y);
        });

        stage.setScene(scene);
        stage.show();
    }

    //Code continue here...
    public static void main(String[] args) {
        launch();
    }

}