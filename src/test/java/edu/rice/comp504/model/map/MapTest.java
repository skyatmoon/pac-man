package edu.rice.comp504.model.map;

import static org.junit.Assert.*;

import edu.rice.comp504.model.map.Map;
import edu.rice.comp504.model.objects.Ghost;
import edu.rice.comp504.model.objects.Pacman;
import edu.rice.comp504.model.strategy.BackToHomeGhostStrategy;
import edu.rice.comp504.model.strategy.IStrategy;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class MapTest {
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
    public void addScoreTest() {

        int[][] map1 = {
                {3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9},
                {9, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9},
                {9, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 9},
                {9, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 9, 9, 9, 9, 1, 0, 1, 9, 9, 9, 1, 0, 0, 0, 0, 0, 2, 0, 1, 9},
                {9, 1, 0, 2, 0, 0, 1, 0, 1, 0, 1, 9, 9, 9, 9, 1, 0, 1, 9, 9, 9, 1, 0, 1, 1, 1, 1, 0, 1, 1, 9},
                {9, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 9},
                {9, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 9},
                {9, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 9},
                {9, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9},
                {9, 1, 0, 7, 7, 7, 7, 7, 7, 0, 7, 8, 8, 8, 8, 7, 0, 7, 7, 7, 7, 7, 0, 7, 0, 1, 0, 7, 0, 1, 9},
                {9, 1, 0, 7, 9, 9, 9, 9, 7, 0, 7, 9, 9, 9, 9, 7, 0, 7, 0, 0, 0, 0, 0, 7, 0, 1, 0, 7, 0, 1, 9},
                {9, 1, 0, 7, 9, 9, 9, 9, 7, 0, 7, 9, 9, 9, 9, 7, 0, 7, 0, 1, 1, 1, 0, 7, 0, 1, 0, 7, 0, 1, 9},
                {9, 1, 0, 7, 9, 9, 9, 9, 7, 0, 7, 9, 9, 9, 9, 7, 0, 7, 0, 1, 1, 1, 0, 7, 0, 0, 0, 7, 0, 1, 9},
                {1, 1, 0, 7, 9, 9, 9, 9, 7, 0, 7, 9, 9, 9, 9, 7, 0, 7, 0, 0, 0, 0, 0, 7, 7, 0, 7, 7, 0, 1, 1},
                {9, 9, 0, 7, 7, 7, 7, 7, 7, 0, 7, 7, 7, 7, 7, 7, 0, 7, 7, 7, 7, 7, 0, 0, 0, 7, 0, 0, 0, 9, 9},
                {1, 1, 0, 7, 0, 0, 0, 0, 7, 0, 7, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 7, 7, 0, 7, 7, 0, 1, 1},
                {9, 1, 0, 7, 0, 1, 1, 0, 7, 0, 7, 0, 1, 1, 1, 1, 0, 7, 0, 1, 1, 1, 0, 7, 0, 0, 0, 7, 0, 1, 9},
                {9, 1, 0, 7, 0, 1, 1, 0, 7, 0, 7, 0, 1, 0, 0, 0, 0, 7, 0, 1, 1, 1, 0, 7, 0, 1, 0, 7, 0, 1, 9},
                {9, 1, 0, 7, 0, 1, 1, 0, 7, 0, 7, 0, 1, 0, 1, 1, 0, 7, 0, 0, 0, 0, 0, 7, 0, 1, 0, 7, 0, 1, 9},
                {9, 1, 0, 7, 0, 1, 1, 0, 7, 0, 7, 0, 1, 0, 0, 0, 0, 7, 7, 7, 7, 7, 0, 7, 0, 1, 0, 7, 0, 1, 9},
                {9, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9},
                {9, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 9},
                {9, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 9},
                {9, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 9, 9, 9, 9, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 9},
                {9, 1, 0, 2, 1, 0, 1, 0, 1, 0, 1, 9, 9, 9, 9, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 1, 9},
                {9, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 9},
                {9, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9},
                {9, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9}
        };
        map.setMap(map1);
        boolean result = map.addScore(2,1);
        assertFalse("Expecting false as there is no special item at (2,1)", result);
        assertEquals("Expecting score to increase by 10", 10, map.getScore());

        result = map.addScore(27,3);
        assertTrue("Expecting true as there is a special item at (27,3)", result);
        assertEquals("Expecting score to increase by 50", 60, map.getScore());

        result = map.addScore(0,0);
        assertFalse("Expecting false as there is no special item at (0,0)", result);
        assertEquals("Expecting score to increase by 100", 160, map.getScore());

    }

    @Test
    public void checkWinTest() {
        int[][] map1 = {
                {9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9},
                {9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9},
                {9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9},
                {9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9, 9, 9, 9, -1, -1, -1, 9, 9, 9, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9},
                {9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9, 9, 9, 9, -1, -1, -1, 9, 9, 9, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9},
                {9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9},
                {9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9},
                {9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9},
                {9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9},
                {9, -1, -1, 7, 7, 7, 7, 7, 7, -1, 7, 8, 8, 8, 8, 7, -1, 7, 7, 7, 7, 7, -1, 7, -1, -1, -1, 7, -1, -1, 9},
                {9, -1, -1, 7, 9, 9, 9, 9, 7, -1, 7, 9, 9, 9, 9, 7, -1, 7, -1, -1, -1, -1, -1, 7, -1, -1, -1, 7, -1, -1, 9},
                {9, -1, -1, 7, 9, 9, 9, 9, 7, -1, 7, 9, 9, 9, 9, 7, -1, 7, -1, -1, -1, -1, -1, 7, -1, -1, -1, 7, -1, -1, 9},
                {9, -1, -1, 7, 9, 9, 9, 9, 7, -1, 7, 9, 9, 9, 9, 7, -1, 7, -1, -1, -1, -1, -1, 7, -1, -1, -1, 7, -1, -1, 9},
                {-1, -1, -1, 7, 9, 9, 9, 9, 7, -1, 7, 9, 9, 9, 9, 7, -1, 7, -1, -1, -1, -1, -1, 7, 7, -1, 7, 7, -1, -1, -1},
                {9, 9, -1, 7, 7, 7, 7, 7, 7, -1, 7, 7, 7, 7, 7, 7, -1, 7, 7, 7, 7, 7, -1, -1, -1, 7, -1, -1, -1, 9, 9},
                {-1, -1, -1, 7, -1, -1, -1, -1, 7, -1, 7, -1, -1, -1, -1, -1, -1, 7, -1, -1, -1, -1, -1, 7, 7, -1, 7, 7, -1, -1, -1},
                {9, -1, -1, 7, -1, -1, -1, -1, 7, -1, 7, -1, -1, -1, -1, -1, -1, 7, -1, -1, -1, -1, -1, 7, -1, -1, -1, 7, -1, -1, 9},
                {9, -1, -1, 7, -1, -1, -1, -1, 7, -1, 7, -1, -1, -1, -1, -1, -1, 7, -1, -1, -1, -1, -1, 7, -1, -1, -1, 7, -1, -1, 9},
                {9, -1, -1, 7, -1, -1, -1, -1, 7, -1, 7, -1, -1, -1, -1, -1, -1, 7, -1, -1, -1, -1, -1, 7, -1, -1, -1, 7, -1, -1, 9},
                {9, -1, -1, 7, -1, -1, -1, -1, 7, -1, 7, -1, -1, -1, -1, -1, -1, 7, 7, 7, 7, 7, -1, 7, -1, -1, -1, 7, -1, -1, 9},
                {9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9},
                {9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9},
                {9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9},
                {9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9, 9, 9, 9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9},
                {9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9, 9, 9, 9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9},
                {9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9},
                {9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9},
                {9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 9}
        };
        map.setMap(map1);
        map.checkWin();
        map.setScore(100);

    }


}