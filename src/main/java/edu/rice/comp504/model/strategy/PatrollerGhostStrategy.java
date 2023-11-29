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

public class PatrollerGhostStrategy implements IStrategy {
    public String name = "SimpleGhostStrategy";

    private boolean catchPacman = false;

    private final Rectangle patrolArea = new Rectangle(16, 16, 11, 11);

    /**
     * Update the ghost move direction.
     */
    @Override
    public void ghostUpdate(Map map, Pacman pacman, Ghost ghost) {
        if (canSee(map.getMap(), ghost.getY(), ghost.getX(), pacman.getY(), pacman.getX())) {
            catchPacman = true;
        } else if (abs(ghost.getX() - pacman.getX()) + abs(ghost.getY() - pacman.getY()) >= 10) {
            catchPacman = false;
        }
        if (catchPacman) {
            String direction = "";
            PathFinder pathFinder = new PathFinder();

            ArrayList<Point> path = pathFinder.searchPath(map.getMap(), new Point(ghost.getY(), ghost.getX()), new Point(pacman.getY(), pacman.getX()));
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
                catchPacman = false;
                ghost.path.clear();
            }
            ghost.setDirection(direction);
        } else {
            PathFinder pathFinder = new PathFinder();
            // go to the lower right corner and start patrolling
            if (!patrolArea.contains(ghost.getX(), ghost.getY())) {
                ArrayList<Point> path = pathFinder.searchPath(map.getMap(), new Point(ghost.getY(), ghost.getX()), new Point(27, 27));
                ghost.path = path;
            }
            if (Objects.equals(ghost.getDirection(), "down") && map.getMap()[ghost.getY() + 1][ghost.getX()] != 1 && map.getMap()[ghost.getY() + 1][ghost.getX()] != 7) {
                return;
            } else if (Objects.equals(ghost.getDirection(), "up") && map.getMap()[ghost.getY() - 1][ghost.getX()] != 1 && map.getMap()[ghost.getY() - 1][ghost.getX()] != 7) {
                return;
            } else if (Objects.equals(ghost.getDirection(), "right") && map.getMap()[ghost.getY()][ghost.getX() + 1] != 1 && map.getMap()[ghost.getY()][ghost.getX() + 1] != 7) {
                return;
            } else if (Objects.equals(ghost.getDirection(), "left") && map.getMap()[ghost.getY()][ghost.getX() - 1] != 1 && map.getMap()[ghost.getY()][ghost.getX() - 1] != 7) {
                return;
            } else {
                ghost.setDirection(new RandUtil().randomDirection());
            }
        }
    }

    /**
     * Ghost can see pacman or not.
     */
    public static boolean canSee(int[][] map, int x1, int y1, int x2, int y2) {
        // Check if A and B are on the same row or column
        if (x1 == x2) {
            // Check for walls in the same row
            for (int i = Math.min(y1, y2) + 1; i < Math.max(y1, y2); i++) {
                if (map[x1][i] == 1 || map[x1][i] == 7) {
                    return false;
                }
            }
        } else if (y1 == y2) {
            // Check for walls in the same column
            for (int i = Math.min(x1, x2) + 1; i < Math.max(x1, x2); i++) {
                if (map[i][y1] == 1 || map[i][y1] == 7) {
                    return false;
                }
            }
        } else {
            // Not on the same row or column
            return false;
        }
        // No walls found, B can be seen by A
        return true;
    }
}
