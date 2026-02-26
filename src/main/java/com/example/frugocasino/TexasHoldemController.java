package com.example.frugocasino;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TexasHoldemController {

    private final String[] handEvaluation = {"high card", "pair", "two pair", "three of a kind", "straight", "flush", "full house", "four of a kind", "straight flush", "royal flush"};
    public ImageView playerCard1, playerCard2, botCard1, botCard2, flopCard1, flopCard2, flopCard3, flopCard4, flopCard5, deckBack;
    public Button playButton, raiseButton, checkButton, foldButton, playAgainButton, backButton;
    public Label moneyLabel, evaluationLabel, betLabel, roundLabel, actionLabel, quotaLabel;
    public TextField raiseAmount;
    private final List<Card> playerHand = new ArrayList<>();
    private final List<Card> botHand = new ArrayList<>();
    private final List<Card> flop = new ArrayList<>();
    private int originalBalance;
    private int bet;
    private double betMultiplier;
    private int playerEvaluation;
    private int botEvaluation;
    private String botEvaluationString;
    private final List<Card> playerWinningHand = new ArrayList<>();
    private final List<Card> botWinningHand = new ArrayList<>();
    private final Deck deck = new Deck();
    private final GlobalCasinoPerks perks = new GlobalCasinoPerks();
    private final GlobalCasinoState state = new GlobalCasinoState();

    public void initialize() {
        state.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
    }

    public void playPoker(ActionEvent actionEvent) {
        originalBalance = state.getMoneyBalance();
        if(state.getMoneyBalance() >= 50 && state.getActionsLeft() > 0) {
            bet = 50;
            state.changeMoneyBalance(-bet);
            state.actionDecrement();
            state.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
            betLabel.setText("Pot: $" + bet);
            playButton.setDisable(true);
            playButton.setVisible(false);
            playerCard1.setDisable(false);
            playerCard1.setVisible(true);
            playerCard2.setDisable(false);
            playerCard2.setVisible(true);
            botCard1.setDisable(false);
            botCard1.setVisible(true);
            botCard2.setDisable(false);
            deck.createDeck();
            botCard2.setVisible(true);
            playerHand.add(deck.draw());
            playerHand.add(deck.draw());
            deck.displayCard(playerHand, playerCard1, 0);
            deck.displayCard(playerHand, playerCard2, 1);
            evaluateHand(playerHand, playerWinningHand);
            botHand.add(deck.draw());
            botHand.add(deck.draw());
            raiseButton.setDisable(false);
            raiseButton.setVisible(true);
            checkButton.setDisable(false);
            checkButton.setVisible(true);
            foldButton.setDisable(false);
            foldButton.setVisible(true);
            evaluationLabel.setVisible(true);
            backButton.setDisable(true);
        } else if (state.getMoneyBalance() >= 50) {
            evaluationLabel.setText("No actions left.");
            backButton.setDisable(false);
        } else {
            evaluationLabel.setText("No mone");
            backButton.setDisable(false);
        }
    }

    public void pokerRaise(ActionEvent actionEvent) {
        if(raiseAmount.isVisible()) {
            try {
                if(flop.isEmpty()) {
                    betMultiplier = 2;
                } else if (flop.size() == 3) {
                    betMultiplier = 1.7;
                } else if (flop.size() == 4) {
                    betMultiplier = 1.4;
                } else if (flop.size() == 5) {
                    betMultiplier = 1.1;
                }
                if (raiseAmount.getText().isEmpty() && state.getMoneyBalance() > 0) {
                    raiseAmount.setText("" + state.getMoneyBalance());
                    return;
                }
                int raise = Integer.parseInt(raiseAmount.getText());
                bet += (int) (raise * betMultiplier);
                if (bet > 0 && !(raise > state.getMoneyBalance())) {
                    state.changeMoneyBalance(-Integer.parseInt(raiseAmount.getText()));
                    state.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
                    betLabel.setText("Pot: $" + bet);
                    raiseAmount.setDisable(true);
                    raiseAmount.setVisible(false);
                    raiseAmount.clear();
                    continuePoker();
                } else {
                    raiseAmount.setPromptText("You can't bet that");
                    raiseAmount.clear();
                }
            } catch (NumberFormatException e) {
                raiseAmount.setPromptText("Invalid input");
                raiseAmount.clear();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            raiseAmount.setDisable(false);
            raiseAmount.setVisible(true);
        }
    }
    public void pokerCheck(ActionEvent actionEvent) {
        raiseAmount.setDisable(true);
        raiseAmount.setVisible(false);
        continuePoker();
    }
    public void pokerFold(ActionEvent actionEvent) {
        raiseButton.setDisable(true);
        raiseButton.setVisible(false);
        checkButton.setDisable(true);
        checkButton.setVisible(false);
        foldButton.setDisable(true);
        foldButton.setVisible(false);
        raiseAmount.setDisable(true);
        raiseAmount.setVisible(false);
        playAgainButton.setDisable(false);
        playAgainButton.setVisible(true);
        backButton.setDisable(false);
        deck.displayCard(botHand, botCard1, 0);
        deck.displayCard(botHand, botCard2, 1);
        botWin();
    }

    public void continuePoker() {
        if(flop.isEmpty()) {
            flop.add(deck.draw());
            flop.add(deck.draw());
            flop.add(deck.draw());
            addFlopToHand(flop, playerHand);
            addFlopToHand(flop, botHand);
            deck.displayCard(flop, flopCard1, 0);
            deck.displayCard(flop, flopCard2, 1);
            deck.displayCard(flop, flopCard3, 2);
            evaluateHand(playerHand, playerWinningHand);
        } else if(flop.size() == 3) {
            flop.add(deck.draw());
            addFlopToHand(flop, playerHand);
            addFlopToHand(flop, botHand);
            deck.displayCard(flop, flopCard4, 3);
            evaluateHand(playerHand, playerWinningHand);
        } else if(flop.size() == 4) {
            flop.add(deck.draw());
            addFlopToHand(flop, playerHand);
            addFlopToHand(flop, botHand);
            deck.displayCard(flop, flopCard5, 4);
            evaluateHand(playerHand, playerWinningHand);

        } else if(flop.size() == 5) {
            raiseButton.setDisable(true);
            raiseButton.setVisible(false);
            checkButton.setDisable(true);
            checkButton.setVisible(false);
            foldButton.setDisable(true);
            foldButton.setVisible(false);
            playAgainButton.setDisable(false);
            playAgainButton.setVisible(true);
            backButton.setDisable(false);
            deck.displayCard(botHand, botCard1, 0);
            deck.displayCard(botHand, botCard2, 1);
            playerEvaluation = evaluateHand(playerHand, playerWinningHand);
            botEvaluation = evaluateHand(botHand, botWinningHand);
            evaluateWinner(playerEvaluation, botEvaluation);
        }
    }

    public void playAgain(ActionEvent actionEvent) {
        playAgainButton.setDisable(true);
        playAgainButton.setVisible(false);
        playButton.setDisable(false);
        playButton.setVisible(true);
        flop.clear();
        playerHand.clear();
        botHand.clear();
        playerWinningHand.clear();
        botWinningHand.clear();
        playerCard1.setImage(null);
        playerCard2.setImage(null);
        flopCard1.setImage(new Image(getClass().getResourceAsStream("/images/deck/deckBack.png")));
        flopCard2.setImage(new Image(getClass().getResourceAsStream("/images/deck/deckBack.png")));
        flopCard3.setImage(new Image(getClass().getResourceAsStream("/images/deck/deckBack.png")));
        flopCard4.setImage(new Image(getClass().getResourceAsStream("/images/deck/deckBack.png")));
        flopCard5.setImage(new Image(getClass().getResourceAsStream("/images/deck/deckBack.png")));
        botCard1.setDisable(false);
        botCard1.setVisible(true);
        botCard2.setDisable(false);
        botCard2.setVisible(true);
        botCard1.setImage(new Image(getClass().getResourceAsStream("/images/deck/deckBack.png")));
        botCard2.setImage(new Image(getClass().getResourceAsStream("/images/deck/deckBack.png")));
        playPoker(actionEvent);
    }

    public int evaluateHand(List<Card> hand, List<Card> winningHand) {
        String evaluation = handEvaluation[0];
        List<Card> tempHand = new ArrayList<>(hand);
        int evaluationHierarchy = 0;
        tempHand.sort(null);
        if(hasRoyalFlush(tempHand, winningHand)) {
            evaluation = handEvaluation[9];
            evaluationHierarchy = 9;
        } else if(hasStraightFlush(tempHand, winningHand)) {
            evaluation = handEvaluation[8];
            evaluationHierarchy = 8;
        } else if(hasFourOfAKind(tempHand, winningHand)) {
            evaluation = handEvaluation[7];
            evaluationHierarchy = 7;
        } else if(hasFullHouse(tempHand, winningHand)) {
            evaluation = handEvaluation[6];
            evaluationHierarchy = 6;
        } else if(hasFlush(tempHand, winningHand)) {
            evaluation = handEvaluation[5];
            evaluationHierarchy = 5;
        } else if(hasStraight(tempHand, winningHand)) {
            evaluation = handEvaluation[4];
            evaluationHierarchy = 4;
        } else if(hasThreeOfAKind(tempHand, winningHand)) {
            evaluation = handEvaluation[3];
            evaluationHierarchy = 3;
        } else if(hasTwoPair(tempHand, winningHand)) {
            evaluation = handEvaluation[2];
            evaluationHierarchy = 2;
        } else if(hasPair(tempHand, winningHand)) {
            evaluation = handEvaluation[1];
            evaluationHierarchy = 1;
        } else {
            winningHand.add(hand.getLast());
        }

        if(hand == playerHand) {
            evaluationLabel.setText("You have a " + evaluation + "!");
            if(perks.getTexasPokerHandEvaluationIncreaseLevel() == 1) {
                evaluationHierarchy++;
                evaluationLabel.setText("You have a " + evaluation + " (" + handEvaluation[evaluationHierarchy] + ")!");
            }
        } else {
            botEvaluationString = evaluation;
        }

        return evaluationHierarchy;
    }

    public boolean hasRoyalFlush(List<Card> hand, List<Card> winningHand) {
        winningHand.clear();
        if(hasFlush(hand, winningHand)){
            winningHand.sort(null);
            if(winningHand.getLast().getValue() == 14 && hasStraight(winningHand, winningHand) ) {
                return true;
            }
        }
        return false;
    }
    public boolean hasStraightFlush(List<Card> hand, List<Card> winningHand) {
        winningHand.clear();
        if(hasFlush(hand, winningHand)) {
            winningHand.sort(null);
            if(hasStraight(winningHand, winningHand)) {
                return true;
            }
        }
        return false;
    }
    public boolean hasFourOfAKind(List<Card> hand, List<Card> winningHand) {
        winningHand.clear();
        for(int i = 0; i < hand.size() - 3; i++) {
            if(hand.get(i).getValue() == hand.get(i + 1).getValue() && hand.get(i + 1).getValue() == hand.get(i + 2).getValue() && hand.get(i + 2).getValue() == hand.get(i + 3).getValue()) {
                winningHand.add(hand.get(i));
                return true;
            }
        }
        return false;
    }
    public boolean hasFullHouse(List<Card> hand, List<Card> winningHand) {
        winningHand.clear();
        List<Card> temp = new ArrayList<>(hand);
        boolean three = false;
        for(int i = 0; i < temp.size() - 2; i++) {
            if(temp.get(i).getValue() == temp.get(i + 1).getValue() && temp.get(i + 1).getValue() == temp.get(i + 2).getValue()) {
                winningHand.add(temp.get(i));
                temp.remove(i);
                temp.remove(i);
                temp.remove(i);
                three = true;
                break;
            }
        }
        if(three) {
            for(int i = 0; i < temp.size() - 1; i++) {
                if(temp.get(i).getValue() == temp.get(i + 1).getValue()) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean hasFlush(List<Card> hand, List<Card> winningHand) {
        winningHand.clear();
        int h = 0;
        int d = 0;
        int c = 0;
        int s = 0;
        for (Card card : hand) {
            switch (card.getSuit()) {
                case 0 -> h++;
                case 1 -> d++;
                case 2 -> c++;
                case 3 -> s++;
            }
        }
        if(h > 4 || d > 4 || c > 4 || s > 4) {
            if(h > 4) {
                for (Card card : hand) {
                    if (card.getSuit() == 0) {
                        winningHand.add(card);
                    }
                }
            } else if(d > 4) {
                for (Card card : hand) {
                    if (card.getSuit() == 1) {
                        winningHand.add(card);
                    }
                }
            } else if(c > 4) {
                for (Card card : hand) {
                    if (card.getSuit() == 2) {
                        winningHand.add(card);
                    }
                }
            } else {
                for (Card card : hand) {
                    if (card.getSuit() == 3) {
                        winningHand.add(card);
                    }
                }
            }
            return true;
        }

        return false;
    }
    public boolean hasStraight(List<Card> hand, List<Card> winningHand) {
        winningHand.clear();
        List<Card> temp = new ArrayList<>(hand);
        temp = temp.reversed();
        for(int i = 0; i < temp.size() - 1; i++) {
            if (temp.get(i).getValue() == temp.get(i + 1).getValue()) {
                temp.remove(i);
                i--;
            }
        }
        for(int i = 0; i < temp.size() - 4; i++) {
            if(temp.get(i).getValue() == temp.get(i + 1).getValue() + 1 && temp.get(i + 1).getValue() == temp.get(i + 2).getValue() + 1 && temp.get(i + 2).getValue() == temp.get(i + 3).getValue() + 1 && temp.get(i + 3).getValue() == temp.get(i + 4).getValue() + 1) {
                winningHand.add(temp.get(i));
                return true;
            }
        }
        return false;
    }
    public boolean hasThreeOfAKind(List<Card> hand, List<Card> winningHand) {
        winningHand.clear();
        for(int i = 0; i < hand.size() - 2; i++) {
            if(hand.get(i).getValue() == hand.get(i + 1).getValue() && hand.get(i + 1).getValue() == hand.get(i + 2).getValue()) {
                winningHand.add(hand.get(i));
                return true;
            }
        }
        return false;
    }
    public boolean hasTwoPair(List<Card> hand, List<Card> winningHand) {
        winningHand.clear();
        int counter = 0;
        for(int i = 0; i < hand.size() - 1; i++) {
            if(hand.get(i).getValue() == hand.get(i + 1).getValue()) {
                winningHand.add(hand.get(i));
                counter++;
                i++;
            }
        }
        if(counter >= 2) {
            winningHand.sort(null);
            return true;
        } else {
            winningHand.clear();
        }
        return false;
    }
    public boolean hasPair(List<Card> hand, List<Card> winningHand) {
        winningHand.clear();
        for(int i = 0; i < hand.size() - 1; i++) {
            if(hand.get(i).getValue() == hand.get(i + 1).getValue()) {
                winningHand.add(hand.get(i));
                return true;
            }
        }
        return false;
    }

    public void evaluateWinner(double playerEvaluation, double botEvaluation) {
        if(playerEvaluation > botEvaluation) {
            playerWin();
        } else if(botEvaluation > playerEvaluation) {
            botWin();
        } else if(botEvaluation == playerEvaluation) {
            if(playerWinningHand.getLast().getValue() > botWinningHand.getLast().getValue()) {
                playerWin();
            } else if(botWinningHand.getLast().getValue() > playerWinningHand.getLast().getValue()) {
                botWin();
            } else if(botWinningHand.getLast().getValue() == playerWinningHand.getLast().getValue() || hasFlush(playerHand, playerWinningHand)) {
                evaluationLabel.setText("It's a tie. You get your money back");
                state.setMoneyBalance(originalBalance);
                state.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
            }
        }
    }

    public void playerWin() {
        int winAmount = (int)(bet * perks.getTexasPokerMoneyMultiplier() * perks.getGlobalMoneyMultiplier());
        evaluationLabel.setText("You have won! You get $" + winAmount + "!");
        state.changeMoneyBalance(winAmount);
        state.changeRoundMoneyMade(winAmount);
        state.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
    }

    public void botWin() {
        if (botEvaluation == playerEvaluation) {
            evaluationLabel.setText("You have lost to a higher card.");
        } else {
            evaluationLabel.setText("You have lost to a " + botEvaluationString + ".");
        }
    }

    public void addFlopToHand(List<Card> flop, List<Card> hand) {
        if(flop.size() == 3) {
            hand.addAll(flop);
        } else {
            hand.add(flop.getLast());
        }
    }

    public void switchToCasinoButton(ActionEvent actionEvent) throws IOException {
        state.switchToSceneButton(actionEvent);
    }
}