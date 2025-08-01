package com.example.frugocasino;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class ShopController {

    public AnchorPane perkInfoPane;
    public Label perkName, perkDescription, phrogMoneyLabel;
    public Button buyPerkButton;
    private int price;

    public void initialize() {
        phrogMoneyLabel.setText("P$" + GlobalCasinoState.getPhrogMoneyBalance());
    }

    public void perkDisplay(ActionEvent actionEvent) {
        perkInfoPane.setVisible(true);
        String buttonId = ((Button) actionEvent.getSource()).getId();
        displayProperPerk(buttonId);
    }

    public void displayProperPerk(String buttonId) {
        switch(buttonId) {
            case "a0" -> {
                price = GlobalCasinoState.basicPerkPrice;
                price *= (GlobalCasinoPerks.getSlotMachineMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Slut Machine");
                perkDescription.setText("Increases the payout from slot machine wins by 10% per level (currently x" + (1 + (double)Math.round(GlobalCasinoPerks.getSlotMachineMoneyMultiplier() * 10) / 10) + ").");
                buyPerkButton.setId(buttonId);
            } case "a1" -> {
                price = GlobalCasinoState.basicPerkPrice;
                price *= (GlobalCasinoPerks.getRouletteTableMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("All on Red");
                perkDescription.setText("Increases the payout from roulette table wins by 10% per level (currently x" + (1 + (double)Math.round(GlobalCasinoPerks.getRouletteTableMoneyMultiplier() * 10) / 10) + ").");
                buyPerkButton.setId(buttonId);
            } case "a2" -> {
                price = GlobalCasinoState.basicPerkPrice;
                price *= (GlobalCasinoPerks.getBlackjackMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("BlackerJack");
                perkDescription.setText("Increases the payout from blackjack wins by 10% per level (currently x" + (1 + (double)Math.round(GlobalCasinoPerks.getBlackjackMoneyMultiplier() * 10) / 10) + ").");
                buyPerkButton.setId(buttonId);
            } case "a3" -> {
                price = GlobalCasinoState.basicPerkPrice;
                price *= (GlobalCasinoPerks.getTexasPokerMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Poker Face");
                perkDescription.setText("Increases the payout from texas poker wins by 10% per level (currently x" + (1 + (double)Math.round(GlobalCasinoPerks.getTexasPokerMoneyMultiplier() * 10) / 10) + ").");
                buyPerkButton.setId(buttonId);
            } case "a4" -> {
                price = GlobalCasinoState.intermediatePerkPrice;
                price *= (GlobalCasinoPerks.getGlobalMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Winner Mentality");
                perkDescription.setText("Increases all payouts by 5% per level (currently x" + (1 + (double)Math.round(GlobalCasinoPerks.getGlobalMoneyMultiplier() * 100) / 100) + ").");
                buyPerkButton.setId(buttonId);
            } case "a5" -> {
                price = GlobalCasinoState.basicPerkPrice;
                price *= (GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Phrog Machine");
                perkDescription.setText("Increases the phrog coins payout from slot machine wins by 5% per level (currently x" + (1 + (double)Math.round(GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplier() * 100) / 100) + ").");
                buyPerkButton.setId(buttonId);
            } case "a6" -> {
                price = GlobalCasinoState.basicPerkPrice;
                price *= (GlobalCasinoPerks.getRouletteTablePhrogMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Phrog on Red");
                perkDescription.setText("Increases the phrog coins payout from roulette table wins by 5% per level (currently x" + (1 + (double)Math.round(GlobalCasinoPerks.getRouletteTablePhrogMoneyMultiplier() * 100) / 100) + ").");
                buyPerkButton.setId(buttonId);
            } case "a7" -> {
                price = GlobalCasinoState.basicPerkPrice;
                price *= (GlobalCasinoPerks.getBlackjackPhrogMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("PhrogJack");
                perkDescription.setText("Increases the phrog coins payout from blackjack wins by 5% per level (currently x" + (1 + (double)Math.round(GlobalCasinoPerks.getBlackjackPhrogMoneyMultiplier() * 100) / 100) + ").");
                buyPerkButton.setId(buttonId);
            } case "a8" -> {
                price = GlobalCasinoState.basicPerkPrice;
                price *= (GlobalCasinoPerks.getTexasPokerPhrogMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Phrog Face");
                perkDescription.setText("Increases the phrog coins payout from texas poker wins by 5% per level (currently x" + (1 + (double)Math.round(GlobalCasinoPerks.getTexasPokerPhrogMoneyMultiplier() * 100) / 100) + ").");
                buyPerkButton.setId(buttonId);
            } case "a9" -> {
                price = GlobalCasinoState.intermediatePerkPrice;
                price *= (GlobalCasinoPerks.getGlobalPhrogMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Phrog Mentality");
                perkDescription.setText("Increases all phrog coins payouts by 4% per level (currently x" + (1 + (double)Math.round(GlobalCasinoPerks.getGlobalPhrogMoneyMultiplier() * 100) / 100) + ").");
                buyPerkButton.setId(buttonId);
            }
        }
    }

    public void buyPerk(ActionEvent actionEvent) {
        String buttonId = ((Button) actionEvent.getSource()).getId();
        switch(buttonId) {
            case "a0" -> {
                if(buyProcess(price)) {
                    GlobalCasinoPerks.setSlotMachineMoneyMultiplierLevel(GlobalCasinoPerks.getSlotMachineMoneyMultiplierLevel() + 1);
                    perkDescription.setText("Increases the payout from slot machine wins by 10% per level (currently x" + (1 + (double) Math.round(GlobalCasinoPerks.getSlotMachineMoneyMultiplier() * 10) / 10) + ").");
                    price = GlobalCasinoState.basicPerkPrice;
                    price *= (GlobalCasinoPerks.getSlotMachineMoneyMultiplierLevel() + 1);
                }
            } case "a1" -> {
                if(buyProcess(price)) {
                    GlobalCasinoPerks.setRouletteTableMoneyMultiplierLevel(GlobalCasinoPerks.getRouletteTableMoneyMultiplierLevel() + 1);
                    perkDescription.setText("Increases the payout from roulette table wins by 10% per level (currently x" + (1 + (double) Math.round(GlobalCasinoPerks.getRouletteTableMoneyMultiplier() * 10) / 10) + ").");
                    price = GlobalCasinoState.basicPerkPrice;
                    price *= (GlobalCasinoPerks.getRouletteTableMoneyMultiplierLevel() + 1);
                }
            } case "a2" -> {
                if(buyProcess(price)) {
                    GlobalCasinoPerks.setBlackjackMoneyMultiplierLevel(GlobalCasinoPerks.getBlackjackMoneyMultiplierLevel() + 1);
                    perkDescription.setText("Increases the payout from blackjack wins by 10% per level (currently x" + (1 + (double) Math.round(GlobalCasinoPerks.getBlackjackMoneyMultiplier() * 10) / 10) + ").");
                    price = GlobalCasinoState.basicPerkPrice;
                    price *= (GlobalCasinoPerks.getBlackjackMoneyMultiplierLevel() + 1);
                }
            } case "a3" -> {
                if(buyProcess(price)) {
                    GlobalCasinoPerks.setTexasPokerMoneyMultiplierLevel(GlobalCasinoPerks.getTexasPokerMoneyMultiplierLevel() + 1);
                    perkDescription.setText("Increases the payout from texas poker wins by 10% per level (currently x" + (1 + (double) Math.round(GlobalCasinoPerks.getTexasPokerMoneyMultiplier() * 10) / 10) + ").");
                    price = GlobalCasinoState.basicPerkPrice;
                    price *= (GlobalCasinoPerks.getTexasPokerMoneyMultiplierLevel() + 1);
                }
            } case "a4" -> {
                if(buyProcess(price)) {
                    GlobalCasinoPerks.setGlobalMoneyMultiplierLevel(GlobalCasinoPerks.getGlobalMoneyMultiplierLevel() + 1);
                    perkDescription.setText("Increases all payouts by 5% per level (currently x" + (1 + (double) Math.round(GlobalCasinoPerks.getGlobalMoneyMultiplier() * 100) / 100) + ").");
                    price = GlobalCasinoState.intermediatePerkPrice;
                    price *= (GlobalCasinoPerks.getGlobalMoneyMultiplierLevel() + 1);
                }
            } case "a5" -> {
                if(buyProcess(price)) {
                    GlobalCasinoPerks.setSlotMachinePhrogMoneyMultiplierLevel(GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplierLevel() + 1);
                    perkDescription.setText("Increases the phrog coins payout from slot machine wins by 5% per level (currently x" + (1 + (double) Math.round(GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplier() * 100) / 100) + ").");
                    price = GlobalCasinoState.basicPerkPrice;
                    price *= (GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplierLevel() + 1);
                }
            } case "a6" -> {
                if(buyProcess(price)) {
                    GlobalCasinoPerks.setRouletteTablePhrogMoneyMultiplierLevel(GlobalCasinoPerks.getRouletteTablePhrogMoneyMultiplierLevel() + 1);
                    perkDescription.setText("Increases the phrog coins payout from roulette table wins by 5% per level (currently x" + (1 + (double) Math.round(GlobalCasinoPerks.getRouletteTablePhrogMoneyMultiplier() * 100) / 100) + ").");
                    price = GlobalCasinoState.basicPerkPrice;
                    price *= (GlobalCasinoPerks.getRouletteTablePhrogMoneyMultiplierLevel() + 1);
                }
            } case "a7" -> {
                if(buyProcess(price)) {
                    GlobalCasinoPerks.setBlackjackPhrogMoneyMultiplierLevel(GlobalCasinoPerks.getBlackjackPhrogMoneyMultiplierLevel() + 1);
                    perkDescription.setText("Increases the phrog coins payout from blackjack wins by 5% per level (currently x" + (1 + (double) Math.round(GlobalCasinoPerks.getBlackjackPhrogMoneyMultiplier() * 100) / 100) + ").");
                    price = GlobalCasinoState.basicPerkPrice;
                    price *= (GlobalCasinoPerks.getBlackjackPhrogMoneyMultiplierLevel() + 1);
                }
            } case "a8" -> {
                if(buyProcess(price)) {
                    GlobalCasinoPerks.setTexasPokerPhrogMoneyMultiplierLevel(GlobalCasinoPerks.getTexasPokerPhrogMoneyMultiplierLevel() + 1);
                    perkDescription.setText("Increases the phrog coins payout from texas poker wins by 5% per level (currently x" + (1 + (double) Math.round(GlobalCasinoPerks.getTexasPokerPhrogMoneyMultiplier() * 100) / 100) + ").");
                    price = GlobalCasinoState.basicPerkPrice;
                    price *= (GlobalCasinoPerks.getTexasPokerPhrogMoneyMultiplierLevel() + 1);
                }
            } case "a9" -> {
                if(buyProcess(price)) {
                    GlobalCasinoPerks.setGlobalPhrogMoneyMultiplierLevel(GlobalCasinoPerks.getGlobalPhrogMoneyMultiplierLevel() + 1);
                    perkDescription.setText("Increases all phrog coins payouts by 4% per level (currently x" + (1 + (double) Math.round(GlobalCasinoPerks.getGlobalPhrogMoneyMultiplier() * 100) / 100) + ").");
                    price = GlobalCasinoState.intermediatePerkPrice;
                    price *= (GlobalCasinoPerks.getGlobalPhrogMoneyMultiplierLevel() + 1);
                }
            }
        }
        buyPerkButton.setText("P$" + price);
    }

    public boolean buyProcess(int price) {
        if(GlobalCasinoState.getPhrogMoneyBalance() >= price) {
            GlobalCasinoState.changePhrogMoneyBalance(-price);
            GlobalCasinoState.changeTotalPhrogMoneySpent(price);
            phrogMoneyLabel.setText("$" + GlobalCasinoState.getPhrogMoneyBalance());
            return true;
        } else {
            buyPerkButton.setText("Broke");
            return false;
        }
    }

    public void refundPerks(ActionEvent actionEvent) {
        GlobalCasinoState.changePhrogMoneyBalance(GlobalCasinoState.getTotalPhrogMoneySpent());
        phrogMoneyLabel.setText("P$" + GlobalCasinoState.getPhrogMoneyBalance());
        perkInfoPane.setVisible(false);
        GlobalCasinoState.setTotalPhrogMoneySpent(0);
        GlobalCasinoPerks.setSlotMachineMoneyMultiplierLevel(0);
        GlobalCasinoPerks.setRouletteTableMoneyMultiplierLevel(0);
        GlobalCasinoPerks.setBlackjackMoneyMultiplierLevel(0);
        GlobalCasinoPerks.setTexasPokerMoneyMultiplierLevel(0);
        GlobalCasinoPerks.setGlobalMoneyMultiplierLevel(0);
        GlobalCasinoPerks.setSlotMachinePhrogMoneyMultiplierLevel(0);
        GlobalCasinoPerks.setRouletteTablePhrogMoneyMultiplierLevel(0);
        GlobalCasinoPerks.setBlackjackPhrogMoneyMultiplierLevel(0);
        GlobalCasinoPerks.setTexasPokerPhrogMoneyMultiplierLevel(0);
        GlobalCasinoPerks.setGlobalPhrogMoneyMultiplierLevel(0);
    }
}
