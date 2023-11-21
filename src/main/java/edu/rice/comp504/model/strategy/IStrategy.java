package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.map.Map;
import edu.rice.comp504.model.objects.Ghost;
import edu.rice.comp504.model.objects.Pacman;

public interface IStrategy {
    /**
     * Update the pacman.
     */
    public void ghostUpdate(Map map, Pacman pacman, Ghost ghost);
}
