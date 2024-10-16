module com.example.oop_todoy {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens com.example.oop_todo to javafx.fxml;
    exports com.example.oop_todo;
    exports com.example.oop_todo.Controller;
    opens com.example.oop_todo.Controller to javafx.fxml;
}