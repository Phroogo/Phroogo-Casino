package com.example.frugocasino;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;


public class CasinoController {
    public Label moneyLabel, phrogMoneyLabel, roundLabel, actionLabel, quotaLabel;

    public void initialize() {
        GlobalCasinoState.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
    }

    public void switchToSceneButton(ActionEvent actionEvent) throws IOException {
        String scene;
        scene = ((Button) actionEvent.getSource()).getId();
        GlobalCasinoState.switchToSceneButton(actionEvent, scene);
    }
}
