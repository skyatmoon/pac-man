package edu.rice.comp504.model.objects;

import edu.rice.comp504.model.strategy.IStrategy;

public class GamObjectFac implements IObjectFac {
    @Override
    public Pacman makePacman(int x, int y, String direction) {
        return new Pacman(x, y, direction);
    }

    @Override
    public Ghost makeGhost(int x, int y, String direction, String color, IStrategy strategy) {
        return new Ghost(x, y, direction, color, strategy);
    }
}
