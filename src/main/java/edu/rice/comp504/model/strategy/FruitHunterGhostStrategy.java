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

public class FruitHunterGhostStrategy implements IStrategy {
    public String name = "FruitHunterGhostStrategy";

    /**
     * Update the ghost move direction.
     */
    @Override
    public void ghostUpdate(Map map, Pacman pacman, Ghost ghost) {
        // if the distance between ghost and fruit is larger than 5, ghost will want to catch fruit
        boolean huntFruit = abs(ghost.getX() - map.getFruitX()) + abs(ghost.getY() - map.getFruitY()) >= 5;
        if (huntFruit) {
            String direction = "";
            PathFinder pathFinder = new PathFinder();

            ArrayList<Point> path = pathFinder.searchPath(map.getMap(), new Point(ghost.getY(), ghost.getX()), new Point(map.getFruitY(), map.getFruitX()));
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
        } else {
            while (true) {
                ghost.setDirection(new RandUtil().randomDirection());
                if (Objects.equals(ghost.getDirection(), "down") && map.getMap()[ghost.getY() + 1][ghost.getX()] != 1 && map.getMap()[ghost.getY() + 1][ghost.getX()] != 7) {
                    break;
                } else if (Objects.equals(ghost.getDirection(), "up") && map.getMap()[ghost.getY() - 1][ghost.getX()] != 1 && map.getMap()[ghost.getY() - 1][ghost.getX()] != 7) {
                    break;
                } else if (Objects.equals(ghost.getDirection(), "right") && map.getMap()[ghost.getY()][ghost.getX() + 1] != 1 && map.getMap()[ghost.getY()][ghost.getX() + 1] != 7) {
                    break;
                } else if (Objects.equals(ghost.getDirection(), "left") && map.getMap()[ghost.getY()][ghost.getX() - 1] != 1 && map.getMap()[ghost.getY()][ghost.getX() - 1] != 7) {
                    break;
                }
            }
        }
    }
}
