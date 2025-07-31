package com.example.frugocasino;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class CasinoController {
    public Label moneyLabel, phrogMoneyLabel;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void initialize() {
        moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
        phrogMoneyLabel.setText("P$" + GlobalCasinoState.getPhrogMoneyBalance());
    }

    public void switchToBlackjackButton(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("blackjack.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToRouletteButton(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("roulette.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSlotMachineButton(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("slotMachine.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToTexasPokerButton(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("texasHoldem.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToCasinoMenuButton(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(getClass().getResource("casinoMenu.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
