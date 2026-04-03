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
    public final int maxBasicPerkLevel = 20;
    public final int maxAdvancedPerkLevel = 10;
    public final int maxOpPerkLevel = 5;
    public final int maxLegendaryPerkLevel = 1;
    public final int basicPerkPrice = 100;
    public final int intermediatePerkPrice = 200;
    public final int advancedPerkPrice = 300;
    public final int expertPerkPrice = 500;
    public final int opPerkPrice = 1000;
    public final int legendaryPerkPrice = 10000;
    private static int moneyBalance = 1000;
    private static int phrogMoneyBalance = 100000;
    private static int totalPhrogMoneySpent = 0;
    private static int round = 1;
    private static int roundMoneyMade = 0;
    private static int actionsLeft = 5;

    public int getMoneyBalance() {
        return moneyBalance;
    }

    public void setMoneyBalance(int amount) {
        moneyBalance = amount;
    }

    public void changeMoneyBalance(int amount) {
        moneyBalance += amount;
    }

    public int getPhrogMoneyBalance() {
        return phrogMoneyBalance;
    }

    public void setPhrogMoneyBalance(int phrogMoneyBalance) {
        GlobalCasinoState.phrogMoneyBalance = phrogMoneyBalance;
    }

    public void changePhrogMoneyBalance(int amount) {
        phrogMoneyBalance += amount;
    }

    public int getTotalPhrogMoneySpent() {
        return totalPhrogMoneySpent;
    }

    public void setTotalPhrogMoneySpent(int totalPhrogMoneySpent) {
        GlobalCasinoState.totalPhrogMoneySpent = totalPhrogMoneySpent;
    }

    public void changeTotalPhrogMoneySpent(int amount) {
        totalPhrogMoneySpent += amount;
    }

    public void switchToSceneButton(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(CasinoApplication.class.getResource("casino.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneButton(ActionEvent actionEvent, String newScene) throws IOException {
        root = FXMLLoader.load(CasinoApplication.class.getResource(newScene + ".fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public int calculatePhrogCoins(int amount) {
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

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        GlobalCasinoState.round = round;
    }

    public void displayInfo(Label moneyLabel, Label roundLabel, Label actionsLabel, Label qoutaLabel) {
        moneyLabel.setText("$" + getMoneyBalance());
        roundLabel.setText("Round " + round);
        qoutaLabel.setText("$" + roundMoneyMade + "/$" + (round * 5000));
        actionsLabel.setText("Actions: " + actionsLeft);
    }

    public int getRoundMoneyMade() {
        return roundMoneyMade;
    }

    public void setRoundMoneyMade(int roundMoneyMade) {
        GlobalCasinoState.roundMoneyMade = roundMoneyMade;
    }

    public void changeRoundMoneyMade(int amount) {
        roundMoneyMade += amount;
    }

    public int getActionsLeft() {
        return actionsLeft;
    }

    public void actionDecrement() {
        actionsLeft -= 1;
    }

    public void actionDecrement(int x) {
        actionsLeft -= x;
    }

    public void setActionsLeft(int actionsLeft) {
        GlobalCasinoState.actionsLeft = actionsLeft;
    }
}
