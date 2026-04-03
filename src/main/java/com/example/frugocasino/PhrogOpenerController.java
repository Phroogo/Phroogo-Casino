package com.example.frugocasino;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PhrogOpenerController {

    public Button backButton, openCaseButton1, openCaseButtonMax, openCaseButtonCustom;
    public TextField openCaseButtonTF;
    public Label moneyLabel, dropLabel, floatLabel, roundLabel, actionLabel, quotaLabel;
    public ImageView caseImage, slot1, slot2, slot3, slot4, slot5;
    public Line theLine;
    private List<String> spinList = new ArrayList<>();
    private int bet;
    private Random random = new Random();
    final private double SPACING = 460;
    final private double WRAP = 1860;
    final private double CUTOFF = 1460;
    private int counter = 0;
    private double lastSlot1X;
    private double lastSlot2X;
    private double lastSlot3X;
    private double lastSlot4X;
    private double lastSlot5X;
    private final GlobalCasinoState state = new GlobalCasinoState();
    AnimationTimer timer = new AnimationTimer() {
        private long lastTime = 0;
        private double elapsed = 0;

        @Override
        public void handle(long now) {
            if (lastTime == 0) {
                lastTime = now;
                return;
            }

            double delta = (now - lastTime) / 1_000_000_000.0;
            lastTime = now;

            elapsed += delta;

            updateSlot(elapsed);

            if (elapsed >= 5.0) {
                elapsed = 0;
                lastTime = 0;
                counter = 0;
                backButton.setDisable(false);
                openCaseButton1.setDisable(false);
                openCaseButtonMax.setDisable(false);
                openCaseButtonCustom.setDisable(false);
                openCaseButtonTF.setDisable(false);
                proceedWin();
                timer.stop();
            }
        }
    };

    public void initialize() {
        state.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
    }

    public void amount(ActionEvent actionEvent) {
        if(openCaseButtonTF.isVisible()) {
            try {
                bet = Integer.parseInt(openCaseButtonTF.getText()) * 50;
                if(bet > 0 && !(bet > state.getMoneyBalance())) {
                    openCase(actionEvent);
                } else {
                    openCaseButtonTF.setPromptText("You can't bet that!");
                    openCaseButtonTF.clear();
                }
            } catch (NumberFormatException e) {
                openCaseButtonTF.setPromptText("Invalid input");
                openCaseButtonTF.clear();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            openCaseButtonTF.setVisible(true);
        }
    }

    public void openCase(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(openCaseButton1)) {
            bet = 50;
        } else if (actionEvent.getSource().equals(openCaseButtonMax)) {
            bet = state.getMoneyBalance() - state.getMoneyBalance() % 50;
        }
        if(bet > 0 && !(bet > state.getMoneyBalance()) && state.getActionsLeft() > 0) {
            state.changeMoneyBalance(-bet);
            state.actionDecrement();
            state.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
            theLine.setVisible(true);
            caseImage.setVisible(false);
            openCaseButton1.setDisable(true);
            openCaseButtonMax.setDisable(true);
            openCaseButtonCustom.setDisable(true);
            openCaseButtonTF.setDisable(true);
            backButton.setDisable(true);
            floatLabel.setVisible(false);
            dropLabel.setVisible(false);
            spinList.clear();
            for (int i = 0; i < 47; i++) {
                int result = random.nextInt(1, 10001);
                if (result < 7993) {
                    spinList.add("blue");
                } else if (result < 9591) {
                    spinList.add("purple");
                } else if (result < 9911) {
                    spinList.add("pink");
                } else if (result < 9975) {
                    spinList.add("red");
                } else {
                    spinList.add("gold");
                }
            }
            slot1.setVisible(true);
            slot2.setVisible(true);
            slot3.setVisible(true);
            slot4.setVisible(true);
            slot5.setVisible(true);
            timer.start();
        } else if (bet > 0 && !(bet > state.getMoneyBalance())) {
            dropLabel.setText("No actions left.");
            dropLabel.setFont(new Font(dropLabel.getFont().getName(), 50));
            floatLabel.setVisible(false);
        } else {
            dropLabel.setText("No mone");
            dropLabel.setFont(new Font(dropLabel.getFont().getName(), 50));
            floatLabel.setVisible(false);
        }
    }

    private void updateSlot(double iteration) {
        lastSlot1X = slot1.getLayoutX();
        lastSlot2X = slot2.getLayoutX();
        lastSlot3X = slot3.getLayoutX();
        lastSlot4X = slot4.getLayoutX();
        lastSlot5X = slot5.getLayoutX();

        double speed = Math.max(1, 51 - iteration * 10) - 20;
        slot1.setLayoutX(slot1.getLayoutX() - speed);
        slot2.setLayoutX(slot2.getLayoutX() - speed);
        slot3.setLayoutX(slot3.getLayoutX() - speed);
        slot4.setLayoutX(slot4.getLayoutX() - speed);
        slot5.setLayoutX(slot5.getLayoutX() - speed);

        if(slot1.getLayoutX() < CUTOFF) slot2.setLayoutX(slot1.getLayoutX() + SPACING);
        else slot2.setLayoutX(slot1.getLayoutX() - WRAP);
        if(slot2.getLayoutX() < CUTOFF) slot3.setLayoutX(slot2.getLayoutX() + SPACING);
        else slot3.setLayoutX(slot2.getLayoutX() - WRAP);
        if(slot3.getLayoutX() < CUTOFF) slot4.setLayoutX(slot3.getLayoutX() + SPACING);
        else slot4.setLayoutX(slot3.getLayoutX() - WRAP);
        if(slot4.getLayoutX() < CUTOFF) slot5.setLayoutX(slot4.getLayoutX() + SPACING);
        else slot5.setLayoutX(slot4.getLayoutX() - WRAP);
        if(slot5.getLayoutX() < CUTOFF) slot1.setLayoutX(slot5.getLayoutX() + SPACING);
        else slot1.setLayoutX(slot5.getLayoutX() - WRAP);

        if (slot1.getLayoutX() - lastSlot1X > 1000) {
            colorToImage(spinList.get(counter), 1);
            counter++;
        } else if (slot2.getLayoutX() - lastSlot2X > 1000) {
            colorToImage(spinList.get(counter), 2);
            counter++;
        } else if (slot3.getLayoutX() - lastSlot3X > 1000) {
            colorToImage(spinList.get(counter), 3);
            counter++;
        } else if (slot4.getLayoutX() - lastSlot4X > 1000) {
            colorToImage(spinList.get(counter), 4);
            counter++;
        } else if (slot5.getLayoutX() - lastSlot5X > 1000) {
            colorToImage(spinList.get(counter), 5);
            counter++;
        }
    }

    private void colorToImage(String color, int slot) {
        switch(slot) {
            case 1 -> {
                slot1.setImage(new Image(getClass().getResourceAsStream("/images/caseOpener/" +  color + ".png")));
                slot1.setId(color);
            }
            case 2 -> {
                slot2.setImage(new Image(getClass().getResourceAsStream("/images/caseOpener/" +  color + ".png")));
                slot2.setId(color);
            }
            case 3 -> {
                slot3.setImage(new Image(getClass().getResourceAsStream("/images/caseOpener/" +  color + ".png")));
                slot3.setId(color);
            }
            case 4 -> {
                slot4.setImage(new Image(getClass().getResourceAsStream("/images/caseOpener/" +  color + ".png")));
                slot4.setId(color);
            }
            case 5 -> {
                slot5.setImage(new Image(getClass().getResourceAsStream("/images/caseOpener/" +  color + ".png")));
                slot5.setId(color);
            }
        }
    }

    private void proceedWin() {
        float flot = random.nextFloat(0, 1);
        String flotS = "Battle-Scarred";
        int stattrak = random.nextInt(0, 10);
        int winAmount = 0;
        String stattrakS = "";
        double mult = 0.4;
        String color = "blue";
        if (flot < .07) {
            mult = 1.2;
            flotS = "Factory New";
        } else if (flot < .15) {
            mult = 0.9;
            flotS = "Minimal Wear";
        } else if (flot < .37) {
            mult = 0.5;
            flotS = "Field-Tested";
        } else if (flot < .44) {
            mult = 0.7;
            flotS = "Well-Worn";
        }
        if (stattrak == 0) {
            mult *= 1.5;
            stattrakS = "StatTrak™ ";
        }
        if (slot1.getLayoutX() >= 530 && slot1.getLayoutX() <= 990) color = slot1.getId();
        else if (slot2.getLayoutX() >= 530 && slot2.getLayoutX() <= 990) color = slot2.getId();
        else if (slot3.getLayoutX() >= 530 && slot3.getLayoutX() <= 990) color = slot3.getId();
        else if (slot4.getLayoutX() >= 530 && slot4.getLayoutX() <= 990) color = slot4.getId();
        else if (slot5.getLayoutX() >= 530 && slot5.getLayoutX() <= 990) color = slot5.getId();
        switch (color) {
            case "blue" -> winAmount = (int)(60 * mult * ((double) bet / 50));
            case "purple" -> winAmount = (int)(200 * mult * ((double) bet / 50));
            case "pink" -> winAmount = (int)(1000 * mult * ((double) bet / 50));
            case "red" -> winAmount = (int)(5000 * mult * ((double) bet / 50));
            case "gold" -> winAmount = (int)(20000 * mult * ((double) bet / 50));
        }
        state.changeMoneyBalance(winAmount);
        dropLabel.setVisible(true);
        floatLabel.setVisible(true);
        if (bet/50 != 1) dropLabel.setText(stattrakS + color + " x" + bet/50 + " ($" + winAmount + ")");
        else dropLabel.setText(stattrakS + color + " (+$" + winAmount + ")");
        floatLabel.setText("Float: " + flot + " (" + flotS + ")");
        state.displayInfo(moneyLabel, roundLabel, actionLabel, quotaLabel);
    }

    public void switchToCasinoButton(ActionEvent actionEvent) throws IOException {
        state.switchToSceneButton(actionEvent);
    }
}