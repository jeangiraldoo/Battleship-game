module com.example.battleship {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.battleship to javafx.fxml;
    exports com.example.battleship;
    exports com.example.battleship.controllers;
    opens com.example.battleship.controllers to javafx.fxml;
    exports com.example.battleship.AI;
    opens com.example.battleship.AI to javafx.fxml;
    exports com.example.battleship.board;
    opens com.example.battleship.board to javafx.fxml;
}