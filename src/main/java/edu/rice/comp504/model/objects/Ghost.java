package edu.rice.comp504.model.objects;

import edu.rice.comp504.model.map.Map;
import edu.rice.comp504.model.strategy.BackToHomeGhostStrategy;
import edu.rice.comp504.model.strategy.IStrategy;
import edu.rice.comp504.util.PathFinder;
import java.awt.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ghost extends AObject {
    private String color;
    private IStrategy strategy;
    private IStrategy prevStrategy;
    private float velocityBuff = 0;
    private int level = 1;
    private float velocity = 3;
    private boolean canEaten = false;
    private boolean beEaten = false;
    public ArrayList<Point> path = new ArrayList<>();
    private final List<Integer> walls = List.of(1, 7);

    /**
     * Constructor.
     *
     * @param x the location on x
     * @param y the location on y
     * @param direction the direction
     */
    public Ghost(int x, int y, String direction, String color, IStrategy strategy) {
        super(x, y, direction);
        this.color = color;
        this.strategy = strategy;
    }

    /**
     * Get the color.
     * @return color
     */
    public String getColor() {
        return color;
    }

    /**
     * Set the color.
     * @param color the ghost color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Get the level.
     * @return level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Set the level.
     * @param level the level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Get the strategy.
     * @return strategy
     */
    public IStrategy getStrategy() {
        return strategy;
    }

    /**
     * Set the strategy.
     * @param strategy the strategy
     */
    public void setStrategy(IStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Get the prevStrategy.
     * @return prevStrategy
     */
    public IStrategy getPrevStrategy() {
        return prevStrategy;
    }

    /**
     * Set the prevStrategy.
     * @param prevStrategy the prevStrategy
     */
    public void setPrevStrategy(IStrategy prevStrategy) {
        this.prevStrategy = prevStrategy;
    }

    /**
     * Get the velocity.
     * @return velocity
     */
    public float getVelocity() {
        return velocity;
    }

    /**
     * Set the velocity.
     * @param velocity the velocity
     */
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    /**
     * Get the canEaten.
     * @return canEaten
     */
    public boolean getCanEaten() {
        return canEaten;
    }

    /**
     * Set the canEaten.
     * @param canEaten the canEaten
     */
    public void setCanEaten(boolean canEaten) {
        this.canEaten = canEaten;
    }

    /**
     * Get the beEaten.
     * @return beEaten
     */
    public boolean getBeEaten() {
        return beEaten;
    }

    /**
     * Set the beEaten.
     * @param beEaten the beEaten
     */
    public void setBeEaten(boolean beEaten) {
        this.beEaten = beEaten;
    }

    /**
     * Move the ghost.
     */
    public void move() {
        if (direction.equals("up")) {
            y -= (int) velocityBuff;
        } else if (direction.equals("down")) {
            y += (int) velocityBuff;
        } else if (direction.equals("left")) {
            x -= (int) velocityBuff;
        } else if (direction.equals("right")) {
            x += (int) velocityBuff;
        }
    }

    /**
     * Check if the ghost can move to the next position.
     */
    public void ghostCollisionDetection(Ghost ghost, Map map, Pacman pacman) {

        if (ghost.getY() < 0) {
            ghost.setY(map.getMap().length - 1);
        } else if (ghost.getY() > map.getMap().length - 1) {
            ghost.setY(0);
        } else if (ghost.getX() <= 0) {
            ghost.setX(map.getMap()[0].length - 2);
        } else if (ghost.getX() > map.getMap()[0].length - 2) {
            ghost.setX(1);
        }

        if (ghost.getBeEaten() && ghost.getX() >= 11 && ghost.getX() <= 14  && ghost.getY() >= 10 && ghost.getY() <= 13) {
            ghost.setCanEaten(false);
            ghost.setBeEaten(false);
            ghost.setVelocity(3);//back to default velocity
            ghost.setStrategy(ghost.getPrevStrategy());
        }

        if (pacman.getEatenTime() <= 0 && ghost.getCanEaten()) {
            ghost.setCanEaten(false);
            ghost.setVelocity(3);//back to default velocity
        }

        if (pacman.getX() == ghost.getX() && pacman.getY() == ghost.getY()) {
            System.out.println("ghost.getCanEaten()" + ghost.getCanEaten());
            System.out.println("ghost.getBeEaten()" + ghost.getBeEaten());
            System.out.println(ghost.getCanEaten() && !ghost.getBeEaten());
            if (ghost.getCanEaten() && !ghost.getBeEaten()) {
                map.setScore(map.getScore() + 200 * pacman.getAteGhost());
                ghost.setBeEaten(true);
                ghost.setStrategy(new BackToHomeGhostStrategy());
            } else if (!ghost.getBeEaten()) {
                pacman.setAteGhost(1);
                pacman.setX(11);
                pacman.setY(21);
                pacman.setDirection("null");
                pacman.setLife(pacman.getLife() - 1);
            }
        }

        if (Objects.equals(ghost.getDirection(), "down") && !walls.contains(map.getMap()[ghost.getY() + 1][ghost.getX()])) {
            ghost.move();
        } else if (Objects.equals(ghost.getDirection(), "up") && !walls.contains(map.getMap()[ghost.getY() - 1][ghost.getX()])) {
            ghost.move();
        } else if (Objects.equals(ghost.getDirection(), "right") && !walls.contains(map.getMap()[ghost.getY()][ghost.getX() + 1])) {
            ghost.move();
        } else if (Objects.equals(ghost.getDirection(), "left") && !walls.contains(map.getMap()[ghost.getY()][ghost.getX() - 1])) {
            ghost.move();
        }
    }

    /**
     * Update the ghost move direction.
     */
    public void update(Ghost ghost, Map map, Pacman pacman) {
        if (ghost.canEaten) {
            strategy.ghostUpdate(map, pacman, ghost);
        } else {
            strategy.ghostUpdate(map, pacman, ghost);
        }
        velocityBuff += level * velocity * 0.05;
        ghost.ghostCollisionDetection(ghost, map, pacman);
        if (velocityBuff >= 1) {
            velocityBuff = 0;
        }
    }
}
