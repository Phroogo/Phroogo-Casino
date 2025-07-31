package com.example.frugocasino;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ShopController {

    public AnchorPane perkInfoPane;
    public Label perkName, perkDescription, phrogMoneyLabel;
    public Button buyPerkButton;
    private CasinoMenuController casinoMenuController;
    private int price;

    public void initialize() {
        phrogMoneyLabel.setText("P$" + GlobalCasinoState.getPhrogMoneyBalance());
    }

    public void perkDisplay(ActionEvent actionEvent) {
        perkInfoPane.setVisible(true);
        String buttonId = ((Button) actionEvent.getSource()).getId();
        displayProperPerk(buttonId);
    }

    public void refundPerks(ActionEvent actionEvent) {
        GlobalCasinoState.changePhrogMoneyBalance(GlobalCasinoState.getTotalPhrogMoneySpent());
        GlobalCasinoState.setTotalPhrogMoneySpent(0);
    }

    public void displayProperPerk(String buttonId) {
        switch(buttonId) {
            case "a0" -> {
                price = 100;
                price = (GlobalCasinoPerks.getSlotMachineMoneyMultiplierLevel() + 1) * price;
                buyPerkButton.setText("$" + price);
                perkName.setText("SLUT MACHINE");
                perkDescription.setText("Increases the payout from slot machine wins by 5% per level (currently x" + (double)Math.round(GlobalCasinoPerks.getSlotMachineMoneyMultiplier() * 100) / 100 + ").");
                buyPerkButton.setId(buttonId);
            } case "a1" -> {
                price = 100;
                price = (GlobalCasinoPerks.getRouletteTableMoneyMultiplierLevel() + 1) * price;
                buyPerkButton.setText("$" + price);
                perkName.setText("ALL ON RED");
                perkDescription.setText("Increases the payout from roulette table wins by 5% per level (currently x" + (double)Math.round(GlobalCasinoPerks.getRouletteTableMoneyMultiplier() * 100) / 100 + ").");
                buyPerkButton.setId(buttonId);
            } case "a2" -> {
                price = 100;
                price = (GlobalCasinoPerks.getBlackjackMoneyMultiplierLevel() + 1) * price;
                buyPerkButton.setText("$" + price);
                perkName.setText("BLACKERJACK");
                perkDescription.setText("Increases the payout from blackjack wins by 5% per level (currently x" + (double)Math.round(GlobalCasinoPerks.getBlackjackMoneyMultiplier() * 100) / 100 + ").");
                buyPerkButton.setId(buttonId);
            } case "a3" -> {
                price = 100;
                price = (GlobalCasinoPerks.getTexasPokerMoneyMultiplierLevel() + 1) * price;
                buyPerkButton.setText("$" + price);
                perkName.setText("POKER FACE");
                perkDescription.setText("Increases the payout from texas poker wins by 5% per level (currently x" + (double)Math.round(GlobalCasinoPerks.getTexasPokerMoneyMultiplier() * 100) / 100 + ").");
                buyPerkButton.setId(buttonId);
            } case "a4" -> {
                price = 300;
                price = (GlobalCasinoPerks.getGlobalMoneyMultiplierLevel() + 1) * price;
                buyPerkButton.setText("$" + price);
                perkName.setText("WINNER MENTALITY");
                perkDescription.setText("Increases all payouts by 5% per level (currently x" + (double)Math.round(GlobalCasinoPerks.getGlobalMoneyMultiplier() * 100) / 100 + ").");
                buyPerkButton.setId(buttonId);
            }
        }
    }

    public void buyPerk(ActionEvent actionEvent) {
        String buttonId = ((Button) actionEvent.getSource()).getId();
        switch(buttonId) {
            case "a0" -> {
                if(buyProcess(price)) {
                    GlobalCasinoPerks.setSlotMachineMoneyMultiplier(GlobalCasinoPerks.getSlotMachineMoneyMultiplier() + 0.05);
                    perkDescription.setText("Increases the payout from slot machine games by 5% per level (currently x" + (double) Math.round(GlobalCasinoPerks.getSlotMachineMoneyMultiplier() * 100) / 100 + ").");
                }
            } case "a1" -> {
                if(buyProcess(price)) {
                    GlobalCasinoPerks.setRouletteTableMoneyMultiplier(GlobalCasinoPerks.getRouletteTableMoneyMultiplier() + 0.05);
                    perkDescription.setText("Increases the payout from slot machine games by 5% per level (currently x" + (double) Math.round(GlobalCasinoPerks.getRouletteTableMoneyMultiplier() * 100) / 100 + ").");
                }
            } case "a2" -> {
                if(buyProcess(price)) {
                    GlobalCasinoPerks.setBlackjackMoneyMultiplier(GlobalCasinoPerks.getBlackjackMoneyMultiplier() + 0.05);
                    perkDescription.setText("Increases the payout from slot machine games by 5% per level (currently x" + (double) Math.round(GlobalCasinoPerks.getBlackjackMoneyMultiplier() * 100) / 100 + ").");
                }
            } case "a3" -> {
                if(buyProcess(price)) {
                    GlobalCasinoPerks.setTexasPokerMoneyMultiplier(GlobalCasinoPerks.getTexasPokerMoneyMultiplier() + 0.05);
                    perkDescription.setText("Increases the payout from slot machine games by 5% per level (currently x" + (double) Math.round(GlobalCasinoPerks.getTexasPokerMoneyMultiplier() * 100) / 100 + ").");
                }
            } case "a4" -> {
                if(buyProcess(price)) {
                    GlobalCasinoPerks.setGlobalMoneyMultiplier(GlobalCasinoPerks.getGlobalMoneyMultiplier() + 0.05);
                    perkDescription.setText("Increases the payout from slot machine games by 5% per level (currently x" + (double) Math.round(GlobalCasinoPerks.getGlobalMoneyMultiplier() * 100) / 100 + ").");
                }
            }
        }
    }

    public boolean buyProcess(int price) {
        if(GlobalCasinoState.getPhrogMoneyBalance() >= price) {
            GlobalCasinoState.changePhrogMoneyBalance(-price);
            GlobalCasinoState.changeTotalPhrogMoneySpent(price);
            phrogMoneyLabel.setText("$" + GlobalCasinoState.getPhrogMoneyBalance());
            return true;
        } else {
            buyPerkButton.setText("Broke ahh bitch");
            return false;
        }
    }

    public void setCasinoMenuController(CasinoMenuController controller) {
        this.casinoMenuController = controller;
    }
}
