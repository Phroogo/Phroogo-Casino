package com.example.frugocasino;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import java.io.IOException;

public class GlobalCasinoState {
    private static Stage stage;
    private static Scene scene;
    private static Parent root;
    public final static int maxBasicPerkLevel = 20;
    public final static int maxAdvancedPerkLevel = 10;
    public final static int maxOpPerkLevel = 5;
    public final static int maxLegendaryPerkLevel = 1;
    public final static int basicPerkPrice = 100;
    public final static int intermediatePerkPrice = 200;
    public final static int advancedPerkPrice = 300;
    public final static int expertPerkPrice = 500;
    public final static int opPerkPrice = 1000;
    public final static int legendaryPerkPrice = 10000;
    private static int moneyBalance = 10000;
    private static int phrogMoneyBalance = 100000;
    private static int totalPhrogMoneySpent = 0;
    private static int round = 1;
    private static int roundMoneyMade = 0;
    private static int actionsLeft = 10;

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

    public static void switchToSceneButton(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(CasinoApplication.class.getResource("casino.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSceneButton(ActionEvent actionEvent, String newScene) throws IOException {
        root = FXMLLoader.load(CasinoApplication.class.getResource(newScene + ".fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static int calculatePhrogCoins(int amount) {
        if(amount < 1000) {
            return amount/400;
        } else if(amount < 10000) {
            return amount/500 + 1;
        } else if(amount < 100000) {
            return amount/600 + 4;
        } else if(amount < 1000000) {
            return amount/800 + 42;
        } else {
            return amount/1000 + 250;
        }
    }

    public static int getRound() {
        return round;
    }

    public static void setRound(int round) {
        GlobalCasinoState.round = round;
    }

    public static void displayInfo(Label moneyLabel, Label roundLabel, Label actionsLabel, Label qoutaLabel) {
        moneyLabel.setText("$" + getMoneyBalance());
        roundLabel.setText("Round " + round);
        qoutaLabel.setText("$" + roundMoneyMade + "/$" + (round * 10000));
        actionsLabel.setText("Actions: " + actionsLeft);
    }

    public static int getRoundMoneyMade() {
        return roundMoneyMade;
    }

    public static void setRoundMoneyMade(int roundMoneyMade) {
        GlobalCasinoState.roundMoneyMade = roundMoneyMade;
    }

    public static void changeRoundMoneyMade(int amount) {
        roundMoneyMade += amount;
    }

    public static int getActionsLeft() {
        return actionsLeft;
    }

    public static void actionDecrement() {
        actionsLeft -= 1;
    }

    public static void actionDecrement(int x) {
        actionsLeft -= x;
    }

    public static void setActionsLeft(int actionsLeft) {
        GlobalCasinoState.actionsLeft = actionsLeft;
    }
}
