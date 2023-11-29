package edu.rice.comp504.util;

import java.awt.Point;
import java.util.*;
/*
 * PathFinder class is used to find the shortest path from start point to end point.
 * The algorithm used is BFS.
 */

public class PathFinder {
    private static final int[][] DIRECTIONS = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };

    static class PathPoint extends Point {
        PathPoint parent;

        PathPoint(int x, int y, PathPoint parent) {
            super(x, y);
            this.parent = parent;
        }
    }

    /*
     * Check if the cell is within the grid, not visited, and not a wall ("1" or "7").
     */
    public static boolean isValid(int[][] map, boolean[][] visited, int x, int y) {
        // Check if the cell is within the grid, not visited, and not a wall ("1" or "7").
        return x >= 0 && x < map.length && y >= 0 && y < map[0].length && map[x][y] != 1 && map[x][y] != 7 && !visited[x][y];
    }

    /*
     * Construct the path from end point to start point.
     */
    private static ArrayList<Point> constructPath(PathPoint end) {
        ArrayList<Point> path = new ArrayList<>();
        PathPoint current = end;
        while (current != null) {
            path.add(new Point(current.x, current.y)); // Convert PathPoint to Point
            current = current.parent;
        }
        Collections.reverse(path); // The path is constructed from end to start, so reverse it.
        return path;
    }

    /**
     * Find the shortest path from start point to end point.
     *
     * @param map The representation of the space in which to search the path.
     * @param start The starting point of the path.
     * @param end The end point of the path.
     * @return A list of Points representing the shortest path, or an empty list if no path is found.
     */
    public static ArrayList<Point> searchPath(int[][] map, Point start, Point end) {
        if (map[start.x][start.y] == 1 || map[start.x][start.y] == 7 ||
                map[end.x][end.y] == 1 || map[end.x][end.y] == 7) {
            return new ArrayList<>();
        }

        boolean[][] visited = new boolean[map.length][map[0].length];
        Queue<PathPoint> queue = new LinkedList<>();

        queue.add(new PathPoint(start.x, start.y, null));
        visited[start.x][start.y] = true;

        while (!queue.isEmpty()) {
            PathPoint point = queue.remove();

            if (point.x == end.x && point.y == end.y) {
                return constructPath(point);
            }

            for (int[] direction : DIRECTIONS) {
                int newX = point.x + direction[0];
                int newY = point.y + direction[1];

                if (isValid(map, visited, newX, newY)) {
                    PathPoint newPoint = new PathPoint(newX, newY, point);
                    queue.add(newPoint);
                    visited[newX][newY] = true;
                }
            }
        }
        return new ArrayList<>();
    }
//    /*
//     * Test the PathFinder class.
//     */
//    public static void main(String[] args) {
//        int[][] map = {
//                {9, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9},
//                {9, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9},
//                {9, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 9},
//                {9, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 9, 9, 9, 9, 1, 0, 1, 9, 9, 9, 1, 0, 0, 0, 0, 0, 2, 0, 1, 9},
//                {9, 1, 0, 2, 0, 0, 1, 0, 1, 0, 1, 9, 9, 9, 9, 1, 0, 1, 9, 9, 9, 1, 0, 1, 1, 1, 1, 0, 1, 1, 9},
//                {9, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 9},
//                {9, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 9},
//                {9, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 9},
//                {9, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9},
//                {9, 1, 0, 7, 7, 7, 7, 7, 7, 0, 7, 8, 8, 8, 8, 7, 0, 7, 7, 7, 7, 7, 0, 7, 0, 1, 0, 7, 0, 1, 9},
//                {9, 1, 0, 7, 9, 9, 9, 9, 7, 0, 7, 9, 9, 9, 9, 7, 0, 7, 0, 0, 0, 0, 0, 7, 0, 1, 0, 7, 0, 1, 9},
//                {9, 1, 0, 7, 9, 9, 9, 9, 7, 0, 7, 9, 9, 9, 9, 7, 0, 7, 0, 1, 1, 1, 0, 7, 0, 1, 0, 7, 0, 1, 9},
//                {9, 1, 0, 7, 9, 9, 9, 9, 7, 0, 7, 9, 9, 9, 9, 7, 0, 7, 0, 1, 1, 1, 0, 7, 0, 0, 0, 7, 0, 1, 9},
//                {1, 1, 0, 7, 9, 9, 9, 9, 7, 0, 7, 9, 9, 9, 9, 7, 0, 7, 0, 0, 0, 0, 0, 7, 7, 0, 7, 7, 0, 1, 1},
//                {9, 9, 0, 7, 7, 7, 7, 7, 7, 0, 7, 7, 7, 7, 7, 7, 0, 7, 7, 7, 7, 7, 0, 0, 0, 7, 0, 0, 0, 9, 9},
//                {1, 1, 0, 7, 0, 0, 0, 0, 7, 0, 7, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 7, 7, 0, 7, 7, 0, 1, 1},
//                {9, 1, 0, 7, 0, 1, 1, 0, 7, 0, 7, 0, 1, 1, 1, 1, 0, 7, 0, 1, 1, 1, 0, 7, 0, 0, 0, 7, 0, 1, 9},
//                {9, 1, 0, 7, 0, 1, 1, 0, 7, 0, 7, 0, 1, 0, 0, 0, 0, 7, 0, 1, 1, 1, 0, 7, 0, 1, 0, 7, 0, 1, 9},
//                {9, 1, 0, 7, 0, 1, 1, 0, 7, 0, 7, 0, 1, 0, 1, 1, 0, 7, 0, 0, 0, 0, 0, 7, 0, 1, 0, 7, 0, 1, 9},
//                {9, 1, 0, 7, 0, 1, 1, 0, 7, 0, 7, 0, 1, 0, 0, 0, 0, 7, 7, 7, 7, 7, 0, 7, 0, 1, 0, 7, 0, 1, 9},
//                {9, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9},
//                {9, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 9},
//                {9, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 9},
//                {9, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 9, 9, 9, 9, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 9},
//                {9, 1, 0, 2, 1, 0, 1, 0, 1, 0, 1, 9, 9, 9, 9, 1, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 2, 0, 1, 9},
//                {9, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 9},
//                {9, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9},
//                {9, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9}
//        };
//        System.out.println(map[11][10]);
//        // x y need to be reversed for use in the map,
//        Point start = new Point(10, 11);
//        Point end = new Point(10, 20);
//        ArrayList<Point> path = searchPath(map, start, end);
//
//        for (Point p : path) {
//            System.out.println("debug");
//            System.out.println("(" + p.x + ", " + p.y + ")");
//        }
//        System.out.println("end");
//    }
}