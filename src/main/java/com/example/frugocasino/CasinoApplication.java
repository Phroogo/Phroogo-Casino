package com.example.frugocasino;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class CasinoApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent casino = FXMLLoader.load(getClass().getResource("casinoMenu.fxml"));

        Scene casinoMenu = new Scene(casino);
        Image icon = new Image(getClass().getResourceAsStream("/images/phroogo.png"));


        stage.setTitle("Frugo Casino");

        stage.getIcons().add(icon);
        stage.setScene(casinoMenu);
        stage.setResizable(false);
        stage.show();
        //CasinoMenuController.balanceLabel.setText(String.valueOf(CasinoMenuController.moneyBalance));

        stage.setOnCloseRequest(event -> {
            event.consume();
            exit(stage);
        });

    }
    public void exit(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit the casino");
        alert.setHeaderText("You're about to exit the casino!");
        alert.setContentText("Do you reaaally want to exit the casino?");
        if(alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }
    }
}