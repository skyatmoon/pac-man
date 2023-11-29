package edu.rice.comp504.model.objects;

import edu.rice.comp504.model.strategy.IStrategy;

public interface IObjectFac {
    /**
     * Create a pacman.
     * @param x the location on x
     * @param y the location on y
     * @param direction the direction
     * @return pacman
     */
    public Pacman makePacman(int x, int y, String direction);

    /**
     * Create a ghost.
     * @param x the location on x
     * @param y the location on y
     * @param direction the direction
     * @param color the color
     * @return ghost
     */
    public Ghost makeGhost(int x, int y, String direction, String color, IStrategy strategy);
}
