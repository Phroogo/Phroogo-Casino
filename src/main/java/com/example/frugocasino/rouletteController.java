package com.example.frugocasino;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class rouletteController {

    private boolean firstSpin = true, betPlaced, won;
    private final int[] red = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
    private final int[] black = {2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35};
    private final int[] rouletteNumbers = {0, 32, 15, 19, 4, 21, 2, 25, 17, 34, 6, 27, 13, 36, 11, 30, 8, 23, 10, 5, 24, 16, 33, 1, 20, 14, 31, 9, 22, 18, 29, 7, 28, 12, 35, 3, 26};
    public Group bettingTable, groupBettingTable, rouletteWheel;
    public Circle innerWheel, outerWheel, rouletteBall;
    public Label moneyLabel, roundLabel, actionLabel, quotaLabel, infoLabel, betLabel;
    public Button backButton, spinButton;
    public TextField betAmount;
    private int bet;
    private final List<Integer> playerBet = new ArrayList<>();
    private final List<RouletteBall> rouletteBalls = new ArrayList<>();
    private final List<Integer> winningNumbers = new ArrayList<>();
    private final Random random = new Random();

    GlobalCasinoState state = new GlobalCasinoState();
    GlobalCasinoPerks perks = new GlobalCasinoPerks();

    private final AnimationTimer timer = new AnimationTimer() {
        double wheelAngle = 0;
        double wheelSpeed = random.nextDouble(7, 9);
        @Override
        public void handle(long now) {
            if (wheelSpeed >= 6) {
                wheelSpeed -= random.nextDouble(0.043, 0.045);
            } else if (wheelSpeed >= 2) {
                wheelSpeed -= random.nextDouble(0.028, 0.03);
            } else {
                wheelSpeed -= random.nextDouble(0.018, 0.02);
            }
            wheelAngle += wheelSpeed;
            innerWheel.setRotate(wheelAngle);

            for (RouletteBall ball : rouletteBalls) {
                ball.spinTheBallFrame(wheelAngle);
            }

            if(wheelSpeed <= 0) {
                timer.stop();
                wheelAngle %= 360;
                wheelSpeed = random.nextDouble(7, 9);
                for (RouletteBall ball : rouletteBalls) {
                    winningNumbers.add(getRouletteNumber(ball.getBallAngle(), wheelAngle));
                }
                backButton.setDisable(false);
                spinButton.setDisable(false);
                for (Integer winningNumber : winningNumbers ) {
                    if (playerBet.contains(winningNumber)) {
                        won = true;
                        break;
                    }
                }
                if (won) {
                    int winAmount = 0;
                    if (playerBet.size() == 1) {
                        winAmount = (int)(34 * bet * perks.getRouletteTableMoneyMultiplier() * perks.getGlobalMoneyMultiplier()) + bet;
                    } else if (playerBet.size() == 12) {
                        winAmount = (int)(2 * bet * perks.getRouletteTableMoneyMultiplier() * perks.getGlobalMoneyMultiplier()) + bet;
                    } else if (playerBet.size() == 18) {
                        winAmount = (int)(bet * perks.getRouletteTableMoneyMultiplier() * perks.getGlobalMoneyMultiplier()) + bet;
                    }
                    infoLabel.setText("You won! You get $" + winAmount + "!");
                    state.changeMoneyBalance(winAmount);
                    state.changeRoundMoneyMade(winAmount - bet);
                    state.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
                } else {
                    infoLabel.setText("You lost.");
                    if (bet > state.getMoneyBalance()) {
                        bet = state.getMoneyBalance();
                        betAmount.setText("$" + bet);
                    }
                }
            }
        }
    };

    public void initialize() {
        state.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
        boolean imageView = true;
        for (Node group : bettingTable.getChildren()) {
            if(imageView) {
                imageView = false;
                continue;
            }
            group.setOnMouseClicked(this::chooseBet);
            group.setOnMouseEntered(e -> {
                group.setScaleX(1.1);
                group.setScaleY(1.1);
            });
            group.setOnMouseExited(e -> {
                group.setScaleX(1.0);
                group.setScaleY(1.0);
            });
        }
        imageView = true;
        for (Node label : groupBettingTable.getChildren()) {
            if(imageView) {
                imageView = false;
                continue;
            }
            label.setOnMouseClicked(this::chooseBet);
            label.setOnMouseEntered(e -> {
                label.setScaleX(1.1);
                label.setScaleY(1.1);
            });
            label.setOnMouseExited(e -> {
                label.setScaleX(1.0);
                label.setScaleY(1.0);
            });
        }
        rouletteBall.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/images/roulette/rouletteBallSilver.png"))));
        innerWheel.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/images/roulette/innerWheel.png"))));
        outerWheel.setFill(new ImagePattern(new Image(getClass().getResourceAsStream("/images/roulette/outerWheel.png"))));
        for(int i = 0; i < perks.getRouletteTableRerollLevel() + 1; i++) {
            String rouletteBallFill = "rouletteBall";
            int color = random.nextInt(0,3);
            if (color == 1) rouletteBallFill += "Blue";
            else if ( color == 2) rouletteBallFill += "Silver";
            rouletteBalls.add(new RouletteBall(rouletteBall.getRadius(), new ImagePattern(new Image(getClass().getResourceAsStream("/images/roulette/" + rouletteBallFill + ".png")))));
            rouletteWheel.getChildren().add(rouletteBalls.get(i));
            if(i == 0) {
                rouletteBalls.getFirst().setLayoutX(rouletteBall.getLayoutX());
                rouletteBalls.getFirst().setLayoutY(rouletteBall.getLayoutY());
                rouletteBalls.getFirst().setBallAngle(-90);
            } else {
                double angle = random.nextDouble(0, 360);
                double x = 374 * Math.cos(Math.toRadians(angle));
                double y = 374 * Math.sin(Math.toRadians(angle));
                rouletteBalls.get(i).setLayoutX(x);
                rouletteBalls.get(i).setLayoutY(y);
                rouletteBalls.get(i).setBallAngle(Math.toDegrees(Math.atan2(y, x)));
            }
        }
    }

    public void spinTheWheel(ActionEvent actionEvent) {
        setBet(actionEvent);
        if (bet > 0 && state.getActionsLeft() > 0 && betPlaced) {
            state.changeMoneyBalance(-bet);
            state.actionDecrement();
            state.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
            won = false;
            winningNumbers.clear();
            for (RouletteBall ball : rouletteBalls) {
                ball.setUpBallStart(firstSpin);
            }
            firstSpin = false;
            backButton.setDisable(true);
            spinButton.setDisable(true);
            timer.start();
        } else if (state.getActionsLeft() <= 0) {
            infoLabel.setText("No Actions Left");
            betAmount.clear();
        } else if (bet <= 0) {
            betAmount.clear();
            betAmount.setPromptText("Can't bet $0");
        } else {
            infoLabel.setText("You need to place a bet first");
        }
    }

    public void setBet(ActionEvent actionEvent) {
        try {
            if(betAmount.getText().startsWith("$")) bet = Integer.parseInt(betAmount.getText().substring(1));
            else bet = Integer.parseInt(betAmount.getText());
        } catch (NumberFormatException e) {
            betAmount.clear();
            bet = 0;
        }
        String buttonID = ((Button) actionEvent.getSource()).getId();
        if (buttonID.startsWith("set")) bet = (int)(state.getMoneyBalance() * (Integer.parseInt(buttonID.substring(6)) / 100.0));
        else if (buttonID.startsWith("mult")) bet *= Integer.parseInt(buttonID.substring(7));
        else if (buttonID.startsWith("div")) bet /= Integer.parseInt(buttonID.substring(6));

        if (bet > state.getMoneyBalance()) bet = state.getMoneyBalance();
        if (bet > 0) betAmount.setText("$" + bet);
    }

    private void chooseBet(MouseEvent mouseEvent) {
        playerBet.clear();
        if (((Node) mouseEvent.getSource()).getId().startsWith("bet")) {
            playerBet.add(Integer.parseInt(((Group) mouseEvent.getSource()).getId().substring(3)));
            infoLabel.setText("You bet on " + playerBet.getFirst());
            betLabel.setText("Your bet: " + playerBet.getFirst());
        } else {
            if(((Label) mouseEvent.getSource()).getId().equals("groupBet1stCol")){
                for(int i = 1; i < 37; i++){
                    if (i % 3 == 1) {
                        playerBet.add(i);
                    }
                }
                infoLabel.setText("You bet on the first column");
                betLabel.setText("Your bet: 1st Column");
            } else if(((Label) mouseEvent.getSource()).getId().equals("groupBet2ndCol")){
                for(int i = 1; i < 37; i++){
                    if (i % 3 == 2) {
                        playerBet.add(i);
                    }
                }
                infoLabel.setText("You bet on the second column");
                betLabel.setText("Your bet: 2nd Column");
            } else if(((Label) mouseEvent.getSource()).getId().equals("groupBet3rdCol")){
                for(int i = 1; i < 37; i++){
                    if (i % 3 == 0) {
                        playerBet.add(i);
                    }
                }
                infoLabel.setText("You bet on the third column");
                betLabel.setText("Your bet: 3rd Column");
            } else if(((Label) mouseEvent.getSource()).getId().equals("groupBet1st12")){
                for(int i = 1; i < 13; i++){
                    playerBet.add(i);
                }
                infoLabel.setText("You bet on the first twelve");
                betLabel.setText("Your bet: 1st 12");
            } else if(((Label) mouseEvent.getSource()).getId().equals("groupBet2nd12")){
                for(int i = 13; i < 25; i++){
                    playerBet.add(i);
                }
                infoLabel.setText("You bet on the second twelve");
                betLabel.setText("Your bet: 2nd 12");
            } else if(((Label) mouseEvent.getSource()).getId().equals("groupBet3rd12")){
                for(int i = 25; i < 37; i++){
                    playerBet.add(i);
                }
                infoLabel.setText("You bet on the third twelve");
                betLabel.setText("Your bet: 3rd 12");
            } else if(((Label) mouseEvent.getSource()).getId().equals("groupBet1to18")){
                for(int i = 1; i < 19; i++){
                    playerBet.add(i);
                }
                infoLabel.setText("You bet on the first half");
                betLabel.setText("Your bet: First Half");
            } else if(((Label) mouseEvent.getSource()).getId().equals("groupBet19to36")){
                for(int i = 19; i < 37; i++){
                    playerBet.add(i);
                }
                infoLabel.setText("You bet on the second half");
                betLabel.setText("Your bet: Second Half");
            } else if(((Label) mouseEvent.getSource()).getId().equals("groupBetEven")){
                for(int i = 1; i < 37; i++){
                    if (i % 2 == 0) {
                        playerBet.add(i);
                    }
                }
                infoLabel.setText("You bet on even");
                betLabel.setText("Your bet: Even");
            } else if(((Label) mouseEvent.getSource()).getId().equals("groupBetOdd")){
                for(int i = 1; i < 37; i++){
                    if (i % 2 == 1) {
                        playerBet.add(i);
                    }
                }
                infoLabel.setText("You bet on odd");
                betLabel.setText("Your bet: Odd");
            } else if(((Label) mouseEvent.getSource()).getId().equals("groupBetRed")){
                for (int red : red) {
                    playerBet.add(red);
                }
                infoLabel.setText("You bet on red");
                betLabel.setText("Your bet: Red");
            } else if(((Label) mouseEvent.getSource()).getId().equals("groupBetBlack")){
                for (int black : black) {
                    playerBet.add(black);
                }
                infoLabel.setText("You bet on black");
                betLabel.setText("Your bet: Black");
            }
        }
        betPlaced = true;
    }

    public int getRouletteNumber(double ballAngle, double wheelAngle) {
        double step = 360.0 / 37;
        double relativeAngle = (((ballAngle % 360) + 360 - wheelAngle + 95.5) % 360 + 360) % 360;
        int slotIndex = (int)(relativeAngle / step);
        return rouletteNumbers[slotIndex];
    }

    public void switchToCasinoButton(ActionEvent actionEvent) throws IOException {
        state.switchToSceneButton(actionEvent);
    }
}