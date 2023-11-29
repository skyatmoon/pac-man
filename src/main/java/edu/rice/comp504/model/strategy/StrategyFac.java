package edu.rice.comp504.model.strategy;

public class StrategyFac implements IStrategyFac {
    @Override
    public IStrategy makeStrategy(String strategy) {
        switch (strategy) {
            case "SimpleGhostStrategy":
                return new SimpleGhostStrategy();
            case "BackToHomeGhostStrategy":
                return new BackToHomeGhostStrategy();
            case "ShyGhostStrategy":
                return new ShyGhostStrategy();
            case "FruitHunterGhostStrategy":
                return new FruitHunterGhostStrategy();
            case "PatrollerGhostStrategy":
                return new PatrollerGhostStrategy();
            default:
                return null;
        }
    }
}
