package edu.rice.comp504.model.objects;

import edu.rice.comp504.model.map.Map;
import edu.rice.comp504.model.strategy.BackToHomeGhostStrategy;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Pacman extends AObject {
    private int life = 3;
    private int ateGhost = 1;
    private int eatenTime = 0;
    private float velocityBuff = 0;
    private final List<Integer> walls = List.of(1, 7, 8);

    /**
     * Constructor.
     */

    public Pacman(int x, int y, String direction) {
        super(x, y, direction);
    }

    /**
     * Move the pacman.
     */
    public void move() {
        switch (direction) {
            case "up":
                y -= (int) velocityBuff;
                break;
            case "down":
                y += (int) velocityBuff;
                break;
            case "left":
                x -= (int) velocityBuff;
                break;
            case "right":
                x += (int) velocityBuff;
                break;
            default:
                break;
        }
        if (velocityBuff >= 1) {
            velocityBuff = 0;
        } else {
            velocityBuff += 0.5;
        }
    }

    /**
     * Get the life.
     * @return life
     */
    public int getLife() {
        return life;
    }

    /**
     * Set the life.
     * @param life the life nume
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * Get the ateGhost.
     * @return ateGhost
     */
    public int getAteGhost() {
        return ateGhost;
    }

    /**
     * Set the ateGhost.
     * @param ateGhost the ateGhost nume
     */
    public void setAteGhost(int ateGhost) {
        this.ateGhost = ateGhost;
    }

    /**
     * Get the eatenTime.
     * @return eatenTime
     */
    public int getEatenTime() {
        return eatenTime;
    }

    /**
     * Set the eatenTime.
     * @param eatenTime the eatenTime nume
     */
    public void setEatenTime(int eatenTime) {
        this.eatenTime = eatenTime;
    }

    /**
     * Check if the pacman can move to the next position.
     * @param operation the operation
     * @return true if the pacman can move to the next position
     */
    public boolean pacmanCollisionDetection(String operation, Pacman pacman, Map map, ArrayList<Ghost> ghost) {
        if (pacman.getEatenTime() > 0) {
            pacman.setEatenTime(pacman.getEatenTime() - 200);
        }

        if (pacman.getY() <= 0) {
            pacman.setY(map.getMap().length - 1);
            System.out.println("i change it" + (map.getMap().length - 1));
        } else if (pacman.getY() > map.getMap().length - 1) {
            pacman.setY(0);
        } else if (pacman.getX() <= 0) {
            pacman.setX(map.getMap()[0].length - 2);
        } else if (pacman.getX() > map.getMap()[0].length - 2) {
            pacman.setX(1);
        }

        for (Ghost value : ghost) {
            System.out.println("pacman.getEatenTime()" + pacman.getEatenTime());
            System.out.println("value.getCanEaten()" + value.getCanEaten());

            if (pacman.getEatenTime() <= 0 && value.getCanEaten()) {
                value.setCanEaten(false);
                value.setVelocity(3);//back to default velocity
            } // time out cannot eat ghost
            if (pacman.getX() == value.getX() && pacman.getY() == value.getY()) {
                System.out.println("value.getBeEaten()" + value.getBeEaten());
                System.out.println(value.getCanEaten());

                if (value.getCanEaten() && !value.getBeEaten()) {
                    value.setBeEaten(true);
                    value.setStrategy(new BackToHomeGhostStrategy());
                    map.setScore(map.getScore() + 200 * pacman.getAteGhost());
                } else if (!value.getBeEaten()) {
                    pacman.setAteGhost(1);
                    pacman.setX(11);
                    pacman.setY(21);
                    pacman.setDirection("null");
                    pacman.setLife(pacman.getLife() - 1);
                }
            }
        }

        if (Objects.equals(operation, "down") && !walls.contains(map.getMap()[pacman.getY() + 1][pacman.getX()])) {
            return true;
        } else if (Objects.equals(operation, "up") && !walls.contains(map.getMap()[pacman.getY() - 1][pacman.getX()])) {
            return true;
        } else if (Objects.equals(operation, "right") && !walls.contains(map.getMap()[pacman.getY()][pacman.getX() + 1])) {
            return true;
        } else if (Objects.equals(operation, "left") && !walls.contains(map.getMap()[pacman.getY()][pacman.getX() - 1])) {
            return true;
        }

        return false;
    }

}
