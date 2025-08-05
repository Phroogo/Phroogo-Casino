package com.example.frugocasino;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TexasHoldemController {

    private final String[] deck = {"h2", "d2", "c2", "s2", "h3", "d3", "c3", "s3", "h4" , "d4", "c4", "s4", "h5", "d5", "c5", "s5", "h6", "d6", "c6", "s6", "h7", "d7", "c7", "s7", "h8", "d8", "c8", "s8", "h9", "d9", "c9", "s9", "h10", "d10", "c10", "s10", "hJack", "dJack", "cJack", "sJack", "hQueen", "dQueen", "cQueen", "sQueen", "hKing", "dKing", "cKing", "sKing", "hAce", "dAce", "cAce", "sAce"};
    private final String[] heart = {"h2", "h3", "h4", "h5", "h6", "h7", "h8", "h9", "h10", "hJack", "hQueen", "hKing", "hAce"};
    private final String[] diamond = {"d2", "d3", "d4", "d5", "d6", "d7", "d8", "d9", "d10", "dJack", "dQueen", "dKing", "dAce"};
    private final String[] club = {"c2", "c3", "c4", "c5", "c6", "c7", "c8", "c9", "c10", "cJack", "cQueen", "cKing", "cAce"};
    private final String[] spade = {"s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9", "s10", "sJack", "sQueen", "sKing", "sAce"};
    private final int[] deckValues = {2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8, 9, 9, 9, 9, 10, 10, 10, 10, 11, 11, 11, 11, 12, 12, 12, 12, 13, 13, 13, 13, 14, 14, 14, 14};
    private final String[] handEvaluation = {"royal flush", "straight flush", "four of a kind", "full house", "flush", "straight", "three of a kind", "two pair", "pair", "high card"};
    public ImageView playerCard1, playerCard2, botCard1, botCard2, flopCard1, flopCard2, flopCard3, flopCard4, flopCard5, deckBack;
    public Button playButton, raiseButton, checkButton, foldButton, playAgainButton, backButton;
    public Label moneyLabel, phrogMoneyLabel, evaluationLabel, betLabel;
    public TextField raiseAmount;
    Random random = new Random();
    List<String> playerHand = new ArrayList<>();
    List<Integer> playerHandValue = new ArrayList<>();
    List<String> botHand = new ArrayList<>();
    List<Integer> botHandValue = new ArrayList<>();
    List<String> flop = new ArrayList<>();
    List<Integer> flopValue = new ArrayList<>();
    List<String> tempDeck = new ArrayList<>();
    List<Integer> tempDeckValues = new ArrayList<>();
    int originalBalance;
    int bet;
    double betMultiplier;
    int playerEvaluation;
    int botEvaluation;
    List<Integer> playerWinningHand = new ArrayList<>();
    List<Integer> botWinningHand = new ArrayList<>();

    public void initialize() {
        moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
        phrogMoneyLabel.setText("P$" + GlobalCasinoState.getPhrogMoneyBalance());
    }

    public void playPoker(ActionEvent actionEvent) {
        for(int i = 0; i < deck.length; i++) {
            tempDeck.add(deck[i]);
            tempDeckValues.add(deckValues[i]);
        }
        originalBalance = GlobalCasinoState.getMoneyBalance();
        if(GlobalCasinoState.getMoneyBalance() >= 50) {
            bet = 50;
            GlobalCasinoState.changeMoneyBalance(-50);
            moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
            betLabel.setText("Bet: $" + bet);
            playButton.setDisable(true);
            playButton.setVisible(false);
            playerCard1.setDisable(false);
            playerCard1.setVisible(true);
            playerCard2.setDisable(false);
            playerCard2.setVisible(true);
            botCard1.setDisable(false);
            botCard1.setVisible(true);
            botCard2.setDisable(false);
            botCard2.setVisible(true);
            draw(tempDeckValues, tempDeck, playerHandValue, playerHand);
            draw(tempDeckValues, tempDeck, playerHandValue, playerHand);
            playerCard1.setImage(new Image(getClass().getResourceAsStream("/images/deck/" + playerHand.getFirst() + ".png")));
            playerCard2.setImage(new Image(getClass().getResourceAsStream("/images/deck/" + playerHand.get(1) + ".png")));
            playerEvaluation = evaluateHand(playerHandValue, playerHand, playerWinningHand);
            draw(tempDeckValues, tempDeck, botHandValue, botHand);
            draw(tempDeckValues, tempDeck, botHandValue, botHand);
            raiseButton.setDisable(false);
            raiseButton.setVisible(true);
            checkButton.setDisable(false);
            checkButton.setVisible(true);
            foldButton.setDisable(false);
            foldButton.setVisible(true);
            evaluationLabel.setVisible(true);
            backButton.setDisable(true);
        } else {
            playButton.setText("No mone");
            backButton.setDisable(false);
        }
    }

    public void pokerRaise(ActionEvent actionEvent) {
        if(raiseAmount.isVisible()) {
            try {
                if(flop.isEmpty()) {
                    betMultiplier = 1.5;
                } else if (flop.size() == 3) {
                    betMultiplier = 1.2;
                } else if (flop.size() == 4) {
                    betMultiplier = 0.8;
                } else if (flop.size() == 5) {
                    betMultiplier = 0.6;
                }
                bet += (int)(Integer.parseInt(raiseAmount.getText()) * betMultiplier);
                if(bet > 0 && !(bet/3 > GlobalCasinoState.getMoneyBalance())) {
                    GlobalCasinoState.changeMoneyBalance(-Integer.parseInt(raiseAmount.getText()));
                    moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
                    betLabel.setText("Pot: $" + 2 * bet);
                    raiseAmount.setDisable(true);
                    raiseAmount.setVisible(false);
                    raiseAmount.clear();
                    continuePoker();
                }
                else {
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
        botCard1.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  botHand.getFirst() + ".png")));
        botCard2.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  botHand.get(1) + ".png")));
        botWin();
    }

    public void continuePoker() {
        if(flop.isEmpty()) {
            draw(tempDeckValues, tempDeck, flopValue, flop);
            draw(tempDeckValues, tempDeck, flopValue, flop);
            draw(tempDeckValues, tempDeck, flopValue, flop);
            addFlopToHand(flopValue, flop, playerHandValue, playerHand);
            addFlopToHand(flopValue, flop, botHandValue, botHand);
            flopCard1.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  flop.getFirst() + ".png")));
            flopCard2.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  flop.get(1) + ".png")));
            flopCard3.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  flop.get(2) + ".png")));
            playerEvaluation = evaluateHand(playerHandValue, playerHand, playerWinningHand);
        } else if(flop.size() == 3) {
            draw(tempDeckValues, tempDeck, flopValue, flop);
            addFlopToHand(flopValue, flop, playerHandValue, playerHand);
            addFlopToHand(flopValue, flop, botHandValue, botHand);
            flopCard4.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  flop.get(3) + ".png")));
            playerEvaluation = evaluateHand(playerHandValue, playerHand, playerWinningHand);
        } else if(flop.size() == 4) {
            draw(tempDeckValues, tempDeck, flopValue, flop);
            addFlopToHand(flopValue, flop, playerHandValue, playerHand);
            addFlopToHand(flopValue, flop, botHandValue, botHand);
            flopCard5.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  flop.get(4) + ".png")));
            playerEvaluation = evaluateHand(playerHandValue, playerHand, playerWinningHand);

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
            botCard1.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  botHand.getFirst() + ".png")));
            botCard2.setImage(new Image(getClass().getResourceAsStream("/images/deck/" +  botHand.get(1) + ".png")));
            botEvaluation = evaluateHand(botHandValue, botHand, botWinningHand);
            evaluateWinner(playerEvaluation, botEvaluation);
        }
    }

    public void playAgain(ActionEvent actionEvent) {
        playAgainButton.setDisable(true);
        playAgainButton.setVisible(false);
        playButton.setDisable(false);
        playButton.setVisible(true);
        flopValue.clear();
        flop.clear();
        tempDeckValues.clear();
        tempDeck.clear();
        playerHandValue.clear();
        playerHand.clear();
        botHandValue.clear();
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

    public void draw(List<Integer> cardValues, List<String> cards, List<Integer> handValue, List<String> hand) {
        int rand = random.nextInt(0, cardValues.size());
        int cardValue = cardValues.get(rand);
        String card = cards.get(rand);
        handValue.add(cardValue);
        hand.add(card);
        cardValues.remove(rand);
        cards.remove(rand);
    }

    public int evaluateHand(List<Integer> handValue, List<String> hand, List<Integer> winningHand) {
        String evaluation = handEvaluation[9];
        List<Integer> tempHandValue = new ArrayList<>(handValue);
        List<String> tempHand = new ArrayList<>(hand);
        int evaluationHierarchy = 0;
        handValue.sort(null);
        hand.sort(null);
        if(hasRoyalFlush(tempHandValue, tempHand, winningHand)) {
            evaluation = handEvaluation[0];
            evaluationHierarchy = 9;
        } else if(hasStraightFlush(tempHandValue, tempHand, winningHand)) {
            evaluation = handEvaluation[1];
            evaluationHierarchy = 8;
        } else if(hasFourOfAKind(tempHandValue, winningHand)) {
            evaluation = handEvaluation[2];
            evaluationHierarchy = 7;
        } else if(hasFullHouse(tempHandValue, winningHand)) {
            evaluation = handEvaluation[3];
            evaluationHierarchy = 6;
        } else if(hasFlush(tempHandValue, tempHand, winningHand)) {
            evaluation = handEvaluation[4];
            evaluationHierarchy = 5;
        } else if(hasStraight(tempHandValue, winningHand)) {
            evaluation = handEvaluation[5];
            evaluationHierarchy = 4;
        } else if(hasThreeOfAKind(tempHandValue, winningHand)) {
            evaluation = handEvaluation[6];
            evaluationHierarchy = 3;
        } else if(hasTwoPair(tempHandValue, winningHand)) {
            evaluation = handEvaluation[7];
            evaluationHierarchy = 2;
        } else if(hasPair(tempHandValue, winningHand)) {
            evaluation = handEvaluation[8];
            evaluationHierarchy = 1;
        } else {
            winningHand.add(handValue.getLast());
        }

        if(hand == playerHand) {
            evaluationLabel.setText("You have a " + evaluation + "!");
            if(GlobalCasinoPerks.getTexasPokerHandEvaluationIncreaseLevel() == 1) {
                evaluationHierarchy++;
                evaluationLabel.setText("You have a " + evaluation + " (+1)!");
            }
        }

        return evaluationHierarchy;
    }

    public boolean hasRoyalFlush(List<Integer> handValue, List<String> hand, List<Integer> winningHand) {
        winningHand.clear();
        if(hasFlush(handValue, hand, winningHand) && hasStraight(handValue, winningHand) && handValue.contains(14) && handValue.contains(13) && handValue.contains(12)) {
            return true;
        }
        return false;
    }
    public boolean hasStraightFlush(List<Integer> handValue, List<String> hand, List<Integer> winningHand) {
        winningHand.clear();
        if(hasFlush(handValue, hand, winningHand) && hasStraight(handValue, winningHand)) {
            return true;
        }
        return false;
    }
    public boolean hasFourOfAKind(List<Integer> handValue, List<Integer> winningHand) {
        winningHand.clear();
        for(int i = 0; i < handValue.size() - 3; i++) {
            if(handValue.get(i).equals(handValue.get(i + 1)) && handValue.get(i + 1).equals(handValue.get(i + 2)) && handValue.get(i + 2).equals(handValue.get(i + 3))) {
                winningHand.add(handValue.get(i));
                return true;
            }
        }
        return false;
    }
    public boolean hasFullHouse(List<Integer> handValue, List<Integer> winningHand) {
        winningHand.clear();
        List<Integer> temp = new ArrayList<>();
        boolean three = false;
        for(int i = 0; i < handValue.size() - 2; i++) {
            if(handValue.get(i).equals(handValue.get(i + 1)) && handValue.get(i + 1).equals(handValue.get(i + 2))) {
                winningHand.add(handValue.get(i));
                temp.add(handValue.get(i));
                temp.add(handValue.get(i + 1));
                temp.add(handValue.get(i + 2));
                handValue.remove(i + 2);
                handValue.remove(i + 1);
                handValue.remove(i);
                three = true;
                break;
            }
        }
        if(three) {
            for(int i = 0; i < handValue.size() - 1; i++) {
                if(handValue.get(i).equals(handValue.get(i + 1))) {
                    handValue.addAll(temp);
                    temp.clear();
                    handValue.sort(null);
                    return true;
                }
            }
            handValue.addAll(temp);
            temp.clear();
        }
        return false;
    }
    public boolean hasFlush(List<Integer> handValue, List<String> hand, List<Integer> winningHand) {
        winningHand.clear();
        int h = 0;
        int d = 0;
        int c = 0;
        int s = 0;
        for(int i = 0; i < heart.length; i++) {
            if(hand.contains(heart[i])) {
                h++;
            } else if(hand.contains(diamond[i])) {
                d++;
            } else if(hand.contains(club[i])) {
                c++;
            } else if(hand.contains(spade[i])) {
                s++;
            }
        }
        if(h > 4 || d > 4 || c > 4 || s > 4) {
            return true;
        }

        return false;
    }
    public boolean hasStraight(List<Integer> handValue, List<Integer> winningHand) {
        winningHand.clear();
        handValue = handValue.reversed();
        for(int i = 0; i < handValue.size() - 4; i++) {
            if(handValue.get(i) == handValue.get(i + 1) + 1 && handValue.get(i + 1) == handValue.get(i + 2) + 1 && handValue.get(i + 2) == handValue.get(i + 3) + 1 && handValue.get(i + 3) == handValue.get(i + 4) + 1) {
                winningHand.add(handValue.get(i));
                return true;
            }
        }
        return false;
    }
    public boolean hasThreeOfAKind(List<Integer> handValue, List<Integer> winningHand) {
        winningHand.clear();
        for(int i = 0; i < handValue.size() - 2; i++) {
            if(handValue.get(i).equals(handValue.get(i + 1)) && handValue.get(i + 1).equals(handValue.get(i + 2))) {
                winningHand.add(handValue.get(i));
                return true;
            }
        }
        return false;
    }
    public boolean hasTwoPair(List<Integer> handValue, List<Integer> winningHand) {
        winningHand.clear();
        int counter = 0;
        for(int i = 0; i < handValue.size() - 1; i++) {
            if(handValue.get(i).equals(handValue.get(i + 1))) {
                winningHand.add(handValue.get(i));
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
    public boolean hasPair(List<Integer> handValue, List<Integer> winningHand) {
        winningHand.clear();
        for(int i = 0; i < handValue.size() - 1; i++) {
            if(handValue.get(i).equals(handValue.get(i + 1))) {
                winningHand.add(handValue.get(i));
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
            if(playerWinningHand.getLast() > botWinningHand.getLast()) {
                playerWin();
            } else if(botWinningHand.getLast() > playerWinningHand.getLast()) {
                botWin();
            } else if(botWinningHand.getLast().equals(playerWinningHand.getLast()) || hasFlush(playerHandValue, playerHand, playerWinningHand)) {
                evaluationLabel.setText("It's a tie. You get your money back");
                GlobalCasinoState.setMoneyBalance(originalBalance);
                moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
            }
        }
    }

    public void playerWin() {
        evaluationLabel.setText("You have won! You get $" + (int)(2 * bet + bet * GlobalCasinoPerks.getTexasPokerMoneyMultiplier() + bet * GlobalCasinoPerks.getGlobalMoneyMultiplier()) + "!");
        GlobalCasinoState.changeMoneyBalance((int)(2 * bet + bet * GlobalCasinoPerks.getTexasPokerMoneyMultiplier() + bet * GlobalCasinoPerks.getGlobalMoneyMultiplier()));
        GlobalCasinoState.changePhrogMoneyBalance(GlobalCasinoState.calculatePhrogCoins((int)(bet + bet * GlobalCasinoPerks.getTexasPokerPhrogMoneyMultiplier() + bet * GlobalCasinoPerks.getGlobalPhrogMoneyMultiplier())));
        moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
    }
    public void botWin() {
        evaluationLabel.setText("You have lost.");
    }

    public void addFlopToHand(List<Integer> flopValue, List<String> flop, List<Integer> handValue, List<String> hand) {
        if(flop.size() == 3) {
            for(int i = 0; i < flop.size(); i++) {
                handValue.add(flopValue.get(i));
                hand.add(flop.get(i));
            }
        } else {
            handValue.add(flopValue.getLast());
            hand.add(flop.getLast());
        }
    }

//    public int getStringArrayIndex(String[] stringArray, String string) {
//        for(int i = 0; i < stringArray.length; i++) {
//            if(stringArray[i].equals(string)) {
//                return i;
//            }
//        }
//        return 0;
//    }

    public void switchToCasinoButton(ActionEvent actionEvent) throws IOException {
        GlobalCasinoState.switchToCasinoButton(actionEvent);
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
