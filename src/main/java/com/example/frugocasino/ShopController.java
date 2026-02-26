package com.example.frugocasino;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class ShopController {

    public AnchorPane perkInfoPane;
    public GridPane perkPane;
    public Label perkName, perkDescription, phrogMoneyLabel, maxLevelLabel;
    public Button buyPerkButton;
    private int price;
    private String openedPerkId;
    private final GlobalCasinoPerks perks = new GlobalCasinoPerks();
    private final GlobalCasinoState state = new GlobalCasinoState();

    public void initialize() {
        phrogMoneyLabel.setText("P$" + state.getPhrogMoneyBalance());
    }

    public void perkDisplay(ActionEvent actionEvent) {

        if(perkInfoPane.isVisible() && Objects.equals(((Button) actionEvent.getSource()).getId(), openedPerkId)) {
            perkInfoPane.setVisible(false);
            perkPane.setPadding(new Insets(140, 20, 20, 20));
            perkPane.setPrefHeight(1200);
            return;
        }
        perkInfoPane.setVisible(true);
        perkPane.setPadding(new Insets(140, 20, 200, 20));
        perkPane.setPrefHeight(1380);
        String buttonId = ((Button) actionEvent.getSource()).getId();
        openedPerkId = buttonId;
        recognizePerk(buttonId, actionEvent);
    }

    public void buyPerk(ActionEvent actionEvent) {
        String buttonId = ((Button) actionEvent.getSource()).getId();
        if(buyProcess(price)) {
            recognizePerk(buttonId, actionEvent);
        }
        buyPerkButton.setText("P$" + price);
    }

    public boolean buyProcess(int price) {
        if(state.getPhrogMoneyBalance() >= price) {
            state.changePhrogMoneyBalance(-price);
            state.changeTotalPhrogMoneySpent(price);
            phrogMoneyLabel.setText("$" + state.getPhrogMoneyBalance());
            return true;
        } else {
            buyPerkButton.setText("Broke");
            return false;
        }
    }

    public void refundPerks(ActionEvent actionEvent) {
        state.changePhrogMoneyBalance(state.getTotalPhrogMoneySpent());
        phrogMoneyLabel.setText("P$" + state.getPhrogMoneyBalance());
        perkInfoPane.setVisible(false);
        state.setTotalPhrogMoneySpent(0);
        perks.setSlotMachineMoneyMultiplierLevel(0);
        perks.setRouletteTableMoneyMultiplierLevel(0);
        perks.setBlackjackMoneyMultiplierLevel(0);
        perks.setTexasPokerMoneyMultiplierLevel(0);
        perks.setGlobalMoneyMultiplierLevel(0);
        perks.setSlotMachinePhrogMoneyMultiplierLevel(0);
        perks.setRouletteTablePhrogMoneyMultiplierLevel(0);
        perks.setBlackjackPhrogMoneyMultiplierLevel(0);
        perks.setTexasPokerPhrogMoneyMultiplierLevel(0);
        perks.setGlobalPhrogMoneyMultiplierLevel(0);
        perks.setGreenPhrogSlotChanceLevel(0);
        perks.setPurplePhrogSlotChanceLevel(0);
        perks.setPhroogoSlotChanceLevel(0);
        perks.setRouletteTableRerollLevel(0);
        perks.setBlackJackBustLimitLevel(0);
        perks.setTexasPokerHandEvaluationIncreaseLevel(0);
    }

    public void maxLevelReached() {
        buyPerkButton.setDisable(true);
        buyPerkButton.setVisible(false);
        maxLevelLabel.setVisible(true);
    }

    public void recognizePerk(String buttonId, ActionEvent actionEvent) {
        switch(buttonId) {
            case "a0", "a1", "a2", "a3", "a5", "a6", "a7", "a8", "a10" -> price = state.basicPerkPrice;
            case "a11" -> price = state.intermediatePerkPrice;
            case "a4", "a9", "a12" -> price = state.advancedPerkPrice;
            case "a14" -> price = state.expertPerkPrice;
            case "a13" -> price = state.opPerkPrice;
            case "a15" -> price = state.legendaryPerkPrice;
        }
        buyPerkButton.setDisable(false);
        buyPerkButton.setVisible(true);
        maxLevelLabel.setVisible(false);
        switch(buttonId) {
            case "a0" -> {
                if(perks.getSlotMachineMoneyMultiplierLevel() < state.maxBasicPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        perks.setSlotMachineMoneyMultiplierLevel(perks.getSlotMachineMoneyMultiplierLevel() + 1);
                        if(perks.getSlotMachineMoneyMultiplierLevel() > state.maxBasicPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (perks.getSlotMachineMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Slut Machine");
                perkDescription.setText("Increases the payout from slot machine wins by 10% per level (currently x" + (1 + (double) Math.round(perks.getSlotMachineMoneyMultiplier() * 10) / 10) + ").");
                buyPerkButton.setId(buttonId);
            }
            case "a1" -> {
                if(perks.getRouletteTableMoneyMultiplierLevel() < state.maxBasicPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        perks.setRouletteTableMoneyMultiplierLevel(perks.getRouletteTableMoneyMultiplierLevel() + 1);
                        if(perks.getRouletteTableMoneyMultiplierLevel() > state.maxBasicPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (perks.getRouletteTableMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("All on Red");
                perkDescription.setText("Increases the payout from roulette table wins by 10% per level (currently x" + (1 + (double) Math.round(perks.getRouletteTableMoneyMultiplier() * 10) / 10) + ").");
                buyPerkButton.setId(buttonId);
            }
            case "a2" -> {
                if(perks.getBlackjackMoneyMultiplierLevel() < state.maxBasicPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        perks.setBlackjackMoneyMultiplierLevel(perks.getBlackjackMoneyMultiplierLevel() + 1);
                        if(perks.getBlackjackMoneyMultiplierLevel() > state.maxBasicPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (perks.getBlackjackMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("BlackerJack");
                perkDescription.setText("Increases the payout from blackjack wins by 10% per level (currently x" + (1 + (double) Math.round(perks.getBlackjackMoneyMultiplier() * 10) / 10) + ").");
                buyPerkButton.setId(buttonId);
            }
            case "a3" -> {
                if(perks.getTexasPokerMoneyMultiplierLevel() < state.maxBasicPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        perks.setTexasPokerMoneyMultiplierLevel(perks.getTexasPokerMoneyMultiplierLevel() + 1);
                        if(perks.getTexasPokerMoneyMultiplierLevel() > state.maxBasicPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (perks.getTexasPokerMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Poker Face");
                perkDescription.setText("Increases the payout from texas poker wins by 10% per level (currently x" + (1 + (double) Math.round(perks.getTexasPokerMoneyMultiplier() * 10) / 10) + ").");
                buyPerkButton.setId(buttonId);
            }
            case "a4" -> {
                if(perks.getGlobalMoneyMultiplierLevel() < state.maxAdvancedPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        perks.setGlobalMoneyMultiplierLevel(perks.getGlobalMoneyMultiplierLevel() + 1);
                        if(perks.getGlobalMoneyMultiplierLevel() > state.maxAdvancedPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (perks.getGlobalMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Winner Mentality");
                perkDescription.setText("Increases all payouts by 5% per level (currently x" + (1 + (double) Math.round(perks.getGlobalMoneyMultiplier() * 100) / 100) + ", multiplicative).");
                buyPerkButton.setId(buttonId);
            }
            case "a5" -> {
                if(perks.getSlotMachinePhrogMoneyMultiplierLevel() < state.maxBasicPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        perks.setSlotMachinePhrogMoneyMultiplierLevel(perks.getSlotMachinePhrogMoneyMultiplierLevel() + 1);
                        if(perks.getSlotMachinePhrogMoneyMultiplierLevel() > state.maxBasicPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (perks.getSlotMachinePhrogMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Phrog Machine");
                perkDescription.setText("Increases the phrog coins payout from slot machine wins by 5% per level (currently x" + (1 + (double) Math.round(perks.getSlotMachinePhrogMoneyMultiplier() * 100) / 100) + ").");
                buyPerkButton.setId(buttonId);
            }
            case "a6" -> {
                if(perks.getRouletteTablePhrogMoneyMultiplierLevel() < state.maxBasicPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        perks.setRouletteTablePhrogMoneyMultiplierLevel(perks.getRouletteTablePhrogMoneyMultiplierLevel() + 1);
                        if(perks.getRouletteTablePhrogMoneyMultiplierLevel() > state.maxBasicPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (perks.getRouletteTablePhrogMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Phrog on Red");
                perkDescription.setText("Increases the phrog coins payout from roulette table wins by 5% per level (currently x" + (1 + (double) Math.round(perks.getRouletteTablePhrogMoneyMultiplier() * 100) / 100) + ").");
                buyPerkButton.setId(buttonId);
            }
            case "a7" -> {
                if(perks.getBlackjackPhrogMoneyMultiplierLevel() < state.maxBasicPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        perks.setBlackjackPhrogMoneyMultiplierLevel(perks.getBlackjackPhrogMoneyMultiplierLevel() + 1);
                        if(perks.getBlackjackPhrogMoneyMultiplierLevel() > state.maxBasicPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (perks.getBlackjackPhrogMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("PhrogJack");
                perkDescription.setText("Increases the phrog coins payout from blackjack wins by 5% per level (currently x" + (1 + (double) Math.round(perks.getBlackjackPhrogMoneyMultiplier() * 100) / 100) + ").");
                buyPerkButton.setId(buttonId);
            }
            case "a8" -> {
                if(perks.getTexasPokerPhrogMoneyMultiplierLevel() < state.maxBasicPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        perks.setTexasPokerPhrogMoneyMultiplierLevel(perks.getTexasPokerPhrogMoneyMultiplierLevel() + 1);
                        if(perks.getTexasPokerPhrogMoneyMultiplierLevel() > state.maxBasicPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (perks.getTexasPokerPhrogMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Phrog Face");
                perkDescription.setText("Increases the phrog coins payout from texas poker wins by 5% per level (currently x" + (1 + (double) Math.round(perks.getTexasPokerPhrogMoneyMultiplier() * 100) / 100) + ").");
                buyPerkButton.setId(buttonId);
            }
            case "a9" -> {
                if(perks.getGlobalPhrogMoneyMultiplierLevel() < state.maxAdvancedPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        perks.setGlobalPhrogMoneyMultiplierLevel(perks.getGlobalPhrogMoneyMultiplierLevel() + 1);
                        if(perks.getGlobalPhrogMoneyMultiplierLevel() > state.maxAdvancedPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (perks.getGlobalPhrogMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Phrog Mentality");
                double rounded = (double) Math.round(perks.getGlobalPhrogMoneyMultiplier() * 100) / 100;
                String formatted = String.format("%.2f", rounded).replaceFirst("0", "1"); //floating point issues
                perkDescription.setText("Increases all phrog coins payouts by 4% per level (currently x" + formatted + ", multiplicative).");
                buyPerkButton.setId(buttonId);
            }
            case "a10" -> {
                if(perks.getGreenPhrogSlotChanceLevel() < state.maxAdvancedPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        perks.setGreenPhrogSlotChanceLevel(perks.getGreenPhrogSlotChanceLevel() + 1);
                        if(perks.getGreenPhrogSlotChanceLevel() > state.maxAdvancedPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (perks.getGreenPhrogSlotChanceLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Zeeper Luck");
                perkDescription.setText("Increases the chance of getting green phrogs in slots (idk by how much, math is hard).");
                buyPerkButton.setId(buttonId);
            }
            case "a11" -> {
                if(perks.getPurplePhrogSlotChanceLevel() < state.maxAdvancedPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        perks.setPurplePhrogSlotChanceLevel(perks.getPurplePhrogSlotChanceLevel() + 1);
                        if(perks.getPurplePhrogSlotChanceLevel() > state.maxAdvancedPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (perks.getPurplePhrogSlotChanceLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Gia Luck");
                perkDescription.setText("Increases the chance of getting purple phrogs in slots (idk by how much, math is hard).");
                buyPerkButton.setId(buttonId);
            }
            case "a12" -> {
                if(perks.getPhroogoSlotChanceLevel() < state.maxAdvancedPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        perks.setPhroogoSlotChanceLevel(perks.getPhroogoSlotChanceLevel() + 1);
                        if(perks.getPhroogoSlotChanceLevel() > state.maxAdvancedPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (perks.getPhroogoSlotChanceLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Phroogo Luck");
                perkDescription.setText("Increases the chance of getting blue phrogs in slots (idk by how much, math is hard).");
                buyPerkButton.setId(buttonId);
            }
            case "a13" -> {
                if(perks.getRouletteTableRerollLevel() < state.maxAdvancedPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        perks.setRouletteTableRerollLevel(perks.getRouletteTableRerollLevel() + 1);
                        if(perks.getRouletteTableRerollLevel() > state.maxAdvancedPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (perks.getRouletteTableRerollLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Reroll Table");
                perkDescription.setText("Increases the chance of reroll if your didn't win in roulette by 5% per level (currently "  + perks.getRouletteTableRerollLevel() * 5 + "%)");
                buyPerkButton.setId(buttonId);
            }
            case "a14" -> {
                if(perks.getBlackJackBustLimitLevel() < state.maxOpPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        perks.setBlackJackBustLimitLevel(perks.getBlackJackBustLimitLevel() + 1);
                        if(perks.getBlackJackBustLimitLevel() > state.maxOpPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (perks.getBlackJackBustLimitLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("No Bustin'");
                perkDescription.setText("Increases the amount of points you can go over 21 in blackjack (currently:  "  + perks.getBlackJackBustLimitLevel() + "), the game is still about who's closer to 21");
                buyPerkButton.setId(buttonId);
            }
            case "a15" -> {
                if(perks.getTexasPokerHandEvaluationIncreaseLevel() < state.maxLegendaryPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        perks.setTexasPokerHandEvaluationIncreaseLevel(perks.getTexasPokerHandEvaluationIncreaseLevel() + 1);
                        if(perks.getTexasPokerHandEvaluationIncreaseLevel() > state.maxLegendaryPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (perks.getTexasPokerHandEvaluationIncreaseLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Simply Better");
                perkDescription.setText("Increases the estimation of your poker hand by one (eg. high card -> pair)");
                buyPerkButton.setId(buttonId);
            }
        }
    }
}
