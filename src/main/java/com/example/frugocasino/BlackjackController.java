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
    public Label insuranceLabel, moneyLabel, playerTotalLabel, dealerTotalLabel, roundLabel, actionLabel, quotaLabel;
    public ImageView dealerCard1, dealerCard2, dealerCard3, dealerCard4, dealerCard5, dealerCard6, dealerCard7, playerCard1, playerCard2, playerCard3, playerCard4, playerCard5, playerCard6, playerCard7, deckBack;
    public Button playButton, betButton, hitButton, standButton, doubleDownButton, playAgainButton, backButton;
    private final List<Card> playerHand = new ArrayList<>();
    private final List<Card> dealerHand = new ArrayList<>();
    private int bet, winAmount;
    private boolean insuranceBought = false;
    private int playerTotal = 0;
    private int dealerTotal = 0;
    private final Deck deck = new Deck();

    public void initialize() {
        GlobalCasinoState.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
    }

    public void playBlackjack(ActionEvent actionEvent) {
        deck.createDeck();
        betAmount.setDisable(false);
        betAmount.setVisible(true);
        betButton.setDisable(false);
        betButton.setVisible(true);
        playButton.setDisable(true);
        playButton.setVisible(false);
    }

    public void checkIfValidInputButton(ActionEvent actionEvent) {
        if (betAmount.getText().isEmpty() && GlobalCasinoState.getMoneyBalance() > 0 && GlobalCasinoState.getActionsLeft() > 0) {
            betAmount.setText("" + GlobalCasinoState.getMoneyBalance());
            return;
        }
        try {
            bet = Integer.parseInt(betAmount.getText());
            if(bet > 0 && !(bet > GlobalCasinoState.getMoneyBalance()) && GlobalCasinoState.getActionsLeft() > 0) {
                GlobalCasinoState.changeMoneyBalance(-bet);
                GlobalCasinoState.actionDecrement();
                GlobalCasinoState.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
                backButton.setDisable(true);
                dealerBlackjackCheck();
            } else if (bet > 0 && !(bet > GlobalCasinoState.getMoneyBalance())) {
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
    }

    public void dealerBlackjackCheck() {
        betButton.setDisable(true);
        betButton.setVisible(false);
        betAmount.setVisible(false);
        dealerHand.add(deck.draw());
        dealerTotalLabel.setVisible(true);
        dealerTotalLabel.setText("Dealer total is: " + getTotal(dealerHand));
        deck.displayCard(dealerHand, dealerCard1, 0);
        if(dealerHand.getFirst().getValue() == 14) {
            insuranceLabel.setVisible(true);
            yesInsurance.setDisable(false);
            yesInsurance.setVisible(true);
            noInsurance.setDisable(false);
            noInsurance.setVisible(true);
            insuranceLabel.setText("Dealer's first card is an ace, would you like to buy insurance?");
            dealerHand.add(deck.draw());
        } else {
            playerHand.add(deck.draw());
            playerHand.add(deck.draw());
            deck.displayCard(playerHand, playerCard1, 0);
            deck.displayCard(playerHand, playerCard2, 1);
            playerCard1.setVisible(true);
            playerCard2.setVisible(true);
            playerTotalLabel.setVisible(true);
            playerTotalLabel.setText("Your total is: " + getTotal(playerHand));
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
        playerHand.add(deck.draw());
        displayPlayerCard(playerHand);
        playerTotal = getTotal(playerHand);
        playerTotalLabel.setText("Your total is: " + playerTotal);
        if (playerTotal > 21 + GlobalCasinoPerks.getBlackJackBustLimitLevel()) {
            insuranceLabel.setVisible(true);
            insuranceLabel.setText("Your total is " +  getTotal(playerHand) + "! You have busted.");
            endGame();
        }
    }

    public void blackjackStand(ActionEvent actionEvent) {
        playerTotal = getTotal(playerHand);
        playerTotalLabel.setText("Your total is: " + playerTotal);
        insuranceLabel.setVisible(false);
        dealerTurn();
    }

    public void blackjackDoubleDown(ActionEvent actionEvent) {
        if(GlobalCasinoState.getMoneyBalance() >= bet) {
            insuranceLabel.setVisible(false);
            GlobalCasinoState.changeMoneyBalance(-bet);
            bet *= 2;
            GlobalCasinoState.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
            playerHand.add(deck.draw());
            displayPlayerCard(playerHand);
            playerTotal = getTotal(playerHand);
            playerTotalLabel.setText("Your total is: " + playerTotal);
            if (playerTotal <= 21 + GlobalCasinoPerks.getBlackJackBustLimitLevel()) {
                dealerTurn();
            } else {
                insuranceLabel.setVisible(true);
                insuranceLabel.setText("Your total is " + getTotal(playerHand) + "! You have busted.");
                endGame();
            }
        } else {
            playerTotalLabel.setText("Not enough money to double down");
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> playerTotalLabel.setText("Your total is: " + getTotal(playerHand)));
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
            dealerHand.add(deck.draw());
        }
        dealerActions.add(() -> deck.displayCard(dealerHand, dealerCard2, 1));
        dealerActionsDelay.add(1.0);
        dealerTotal = getTotal(dealerHand);
        dealerTotalLabel.setText("Dealer total is: " + dealerTotal);
        while(dealerTotal < 17) {
            dealerHand.add(deck.draw());

            int tempTotal = getTotal(new ArrayList<>(dealerHand));
            List<Card> tempHand = new ArrayList<>(dealerHand);

            dealerActions.add(() -> {
                displayDealerCard(tempHand);
                dealerTotalLabel.setText("Dealer total is: " + tempTotal);
            });

            dealerActionsDelay.add(1.0);
            dealerTotal = getTotal(dealerHand);
        }
        dealerActions.add(() -> {
            playerTotal = getTotal(playerHand);
            insuranceLabel.setVisible(true);

            if(dealerTotal > 21) {
                insuranceLabel.setText("Dealer has busted!");
                winAmount = (int)(bet * GlobalCasinoPerks.getBlackjackMoneyMultiplier() * GlobalCasinoPerks.getGlobalMoneyMultiplier()) + bet;
            } else if(21 - dealerTotal < Math.abs(21 - playerTotal)) {
                bet = 0;
                insuranceLabel.setText("Dealer has won.");
            } else if(21 - dealerTotal == Math.abs(21 - playerTotal)) {
                winAmount = bet;
                insuranceLabel.setText("It's a tie!");
            } else {
                insuranceLabel.setText("You won! Congratulations");
                winAmount = (int)(bet * GlobalCasinoPerks.getBlackjackMoneyMultiplier() * GlobalCasinoPerks.getGlobalMoneyMultiplier()) + bet;
            }
            GlobalCasinoState.changeMoneyBalance(winAmount);
            GlobalCasinoState.changeRoundMoneyMade(winAmount - bet);
            GlobalCasinoState.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
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

    public void displayPlayerCard(List<Card> hand) {
        switch (hand.size()) {
            case 3 -> deck.displayCard(hand, playerCard3, 2);
            case 4 -> deck.displayCard(hand, playerCard4, 3);
            case 5 -> deck.displayCard(hand, playerCard5, 4);
            case 6 -> deck.displayCard(hand, playerCard6, 5);
            case 7 -> deck.displayCard(hand, playerCard7, 6);
        }
    }

    public void displayDealerCard(List<Card> hand) {
        switch (hand.size()) {
            case 3 -> deck.displayCard(hand, dealerCard3, 2);
            case 4 -> deck.displayCard(hand, dealerCard4, 3);
            case 5 -> deck.displayCard(hand, dealerCard5, 4);
            case 6 -> deck.displayCard(hand, dealerCard6, 5);
            case 7 -> deck.displayCard(hand, dealerCard7, 6);
        }
    }

    public int getTotal(List<Card> hand) {
        List<Card> tempHand = new ArrayList<>(hand);
        tempHand.sort(null);
        int total = 0;
        for (Card card : tempHand) {
            total += getBlackjackValue(card);
        }
        while(total > 21 + GlobalCasinoPerks.getBlackJackBustLimitLevel() && tempHand.getLast().getValue() == 14) {
            tempHand.set(tempHand.size() - 1, new Card(1, tempHand.getLast().getSuit()));
            tempHand.sort(null);
            total -= 10;
        }
        return total;
    }

    public void switchToCasinoButton(ActionEvent actionEvent) throws IOException {
        GlobalCasinoState.switchToSceneButton(actionEvent);
    }

    public void insuranceButton(ActionEvent actionEvent) {
        if(bet * 0.5 > GlobalCasinoState.getMoneyBalance()) {
            yesInsurance.setDisable(true);
        }
        if(yesInsurance.isSelected() && !(bet * 0.5 > GlobalCasinoState.getMoneyBalance())) {
            insuranceBought = true;
            GlobalCasinoState.changeMoneyBalance(-((int)(0.5 * bet)));
            GlobalCasinoState.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
        }
        insuranceLabel.setVisible(false);
        yesInsurance.setDisable(true);
        yesInsurance.setVisible(false);
        noInsurance.setDisable(true);
        noInsurance.setVisible(false);
        if (getBlackjackValue(dealerHand.get(1)) == 10 && insuranceBought) {
            insuranceLabel.setVisible(true);
            deck.displayCard(dealerHand, dealerCard2, 1);
            insuranceLabel.setText("Dealer had blackjack but you bought the insurance");
            winAmount = (int)(2 * bet * GlobalCasinoPerks.getBlackjackMoneyMultiplier() * GlobalCasinoPerks.getGlobalMoneyMultiplier()) + bet;
            GlobalCasinoState.changeMoneyBalance(winAmount);
            GlobalCasinoState.changeRoundMoneyMade(winAmount - bet);
            GlobalCasinoState.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
            dealerTotalLabel.setText("Dealer total is: " + getTotal(dealerHand));
            endGame();
        } else if (getBlackjackValue(dealerHand.get(1)) == 10 && !insuranceBought) {
            insuranceLabel.setVisible(true);
            deck.displayCard(dealerHand, dealerCard2, 1);
            insuranceLabel.setText("Dealer had blackjack and you didn't buy the insurance");
            endGame();
        } else {
            insuranceLabel.setVisible(true);
            insuranceLabel.setText("Dealer didn't have blackjack");
            playerHand.add(deck.draw());
            playerHand.add(deck.draw());
            deck.displayCard(playerHand, playerCard1, 0);
            deck.displayCard(playerHand, playerCard2, 1);
            playerCard1.setVisible(true);
            playerCard2.setVisible(true);
            playerTotalLabel.setVisible(true);
            playerTotalLabel.setText("Your total is: " + getTotal(playerHand));
            continueBlackjack();
        }
        insurance.selectToggle(null);
    }

    public void playAgain(ActionEvent actionEvent) {
        deck.createDeck();
        winAmount = 0;
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
        playerHand.clear();
        dealerHand.clear();
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

    public int getBlackjackValue(Card card) {
        int value;
        switch (card.getValue()) {
            case 14 -> value = 11;
            case 13, 12, 11 -> value = 10;
            default -> value = card.getValue();

        }
        return value;
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