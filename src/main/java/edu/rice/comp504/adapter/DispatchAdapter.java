package edu.rice.comp504.adapter;


import edu.rice.comp504.model.PacManWorldStore;
import edu.rice.comp504.model.response.ResponseGhosts;
import edu.rice.comp504.model.response.ResponseMap;
import edu.rice.comp504.model.response.ResponsePacMan;

/**
 * This adapter interfaces with the view (paint objects) and the controller.
 */
public class DispatchAdapter {
    private static DispatchAdapter singleton;
    private static PacManWorldStore pacManWorldStore = new PacManWorldStore();

    /**
     * Constructor.
     */
    public DispatchAdapter() {

    }

    /**
     * Singleton constructor.
     * @return the singleton object
     */
    public static DispatchAdapter makeDispatchAdapter() {
        if (singleton == null) {
            singleton = new DispatchAdapter();
        }
        return singleton;
    }

    /**
     * Operate the pacman.
     * @param operation the operation
     * @return the response
     */
    public static ResponsePacMan updatePacMan(String operation) {
        return pacManWorldStore.updatePacMan(operation);
    }

    /**
     * Initialize the game.
     * @return the response
     */
    public static void init() {
        pacManWorldStore.init();
    }

    /**
     * Update the map.
     * @return the response
     */
    public ResponseMap update() {
        return pacManWorldStore.update();
    }

    /**
     * Update the ghosts.
     * @return the response
     */
    public ResponseGhosts updateGhosts() {
        return pacManWorldStore.updateGhosts();
    }

    public void setLevel(int level) {
        pacManWorldStore.setLevel(level);
    }
}
