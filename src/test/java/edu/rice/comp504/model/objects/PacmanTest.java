package edu.rice.comp504.model.objects;

import static org.junit.Assert.*;

import edu.rice.comp504.model.map.Map;
import edu.rice.comp504.model.strategy.BackToHomeGhostStrategy;
import edu.rice.comp504.model.strategy.IStrategy;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PacmanTest {
    private Pacman pacman;
    private Map map;
    private ArrayList<Ghost> ghost;

    private Ghost ghost1;

    /**
     * The main entry point into the program.
     *
     */
    @Before
    public void setUp() {
        pacman = new Pacman(10, 10, "right");
        map = new Map();
        ghost = new ArrayList<>();
        IStrategy strategy = new BackToHomeGhostStrategy();
        ghost1 = new Ghost(10, 10, "left", "red", strategy);
        ghost.add(ghost1);
    }

    @Test
    public void testMove() {
        pacman.move();
        assertEquals("X increase", 10, pacman.getX());
        assertEquals("Y stable", 10, pacman.getY());

        pacman.setX(10);
        pacman.setY(10);
        pacman.setDirection("left");

        pacman.move();
        assertEquals("X decrease", 10, pacman.getX());
        assertEquals("Y stable", 10, pacman.getY());

        pacman.setX(10);
        pacman.setY(10);
        pacman.setDirection("up");
        pacman.move();
        assertEquals("y decrease", 9, pacman.getY());
        assertEquals("X stable", 10, pacman.getX());

        // Reset position and test down movement
        pacman.setX(10);
        pacman.setY(10);
        pacman.setDirection("down");
        pacman.move();
        assertEquals("Y increase", 10, pacman.getY());
        assertEquals("X stable", 10, pacman.getX());

        pacman.getLife();

    }

    @Test
    public void testEatenTimeDecrease() {
        pacman.setEatenTime(1000);
        pacman.pacmanCollisionDetection("up", pacman, map, ghost);
        assertTrue("Eaten time should decrease", pacman.getEatenTime() < 1000);
    }

    @Test
    public void testBoundaryHandling() {
        pacman.setX(7);
        pacman.setY(7);
        pacman.pacmanCollisionDetection("left", pacman, map, ghost);
        pacman.pacmanCollisionDetection("right", pacman, map, ghost);
        pacman.pacmanCollisionDetection("down", pacman, map, ghost);
        pacman.pacmanCollisionDetection("up", pacman, map, ghost);
        System.out.println(pacman.pacmanCollisionDetection("up", pacman, map, ghost));
        pacman.setX(7);
        pacman.setY(0);
        assertTrue("Should handle upper boundary correctly", pacman.pacmanCollisionDetection("up", pacman, map, ghost));
        pacman.setX(0);
        pacman.setY(7);
        assertTrue("Should handle left boundary correctly", pacman.pacmanCollisionDetection("left", pacman, map, ghost));
        pacman.setX(32);
        pacman.setY(7);
        assertTrue("Should handle right boundary correctly", pacman.pacmanCollisionDetection("right", pacman, map, ghost));
        pacman.setX(7);
        pacman.setY(32);
        assertTrue("Should handle lower boundary correctly", pacman.pacmanCollisionDetection("down", pacman, map, ghost));

    }

    @Test
    public void testCollisionWithEatableGhost() {
        ghost1.setCanEaten(true);
        pacman.setEatenTime(-1);
        pacman.pacmanCollisionDetection("left", pacman, map, ghost);
        pacman.setX(10);
        pacman.setY(10);
        ghost1.setX(10);
        ghost1.setY(10);


        ghost1.setCanEaten(true);
        ghost1.setBeEaten(false);

        pacman.setX(10);
        pacman.setY(10);


        pacman.setEatenTime(1);

        ghost1.setCanEaten(true);
        ghost1.setBeEaten(false);

        pacman.pacmanCollisionDetection("left", pacman, map, ghost);
        assertEquals("Ghost should be not eaten", false, ghost1.getBeEaten());
        ghost1.setCanEaten(false);
        pacman.pacmanCollisionDetection("left", pacman, map, ghost);
        assertEquals("Ghost should be not eaten", false, ghost1.getBeEaten());
        pacman.getAteGhost();
    }
    @Test
    public void testCollisionWithEatableGhost1() {
        ghost1.setCanEaten(true);
        ghost1.setBeEaten(false);
        pacman.setX(10);
        pacman.setY(10);
        ghost1.setX(10);
        ghost1.setY(10);
        pacman.setEatenTime(201);


        pacman.pacmanCollisionDetection("left", pacman, map, ghost);
        assertEquals("Ghost should be eaten", true, ghost1.getBeEaten());
    }
}
