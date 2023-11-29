package edu.rice.comp504.model.strategy;


import static org.junit.Assert.*;

import edu.rice.comp504.model.map.Map;
import edu.rice.comp504.model.objects.Ghost;
import edu.rice.comp504.model.objects.Pacman;
import edu.rice.comp504.model.strategy.BackToHomeGhostStrategy;
import edu.rice.comp504.model.strategy.IStrategy;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class StrategyTest {
    private Map map;
    private Pacman pacman;
    private Ghost ghost;
    private BackToHomeGhostStrategy strategy;
    private FruitHunterGhostStrategy strategy1;
    private SimpleGhostStrategy strategy2;
    private PatrollerGhostStrategy strategy3;

    private ShyGhostStrategy strategy4;



    @Before
    public void setUp() {
        map = new Map();
        pacman = new Pacman(10, 10, "right");
    }

    @Test
    public void testBackToHomeGhostUpdate() {
        strategy = new BackToHomeGhostStrategy();
        ghost = new Ghost(5, 5, "left", "red", strategy);

        strategy.ghostUpdate(map, pacman, ghost);
        assertNotNull("Direction should not be null", ghost.getDirection());
        assertNotEquals("Direction should not be random", "null", ghost.getDirection());
        // 测试向上移动
        ghost.setX(11);
        ghost.setY(12);
        strategy.ghostUpdate(map, pacman, ghost);
        assertEquals("Ghost should move up", "up", ghost.getDirection());

        // 测试向下移动
        ghost.setX(11);
        ghost.setY(10);
        strategy.ghostUpdate(map, pacman, ghost);
        assertEquals("Ghost should move down", "down", ghost.getDirection());

        // 测试向左移动
        ghost.setX(12);
        ghost.setY(11);
        strategy.ghostUpdate(map, pacman, ghost);
        assertEquals("Ghost should move left", "left", ghost.getDirection());

        // 测试向右移动
        ghost.setX(10);
        ghost.setY(11);
        strategy.ghostUpdate(map, pacman, ghost);
    }

    @Test
    public void testFruitHunterGhostStrategy() {
        strategy1 = new FruitHunterGhostStrategy();
        ghost = new Ghost(5, 5, "left", "red", strategy1);

        strategy1.ghostUpdate(map, pacman, ghost);
        assertNotNull("Direction should not be null", ghost.getDirection());
        assertNotEquals("Direction should not be random", "null", ghost.getDirection());
        int initialX = ghost.getX();
        int initialY = ghost.getY();

        map.setFruitPosition(ghost.getX() + 5, ghost.getY());
        strategy1.ghostUpdate(map, pacman, ghost);
        assertTrue("Ghost X position should change", ghost.getX() == initialX);

        map.setFruitPosition(ghost.getX() - 5, ghost.getY());
        strategy1.ghostUpdate(map, pacman, ghost);
        assertTrue("Ghost X position should change", ghost.getX() == initialX);

        map.setFruitPosition(ghost.getX(), ghost.getY() + 5);
        strategy1.ghostUpdate(map, pacman, ghost);
        assertTrue("Ghost X position should change", ghost.getX() == initialX);

        map.setFruitPosition(ghost.getX(), ghost.getY() - 5);
        strategy1.ghostUpdate(map, pacman, ghost);
        assertTrue("Ghost X position should change", ghost.getX() == initialX);

        map.setFruitPosition(ghost.getX(), ghost.getY());
        strategy1.ghostUpdate(map, pacman, ghost);
        assertTrue("Ghost X position should change", ghost.getX() == initialX);

        map.setFruitPosition(15, 15);
        ghost.setX(10);
        ghost.setY(10);
        strategy1.ghostUpdate(map, pacman, ghost);
        assertTrue("Ghost X position should change", ghost.getX() != initialX);

        map.setFruitPosition(11, 10);
        strategy1.ghostUpdate(map, pacman, ghost);
        assertTrue("Ghost X position should change", ghost.getX() != initialX);

        map.setFruitPosition(9, 10);
        strategy1.ghostUpdate(map, pacman, ghost);
        assertTrue("Ghost X position should change", ghost.getX() != initialX);

        map.setFruitPosition(10, 11);
        strategy1.ghostUpdate(map, pacman, ghost);
        assertTrue("Ghost X position should change", ghost.getX() != initialX);

        map.setFruitPosition(10, 9);
        strategy1.ghostUpdate(map, pacman, ghost);
        assertTrue("Ghost X position should change", ghost.getX() != initialX);

        map.setFruitPosition(10, 10);
        strategy1.ghostUpdate(map, pacman, ghost);
        assertTrue("Ghost X position should change", ghost.getX() != initialX);
    }

    @Test
    public void testSimpleGhostStrategy() {
        strategy2 = new SimpleGhostStrategy();
        pacman = new Pacman(10, 10, "right");
        ghost = new Ghost(10, 10, "left", "red", strategy2);

        pacman.setX(6);
        pacman.setY(2);
        ghost.setX(3);
        ghost.setY(2);
        strategy2.ghostUpdate(map, pacman, ghost);

        pacman.setX(4);
        pacman.setY(5);
        ghost.setX(4);
        ghost.setY(2);
        strategy2.ghostUpdate(map, pacman, ghost);

        assertNotNull("Direction should not be null", ghost.getDirection());
        assertNotEquals("Direction should not be random", "null", ghost.getDirection());

        pacman.setX(1);
        pacman.setY(1);
        ghost.setX(18);
        ghost.setY(18);
        strategy2.ghostUpdate(map, pacman, ghost);


        pacman.setX(4);
        pacman.setY(4);
        ghost.setX(4);
        ghost.setY(4);
//        path.get(1)java.awt.Point[x=10,y=2]

        strategy2.ghostUpdate(map, pacman, ghost);

        pacman.setX(2);
        pacman.setY(4);
        ghost.setX(2);
        ghost.setY(11);
//        path.get(1)java.awt.Point[x=10,y=2]

        strategy2.ghostUpdate(map, pacman, ghost);

        pacman.setX(2);
        pacman.setY(11);
        ghost.setX(2);
        ghost.setY(4);
//        path.get(1)java.awt.Point[x=10,y=2]

        strategy2.ghostUpdate(map, pacman, ghost);

        pacman.setX(12);
        pacman.setY(1);
        ghost.setX(15);
        ghost.setY(1);
//        path.get(1)java.awt.Point[x=10,y=2]

        strategy2.ghostUpdate(map, pacman, ghost);

        pacman.setX(15);
        pacman.setY(1);
        ghost.setX(11);
        ghost.setY(1);
//        path.get(1)java.awt.Point[x=10,y=2]

        strategy2.ghostUpdate(map, pacman, ghost);


    }


    @Test
    public void PatrollerGhostStrategy() {
        strategy3 = new PatrollerGhostStrategy();
        pacman = new Pacman(10, 10, "right");
        ghost = new Ghost(10, 10, "left", "red", strategy3);
        pacman.setX(6);
        pacman.setY(2);
        ghost.setX(3);
        ghost.setY(2);
        strategy3.ghostUpdate(map, pacman, ghost);

        pacman.setX(4);
        pacman.setY(5);
        ghost.setX(4);
        ghost.setY(2);
        strategy3.ghostUpdate(map, pacman, ghost);

        assertNotNull("Direction should not be null", ghost.getDirection());
        assertNotEquals("Direction should not be random", "null", ghost.getDirection());

        pacman.setX(1);
        pacman.setY(1);
        ghost.setX(18);
        ghost.setY(18);
        strategy3.ghostUpdate(map, pacman, ghost);

        pacman.setX(4);
        pacman.setY(4);
        ghost.setX(4);
        ghost.setY(4);
//        path.get(1)java.awt.Point[x=10,y=2]

        strategy3.ghostUpdate(map, pacman, ghost);

        pacman.setX(2);
        pacman.setY(4);
        ghost.setX(2);
        ghost.setY(11);
//        path.get(1)java.awt.Point[x=10,y=2]

        strategy3.ghostUpdate(map, pacman, ghost);

        pacman.setX(2);
        pacman.setY(11);
        ghost.setX(2);
        ghost.setY(4);
//        path.get(1)java.awt.Point[x=10,y=2]

        strategy3.ghostUpdate(map, pacman, ghost);

        pacman.setX(12);
        pacman.setY(1);
        ghost.setX(15);
        ghost.setY(1);
//        path.get(1)java.awt.Point[x=10,y=2]

        strategy3.ghostUpdate(map, pacman, ghost);

        pacman.setX(15);
        pacman.setY(1);
        ghost.setX(11);
        ghost.setY(1);
//        path.get(1)java.awt.Point[x=10,y=2]

        strategy3.ghostUpdate(map, pacman, ghost);
    }

    @Test
    public void ShyGhostStrategy() {
        strategy4 = new ShyGhostStrategy();
        pacman = new Pacman(10, 10, "right");
        ghost = new Ghost(10, 10, "left", "red", strategy4);

        pacman.setX(6);
        pacman.setY(2);
        ghost.setX(3);
        ghost.setY(2);
        strategy4.ghostUpdate(map, pacman, ghost);

        pacman.setX(4);
        pacman.setY(5);
        ghost.setX(4);
        ghost.setY(2);
        strategy4.ghostUpdate(map, pacman, ghost);

        assertNotNull("Direction should not be null", ghost.getDirection());
        assertNotEquals("Direction should not be random", "null", ghost.getDirection());

        pacman.setX(1);
        pacman.setY(1);
        ghost.setX(18);
        ghost.setY(18);
        strategy4.ghostUpdate(map, pacman, ghost);

        pacman.setX(4);
        pacman.setY(4);
        ghost.setX(4);
        ghost.setY(4);
//        path.get(1)java.awt.Point[x=10,y=2]

        strategy4.ghostUpdate(map, pacman, ghost);

        pacman.setX(2);
        pacman.setY(4);
        ghost.setX(2);
        ghost.setY(18);
//        path.get(1)java.awt.Point[x=10,y=2]

        strategy4.ghostUpdate(map, pacman, ghost);

        pacman.setX(2);
        pacman.setY(11);
        ghost.setX(2);
        ghost.setY(4);
//        path.get(1)java.awt.Point[x=10,y=2]

        strategy4.ghostUpdate(map, pacman, ghost);

        pacman.setX(12);
        pacman.setY(1);
        ghost.setX(15);
        ghost.setY(1);
//        path.get(1)java.awt.Point[x=10,y=2]

        strategy4.ghostUpdate(map, pacman, ghost);

        pacman.setX(15);
        pacman.setY(1);
        ghost.setX(11);
        ghost.setY(1);
//        path.get(1)java.awt.Point[x=10,y=2]

        strategy4.ghostUpdate(map, pacman, ghost);
    }
}