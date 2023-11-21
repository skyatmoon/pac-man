package edu.rice.comp504.model.response;

import edu.rice.comp504.model.map.Map;
import edu.rice.comp504.model.objects.Ghost;
import edu.rice.comp504.model.objects.Pacman;

public class ResponseMap {
    Map map;
    /**
     * Constructor.
     */
    public ResponseMap makeResponse(Map map) {
        this.map = map;
        return this;
    }
}
