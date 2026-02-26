package com.example.frugocasino;

import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RouletteController {

    private final int[] red = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
    private final int[] black = {2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35};
    private final int [] rouletteNumbers = {0, 1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36, 2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35};
    public Label moneyLabel, roundLabel, actionLabel, quotaLabel;
    public ToggleGroup rouletteBet;
    public RadioButton bet0, bet1, bet2, bet3, bet4, bet5, bet6, bet7, bet8, bet9, bet10, bet11, bet12, bet13, bet14, bet15, bet16, bet17, bet18, bet19, bet20, bet21, bet22, bet23, bet24, bet25, bet26, bet27, bet28, bet29, bet30, bet31, bet32, bet33, bet34, bet35, bet36, betColumn1, betColumn2, betColumn3, bet12one, bet12two, bet12three, bet18one, bet18two, betEven, betOdd, betRed, betBlack;
    public List<Integer> playerBet = new ArrayList<>();
    public List<Integer> rerollList = new ArrayList<>();
    public Label betLabel;
    public TextField betAmount;
    private final Random random = new Random();
    private boolean betPlaced = false;
    private boolean rerollAvailable = true;
    private final GlobalCasinoPerks perks = new GlobalCasinoPerks();
    private final GlobalCasinoState state = new GlobalCasinoState();

    public void initialize() {
        state.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
        int temp = perks.getRouletteTableRerollLevel();
        while(temp > 0) {
            rerollList.add(temp);
            temp--;
        }
    }

    public void rouletteSpin(ActionEvent actionEvent) {
        if(state.getMoneyBalance() > 0) {
            try {
                if (betAmount.getText().isEmpty() && state.getMoneyBalance() > 0 && state.getActionsLeft() > 0) {
                    betAmount.setText("" + state.getMoneyBalance());
                    return;
                }
                int bet = Integer.parseInt(betAmount.getText());
                if(bet > 0 && !(bet > state.getMoneyBalance()) && state.getActionsLeft() > 0) {
                    if (betPlaced) {
                        if(rerollAvailable) {
                            state.changeMoneyBalance(-bet);
                            state.actionDecrement();
                        }
                        int roulette = random.nextInt(37);

                        if (playerBet.contains(roulette)) {
                            int winAmount = 0;
                            if (playerBet.size() == 1) {
                                winAmount = (int)(34 * bet * perks.getRouletteTableMoneyMultiplier() * perks.getGlobalMoneyMultiplier()) + bet;
                            } else if (playerBet.size() == 12) {
                                winAmount = (int)(2 * bet * perks.getRouletteTableMoneyMultiplier() * perks.getGlobalMoneyMultiplier()) + bet;
                            } else if (playerBet.size() == 18) {
                                winAmount = (int)(bet * perks.getRouletteTableMoneyMultiplier() * perks.getGlobalMoneyMultiplier()) + bet;
                            }
                            betLabel.setText("The number was " + roulette + "! You get $" + winAmount + "!");
                            state.changeMoneyBalance(winAmount);
                            state.changeRoundMoneyMade(winAmount - bet);
                        } else if(perks.getRouletteTableRerollLevel() > 0 && rerollAvailable) {
                            rerollAvailable = false;
                            int reroll = random.nextInt(1, 21);
                            if(rerollList.contains(reroll)) {
                                rouletteSpin(actionEvent);
                            } else {
                                betLabel.setText("You have lost, the number was " + roulette);
                                rerollAvailable = true;
                            }
                        } else {
                            betLabel.setText("You have lost, the number was " + roulette);
                            if (!rerollAvailable) return;
                        }
                        state.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
                        if(!rerollAvailable) {
                            betLabel.setText(betLabel.getText() + " (rerolled)");
                            rerollAvailable = true;
                        }
                    } else {
                        betLabel.setText("You have to place a bet first");
                    }
                } else if (bet > 0 && !(bet > state.getMoneyBalance())) {
                    betAmount.setPromptText("No actions left.");
                    betAmount.clear();
                } else {
                    betAmount.setPromptText("You can't bet that!");
                    betAmount.clear();
                }
            } catch (NumberFormatException e) {
                betAmount.setPromptText("Invalid input");
                betAmount.clear();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            betLabel.setText("You have run out of money");
        }
    }

    public boolean chooseBet(ActionEvent actionEvent) {
        playerBet.clear();
        try {
            playerBet.add(Integer.parseInt(((RadioButton) actionEvent.getSource()).getId().substring(3)));
            betLabel.setText("You bet on " + playerBet.getFirst());
        } catch (NumberFormatException e) {
            if(betColumn1.isSelected()){
                for(int i = 1; i < 37; i++){
                    if (i % 3 == 1) {
                        playerBet.add(i);
                    }
                }
                betLabel.setText("You bet on the first column");
            } else if(betColumn2.isSelected()){
                for(int i = 1; i < 37; i++){
                    if (i % 3 == 2) {
                        playerBet.add(i);
                    }
                }
                betLabel.setText("You bet on the second column");
            } else if(betColumn3.isSelected()){
                for(int i = 1; i < 37; i++){
                    if (i % 3 == 0) {
                        playerBet.add(i);
                    }
                }
                betLabel.setText("You bet on the third column");
            } else if(bet12one.isSelected()){
                for(int i = 1; i < 13; i++){
                    playerBet.add(i);
                }
                betLabel.setText("You bet on the first twelve");
            } else if(bet12two.isSelected()){
                for(int i = 13; i < 25; i++){
                    playerBet.add(i);
                }
                betLabel.setText("You bet on the second twelve");
            } else if(bet12three.isSelected()){
                for(int i = 25; i < 37; i++){
                    playerBet.add(i);
                }
                betLabel.setText("You bet on the third twelve");
            } else if(bet18one.isSelected()){
                for(int i = 1; i < 19; i++){
                    playerBet.add(i);
                }
                betLabel.setText("You bet on the first half");
            } else if(bet18two.isSelected()){
                for(int i = 19; i < 37; i++){
                    playerBet.add(i);
                }
                betLabel.setText("You bet on the second half");
            } else if(betEven.isSelected()){
                for(int i = 1; i < 37; i++){
                    if (i % 2 == 0) {
                        playerBet.add(i);
                    }
                }
                betLabel.setText("You bet on even");
            } else if(betOdd.isSelected()){
                for(int i = 1; i < 37; i++){
                    if (i % 2 == 1) {
                        playerBet.add(i);
                    }
                }
                betLabel.setText("You bet on odd");
            } else if(betRed.isSelected()){
                for(int i = 0; i < red.length; i++){
                    playerBet.add(red[i]);
                }
                betLabel.setText("You bet on red");
            } else if(betBlack.isSelected()){
                for(int i = 0; i < black.length; i++){
                    playerBet.add(black[i]);
                }
                betLabel.setText("You bet on black");
            }
        }
        return betPlaced = true;
    }

    public void switchToCasinoButton(ActionEvent actionEvent) throws IOException {
        state.switchToSceneButton(actionEvent);
    }
}
