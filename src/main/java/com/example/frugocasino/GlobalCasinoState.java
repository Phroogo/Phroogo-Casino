package com.example.frugocasino;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GlobalCasinoState {
    private static Stage stage;
    private static Scene scene;
    private static Parent root;
    private static int moneyBalance = 10000;
    private static int phrogMoneyBalance = 100;
    private static int totalPhrogMoneySpent = 0;

    public static int getMoneyBalance() {
        return moneyBalance;
    }

    public static void setMoneyBalance(int amount) {
        moneyBalance = amount;
    }

    public static void changeMoneyBalance(int amount) {
        moneyBalance += amount;
    }

    public static int getPhrogMoneyBalance() {
        return phrogMoneyBalance;
    }

    public static void setPhrogMoneyBalance(int phrogMoneyBalance) {
        GlobalCasinoState.phrogMoneyBalance = phrogMoneyBalance;
    }

    public static void changePhrogMoneyBalance(int amount) {
        phrogMoneyBalance += amount;
    }

    public static int getTotalPhrogMoneySpent() {
        return totalPhrogMoneySpent;
    }

    public static void setTotalPhrogMoneySpent(int totalPhrogMoneySpent) {
        GlobalCasinoState.totalPhrogMoneySpent = totalPhrogMoneySpent;
    }

    public static void changeTotalPhrogMoneySpent(int amount) {
        totalPhrogMoneySpent += amount;
    }

    public static void switchToCasinoButton(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(CasinoApplication.class.getResource("casino.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
