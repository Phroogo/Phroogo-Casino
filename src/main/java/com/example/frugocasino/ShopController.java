package com.example.frugocasino;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ShopController {

    public AnchorPane perkInfoPane;
    public Label perkName, perkDescription, phrogMoneyLabel, maxLevelLabel;
    public Button buyPerkButton;
    private int price;

    public void initialize() {
        phrogMoneyLabel.setText("P$" + GlobalCasinoState.getPhrogMoneyBalance());
    }

    public void perkDisplay(ActionEvent actionEvent) {
        perkInfoPane.setVisible(true);
        String buttonId = ((Button) actionEvent.getSource()).getId();
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
        GlobalCasinoPerks.setGreenPhrogSlotChanceLevel(0);
        GlobalCasinoPerks.setPurplePhrogSlotChanceLevel(0);
        GlobalCasinoPerks.setPhroogoSlotChanceLevel(0);
        GlobalCasinoPerks.setRouletteTableRerollLevel(0);
        GlobalCasinoPerks.setBlackJackBustLimitLevel(0);
        GlobalCasinoPerks.setTexasPokerHandEvaluationIncreaseLevel(0);
    }

    public void maxLevelReached() {
        buyPerkButton.setDisable(true);
        buyPerkButton.setVisible(false);
        maxLevelLabel.setVisible(true);
    }

    public void recognizePerk(String buttonId, ActionEvent actionEvent) {
        switch(buttonId) {
            case "a0", "a1", "a2", "a3", "a5", "a6", "a7", "a8", "a10" -> price = GlobalCasinoState.basicPerkPrice;
            case "a11" -> price = GlobalCasinoState.intermediatePerkPrice;
            case "a4", "a9", "a12" -> price = GlobalCasinoState.advancedPerkPrice;
            case "a14" -> price = GlobalCasinoState.expertPerkPrice;
            case "a13" -> price = GlobalCasinoState.opPerkPrice;
            case "a15" -> price = GlobalCasinoState.legendaryPerkPrice;
        }
        buyPerkButton.setDisable(false);
        buyPerkButton.setVisible(true);
        maxLevelLabel.setVisible(false);
        switch(buttonId) {
            case "a0" -> {
                if(GlobalCasinoPerks.getSlotMachineMoneyMultiplierLevel() < GlobalCasinoState.maxBasicPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        GlobalCasinoPerks.setSlotMachineMoneyMultiplierLevel(GlobalCasinoPerks.getSlotMachineMoneyMultiplierLevel() + 1);
                        if(GlobalCasinoPerks.getSlotMachineMoneyMultiplierLevel() > GlobalCasinoState.maxBasicPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (GlobalCasinoPerks.getSlotMachineMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Slut Machine");
                perkDescription.setText("Increases the payout from slot machine wins by 10% per level (currently x" + (1 + (double) Math.round(GlobalCasinoPerks.getSlotMachineMoneyMultiplier() * 10) / 10) + ").");
                buyPerkButton.setId(buttonId);
            }
            case "a1" -> {
                if(GlobalCasinoPerks.getRouletteTableMoneyMultiplierLevel() < GlobalCasinoState.maxBasicPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        GlobalCasinoPerks.setRouletteTableMoneyMultiplierLevel(GlobalCasinoPerks.getRouletteTableMoneyMultiplierLevel() + 1);
                        if(GlobalCasinoPerks.getRouletteTableMoneyMultiplierLevel() > GlobalCasinoState.maxBasicPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (GlobalCasinoPerks.getRouletteTableMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("All on Red");
                perkDescription.setText("Increases the payout from roulette table wins by 10% per level (currently x" + (1 + (double) Math.round(GlobalCasinoPerks.getRouletteTableMoneyMultiplier() * 10) / 10) + ").");
                buyPerkButton.setId(buttonId);
            }
            case "a2" -> {
                if(GlobalCasinoPerks.getBlackjackMoneyMultiplierLevel() < GlobalCasinoState.maxBasicPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        GlobalCasinoPerks.setBlackjackMoneyMultiplierLevel(GlobalCasinoPerks.getBlackjackMoneyMultiplierLevel() + 1);
                        if(GlobalCasinoPerks.getBlackjackMoneyMultiplierLevel() > GlobalCasinoState.maxBasicPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (GlobalCasinoPerks.getBlackjackMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("BlackerJack");
                perkDescription.setText("Increases the payout from blackjack wins by 10% per level (currently x" + (1 + (double) Math.round(GlobalCasinoPerks.getBlackjackMoneyMultiplier() * 10) / 10) + ").");
                buyPerkButton.setId(buttonId);
            }
            case "a3" -> {
                if(GlobalCasinoPerks.getTexasPokerMoneyMultiplierLevel() < GlobalCasinoState.maxBasicPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        GlobalCasinoPerks.setTexasPokerMoneyMultiplierLevel(GlobalCasinoPerks.getTexasPokerMoneyMultiplierLevel() + 1);
                        if(GlobalCasinoPerks.getTexasPokerMoneyMultiplierLevel() > GlobalCasinoState.maxBasicPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (GlobalCasinoPerks.getTexasPokerMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Poker Face");
                perkDescription.setText("Increases the payout from texas poker wins by 10% per level (currently x" + (1 + (double) Math.round(GlobalCasinoPerks.getTexasPokerMoneyMultiplier() * 10) / 10) + ").");
                buyPerkButton.setId(buttonId);
            }
            case "a4" -> {
                if(GlobalCasinoPerks.getGlobalMoneyMultiplierLevel() < GlobalCasinoState.maxAdvancedPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        GlobalCasinoPerks.setGlobalMoneyMultiplierLevel(GlobalCasinoPerks.getGlobalMoneyMultiplierLevel() + 1);
                        if(GlobalCasinoPerks.getGlobalMoneyMultiplierLevel() > GlobalCasinoState.maxAdvancedPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (GlobalCasinoPerks.getGlobalMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Winner Mentality");
                perkDescription.setText("Increases all payouts by 5% per level (currently x" + (1 + (double) Math.round(GlobalCasinoPerks.getGlobalMoneyMultiplier() * 100) / 100) + ", multiplicative).");
                buyPerkButton.setId(buttonId);
            }
            case "a5" -> {
                if(GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplierLevel() < GlobalCasinoState.maxBasicPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        GlobalCasinoPerks.setSlotMachinePhrogMoneyMultiplierLevel(GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplierLevel() + 1);
                        if(GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplierLevel() > GlobalCasinoState.maxBasicPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Phrog Machine");
                perkDescription.setText("Increases the phrog coins payout from slot machine wins by 5% per level (currently x" + (1 + (double) Math.round(GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplier() * 100) / 100) + ").");
                buyPerkButton.setId(buttonId);
            }
            case "a6" -> {
                if(GlobalCasinoPerks.getRouletteTablePhrogMoneyMultiplierLevel() < GlobalCasinoState.maxBasicPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        GlobalCasinoPerks.setRouletteTablePhrogMoneyMultiplierLevel(GlobalCasinoPerks.getRouletteTablePhrogMoneyMultiplierLevel() + 1);
                        if(GlobalCasinoPerks.getRouletteTablePhrogMoneyMultiplierLevel() > GlobalCasinoState.maxBasicPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (GlobalCasinoPerks.getRouletteTablePhrogMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Phrog on Red");
                perkDescription.setText("Increases the phrog coins payout from roulette table wins by 5% per level (currently x" + (1 + (double) Math.round(GlobalCasinoPerks.getRouletteTablePhrogMoneyMultiplier() * 100) / 100) + ").");
                buyPerkButton.setId(buttonId);
            }
            case "a7" -> {
                if(GlobalCasinoPerks.getBlackjackPhrogMoneyMultiplierLevel() < GlobalCasinoState.maxBasicPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        GlobalCasinoPerks.setBlackjackPhrogMoneyMultiplierLevel(GlobalCasinoPerks.getBlackjackPhrogMoneyMultiplierLevel() + 1);
                        if(GlobalCasinoPerks.getBlackjackPhrogMoneyMultiplierLevel() > GlobalCasinoState.maxBasicPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (GlobalCasinoPerks.getBlackjackPhrogMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("PhrogJack");
                perkDescription.setText("Increases the phrog coins payout from blackjack wins by 5% per level (currently x" + (1 + (double) Math.round(GlobalCasinoPerks.getBlackjackPhrogMoneyMultiplier() * 100) / 100) + ").");
                buyPerkButton.setId(buttonId);
            }
            case "a8" -> {
                if(GlobalCasinoPerks.getTexasPokerPhrogMoneyMultiplierLevel() < GlobalCasinoState.maxBasicPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        GlobalCasinoPerks.setTexasPokerPhrogMoneyMultiplierLevel(GlobalCasinoPerks.getTexasPokerPhrogMoneyMultiplierLevel() + 1);
                        if(GlobalCasinoPerks.getTexasPokerPhrogMoneyMultiplierLevel() > GlobalCasinoState.maxBasicPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (GlobalCasinoPerks.getTexasPokerPhrogMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Phrog Face");
                perkDescription.setText("Increases the phrog coins payout from texas poker wins by 5% per level (currently x" + (1 + (double) Math.round(GlobalCasinoPerks.getTexasPokerPhrogMoneyMultiplier() * 100) / 100) + ").");
                buyPerkButton.setId(buttonId);
            }
            case "a9" -> {
                if(GlobalCasinoPerks.getGlobalPhrogMoneyMultiplierLevel() < GlobalCasinoState.maxAdvancedPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        GlobalCasinoPerks.setGlobalPhrogMoneyMultiplierLevel(GlobalCasinoPerks.getGlobalPhrogMoneyMultiplierLevel() + 1);
                        if(GlobalCasinoPerks.getGlobalPhrogMoneyMultiplierLevel() > GlobalCasinoState.maxAdvancedPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (GlobalCasinoPerks.getGlobalPhrogMoneyMultiplierLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Phrog Mentality");
                double rounded = (double) Math.round(GlobalCasinoPerks.getGlobalPhrogMoneyMultiplier() * 100) / 100;
                String formatted = String.format("%.2f", rounded).replaceFirst("0", "1"); //floating point issues
                perkDescription.setText("Increases all phrog coins payouts by 4% per level (currently x" + formatted + ", multiplicative).");
                buyPerkButton.setId(buttonId);
            }
            case "a10" -> {
                if(GlobalCasinoPerks.getGreenPhrogSlotChanceLevel() < GlobalCasinoState.maxAdvancedPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        GlobalCasinoPerks.setGreenPhrogSlotChanceLevel(GlobalCasinoPerks.getGreenPhrogSlotChanceLevel() + 1);
                        if(GlobalCasinoPerks.getGreenPhrogSlotChanceLevel() > GlobalCasinoState.maxAdvancedPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (GlobalCasinoPerks.getGreenPhrogSlotChanceLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Zeeper Luck");
                perkDescription.setText("Increases the chance of getting green phrogs in slots (idk by how much, math is hard).");
                buyPerkButton.setId(buttonId);
            }
            case "a11" -> {
                if(GlobalCasinoPerks.getPurplePhrogSlotChanceLevel() < GlobalCasinoState.maxAdvancedPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        GlobalCasinoPerks.setPurplePhrogSlotChanceLevel(GlobalCasinoPerks.getPurplePhrogSlotChanceLevel() + 1);
                        if(GlobalCasinoPerks.getPurplePhrogSlotChanceLevel() > GlobalCasinoState.maxAdvancedPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (GlobalCasinoPerks.getPurplePhrogSlotChanceLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Gia Luck");
                perkDescription.setText("Increases the chance of getting purple phrogs in slots (idk by how much, math is hard).");
                buyPerkButton.setId(buttonId);
            }
            case "a12" -> {
                if(GlobalCasinoPerks.getPhroogoSlotChanceLevel() < GlobalCasinoState.maxAdvancedPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        GlobalCasinoPerks.setPhroogoSlotChanceLevel(GlobalCasinoPerks.getPhroogoSlotChanceLevel() + 1);
                        if(GlobalCasinoPerks.getPhroogoSlotChanceLevel() > GlobalCasinoState.maxAdvancedPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (GlobalCasinoPerks.getPhroogoSlotChanceLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Phroogo Luck");
                perkDescription.setText("Increases the chance of getting blue phrogs in slots (idk by how much, math is hard).");
                buyPerkButton.setId(buttonId);
            }
            case "a13" -> {
                if(GlobalCasinoPerks.getRouletteTableRerollLevel() < GlobalCasinoState.maxAdvancedPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        GlobalCasinoPerks.setRouletteTableRerollLevel(GlobalCasinoPerks.getRouletteTableRerollLevel() + 1);
                        if(GlobalCasinoPerks.getRouletteTableRerollLevel() > GlobalCasinoState.maxAdvancedPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (GlobalCasinoPerks.getRouletteTableRerollLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Reroll Table");
                perkDescription.setText("Increases the chance of reroll if your didn't win in roulette by 5% per level (currently "  + GlobalCasinoPerks.getRouletteTableRerollLevel() * 5 + "%)");
                buyPerkButton.setId(buttonId);
            }
            case "a14" -> {
                if(GlobalCasinoPerks.getBlackJackBustLimitLevel() < GlobalCasinoState.maxOpPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        GlobalCasinoPerks.setBlackJackBustLimitLevel(GlobalCasinoPerks.getBlackJackBustLimitLevel() + 1);
                        if(GlobalCasinoPerks.getBlackJackBustLimitLevel() > GlobalCasinoState.maxOpPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (GlobalCasinoPerks.getBlackJackBustLimitLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("No Bustin'");
                perkDescription.setText("Increases the amount of points you can go over 21 in blackjack (currently:  "  + GlobalCasinoPerks.getBlackJackBustLimitLevel() + "), the game is still about who's closer to 21");
                buyPerkButton.setId(buttonId);
            }
            case "a15" -> {
                if(GlobalCasinoPerks.getTexasPokerHandEvaluationIncreaseLevel() < GlobalCasinoState.maxLegendaryPerkLevel) {
                    if(actionEvent.getSource() == buyPerkButton) {
                        GlobalCasinoPerks.setTexasPokerHandEvaluationIncreaseLevel(GlobalCasinoPerks.getTexasPokerHandEvaluationIncreaseLevel() + 1);
                        if(GlobalCasinoPerks.getTexasPokerHandEvaluationIncreaseLevel() > GlobalCasinoState.maxLegendaryPerkLevel - 1) {
                            maxLevelReached();
                        }
                    }
                } else {
                    maxLevelReached();
                }
                price *= (GlobalCasinoPerks.getTexasPokerHandEvaluationIncreaseLevel() + 1);
                buyPerkButton.setText("P$" + price);
                perkName.setText("Simply Better");
                perkDescription.setText("Increases the estimation of your poker hand by one (eg. high card -> pair)");
                buyPerkButton.setId(buttonId);
            }
        }
    }
}
