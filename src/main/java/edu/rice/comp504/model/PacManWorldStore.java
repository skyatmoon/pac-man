package edu.rice.comp504.model;


import edu.rice.comp504.model.map.Map;
import edu.rice.comp504.model.objects.GamObjectFac;
import edu.rice.comp504.model.objects.Ghost;
import edu.rice.comp504.model.objects.Pacman;
import edu.rice.comp504.model.response.ResponseGhost;
import edu.rice.comp504.model.response.ResponseGhosts;
import edu.rice.comp504.model.response.ResponseMap;
import edu.rice.comp504.model.response.ResponsePacMan;
import edu.rice.comp504.model.strategy.StrategyFac;

import java.util.ArrayList;

/**
 * This adapter interfaces with the view (paint objects) and the controller.
 */
public class PacManWorldStore {
    private static PacManWorldStore singleton;
    private static GamObjectFac gamObjectFac = new GamObjectFac();
    private static StrategyFac strategyFac = new StrategyFac();
    private static Map map;
    private static Pacman pacman;
    private static ArrayList<Ghost> ghost = new ArrayList<>();
    private static String[] colors = {"red", "Aqua", "magenta", "orange", "pink", "green"};
    private static int level = 1;
    private static int points = 240;
    private static int ghosts = 4;
    private static int life = 3;

    /**
     * Constructor.
     */
    public PacManWorldStore() {
        init();
    }

    /**
     * Singleton constructor.
     * @return the singleton object
     */
    public static PacManWorldStore makeDispatchAdapter() {
        if (singleton == null) {
            singleton = new PacManWorldStore();
        }
        return singleton;
    }

    /**
     * Initialize the game.
     */
    public static void init() {
        map = new Map();
        map.setPoints(points);
        pacman = gamObjectFac.makePacman(10, 20, "null");
        pacman.setLife(life);
        ghost.clear();
        for (int i = 0; i < ghosts; i++) {
            ghost.add(gamObjectFac.makeGhost(11 + i % 4, 10 + i / 4, "null", colors[i], strategyFac.makeStrategy("SimpleGhostStrategy")));
        }
        // set the velocity as difficulty.
        for (Ghost ghost : ghost) {
            ghost.setLevel(level);
        }
    }

    /**
     * Operate the pacman.
     * @param operation the operation
     * @return the response
     */
    public static ResponsePacMan updatePacMan(String operation) {
        if (pacman.pacmanCollisionDetection(operation, pacman, map, ghost)) {
            pacman.setDirection(operation);
            pacman.move();
        } else {
            if (!pacman.getDirection().equals("null")) {
                pacman.setDirection(operation);
            }
        }
        ResponsePacMan responsePacMan = new ResponsePacMan();
        return responsePacMan.makeResponse(pacman);
    }

    /**
     * Update the map.
     * @return the response
     */
    public ResponseMap update() {
        if (map.addScore(pacman.getX(), pacman.getY())) {
            for (Ghost ghost : ghost) {
                ghost.setCanEaten(true);
                ghost.setVelocity(1);
                ghost.setPrevStrategy(ghost.getStrategy());
            }
            pacman.setEatenTime(900); // 15 seconds for Pac man eat ghost.
        }
        map.checkWin();
        ResponseMap responseMap = new ResponseMap();
        return responseMap.makeResponse(map);
    }

    /**
     * Update the ghosts.
     * @return the response
     */
    public ResponseGhosts updateGhosts() {
        ResponseGhosts responseGhosts = new ResponseGhosts();
        ArrayList<ResponseGhost> responseGhostsArray = new ArrayList<>();
        for (Ghost ghost : ghost) {
            ghost.update(ghost, map, pacman);
            ResponseGhost responseGhost = new ResponseGhost();
            responseGhostsArray.add(responseGhost.makeResponse(ghost.getX(), ghost.getY(), ghost.getDirection(), ghost.getColor(), ghost.getCanEaten(), ghost.getBeEaten()));
        }
        return responseGhosts.makeResponse(responseGhostsArray);
    }

    /**
     * Get the level.
     * @return the level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Set User Settings.
     */
    public void setSettings(int points, int ghosts, int life) {
        this.points = points;
        this.ghosts = ghosts;
        this.life = life;
    }
}
