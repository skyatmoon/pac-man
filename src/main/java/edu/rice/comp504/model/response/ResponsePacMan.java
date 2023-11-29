package edu.rice.comp504.model.response;

import edu.rice.comp504.model.map.Map;
import edu.rice.comp504.model.objects.Ghost;
import edu.rice.comp504.model.objects.Pacman;

public class ResponsePacMan {
    Pacman pacman;

    /**
     * Constructor.
     */
    public ResponsePacMan makeResponse(Pacman pacman) {
        this.pacman = pacman;
        return this;
    }
}
