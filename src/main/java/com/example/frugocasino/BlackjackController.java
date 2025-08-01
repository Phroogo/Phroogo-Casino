package com.example.frugocasino;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlackjackController {
    public TextField betAmount;
    public RadioButton yesInsurance, noInsurance;
    public ToggleGroup insurance;
    public Label insuranceLabel, moneyLabel, phrogMoneyLabel, playerTotalLabel, dealerTotalLabel;
    Random random = new Random();
    private final String[] deck = {"h2", "d2", "c2", "s2", "h3", "d3", "c3", "s3", "h4" , "d4", "c4", "s4", "h5", "d5", "c5", "s5", "h6", "d6", "c6", "s6", "h7", "d7", "c7", "s7", "h8", "d8", "c8", "s8", "h9", "d9", "c9", "s9", "h10", "d10", "c10", "s10", "hJack", "dJack", "cJack", "sJack", "hQueen", "dQueen", "cQueen", "sQueen", "hKing", "dKing", "cKing", "sKing", "hAce", "dAce", "cAce", "sAce"};
    private final int[] deckValues = {2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8, 9, 9, 9, 9, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11};
    public ImageView dealerCard1, dealerCard2, dealerCard3, dealerCard4, dealerCard5, dealerCard6, dealerCard7, playerCard1, playerCard2, playerCard3, playerCard4, playerCard5, playerCard6, playerCard7, deckBack;
    public Button playButton, betButton, hitButton, standButton, doubleDownButton, playAgainButton, backButton;
    List<String> playerHand = new ArrayList<>();
    List<Integer> playerHandValue = new ArrayList<>();
    List<String> dealerHand = new ArrayList<>();
    List<Integer> dealerHandValue = new ArrayList<>();
    List<String> tempDeck = new ArrayList<>();
    List<Integer> tempDeckValues = new ArrayList<>();
    int bet;
    boolean insuranceBought = false;
    int playerTotal = 0;
    int dealerTotal = 0;

    public void initialize() {
        moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
        phrogMoneyLabel.setText("P$" + GlobalCasinoState.getPhrogMoneyBalance());
    }

    public void playBlackjack(ActionEvent actionEvent) {
        betAmount.setDisable(false);
        betAmount.setVisible(true);
        betButton.setDisable(false);
        betButton.setVisible(true);
        playButton.setDisable(true);
        playButton.setVisible(false);
    }

    public void checkIfValidInputButton(ActionEvent actionEvent) {
        try {
            bet = Integer.parseInt(betAmount.getText());
            if(bet > 0 && !(bet > GlobalCasinoState.getMoneyBalance())) {
                GlobalCasinoState.changeMoneyBalance(-bet);
                moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
                backButton.setDisable(true);
                dealerBlackjackCheck();
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
    }

    public void dealerBlackjackCheck() {
        for(int i = 0; i < deck.length; i++) {
            tempDeck.add(deck[i]);
            tempDeckValues.add(deckValues[i]);
        }
        betButton.setDisable(true);
        betButton.setVisible(false);
        betAmount.setVisible(false);
        draw(tempDeckValues, tempDeck, dealerHandValue, dealerHand);
        dealerTotalLabel.setVisible(true);
        dealerTotalLabel.setText("Dealer total is: " + getTotal(dealerHandValue));
        dealerCard1.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  dealerHand.getFirst() + ".png")));

        if(dealerHandValue.getFirst() == 11) {
            insuranceLabel.setVisible(true);
            yesInsurance.setDisable(false);
            yesInsurance.setVisible(true);
            noInsurance.setDisable(false);
            noInsurance.setVisible(true);
            insuranceLabel.setText("Dealer's first card is an ace, would you like to buy insurance?");
            draw(tempDeckValues, tempDeck, dealerHandValue, dealerHand);
        } else {
            draw(tempDeckValues, tempDeck, playerHandValue, playerHand);
            draw(tempDeckValues, tempDeck, playerHandValue, playerHand);
            playerCard1.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  playerHand.getFirst() + ".png")));
            playerCard2.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  playerHand.get(1) + ".png")));
            playerCard1.setVisible(true);
            playerCard2.setVisible(true);
            playerTotalLabel.setVisible(true);
            playerTotalLabel.setText("Your total is: " + getTotal(playerHandValue));
            continueBlackjack();
        }
    }

    public void continueBlackjack() {
        hitButton.setDisable(false);
        hitButton.setVisible(true);
        standButton.setDisable(false);
        standButton.setVisible(true);
        doubleDownButton.setDisable(false);
        doubleDownButton.setVisible(true);
    }

    public void blackjackHit(ActionEvent actionEvent) {
        insuranceLabel.setVisible(false);
        draw(tempDeckValues, tempDeck, playerHandValue, playerHand);
        displayPlayerCard(playerHand);
        playerTotal = getTotal(playerHandValue);
        playerTotalLabel.setText("Your total is: " + playerTotal);
//        if (playerTotal > 21 && playerHandValue.contains(11)) {
//            playerTotal = getTotal(playerHandValue) - 10;
//        }
        if (playerTotal > 21) {
            insuranceLabel.setVisible(true);
            insuranceLabel.setText("Your total is " +  getTotal(playerHandValue) + "! You have busted.");
            endGame();
        }
    }

    public void blackjackStand(ActionEvent actionEvent) {
        playerTotal = getTotal(playerHandValue);
        playerTotalLabel.setText("Your total is: " + playerTotal);
        insuranceLabel.setVisible(false);
        dealerTurn();
    }

    public void blackjackDoubleDown(ActionEvent actionEvent) {
        if(GlobalCasinoState.getMoneyBalance() >= bet) {
            insuranceLabel.setVisible(false);
            GlobalCasinoState.changeMoneyBalance(-bet);
            bet *= 2;
            moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
            draw(tempDeckValues, tempDeck, playerHandValue, playerHand);
            displayPlayerCard(playerHand);
            playerTotal = getTotal(playerHandValue);
            playerTotalLabel.setText("Your total is: " + playerTotal);
            if (playerTotal <= 21) {
                dealerTurn();
            } else {
                insuranceLabel.setVisible(true);
                insuranceLabel.setText("Your total is " + getTotal(playerHandValue) + "! You have busted.");
                endGame();
            }
        } else {
            playerTotalLabel.setText("Not enough money to double down");
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> playerTotalLabel.setText("Your total is: " + getTotal(playerHandValue)));
            pause.play();
        }
    }

    public void dealerTurn() {
        List<Runnable> dealerActions = new ArrayList<>();
        List<Double> dealerActionsDelay = new ArrayList<>();
        hitButton.setDisable(true);
        hitButton.setVisible(false);
        standButton.setDisable(true);
        standButton.setVisible(false);
        doubleDownButton.setDisable(true);
        doubleDownButton.setVisible(false);
        if (dealerHand.size() == 1) {
            draw(tempDeckValues, tempDeck, dealerHandValue, dealerHand);
        }
        String temp = dealerHand.getLast();
        dealerActions.add(() -> dealerCard2.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  temp + ".png"))));
        dealerActionsDelay.add(1.0);
        dealerTotal = getTotal(dealerHandValue);
        dealerTotalLabel.setText("Dealer total is: " + dealerTotal);
        while(dealerTotal < 17) {
            draw(tempDeckValues, tempDeck, dealerHandValue, dealerHand);
            dealerActions.add(() -> {
                displayDealerCard(dealerHand);
                dealerTotalLabel.setText("Dealer total is: " + dealerTotal);
            });
            dealerActionsDelay.add(1.0);
            dealerTotal = getTotal(dealerHandValue);
        }
        dealerActions.add(() -> {
            playerTotal = getTotal(playerHandValue);
            insuranceLabel.setVisible(true);
            if(dealerTotal > 21) {
                insuranceLabel.setText("Dealer has busted!");
                GlobalCasinoState.changeMoneyBalance((int)(2 * bet + bet * GlobalCasinoPerks.getRouletteTableMoneyMultiplier() + bet * GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                GlobalCasinoState.changePhrogMoneyBalance(GlobalCasinoState.calculatePhrogCoins((int)(bet + bet * GlobalCasinoPerks.getBlackjackPhrogMoneyMultiplier() + bet * GlobalCasinoPerks.getGlobalPhrogMoneyMultiplier())));
                moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
            } else if(dealerTotal > playerTotal) {
                insuranceLabel.setText("Dealer has won.");
            } else if(dealerTotal == playerTotal) {
                insuranceLabel.setText("It's a tie!");
                GlobalCasinoState.changeMoneyBalance(bet);
                moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
            } else {
                insuranceLabel.setText("You won! Congratulations");
                GlobalCasinoState.changeMoneyBalance((int)(2 * bet + bet * GlobalCasinoPerks.getRouletteTableMoneyMultiplier() + bet * GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                GlobalCasinoState.changePhrogMoneyBalance(GlobalCasinoState.calculatePhrogCoins((int)(bet + bet * GlobalCasinoPerks.getBlackjackPhrogMoneyMultiplier() + bet * GlobalCasinoPerks.getGlobalPhrogMoneyMultiplier())));
                moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
            }
            endGame();
        });
        dealerActionsDelay.add(0.0);
        playDelayedSteps(dealerActions, dealerActionsDelay);
    }

    public void endGame() {
        hitButton.setDisable(true);
        hitButton.setVisible(false);
        standButton.setDisable(true);
        standButton.setVisible(false);
        doubleDownButton.setDisable(true);
        doubleDownButton.setVisible(false);
        playAgainButton.setDisable(false);
        playAgainButton.setVisible(true);
        backButton.setDisable(false);
    }

    public void displayPlayerCard(List<String> hand) {
        switch (hand.size()) {
            case 3 -> playerCard3.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  playerHand.getLast() + ".png")));
            case 4 -> playerCard4.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  playerHand.getLast() + ".png")));
            case 5 -> playerCard5.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  playerHand.getLast() + ".png")));
            case 6 -> playerCard6.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  playerHand.getLast() + ".png")));
            case 7 -> playerCard7.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  playerHand.getLast() + ".png")));
        }
    }

    public void displayDealerCard(List<String> hand) {
        switch (hand.size()) {
            case 3 -> dealerCard3.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  dealerHand.getLast() + ".png")));
            case 4 -> dealerCard4.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  dealerHand.getLast() + ".png")));
            case 5 -> dealerCard5.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  dealerHand.getLast() + ".png")));
            case 6 -> dealerCard6.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  dealerHand.getLast() + ".png")));
            case 7 -> dealerCard7.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  dealerHand.getLast() + ".png")));
        }
    }

    public void draw(List<Integer> cardValues, List<String> cards, List<Integer> handValue, List<String> hand) {
        int rand = random.nextInt(0, cardValues.size());
        int cardValue = cardValues.get(rand);
        String card = cards.get(rand);
        handValue.add(cardValue);
        hand.add(card);
        cardValues.remove(rand);
        cards.remove(rand);
    }

    public int getTotal(List<Integer> handValue) {
        int total = 0;
        for (Integer card : handValue) {
            total += card;
        }
        while(total > 21 && handValue.contains(11)) {
            handValue.set(handValue.indexOf(11), 1);
            total -= 10;
        }
        return total;
    }

    public void switchToCasinoButton(ActionEvent actionEvent) throws IOException {
        GlobalCasinoState.switchToCasinoButton(actionEvent);
    }

    public void insuranceButton(ActionEvent actionEvent) {
        if(yesInsurance.isSelected()) {
            insuranceLabel.setVisible(false);
            yesInsurance.setDisable(true);
            yesInsurance.setVisible(false);
            noInsurance.setDisable(true);
            noInsurance.setVisible(false);
            insuranceBought = true;
            GlobalCasinoState.changeMoneyBalance(-((int)(0.5 * bet)));
            moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
        } else if (noInsurance.isSelected()) {
            insuranceLabel.setVisible(false);
            yesInsurance.setDisable(true);
            yesInsurance.setVisible(false);
            noInsurance.setDisable(true);
            noInsurance.setVisible(false);
        }
        if (dealerHandValue.get(1) == 10 && insuranceBought) {
            insuranceLabel.setVisible(true);
            dealerCard2.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  dealerHand.getLast() + ".png")));
            insuranceLabel.setText("Dealer had blackjack but you bought the insurance");
            GlobalCasinoState.changeMoneyBalance((int)(3 * bet + 1.5 * bet * GlobalCasinoPerks.getRouletteTableMoneyMultiplier() + 1.5 * bet * GlobalCasinoPerks.getGlobalMoneyMultiplier()));
            GlobalCasinoState.changePhrogMoneyBalance(GlobalCasinoState.calculatePhrogCoins((int)(1.5 * bet + 1.5 * bet * GlobalCasinoPerks.getBlackjackPhrogMoneyMultiplier() + 1.5 * bet * GlobalCasinoPerks.getGlobalPhrogMoneyMultiplier())));
            moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
            dealerTotalLabel.setText("Dealer total is: " + getTotal(dealerHandValue));
            endGame();
        } else if (dealerHandValue.get(1) == 10 && !insuranceBought) {
            insuranceLabel.setVisible(true);
            dealerCard2.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  dealerHand.getLast() + ".png")));
            insuranceLabel.setText("Dealer had blackjack and you didn't buy the insurance");
            endGame();
        } else {
            insuranceLabel.setVisible(true);
            insuranceLabel.setText("Dealer didn't have blackjack");
            draw(tempDeckValues, tempDeck, playerHandValue, playerHand);
            draw(tempDeckValues, tempDeck, playerHandValue, playerHand);
            playerCard1.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  playerHand.getFirst() + ".png")));
            playerCard2.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  playerHand.get(1) + ".png")));
            playerCard1.setVisible(true);
            playerCard2.setVisible(true);
            playerTotalLabel.setVisible(true);
            playerTotalLabel.setText("Your total is: " + getTotal(playerHandValue));
            continueBlackjack();
        }
        insurance.selectToggle(null);
    }

    public void playAgain(ActionEvent actionEvent) {
        playerCard1.setImage(null);
        playerCard2.setImage(null);
        playerCard3.setImage(null);
        playerCard4.setImage(null);
        playerCard5.setImage(null);
        playerCard6.setImage(null);
        playerCard7.setImage(null);
        dealerCard1.setImage(new Image(getClass().getResourceAsStream("/images/deck/deckBack.png")));
        dealerCard2.setImage(new Image(getClass().getResourceAsStream("/images/deck/deckBack.png")));
        dealerCard3.setImage(null);
        dealerCard4.setImage(null);
        dealerCard5.setImage(null);
        dealerCard6.setImage(null);
        dealerCard7.setImage(null);
        betAmount.setDisable(false);
        betAmount.setVisible(true);
        betButton.setDisable(false);
        betButton.setVisible(true);
        playAgainButton.setDisable(true);
        playAgainButton.setVisible(false);
        playerHandValue.clear();
        playerHand.clear();
        dealerHandValue.clear();
        dealerHand.clear();
        tempDeckValues.clear();
        tempDeck.clear();
        insuranceBought = false;
        insuranceLabel.setVisible(false);
        hitButton.setDisable(true);
        hitButton.setVisible(false);
        standButton.setDisable(true);
        standButton.setVisible(false);
        doubleDownButton.setDisable(true);
        doubleDownButton.setVisible(false);
        dealerTotalLabel.setVisible(false);
        playerTotalLabel.setVisible(false);
    }

    private void playDelayedSteps(List<Runnable> steps, List<Double> delaysInSeconds) {
        if (steps.isEmpty() || delaysInSeconds.isEmpty()) return;

        Runnable currentStep = steps.remove(0);
        double currentDelay = delaysInSeconds.remove(0);

        currentStep.run();

        if (!steps.isEmpty()) {
            PauseTransition pause = new PauseTransition(Duration.seconds(currentDelay));
            pause.setOnFinished(e -> playDelayedSteps(steps, delaysInSeconds));
            pause.play();
        }
    }
}
