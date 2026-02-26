package com.example.frugocasino;

public class Card implements Comparable<Card>{
    private int value;
    private int suit;


    public Card(int value, int suit) {
        if (value < 1 || value > 14) throw new IllegalArgumentException("Invalid rank");
        if (suit < 0 || suit > 3) throw new IllegalArgumentException("Invalid suit");
        this.value = value;
        this.suit = suit;
    }

    public int getSuit() {
        return this.suit;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public int compareTo(Card other) {
        return Integer.compare(this.value, other.value);
    }

    @Override
    public String toString() {
        String suit, value;
        switch (this.suit) {
            case 0 -> suit = "hearts";
            case 1 -> suit = "diamonds";
            case 2 -> suit = "clubs";
            case 3 -> suit = "spades";
            default -> suit = "?";
        }
        switch (this.value) {
            case 11 -> value = "Jack";
            case 12 -> value = "Queen";
            case 13 -> value = "King";
            case 14 -> value = "Ace";
            default -> value = this.getValue() + "";
        }
        return value + " of " + suit;
    }
}