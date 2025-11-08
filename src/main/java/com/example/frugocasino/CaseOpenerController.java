package com.example.frugocasino;

import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
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

public class CaseOpenerController {

    public Button backButton, openCaseButton;
    public Label moneyLabel, phrogMoneyLabel;
    public ImageView caseImage, slot1, slot2, slot3, slot4, slot5;
    private List<String> spinList = new ArrayList<>();
    List<Runnable> spinAnimation = new ArrayList<>();
    List<Double> spinAnimationDelay = new ArrayList<>();
    private Random random = new Random();

    public void openCase(ActionEvent actionEvent) {
        caseImage.setVisible(false);

        for(int i = 0; i < random.nextInt(99, 119); i++) {
            int result = random.nextInt(1, 10001);
            if(result < 7993) {
                spinList.add( "blue");
            } else if(result < 9591) {
                spinList.add( "purple");
            } else if(result < 9911) {
                spinList.add( "pink");
            } else if(result < 9975) {
                spinList.add( "red");
            } else {
                spinList.add( "gold");
            }
        }
        slot1.setVisible(true);
        slot2.setVisible(true);
        slot3.setVisible(true);
        slot4.setVisible(true);
        slot5.setVisible(true);
        TranslateTransition transition1 = new TranslateTransition();
        TranslateTransition transition2 = new TranslateTransition();
        TranslateTransition transition3 = new TranslateTransition();
        TranslateTransition transition4 = new TranslateTransition();
        TranslateTransition transition5 = new TranslateTransition();
        transition1.setNode(slot1);
        transition1.setToX(-400);
        transition1.setCycleCount(spinList.size() / 5);
        transition1.setDuration(Duration.millis(1000));
        transition2.setNode(slot2);
        transition2.setToX(-400);
        transition2.setCycleCount(spinList.size() / 5);
        transition2.setDuration(Duration.millis(1000));
        transition3.setNode(slot3);
        transition3.setToX(-400);
        transition3.setCycleCount(spinList.size() / 5);
        transition3.setDuration(Duration.millis(1000));
        transition4.setNode(slot4);
        transition4.setToX(-400);
        transition4.setCycleCount(spinList.size() / 5);
        transition4.setDuration(Duration.millis(1000));
        transition5.setNode(slot5);
        transition5.setToX(-2720);
        transition5.setCycleCount(spinList.size() / 5);
        transition5.setDuration(Duration.millis(1000));
        transition1.play();
        spinAnimation.add(transition2::play);
        spinAnimation.add(transition3::play);
        spinAnimation.add(transition4::play);
        spinAnimation.add(transition5::play);
        spinAnimationDelay.add(250.0);
        spinAnimationDelay.add(250.0);
        spinAnimationDelay.add(250.0);
        spinAnimationDelay.add(250.0);
        for(int i = 0; i < spinList.size(); i++) {
            int temp = i;
            double delay = 80 + 1.25 * i + 0.007 * (i * i);
            spinAnimation.add(() -> {
               colorToImage(spinList.get(temp), 1);
                colorToImage(spinList.get(temp + 1), 2);
                colorToImage(spinList.get(temp + 2), 3);
                colorToImage(spinList.get(temp + 3), 4);
                colorToImage(spinList.get(temp + 4), 5);
            });
            spinAnimationDelay.add(1000.0);

//            spinAnimation.add(() -> {
//                ImageView slot = null;
//                switch(temp % 5) {
//                    case 0 -> slot = slot1;
//                    case 1 -> slot = slot2;
//                    case 2 -> slot = slot3;
//                    case 3 -> slot = slot4;
//                    case 4 -> slot = slot5;
//                }
//
//                if(!slot.isVisible()) slot.setVisible(true);
//
//                //slot.setX(-400);
//
//                colorToImage(spinList.get(temp), temp % 5 + 1);
//
//                TranslateTransition translate = new TranslateTransition(Duration.millis(delay * 5), slot);
//                translate.setByX(2720);
//                translate.play();
//            });
//
//            spinAnimationDelay.add(delay);
        }
        playDelayedSteps(spinAnimation, spinAnimationDelay);
    }

    private String numberToColor(int number) {
        String color;
        switch(number) {
            case 1 -> color = "purple";
            case 2 -> color = "pink";
            case 3 -> color = "red";
            case 4 -> color = "gold";
            default -> color = "blue";
        }
        return color;
    }

    private void colorToImage(String color, int slot) {
        switch(slot) {
            case 1 -> slot1.setImage(new Image(getClass().getResourceAsStream("/images/caseOpener/" +  color + ".png")));
            case 2 -> slot2.setImage(new Image(getClass().getResourceAsStream("/images/caseOpener/" +  color + ".png")));
            case 3 -> slot3.setImage(new Image(getClass().getResourceAsStream("/images/caseOpener/" +  color + ".png")));
            case 4 -> slot4.setImage(new Image(getClass().getResourceAsStream("/images/caseOpener/" +  color + ".png")));
            case 5 -> slot5.setImage(new Image(getClass().getResourceAsStream("/images/caseOpener/" +  color + ".png")));
        }
    }


    private void playDelayedSteps(List<Runnable> steps, List<Double> delaysInMilliseconds) {
        if (steps.isEmpty() || delaysInMilliseconds.isEmpty()) return;

        Runnable currentStep = steps.remove(0);
        double currentDelay = delaysInMilliseconds.remove(0);

        currentStep.run();

        if (!steps.isEmpty()) {
            PauseTransition pause = new PauseTransition(Duration.millis(currentDelay));
            pause.setOnFinished(e -> playDelayedSteps(steps, delaysInMilliseconds));
            pause.play();
        }
    }

    public void switchToCasinoButton(ActionEvent actionEvent) throws IOException {
        GlobalCasinoState.switchToSceneButton(actionEvent);
    }
}
