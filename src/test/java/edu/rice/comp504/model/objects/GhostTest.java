package edu.rice.comp504.model.objects;


import static org.junit.Assert.*;

import edu.rice.comp504.model.map.Map;
import edu.rice.comp504.model.strategy.BackToHomeGhostStrategy;
import edu.rice.comp504.model.strategy.IStrategy;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class GhostTest {
    private Ghost ghost;
    private Map map;
    private Pacman pacman;
    private IStrategy strategy;

    /**
     * The main entry point into the program.
     *
     */
    @Before
    public void setUp() {
        map = new Map();
        pacman = new Pacman(10, 10, "right");
        strategy = new BackToHomeGhostStrategy();
        ghost = new Ghost(10, 10, "left", "red", strategy);
    }

    @Test
    public void testMove() {


        ghost.setX(10);
        ghost.setY(10);
        ghost.setDirection("left");
        ghost.move();

        ghost.setX(10);
        ghost.setY(10);
        ghost.setDirection("up");
        ghost.move();

        ghost.setX(10);
        ghost.setY(10);
        ghost.setDirection("down");
        ghost.move();

        ghost.setX(10);
        ghost.setY(10);
        ghost.setDirection("right");
        ghost.move();

        ghost.setColor("red");
        ghost.getColor();
        ghost.getLevel();
    }

    @Test
    public void testGhostBoundaryHandling() {
        Ghost testGhost = new Ghost(7, 7, "left", "red", new BackToHomeGhostStrategy());
        testGhost.ghostCollisionDetection(testGhost, map, pacman);

        testGhost.setX(7);
        testGhost.setY(0);
        testGhost.ghostCollisionDetection(testGhost, map, pacman);

        testGhost.setX(0);
        testGhost.setY(7);
        testGhost.ghostCollisionDetection(testGhost, map, pacman);

        testGhost.setX(map.getMap()[0].length);
        testGhost.setY(7);
        testGhost.ghostCollisionDetection(testGhost, map, pacman);

        testGhost.setX(7);
        testGhost.setY(map.getMap().length);
        testGhost.ghostCollisionDetection(testGhost, map, pacman);

        testGhost.setX(7);
        testGhost.setY(-1);
        testGhost.ghostCollisionDetection(testGhost, map, pacman);
    }

    @Test
    public void testCollisionWithPacman() {
        Ghost testGhost = new Ghost(10, 10, "left", "red", new BackToHomeGhostStrategy());
        pacman.setX(10);
        pacman.setY(10);


        pacman.setEatenTime(1);

        testGhost.setCanEaten(true);
        testGhost.setBeEaten(false);
        int initialScore = map.getScore();
        testGhost.ghostCollisionDetection(testGhost, map, pacman);

//        testGhost.setCanEaten(false);
//        testGhost.setBeEaten(false);
//        int initialLives = pacman.getLife();
//        testGhost.ghostCollisionDetection(testGhost, map, pacman);
    }

    @Test
    public void testGhostResetAfterBeingEaten() {
        Ghost testGhost = new Ghost(12, 11, "left", "red", new BackToHomeGhostStrategy());
        IStrategy prevStrategy = strategy;
        testGhost.setPrevStrategy(prevStrategy);
        testGhost.setBeEaten(true);
        testGhost.setCanEaten(true);

        testGhost.ghostCollisionDetection(testGhost, map, pacman);

        assertFalse("Ghost's canEaten should be reset to false", testGhost.getCanEaten());
        assertFalse("Ghost's beEaten should be reset to false", testGhost.getBeEaten());
        assertEquals("Ghost's velocity should be reset to 3", 3, testGhost.getVelocity(), 0.001);
        assertSame("Ghost's strategy should be reset to prevStrategy", prevStrategy, testGhost.getStrategy());
    }


    @Test
    public void testUpdateMethod() {
        Ghost testGhost = new Ghost(10, 10, "left", "red", new BackToHomeGhostStrategy());
        testGhost.setLevel(20);
        testGhost.setVelocity(30);
        testGhost.update(testGhost, map, pacman);
        assertEquals("Ghost's velocity should be reset to 7", 7, testGhost.getVelocity(), 0.001);
        testGhost.setCanEaten(true);
        testGhost.update(testGhost, map, pacman);
        assertEquals("Ghost's velocity should be reset to 3", 3, testGhost.getVelocity(), 0.001);

    }

    @Test
    public void testGhostMovement() {
        Ghost testGhost = new Ghost(7, 6, "up", "red", new BackToHomeGhostStrategy());

        testGhost.ghostCollisionDetection(testGhost, map, pacman);
        assertEquals("Ghost's direction should be reset to up", "up", testGhost.getDirection());
        testGhost.setDirection("down");
        testGhost.ghostCollisionDetection(testGhost, map, pacman);
        assertEquals("Ghost's direction should be reset to down", "down", testGhost.getDirection());

        testGhost.setDirection("left");
        testGhost.ghostCollisionDetection(testGhost, map, pacman);
        Ghost testGhost1 = new Ghost(10, 10, "up", "red", new BackToHomeGhostStrategy());

        testGhost1.setDirection("right");

        testGhost1.ghostCollisionDetection(testGhost1, map, pacman);
        assertEquals("Ghost's direction should be reset to right", "right", testGhost1.getDirection());
    }


}