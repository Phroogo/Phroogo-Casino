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
    private static int greenPhrogSlotChanceLevel = 0;
    private static int purplePhrogSlotChanceLevel = 0;
    private static int phroogoSlotChanceLevel = 0;
    private static int rouletteTableRerollLevel = 0;
    private static int blackJackBustLimitLevel = 0;
    private static int texasPokerHandEvaluationIncreaseLevel = 0;

    public int getSlotMachineMoneyMultiplierLevel() {
        return slotMachineMoneyMultiplierLevel;
    }

    public void setSlotMachineMoneyMultiplierLevel(int slotMachineMoneyMultiplierLevel) {
        GlobalCasinoPerks.slotMachineMoneyMultiplierLevel = slotMachineMoneyMultiplierLevel;
    }

    public int getRouletteTableMoneyMultiplierLevel() {
        return rouletteTableMoneyMultiplierLevel;
    }

    public void setRouletteTableMoneyMultiplierLevel(int rouletteTableMoneyMultiplierLevel) {
        GlobalCasinoPerks.rouletteTableMoneyMultiplierLevel = rouletteTableMoneyMultiplierLevel;
    }

    public int getBlackjackMoneyMultiplierLevel() {
        return blackjackMoneyMultiplierLevel;
    }

    public void setBlackjackMoneyMultiplierLevel(int blackjackMoneyMultiplierLevel) {
        GlobalCasinoPerks.blackjackMoneyMultiplierLevel = blackjackMoneyMultiplierLevel;
    }

    public int getTexasPokerMoneyMultiplierLevel() {
        return texasPokerMoneyMultiplierLevel;
    }

    public void setTexasPokerMoneyMultiplierLevel(int texasPokerMoneyMultiplierLevel) {
        GlobalCasinoPerks.texasPokerMoneyMultiplierLevel = texasPokerMoneyMultiplierLevel;
    }

    public int getGlobalMoneyMultiplierLevel() {
        return globalMoneyMultiplierLevel;
    }

    public void setGlobalMoneyMultiplierLevel(int globalMoneyMultiplierLevel) {
        GlobalCasinoPerks.globalMoneyMultiplierLevel = globalMoneyMultiplierLevel;
    }

    public double getSlotMachineMoneyMultiplier() {
        return ((double)slotMachineMoneyMultiplierLevel / 10) + 1;
    }

    public double getRouletteTableMoneyMultiplier() {
        return ((double)rouletteTableMoneyMultiplierLevel / 10) + 1;
    }

    public double getBlackjackMoneyMultiplier() {
        return ((double)blackjackMoneyMultiplierLevel / 10) + 1;
    }

    public double getTexasPokerMoneyMultiplier() {
        return ((double)texasPokerMoneyMultiplierLevel / 10) + 1;
    }

    public double getGlobalMoneyMultiplier() {
        return ((double)globalMoneyMultiplierLevel / 20) + 1;
    }

    public int getSlotMachinePhrogMoneyMultiplierLevel() {
        return slotMachinePhrogMoneyMultiplierLevel;
    }

    public void setSlotMachinePhrogMoneyMultiplierLevel(int slotMachinePhrogMoneyMultiplierLevel) {
        GlobalCasinoPerks.slotMachinePhrogMoneyMultiplierLevel = slotMachinePhrogMoneyMultiplierLevel;
    }

    public int getRouletteTablePhrogMoneyMultiplierLevel() {
        return rouletteTablePhrogMoneyMultiplierLevel;
    }

    public void setRouletteTablePhrogMoneyMultiplierLevel(int rouletteTablePhrogMoneyMultiplierLevel) {
        GlobalCasinoPerks.rouletteTablePhrogMoneyMultiplierLevel = rouletteTablePhrogMoneyMultiplierLevel;
    }

    public int getBlackjackPhrogMoneyMultiplierLevel() {
        return blackjackPhrogMoneyMultiplierLevel;
    }

    public void setBlackjackPhrogMoneyMultiplierLevel(int blackjackPhrogMoneyMultiplierLevel) {
        GlobalCasinoPerks.blackjackPhrogMoneyMultiplierLevel = blackjackPhrogMoneyMultiplierLevel;
    }

    public int getTexasPokerPhrogMoneyMultiplierLevel() {
        return texasPokerPhrogMoneyMultiplierLevel;
    }

    public void setTexasPokerPhrogMoneyMultiplierLevel(int texasPokerPhrogMoneyMultiplierLevel) {
        GlobalCasinoPerks.texasPokerPhrogMoneyMultiplierLevel = texasPokerPhrogMoneyMultiplierLevel;
    }

    public int getGlobalPhrogMoneyMultiplierLevel() {
        return globalPhrogMoneyMultiplierLevel;
    }

    public void setGlobalPhrogMoneyMultiplierLevel(int globalPhrogMoneyMultiplierLevel) {
        GlobalCasinoPerks.globalPhrogMoneyMultiplierLevel = globalPhrogMoneyMultiplierLevel;
    }

    public double getSlotMachinePhrogMoneyMultiplier() {
        return ((double)slotMachinePhrogMoneyMultiplierLevel / 20);
    }

    public double getRouletteTablePhrogMoneyMultiplier() {
        return ((double)rouletteTablePhrogMoneyMultiplierLevel / 20);
    }

    public double getBlackjackPhrogMoneyMultiplier() {
        return ((double)blackjackPhrogMoneyMultiplierLevel / 20);
    }

    public double getTexasPokerPhrogMoneyMultiplier() {
        return ((double)texasPokerPhrogMoneyMultiplierLevel / 20);
    }

    public double getGlobalPhrogMoneyMultiplier() {
        return ((double)globalPhrogMoneyMultiplierLevel / 25);
    }

    public int getGreenPhrogSlotChanceLevel() {
        return greenPhrogSlotChanceLevel;
    }

    public void setGreenPhrogSlotChanceLevel(int greenPhrogSlotChanceLevel) {
        GlobalCasinoPerks.greenPhrogSlotChanceLevel = greenPhrogSlotChanceLevel;
    }

    public int getPurplePhrogSlotChanceLevel() {
        return purplePhrogSlotChanceLevel;
    }

    public void setPurplePhrogSlotChanceLevel(int purplePhrogSlotChanceLevel) {
        GlobalCasinoPerks.purplePhrogSlotChanceLevel = purplePhrogSlotChanceLevel;
    }

    public int getPhroogoSlotChanceLevel() {
        return phroogoSlotChanceLevel;
    }

    public void setPhroogoSlotChanceLevel(int phroogoSlotChanceLevel) {
        GlobalCasinoPerks.phroogoSlotChanceLevel = phroogoSlotChanceLevel;
    }

    public int getRouletteTableRerollLevel() {
        return rouletteTableRerollLevel;
    }

    public void setRouletteTableRerollLevel(int rouletteTableRerollLevel) {
        GlobalCasinoPerks.rouletteTableRerollLevel = rouletteTableRerollLevel;
    }

    public int getBlackJackBustLimitLevel() {
        return blackJackBustLimitLevel;
    }

    public void setBlackJackBustLimitLevel(int blackJackBustLimitLevel) {
        GlobalCasinoPerks.blackJackBustLimitLevel = blackJackBustLimitLevel;
    }

    public int getTexasPokerHandEvaluationIncreaseLevel() {
        return texasPokerHandEvaluationIncreaseLevel;
    }

    public void setTexasPokerHandEvaluationIncreaseLevel(int texasPokerHandEvaluationIncreaseLevel) {
        GlobalCasinoPerks.texasPokerHandEvaluationIncreaseLevel = texasPokerHandEvaluationIncreaseLevel;
    }
}
