package com.example.frugocasino;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import java.io.IOException;

public class CasinoMenuController{

    public Label moneyLabel, phrogMoneyLabel;
    public Button freeMoneyButton, closeShopButton;
    public ScrollPane shopPane;
    public AnchorPane anchorPane;
    public Region grayOverlay;
    public Parent shopRoot = null;
    private final GlobalCasinoState state = new GlobalCasinoState();

    public void initialize() {
        moneyLabel.setText("$" + state.getMoneyBalance());
        phrogMoneyLabel.setText("P$" + state.getPhrogMoneyBalance());
        if (state.getMoneyBalance() <= 0 || state.getActionsLeft() <= 0) {
            freeMoneyButton.setVisible(true);
            freeMoneyButton.setDisable(false);
        }
    }

    public void toggleShop() {
        if (!shopPane.isVisible()) {
            try {
                if(shopRoot == null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Shop.fxml"));
                    shopRoot = loader.load();
                }
                moneyLabel.setVisible(false);
                phrogMoneyLabel.setVisible(false);
                shopPane.setContent(shopRoot);
                shopPane.setVisible(true);
                grayOverlay.setVisible(true);
                closeShopButton.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            shopPane.setVisible(false);
            grayOverlay.setVisible(false);
            closeShopButton.setVisible(false);
            shopPane.setContent(null);
            moneyLabel.setVisible(true);
            phrogMoneyLabel.setVisible(true);
            phrogMoneyLabel.setText("P$" + state.getPhrogMoneyBalance());
        }
    }

    public void freeMoney(ActionEvent actionEvent) {
        state.setMoneyBalance(1000);
        state.setActionsLeft(5);
        state.setRound(1);
        state.setRoundMoneyMade(0);
        moneyLabel.setText("$" + state.getMoneyBalance());
        freeMoneyButton.setVisible(false);
        freeMoneyButton.setDisable(true);
    }

    public void switchToCasino(ActionEvent actionEvent) throws IOException {
        state.switchToSceneButton(actionEvent);
    }
}