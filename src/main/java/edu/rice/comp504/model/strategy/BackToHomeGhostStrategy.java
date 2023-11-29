package edu.rice.comp504.model.strategy;

import edu.rice.comp504.model.map.Map;
import edu.rice.comp504.model.objects.Ghost;
import edu.rice.comp504.model.objects.Pacman;
import edu.rice.comp504.util.PathFinder;
import edu.rice.comp504.util.RandUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Math.abs;

public class BackToHomeGhostStrategy implements IStrategy {
    public String name = "BackToHomeGhostStrategy";

    /**
     * Update the ghost move direction.
     */

    @Override
    public void ghostUpdate(Map map, Pacman pacman, Ghost ghost) {
        ghost.setVelocity(7);
        String direction = "";
        PathFinder pathFinder = new PathFinder();

        ArrayList<Point> path = pathFinder.searchPath(map.getMap(), new Point(ghost.getY(), ghost.getX()), new Point(11, 11));
        ghost.path = path;
        if (path.size() > 1 && path.get(1) != null) {
            Point nextPoint = path.get(1);
            int x = nextPoint.y - ghost.getX();
            int y = nextPoint.x - ghost.getY();
            if (x == 1) {
                direction = "right";
            } else if (x == -1) {
                direction = "left";
            } else if (y == 1) {
                direction = "down";
            } else if (y == -1) {
                direction = "up";
            }
        } else {
            direction = new RandUtil().randomDirection();
            ghost.path.clear();
        }
        ghost.setDirection(direction);
    }
}
