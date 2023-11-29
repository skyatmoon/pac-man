package edu.rice.comp504.model;

import edu.rice.comp504.model.map.Map;
import edu.rice.comp504.model.objects.Ghost;
import edu.rice.comp504.model.objects.Pacman;
import edu.rice.comp504.model.response.ResponseGhosts;
import edu.rice.comp504.model.response.ResponseMap;
import edu.rice.comp504.model.response.ResponsePacMan;
import edu.rice.comp504.model.strategy.BackToHomeGhostStrategy;
import edu.rice.comp504.model.strategy.IStrategy;
import edu.rice.comp504.model.strategy.StrategyFac;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PacManWorldStoreTest {
    private Pacman pacman;
    private Map map;
    private ArrayList<Ghost> ghost;

    private Ghost ghost1;

    private StrategyFac strategyFac = new StrategyFac();

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
        strategyFac.makeStrategy("FruitHunterGhostStrategy");
        strategyFac.makeStrategy("BackToHomeGhostStrategy");
        strategyFac.makeStrategy("SimpleGhostStrategy");
        strategyFac.makeStrategy("123Strategy");

    }

    @Test
    public void testSingletonInstance() {
        PacManWorldStore instance1 = PacManWorldStore.makeDispatchAdapter();
        PacManWorldStore instance2 = PacManWorldStore.makeDispatchAdapter();
        assertSame("Singleton instances should be the same", instance1, instance2);
    }

    @Test
    public void testUpdatePacMan() {
        PacManWorldStore.makeDispatchAdapter();

        String operation = "up";
        pacman.setX(7);
        pacman.setY(7);
        ResponsePacMan response = PacManWorldStore.updatePacMan(operation);



        assertNotNull("Response should not be null", response);
    }

    @Test
    public void testUpdate() {
        PacManWorldStore.makeDispatchAdapter();


        PacManWorldStore instance1 = PacManWorldStore.makeDispatchAdapter();
        instance1.setPacman(27,3);

        ResponseMap response = PacManWorldStore.update();

        assertNotNull("Response should not be null", response);

        instance1.setPacmanDirection("up");
        ResponsePacMan response1 = PacManWorldStore.updatePacMan("up");
    }

    @Test
    public void testUpdateGhosts() {
        PacManWorldStore.makeDispatchAdapter();
        ResponseGhosts response;
        response = PacManWorldStore.updateGhosts();

        assertNotNull("Response should not be null", response);

    }

    @Test
    public void testSetSettings() {
        PacManWorldStore.makeDispatchAdapter();
        int points = 300;
        int ghosts = 5;
        int life = 4;

//        PacManWorldStore.setSettings(points, ghosts, life);

        PacManWorldStore adapter = new PacManWorldStore();
        adapter.setSettings(points, ghosts, life);

    }


}
