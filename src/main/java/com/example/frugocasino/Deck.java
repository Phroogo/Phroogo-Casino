package com.example.frugocasino;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
    private final int[] suits = {0, 1, 2, 3};
    private final int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
    private final List<Card> deck = new ArrayList<>();
    private final Random random = new Random();

    public List<Card> getDeck() {
        return deck;
    }

    public void createDeck() {
        deck.clear();
        for(int value : values) {
            for (int suit : suits) {
                deck.add(new Card(value, suit));
            }
        }
    }

    public Card draw() {
        int index = random.nextInt(deck.size());
        Card card = deck.get(index);
        deck.remove(index);
        return card;
    }

    public void displayCard(List<Card> hand, ImageView imageView, int index) {
        String suit, value, card;
        switch (hand.get(index).getSuit()) {
            case 0 -> suit = "h";
            case 1 -> suit = "d";
            case 2 -> suit = "c";
            case 3 -> suit = "s";
            default -> suit = "?";
        }
        switch (hand.get(index).getValue()) {
            case 11 -> value = "Jack";
            case 12 -> value = "Queen";
            case 13 -> value = "King";
            case 14 -> value = "Ace";
            default -> value = hand.get(index).getValue() + "";
        }
        card = suit + value;
        imageView.setImage(new Image(getClass().getResourceAsStream("/images/deck/" + card + ".png")));
    }
}
