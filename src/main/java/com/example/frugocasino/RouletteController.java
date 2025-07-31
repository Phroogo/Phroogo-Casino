package com.example.frugocasino;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class RouletteController {

    private final int[] red = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
    private final int[] black = {2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35};
    private final int [] rouletteNumbers = {0, 1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36, 2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35};
    public Label moneyLabel, phrogMoneyLabel;
    public ToggleGroup rouletteBet;
    public RadioButton bet0, bet1, bet2, bet3, bet4, bet5, bet6, bet7, bet8, bet9, bet10, bet11, bet12, bet13, bet14, bet15, bet16, bet17, bet18, bet19, bet20, bet21, bet22, bet23, bet24, bet25, bet26, bet27, bet28, bet29, bet30, bet31, bet32, bet33, bet34, bet35, bet36, betColumn1, betColumn2, betColumn3, bet12one, bet12two, bet12three, bet18one, bet18two, betEven, betOdd, betRed, betBlack;
    public ArrayList<Integer> playerBet = new ArrayList<>();
    public Label betLabel;
    public TextField betAmount;
    private Random random = new Random();
    private boolean betPlaced = false;

    public void initialize() {
        moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
        phrogMoneyLabel.setText("P$" + GlobalCasinoState.getPhrogMoneyBalance());
    }

    public void rouletteSpin(ActionEvent actionEvent) {
        if(GlobalCasinoState.getMoneyBalance() > 0) {
            try {
                int bet = Integer.parseInt(betAmount.getText());
                if(bet > 0 && !(bet > GlobalCasinoState.getMoneyBalance())) {
                    if (betPlaced) {
                        GlobalCasinoState.setMoneyBalance(GlobalCasinoState.getMoneyBalance() - bet);
                        int roulette = random.nextInt(0, 37);

                        //●

                        if (playerBet.contains(roulette)) {
                            if (playerBet.size() == 1) {
                                betLabel.setText("The number was " + roulette + "! You get 35x your money!");
                                GlobalCasinoState.changeMoneyBalance((int)(35 * bet * GlobalCasinoPerks.getRouletteTableMoneyMultiplier() * GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                            } else if (playerBet.size() == 12) {
                                betLabel.setText("The number was " + roulette + "! You triple your money!");
                                GlobalCasinoState.changeMoneyBalance((int)(3 * bet * GlobalCasinoPerks.getRouletteTableMoneyMultiplier() * GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                            } else if (playerBet.size() == 18) {
                                betLabel.setText("The number was " + roulette + "! You double your money!");
                                GlobalCasinoState.changeMoneyBalance((int)(2 * bet * GlobalCasinoPerks.getRouletteTableMoneyMultiplier() * GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                            }
                        } else {
                            betLabel.setText("You have lost, the number was " + roulette);
                        }
                        moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
                    } else {
                        betLabel.setText("You have to place a bet first");
                    }
                }
                else {
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

    public void switchToCasinoButton(ActionEvent actionEvent) throws IOException {
        GlobalCasinoState.switchToCasinoButton(actionEvent);
    }

    public boolean chooseBet(ActionEvent actionEvent) {
        playerBet.clear();
        if(bet0.isSelected()){
            playerBet.add(0);
        } else if(bet1.isSelected()) {
            playerBet.add(1);
        } else if(bet2.isSelected()) {
            playerBet.add(2);
        } else if(bet3.isSelected()) {
            playerBet.add(3);
        } else if(bet4.isSelected()) {
            playerBet.add(4);
        } else if(bet5.isSelected()) {
            playerBet.add(5);
        } else if(bet6.isSelected()) {
            playerBet.add(6);
        } else if(bet7.isSelected()) {
            playerBet.add(7);
        } else if(bet8.isSelected()) {
            playerBet.add(8);
        } else if(bet9.isSelected()) {
            playerBet.add(9);
        } else if(bet10.isSelected()) {
            playerBet.add(10);
        } else if(bet11.isSelected()) {
            playerBet.add(11);
        } else if(bet12.isSelected()) {
            playerBet.add(12);
        } else if(bet13.isSelected()) {
            playerBet.add(13);
        } else if(bet14.isSelected()) {
            playerBet.add(14);
        } else if(bet15.isSelected()) {
            playerBet.add(15);
        } else if(bet16.isSelected()) {
            playerBet.add(16);
        } else if(bet17.isSelected()) {
            playerBet.add(17);
        } else if(bet18.isSelected()) {
            playerBet.add(18);
        } else if(bet19.isSelected()) {
            playerBet.add(19);
        } else if(bet20.isSelected()) {
            playerBet.add(20);
        } else if(bet21.isSelected()) {
            playerBet.add(21);
        } else if(bet22.isSelected()) {
            playerBet.add(22);
        } else if(bet23.isSelected()) {
            playerBet.add(23);
        } else if(bet24.isSelected()) {
            playerBet.add(24);
        } else if(bet25.isSelected()) {
            playerBet.add(25);
        } else if(bet26.isSelected()) {
            playerBet.add(26);
        } else if(bet27.isSelected()) {
            playerBet.add(27);
        } else if(bet28.isSelected()) {
            playerBet.add(28);
        } else if(bet29.isSelected()) {
            playerBet.add(29);
        } else if(bet30.isSelected()) {
            playerBet.add(30);
        } else if(bet31.isSelected()) {
            playerBet.add(31);
        } else if(bet32.isSelected()) {
            playerBet.add(32);
        } else if(bet33.isSelected()) {
            playerBet.add(33);
        } else if(bet34.isSelected()) {
            playerBet.add(34);
        } else if(bet35.isSelected()) {
            playerBet.add(35);
        } else if(bet36.isSelected()) {
            playerBet.add(36);
        } else if(betColumn1.isSelected()){
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
                betLabel.setText("You bet on even");
            }
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
        if(playerBet.size() == 1){
            betLabel.setText("You bet on " + playerBet.getFirst());
        }
        return betPlaced = true;
    }
}
