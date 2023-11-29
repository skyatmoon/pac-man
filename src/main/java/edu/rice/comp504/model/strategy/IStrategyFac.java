package edu.rice.comp504.model.strategy;

public interface IStrategyFac {
    /**
     * Create a simple ghost strategy.
     * @return simple ghost strategy
     */
    public IStrategy makeStrategy(String strategy);
}
