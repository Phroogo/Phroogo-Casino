package com.example.frugocasino;

public class GlobalCasinoPerks {
    private static int slotMachineMoneyMultiplierLevel = 0;
    private static int rouletteTableMoneyMultiplierLevel = 0;
    private static int blackjackMoneyMultiplierLevel = 0;
    private static int texasPokerMoneyMultiplierLevel = 0;
    private static int globalMoneyMultiplierLevel = 0;
    private static int slotMachinePhrogMoneyMultiplierLevel = 0;
    private static int rouletteTablePhrogMoneyMultiplierLevel = 0;
    private static int blackjackPhrogMoneyMultiplierLevel = 0;
    private static int texasPokerPhrogMoneyMultiplierLevel = 0;
    private static int globalPhrogMoneyMultiplierLevel = 0;

    public static int getSlotMachineMoneyMultiplierLevel() {
        return slotMachineMoneyMultiplierLevel;
    }

    public static void setSlotMachineMoneyMultiplierLevel(int slotMachineMoneyMultiplierLevel) {
        GlobalCasinoPerks.slotMachineMoneyMultiplierLevel = slotMachineMoneyMultiplierLevel;
    }

    public static int getRouletteTableMoneyMultiplierLevel() {
        return rouletteTableMoneyMultiplierLevel;
    }

    public static void setRouletteTableMoneyMultiplierLevel(int rouletteTableMoneyMultiplierLevel) {
        GlobalCasinoPerks.rouletteTableMoneyMultiplierLevel = rouletteTableMoneyMultiplierLevel;
    }

    public static int getBlackjackMoneyMultiplierLevel() {
        return blackjackMoneyMultiplierLevel;
    }

    public static void setBlackjackMoneyMultiplierLevel(int blackjackMoneyMultiplierLevel) {
        GlobalCasinoPerks.blackjackMoneyMultiplierLevel = blackjackMoneyMultiplierLevel;
    }

    public static int getTexasPokerMoneyMultiplierLevel() {
        return texasPokerMoneyMultiplierLevel;
    }

    public static void setTexasPokerMoneyMultiplierLevel(int texasPokerMoneyMultiplierLevel) {
        GlobalCasinoPerks.texasPokerMoneyMultiplierLevel = texasPokerMoneyMultiplierLevel;
    }

    public static int getGlobalMoneyMultiplierLevel() {
        return globalMoneyMultiplierLevel;
    }

    public static void setGlobalMoneyMultiplierLevel(int globalMoneyMultiplierLevel) {
        GlobalCasinoPerks.globalMoneyMultiplierLevel = globalMoneyMultiplierLevel;
    }

    public static double getSlotMachineMoneyMultiplier() {
        return ((double)slotMachineMoneyMultiplierLevel / 10);
    }

    public static double getRouletteTableMoneyMultiplier() {
        return ((double)rouletteTableMoneyMultiplierLevel / 10);
    }

    public static double getBlackjackMoneyMultiplier() {
        return ((double)blackjackMoneyMultiplierLevel / 10);
    }

    public static double getTexasPokerMoneyMultiplier() {
        return ((double)texasPokerMoneyMultiplierLevel / 10);
    }

    public static double getGlobalMoneyMultiplier() {
        return ((double)globalMoneyMultiplierLevel / 20);
    }

    public static int getSlotMachinePhrogMoneyMultiplierLevel() {
        return slotMachinePhrogMoneyMultiplierLevel;
    }

    public static void setSlotMachinePhrogMoneyMultiplierLevel(int slotMachinePhrogMoneyMultiplierLevel) {
        GlobalCasinoPerks.slotMachinePhrogMoneyMultiplierLevel = slotMachinePhrogMoneyMultiplierLevel;
    }

    public static int getRouletteTablePhrogMoneyMultiplierLevel() {
        return rouletteTablePhrogMoneyMultiplierLevel;
    }

    public static void setRouletteTablePhrogMoneyMultiplierLevel(int rouletteTablePhrogMoneyMultiplierLevel) {
        GlobalCasinoPerks.rouletteTablePhrogMoneyMultiplierLevel = rouletteTablePhrogMoneyMultiplierLevel;
    }

    public static int getBlackjackPhrogMoneyMultiplierLevel() {
        return blackjackPhrogMoneyMultiplierLevel;
    }

    public static void setBlackjackPhrogMoneyMultiplierLevel(int blackjackPhrogMoneyMultiplierLevel) {
        GlobalCasinoPerks.blackjackPhrogMoneyMultiplierLevel = blackjackPhrogMoneyMultiplierLevel;
    }

    public static int getTexasPokerPhrogMoneyMultiplierLevel() {
        return texasPokerPhrogMoneyMultiplierLevel;
    }

    public static void setTexasPokerPhrogMoneyMultiplierLevel(int texasPokerPhrogMoneyMultiplierLevel) {
        GlobalCasinoPerks.texasPokerPhrogMoneyMultiplierLevel = texasPokerPhrogMoneyMultiplierLevel;
    }

    public static int getGlobalPhrogMoneyMultiplierLevel() {
        return globalPhrogMoneyMultiplierLevel;
    }

    public static void setGlobalPhrogMoneyMultiplierLevel(int globalPhrogMoneyMultiplierLevel) {
        GlobalCasinoPerks.globalPhrogMoneyMultiplierLevel = globalPhrogMoneyMultiplierLevel;
    }

    public static double getSlotMachinePhrogMoneyMultiplier() {
        return ((double)slotMachinePhrogMoneyMultiplierLevel / 20);
    }

    public static double getRouletteTablePhrogMoneyMultiplier() {
        return ((double)rouletteTablePhrogMoneyMultiplierLevel / 20);
    }

    public static double getBlackjackPhrogMoneyMultiplier() {
        return ((double)blackjackPhrogMoneyMultiplierLevel / 20);
    }

    public static double getTexasPokerPhrogMoneyMultiplier() {
        return ((double)texasPokerPhrogMoneyMultiplierLevel / 20);
    }

    public static double getGlobalPhrogMoneyMultiplier() {
        return ((double)globalPhrogMoneyMultiplierLevel / 25);
    }
}
