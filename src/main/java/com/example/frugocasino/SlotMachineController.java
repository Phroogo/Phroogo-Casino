package com.example.frugocasino;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    public Button spinButton;
    public Label moneyLabel, phrogMoneyLabel, slotResultLabel;
    public Button backButton;
    Random random = new Random();
    char slotChar1, slotChar2, slotChar3, preSlotChar1, preSlotChar2, preSlotChar3, postSlotChar1, postSlotChar2, postSlotChar3;

    public void initialize() {
        moneyLabel.setText("$" + GlobalCasinoState.getMoneyBalance());
        phrogMoneyLabel.setText("P$" + GlobalCasinoState.getPhrogMoneyBalance());
    }


    public void slotSpin(ActionEvent actionEvent) {
        if(GlobalCasinoState.getMoneyBalance() >= 50) {
            spinButton.setDisable(true);
            backButton.setDisable(true);
            slotResultLabel.setVisible(false);
            GlobalCasinoState.changeMoneyBalance(-50);
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
            spinButton.setText("No mone");
        }
    }

    public char slotRandomizer() {
        char x;
        int i = random.nextInt(1, 120);
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
        } else if(i <= 107) {
            x = slotOptions[7];
        } else if (i <= 114) {
            x = slotOptions[8];
        } else if(i <= 119) {
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
    }
    public void jackpot() {
    switch (slotChar1){
        case '-':
            slotResultLabel.setText("Small JackPot, you get $200!");
            GlobalCasinoState.changeMoneyBalance((int)(200 * GlobalCasinoPerks.getSlotMachineMoneyMultiplier() * GlobalCasinoPerks.getGlobalMoneyMultiplier()));
            break;
        case '+':
            slotResultLabel.setText("Small JackPot, you get $250!");
            GlobalCasinoState.changeMoneyBalance((int)(250 * GlobalCasinoPerks.getSlotMachineMoneyMultiplier() * GlobalCasinoPerks.getGlobalMoneyMultiplier()));
            break;
        case '=':
            slotResultLabel.setText("Medium JackPot, you get $400!");
            GlobalCasinoState.changeMoneyBalance((int)(400 * GlobalCasinoPerks.getSlotMachineMoneyMultiplier() * GlobalCasinoPerks.getGlobalMoneyMultiplier()));
            break;
        case '^':
            slotResultLabel.setText("Medium JackPot, you get $600!");
            GlobalCasinoState.changeMoneyBalance((int)(600 * GlobalCasinoPerks.getSlotMachineMoneyMultiplier() * GlobalCasinoPerks.getGlobalMoneyMultiplier()));
            break;
        case '!':
            slotResultLabel.setText("Big JackPot, you get $1000!");
            GlobalCasinoState.changeMoneyBalance((int)(1000 * GlobalCasinoPerks.getSlotMachineMoneyMultiplier() * GlobalCasinoPerks.getGlobalMoneyMultiplier()));
            break;
        case '%':
            slotResultLabel.setText("Big JackPot, you get $1600!");
            GlobalCasinoState.changeMoneyBalance((int)(1600 * GlobalCasinoPerks.getSlotMachineMoneyMultiplier() * GlobalCasinoPerks.getGlobalMoneyMultiplier()));
            break;
        case '&':
            slotResultLabel.setText("WHITE PHROOGO JACKPOT. You get $2500!");
            GlobalCasinoState.changeMoneyBalance((int)(2500 * GlobalCasinoPerks.getSlotMachineMoneyMultiplier() * GlobalCasinoPerks.getGlobalMoneyMultiplier()));
            break;
        case '@':
            slotResultLabel.setText("GREEN PHROOGO JACKPOT!! You get $4000!");
            GlobalCasinoState.changeMoneyBalance((int)(4000 * GlobalCasinoPerks.getSlotMachineMoneyMultiplier() * GlobalCasinoPerks.getGlobalMoneyMultiplier()));
            break;
        case '#':
            slotResultLabel.setText("PURPLE PHROOGO JACKPOT!!! You get $6000!");
            GlobalCasinoState.changeMoneyBalance((int)(6000 * GlobalCasinoPerks.getSlotMachineMoneyMultiplier() * GlobalCasinoPerks.getGlobalMoneyMultiplier()));
            break;
        case '$':
            slotResultLabel.setText("PHROOGO JACKPOT!!!! You get $10000!");
            GlobalCasinoState.changeMoneyBalance((int)(10000 * GlobalCasinoPerks.getSlotMachineMoneyMultiplier() * GlobalCasinoPerks.getGlobalMoneyMultiplier()));
            break;
    }
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
                slotResultLabel.setText("Small pair, you get $20!");
                GlobalCasinoState.changeMoneyBalance((int)(20 * GlobalCasinoPerks.getSlotMachineMoneyMultiplier() * GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                break;
            case '^', '!', '%':
                slotResultLabel.setText("Medium pair, you get $40!");
                GlobalCasinoState.changeMoneyBalance((int)(40 * GlobalCasinoPerks.getSlotMachineMoneyMultiplier() * GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                break;
            case '&', '@', '#':
                slotResultLabel.setText("Phroogo pair, you get $100!");
                GlobalCasinoState.changeMoneyBalance((int)(100 * GlobalCasinoPerks.getSlotMachineMoneyMultiplier() * GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                break;
            case '$':
                slotResultLabel.setText("Best Phroogo pair! you get $500!");
                GlobalCasinoState.changeMoneyBalance((int)(500 * GlobalCasinoPerks.getSlotMachineMoneyMultiplier() * GlobalCasinoPerks.getGlobalMoneyMultiplier()));
                break;
        }
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
