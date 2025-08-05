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

public class SlotMachineController {
    private final char[] slotOptions = {'-', '+', '=', '^', '!', '%', '&', '@', '#', '$'};
    public ImageView slot1, slot2, slot3, preSlot1, preSlot2, preSlot3, postSlot1, postSlot2, postSlot3;
    public Button backButton, playButton, spinButton;
    public Label moneyLabel, phrogMoneyLabel, slotResultLabel;
    public TextField betAmount;
    Random random = new Random();
    char slotChar1, slotChar2, slotChar3, preSlotChar1, preSlotChar2, preSlotChar3, postSlotChar1, postSlotChar2, postSlotChar3;
    int bet, winAmount, phrogWinAmount;
    double multiplier;

    public void initialize() {
        moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
        phrogMoneyLabel.setText("P$" + GlobalCasinoState.getPhrogMoneyBalance());
    }

    public void playSlots(ActionEvent actionEvent) {
        betAmount.setDisable(false);
        betAmount.setVisible(true);
        spinButton.setDisable(false);
        spinButton.setVisible(true);
        playButton.setDisable(true);
        playButton.setVisible(false);
    }

    public void slotSpin(ActionEvent actionEvent) {
        try {
            bet = Integer.parseInt(betAmount.getText());
            if(bet > 0 && !(bet > GlobalCasinoState.getMoneyBalance())) {
                spinButton.setDisable(true);
                spinButton.setVisible(false);
                betAmount.setDisable(true);
                betAmount.setVisible(false);
                backButton.setDisable(true);
                slotResultLabel.setVisible(false);
                GlobalCasinoState.changeMoneyBalance(-bet);
                moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
                slotChar1 = slotRandomizer();
                slotChar2 = slotRandomizer();
                slotChar3 = slotRandomizer();
                preSlotChar1 = slotOptions[getPreviousArrayIndex(slotOptions, getCharArrayIndex(slotOptions, slotChar1) - 1)];
                preSlotChar2 = slotOptions[getPreviousArrayIndex(slotOptions, getCharArrayIndex(slotOptions, slotChar2) - 1)];
                preSlotChar3 = slotOptions[getPreviousArrayIndex(slotOptions, getCharArrayIndex(slotOptions, slotChar3) - 1)];
                postSlotChar1 = slotOptions[getNextArrayIndex(slotOptions, getCharArrayIndex(slotOptions, slotChar1) + 1)];
                postSlotChar2 = slotOptions[getNextArrayIndex(slotOptions, getCharArrayIndex(slotOptions, slotChar2) + 1)];
                postSlotChar3 = slotOptions[getNextArrayIndex(slotOptions, getCharArrayIndex(slotOptions, slotChar3) + 1)];
                List<Runnable> slot1Spin = new ArrayList<>();
                List<Runnable> slot2Spin = new ArrayList<>();
                List<Runnable> slot3Spin = new ArrayList<>();
                slot2Spin.add(() -> System.out.print(""));
                slot3Spin.add(() -> System.out.print(""));
                for (int i = 0; i < 30; i++) {
                    slot1Spin.add(() -> {
                        nextSlot(slotChar1, 1);
                        nextPreSlot(preSlotChar1, 1);
                        nextPostSlot(postSlotChar1, 1);
                    });
                    slot2Spin.add(() -> {
                        nextSlot(slotChar2, 2);
                        nextPreSlot(preSlotChar2, 2);
                        nextPostSlot(postSlotChar2, 2);
                    });
                    slot3Spin.add(() -> {
                        nextSlot(slotChar3, 3);
                        nextPreSlot(preSlotChar3, 3);
                        nextPostSlot(postSlotChar3, 3);
                    });
                    if (i == 29) {
                        slot3Spin.add(() -> {
                            spinButton.setDisable(false);
                            spinButton.setVisible(true);
                            betAmount.setDisable(false);
                            betAmount.setVisible(true);
                            backButton.setDisable(false);
                            slotResult();
                        });
                    }
                }
                List<Double> slot1Delays = new ArrayList<>();
                List<Double> slot2Delays = new ArrayList<>();
                List<Double> slot3Delays = new ArrayList<>();
                slot2Delays.add(0.5);
                slot3Delays.add(1.0);
                for (int i = 0; i < 30; i++) {
                    if (i < 20) {
                        slot1Delays.add((double) i / 100);
                        slot2Delays.add((double) i / 100);
                        slot3Delays.add((double) i / 100);
                    } else if (i < 29) {
                        slot1Delays.add((double) i / 75);
                        slot2Delays.add((double) i / 75);
                        slot3Delays.add((double) i / 75);
                    } else {
                        slot1Delays.add((double) i / 50);
                        slot2Delays.add((double) i / 50);
                        slot3Delays.add((double) i / 50);
                    }
                }
                slot3Delays.add(0.5);
                slot3Delays.add(0.1);
                playDelayedSteps(slot1Spin, slot1Delays);
                playDelayedSteps(slot2Spin, slot2Delays);
                playDelayedSteps(slot3Spin, slot3Delays);
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

    public char slotRandomizer() {
        char x;
        int i = random.nextInt(1, 120 + GlobalCasinoPerks.getGreenPhrogSlotChanceLevel() * 3 + GlobalCasinoPerks.getPurplePhrogSlotChanceLevel() * 3 + GlobalCasinoPerks.getPhroogoSlotChanceLevel() * 3);
        if(i <= 20) {
            x = slotOptions[0];
        } else if(i <= 38) {
            x = slotOptions[1];
        } else if(i <= 54) {
            x = slotOptions[2];
        } else if(i <= 68) {
            x = slotOptions[3];
        } else if(i <= 80) {
            x = slotOptions[4];
        } else if(i <= 90) {
            x = slotOptions[5];
        } else if(i <= 99) {
            x = slotOptions[6];
        } else if(i <= 107 + GlobalCasinoPerks.getGreenPhrogSlotChanceLevel() * 3) {
            x = slotOptions[7];
        } else if (i <= 114 + GlobalCasinoPerks.getGreenPhrogSlotChanceLevel() * 3 + GlobalCasinoPerks.getPurplePhrogSlotChanceLevel() * 3) {
            x = slotOptions[8];
        } else if(i <= 119 + GlobalCasinoPerks.getGreenPhrogSlotChanceLevel() * 3 + GlobalCasinoPerks.getPurplePhrogSlotChanceLevel() * 3 + GlobalCasinoPerks.getPhroogoSlotChanceLevel() * 3) {
            x = slotOptions[9];
        } else {
            x = '-';
        }
        return x;
    }

    public int getCharArrayIndex(char[] array, char char1) {
        int index = 0;
        for(int i = 0; i < array.length; i++) {
            if(array[i] == char1) {
                index = i;
                break;
            }
        }
        return index;
    }
    public int getNextArrayIndex(char[] array, int index) {
        if(index > array.length - 1) {
            index = 0;
        }
        return index;
    }
    public int getPreviousArrayIndex(char[] array, int index) {
        if(index < 0) {
            index = array.length - 1;
        }
        return index;
    }

    public void nextSlot(char charSlot, int slotNumber) {
        char nextSlot = getNextSlot(charSlot);
        switch (slotNumber) {
            case 1 -> {
                slot1.setImage(new Image(getClass().getResourceAsStream("/images/slots/" + charToImage(nextSlot) + ".png")));
                slotChar1 = nextSlot;
            }
            case 2 -> {
                slot2.setImage(new Image(getClass().getResourceAsStream("/images/slots/" + charToImage(nextSlot) + ".png")));
                slotChar2 = nextSlot;
            }
            case 3 -> {
                slot3.setImage(new Image(getClass().getResourceAsStream("/images/slots/" + charToImage(nextSlot) + ".png")));
                slotChar3 = nextSlot;
            }
        }
    }

    public void nextPreSlot(char charSlot, int slotNumber) {
        char nextSlot = getPreviousSlot(charSlot);
        switch (slotNumber) {
            case 1 -> {
                preSlot1.setImage(new Image(getClass().getResourceAsStream("/images/slots/" + charToImage(nextSlot) + ".png")));
                preSlotChar1 = nextSlot;
            }
            case 2 -> {
                preSlot2.setImage(new Image(getClass().getResourceAsStream("/images/slots/" + charToImage(nextSlot) + ".png")));
                preSlotChar2 = nextSlot;
            }
            case 3 -> {
                preSlot3.setImage(new Image(getClass().getResourceAsStream("/images/slots/" + charToImage(nextSlot) + ".png")));
                preSlotChar3 = nextSlot;
            }
        }
    }

    public void nextPostSlot(char charSlot, int slotNumber) {
        char nextSlot = getNextSlot(charSlot);
        switch (slotNumber) {
            case 1 -> {
                postSlot1.setImage(new Image(getClass().getResourceAsStream("/images/slots/" + charToImage(nextSlot) + ".png")));
                postSlotChar1 = nextSlot;
            }
            case 2 -> {
                postSlot2.setImage(new Image(getClass().getResourceAsStream("/images/slots/" + charToImage(nextSlot) + ".png")));
                postSlotChar2 = nextSlot;
            }
            case 3 -> {
                postSlot3.setImage(new Image(getClass().getResourceAsStream("/images/slots/" + charToImage(nextSlot) + ".png")));
                postSlotChar3 = nextSlot;
            }
        }
    }

    private char getNextSlot(char charSlot) {
        char nextSlot;
        switch (charSlot) {
            case '-' -> nextSlot = slotOptions[1];
            case '+' -> nextSlot = slotOptions[2];
            case '=' -> nextSlot = slotOptions[3];
            case '^' -> nextSlot = slotOptions[4];
            case '!' -> nextSlot = slotOptions[5];
            case '%' -> nextSlot = slotOptions[6];
            case '&' -> nextSlot = slotOptions[7];
            case '@' -> nextSlot = slotOptions[8];
            case '#' -> nextSlot = slotOptions[9];
            case '$' -> nextSlot = slotOptions[0];
            default -> nextSlot = '-';
        }
        return nextSlot;
    }
    private char getPreviousSlot(char charSlot) {
        char nextSlot;
        switch (charSlot) {
            case '-' -> nextSlot = slotOptions[9];
            case '+' -> nextSlot = slotOptions[0];
            case '=' -> nextSlot = slotOptions[1];
            case '^' -> nextSlot = slotOptions[2];
            case '!' -> nextSlot = slotOptions[3];
            case '%' -> nextSlot = slotOptions[4];
            case '&' -> nextSlot = slotOptions[5];
            case '@' -> nextSlot = slotOptions[6];
            case '#' -> nextSlot = slotOptions[7];
            case '$' -> nextSlot = slotOptions[8];
            default -> nextSlot = '-';
        }
        return nextSlot;
    }

    public String charToImage(char slot) {
        String image;
        image = "slot" + slot;
        return image;
    }

    public void slotResult() {
        if (slotChar1 == slotChar2 && slotChar2 == slotChar3) {
            jackpot();
        } else if (slotChar1 == slotChar2 || slotChar1 == slotChar3 || slotChar2 == slotChar3) {
            pair();
        } else {
            slotResultLabel.setText("You got nothing. Better luck next time!");
        }
        slotResultLabel.setVisible(true);
        moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
        phrogMoneyLabel.setText("P$" + GlobalCasinoState.getPhrogMoneyBalance());
    }

    public void jackpot() {
        switch (slotChar1){
            case '-':
                multiplier = 3;
                winAmount = (int)(bet * (multiplier + 1) + multiplier * bet * (GlobalCasinoPerks.getSlotMachineMoneyMultiplier() + GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                phrogWinAmount = (int)(GlobalCasinoState.calculatePhrogCoins((int)(bet * multiplier )) * (1 + GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplier()) * (1 + GlobalCasinoPerks.getGlobalPhrogMoneyMultiplier()));
                slotResultLabel.setText("Small JackPot, you get $" + winAmount + " and P$" + phrogWinAmount + "!");
                break;
            case '+':
                multiplier = 5;
                winAmount = (int)(bet * (multiplier + 1) + multiplier * bet * (GlobalCasinoPerks.getSlotMachineMoneyMultiplier() + GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                phrogWinAmount = (int)(GlobalCasinoState.calculatePhrogCoins((int)(bet * multiplier )) * (1 + GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplier()) * (1 + GlobalCasinoPerks.getGlobalPhrogMoneyMultiplier()));
                slotResultLabel.setText("Small JackPot, you get $" + winAmount + " and P$" + phrogWinAmount + "!");
                break;
            case '=':
                multiplier = 7;
                winAmount = (int)(bet * (multiplier + 1) + multiplier * bet * (GlobalCasinoPerks.getSlotMachineMoneyMultiplier() + GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                phrogWinAmount = (int)(GlobalCasinoState.calculatePhrogCoins((int)(bet * multiplier )) * (1 + GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplier()) * (1 + GlobalCasinoPerks.getGlobalPhrogMoneyMultiplier()));
                slotResultLabel.setText("Medium JackPot, you get $" + winAmount + " and P$" + phrogWinAmount + "!");
                break;
            case '^':
                multiplier = 11;
                winAmount = (int)(bet * (multiplier + 1) + multiplier * bet * (GlobalCasinoPerks.getSlotMachineMoneyMultiplier() + GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                phrogWinAmount = (int)(GlobalCasinoState.calculatePhrogCoins((int)(bet * multiplier )) * (1 + GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplier()) * (1 + GlobalCasinoPerks.getGlobalPhrogMoneyMultiplier()));
                slotResultLabel.setText("Medium JackPot, you get $" + winAmount + " and P$" + phrogWinAmount + "!");
                break;
            case '!':
                multiplier = 19;
                winAmount = (int)(bet * (multiplier + 1) + multiplier * bet * (GlobalCasinoPerks.getSlotMachineMoneyMultiplier() + GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                phrogWinAmount = (int)(GlobalCasinoState.calculatePhrogCoins((int)(bet * multiplier )) * (1 + GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplier()) * (1 + GlobalCasinoPerks.getGlobalPhrogMoneyMultiplier()));
                slotResultLabel.setText("Big JackPot, you get $" + winAmount + " and P$" + phrogWinAmount + "!");
                break;
            case '%':
                multiplier = 31;
                winAmount = (int)(bet * (multiplier + 1) + multiplier * bet * (GlobalCasinoPerks.getSlotMachineMoneyMultiplier() + GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                phrogWinAmount = (int)(GlobalCasinoState.calculatePhrogCoins((int)(bet * multiplier )) * (1 + GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplier()) * (1 + GlobalCasinoPerks.getGlobalPhrogMoneyMultiplier()));
                slotResultLabel.setText("Big JackPot, you get $" + winAmount + " and P$" + phrogWinAmount + "!");
                break;
            case '&':
                multiplier = 49;
                winAmount = (int)(bet * (multiplier + 1) + multiplier * bet * (GlobalCasinoPerks.getSlotMachineMoneyMultiplier() + GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                phrogWinAmount = (int)(GlobalCasinoState.calculatePhrogCoins((int)(bet * multiplier )) * (1 + GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplier()) * (1 + GlobalCasinoPerks.getGlobalPhrogMoneyMultiplier()));
                slotResultLabel.setText("WHITE PHROOGO JACKPOT. You get $" + winAmount + " and P$" + phrogWinAmount + "!");
                break;
            case '@':
                multiplier = 79;
                winAmount = (int)(bet * (multiplier + 1) + multiplier * bet * (GlobalCasinoPerks.getSlotMachineMoneyMultiplier() + GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                phrogWinAmount = (int)(GlobalCasinoState.calculatePhrogCoins((int)(bet * multiplier )) * (1 + GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplier()) * (1 + GlobalCasinoPerks.getGlobalPhrogMoneyMultiplier()));
                slotResultLabel.setText("GREEN PHROOGO JACKPOT!! You get " + winAmount + " and P$" + phrogWinAmount + "!");
                break;
            case '#':
                multiplier = 119;
                winAmount = (int)(bet * (multiplier + 1) + multiplier * bet * (GlobalCasinoPerks.getSlotMachineMoneyMultiplier() + GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                phrogWinAmount = (int)(GlobalCasinoState.calculatePhrogCoins((int)(bet * multiplier )) * (1 + GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplier()) * (1 + GlobalCasinoPerks.getGlobalPhrogMoneyMultiplier()));
                slotResultLabel.setText("PURPLE PHROOGO JACKPOT!!! You get $" + winAmount + " and P$" + phrogWinAmount + "!");
                break;
            case '$':
                multiplier = 199;
                winAmount = (int)(bet * (multiplier + 1) + multiplier * bet * (GlobalCasinoPerks.getSlotMachineMoneyMultiplier() + GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                phrogWinAmount = (int)(GlobalCasinoState.calculatePhrogCoins((int)(bet * multiplier )) * (1 + GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplier()) * (1 + GlobalCasinoPerks.getGlobalPhrogMoneyMultiplier()));
                slotResultLabel.setText("PHROOGO JACKPOT!!!! You get $" + winAmount + " and P$" + phrogWinAmount + "!");
                break;
        }
        GlobalCasinoState.changeMoneyBalance(winAmount);
        GlobalCasinoState.changePhrogMoneyBalance(phrogWinAmount);
    }

    public void pair() {
        char temp;
        if(slotChar1 == slotChar2 || slotChar1 == slotChar3) {
            temp = slotChar1;
        } else {
            temp = slotChar2;
        }

        switch (temp) {
            case '-', '+', '=':
                multiplier = 0.4;
                winAmount = (int)(bet * (multiplier) + multiplier * bet * (GlobalCasinoPerks.getSlotMachineMoneyMultiplier() + GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                phrogWinAmount = (int)(GlobalCasinoState.calculatePhrogCoins((int)(bet * multiplier )) * (1 + GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplier()) * (1 + GlobalCasinoPerks.getGlobalPhrogMoneyMultiplier()));
                slotResultLabel.setText("Small pair, you get $" + winAmount + " and P$" + phrogWinAmount + "!");
                break;
            case '^', '!', '%':
                multiplier = 0.8;
                winAmount = (int)(bet * (multiplier) + multiplier * bet * (GlobalCasinoPerks.getSlotMachineMoneyMultiplier() + GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                phrogWinAmount = (int)(GlobalCasinoState.calculatePhrogCoins((int)(bet * multiplier )) * (1 + GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplier()) * (1 + GlobalCasinoPerks.getGlobalPhrogMoneyMultiplier()));
                slotResultLabel.setText("Medium pair, you get $" + winAmount + " and P$" + phrogWinAmount + "!");
                break;
            case '&', '@', '#':
                multiplier = 1;
                winAmount = (int)(bet * (multiplier + 1) + multiplier * bet * (GlobalCasinoPerks.getSlotMachineMoneyMultiplier() + GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                phrogWinAmount = (int)(GlobalCasinoState.calculatePhrogCoins((int)(bet * multiplier )) * (1 + GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplier()) * (1 + GlobalCasinoPerks.getGlobalPhrogMoneyMultiplier()));
                slotResultLabel.setText("Phroogo pair, you get $" + winAmount + " and P$" + phrogWinAmount + "!");
                break;
            case '$':
                multiplier = 9;
                winAmount = (int)(bet * (multiplier + 1) + multiplier * bet * (GlobalCasinoPerks.getSlotMachineMoneyMultiplier() + GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                phrogWinAmount = (int)(GlobalCasinoState.calculatePhrogCoins((int)(bet * multiplier )) * (1 + GlobalCasinoPerks.getSlotMachinePhrogMoneyMultiplier()) * (1 + GlobalCasinoPerks.getGlobalPhrogMoneyMultiplier()));
                slotResultLabel.setText("Best Phroogo pair! you get $" + winAmount + " and P$" + phrogWinAmount + "!");
                break;
        }
        GlobalCasinoState.changeMoneyBalance(winAmount);
        GlobalCasinoState.changePhrogMoneyBalance(phrogWinAmount);
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

    public void switchToCasinoButton(ActionEvent actionEvent) throws IOException {
        GlobalCasinoState.switchToCasinoButton(actionEvent);
    }
}
