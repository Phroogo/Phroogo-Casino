module com.example.frugocasino {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.frugocasino to javafx.fxml;
    exports com.example.frugocasino;
}