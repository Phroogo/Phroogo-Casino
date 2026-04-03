package com.example.frugocasino;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;


public class CasinoController {
    public Label moneyLabel, roundLabel, actionLabel, quotaLabel;
    public Button skipRoundButton;
    private final GlobalCasinoState state = new GlobalCasinoState();

    public void initialize() {
        state.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
        if(state.getRoundMoneyMade() >= 5000 * state.getRound()) skipRoundButton.setVisible(true);
    }

    public void skipRound(ActionEvent actionEvent) {
        state.setRound(state.getRound() + 1);
        state.setActionsLeft(state.getRound() * 5);
        state.setRoundMoneyMade(0);
        state.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
        skipRoundButton.setVisible(false);
    }

    public void switchToSceneButton(ActionEvent actionEvent) throws IOException {
        String scene;
        scene = ((Button) actionEvent.getSource()).getId();
        state.switchToSceneButton(actionEvent, scene);
    }
}
