package domain.client;

import domain.client.RandomPlayerActionStrategies.*;

public class RandomPlayerActionFactory {
    private static RandomPlayerActionFactory ourInstance;
    private float[] chances;

    public static RandomPlayerActionFactory getInstance() {
        if (ourInstance == null) {
            ourInstance = new RandomPlayerActionFactory();
        }
        return ourInstance;
    }

    private RandomPlayerActionFactory() {
        chances = new float[]{0.2f, 0.4f, 0.6f};
    }

    public RandomPlayerActionStrategy generateStrategy(String strategy) {
        if (strategy.equals("Roll")) {
            return new RandomPlayerActionRollStrategy();
        } else if (strategy.equals("PayRent")) {
            return new RandomPlayerActionPayRentStrategy();
        } else if (strategy.equals("DrawCard")) {
            return new RandomPlayerActionDrawCardStrategy();
        } else if (strategy.equals("MrMonopoly")) {
            return new RandomPlayerActionMrMonopolyStrategy();
        } else if (strategy.equals("FinishTurn")) {
            return new RandomPlayerActionFinishTurnStrategy();
        } else return null;
    }

    public RandomPlayerActionStrategy generateStrategy(String strategy, int difficulty) {
        float diff = chances[difficulty];
        if (strategy.equals("Buy")) {
            return new RandomPlayerActionBuyStrategy(diff);
        } else return null;
    }
}
