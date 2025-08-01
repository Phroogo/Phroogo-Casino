package com.example.frugocasino;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
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

    public void initialize() {
        moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
        phrogMoneyLabel.setText("P$" + GlobalCasinoState.getPhrogMoneyBalance());
        if (GlobalCasinoState.getMoneyBalance() <= 0) {
            freeMoneyButton.setVisible(true);
            freeMoneyButton.setDisable(false);
        }
    }

    public void switchToCasino(ActionEvent actionEvent) throws IOException {
        GlobalCasinoState.switchToCasinoButton(actionEvent);
    }

    public void toggleShop() {
        if (!shopPane.isVisible()) {
            try {
                if(shopRoot == null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Shop.fxml"));
                    shopRoot = loader.load();
                }
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
        }
    }

    public void freeMoney(ActionEvent actionEvent) {
        GlobalCasinoState.setMoneyBalance(10000);
        moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
        freeMoneyButton.setVisible(false);
        freeMoneyButton.setDisable(true);
    }
}