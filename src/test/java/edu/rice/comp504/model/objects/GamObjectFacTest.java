package edu.rice.comp504.model.objects;

import edu.rice.comp504.model.strategy.BackToHomeGhostStrategy;
import edu.rice.comp504.model.strategy.IStrategy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GamObjectFacTest {
    private GamObjectFac factory;
    private IStrategy strategy;

    @Before
    public void setUp() {
        factory = new GamObjectFac();
        strategy = new BackToHomeGhostStrategy(); // 替换为具体的策略实现
    }

    @Test
    public void testMakePacman() {
        Pacman pacman = factory.makePacman(10, 10, "right");
        assertNotNull("Pacman should not be null", pacman);
        assertEquals("Pacman X coordinate should be 10", 10, pacman.getX());
        assertEquals("Pacman Y coordinate should be 10", 10, pacman.getY());
        assertEquals("Pacman direction should be 'right'", "right", pacman.getDirection());
    }

    @Test
    public void testMakeGhost() {
        Ghost ghost = factory.makeGhost(15, 15, "left", "blue", strategy);
        assertNotNull("Ghost should not be null", ghost);
        assertEquals("Ghost X coordinate should be 15", 15, ghost.getX());
        assertEquals("Ghost Y coordinate should be 15", 15, ghost.getY());
        assertEquals("Ghost direction should be 'left'", "left", ghost.getDirection());
        assertEquals("Ghost color should be 'blue'", "blue", ghost.getColor());
        assertSame("Ghost strategy should be the same as provided", strategy, ghost.getStrategy());
    }
}
