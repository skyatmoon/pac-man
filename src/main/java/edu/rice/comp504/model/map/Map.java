package edu.rice.comp504.model.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    private int[][] map = {
            {9, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9},
            {9, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9},
            {9, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 9},
            {9, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 9, 9, 9, 9, 1, 0, 1, 9, 9, 9, 1, 0, 0, 0, 0, 0, 2, 0, 1, 9},
            {9, 1, 0, 2, 0, 0, 1, 0, 1, 0, 1, 9, 9, 9, 9, 1, 0, 1, 9, 9, 9, 1, 0, 1, 1, 1, 1, 0, 1, 1, 9},
            {9, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 9},
            {9, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 9},
            {9, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 9},
            {9, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9},
            {9, 1, 0, 7, 7, 7, 7, 7, 7, 0, 7, 8, 8, 8, 8, 7, 0, 7, 7, 7, 7, 7, 0, 7, 0, 1, 0, 7, 0, 1, 9},
            {9, 1, 0, 7, 9, 9, 9, 9, 7, 0, 7, 9, 9, 9, 9, 7, 0, 7, 0, 0, 0, 0, 0, 7, 0, 1, 0, 7, 0, 1, 9},
            {9, 1, 0, 7, 9, 9, 9, 9, 7, 0, 7, 9, 9, 9, 9, 7, 0, 7, 0, 1, 1, 1, 0, 7, 0, 1, 0, 7, 0, 1, 9},
            {9, 1, 0, 7, 9, 9, 9, 9, 7, 0, 7, 9, 9, 9, 9, 7, 0, 7, 0, 1, 1, 1, 0, 7, 0, 0, 0, 7, 0, 1, 9},
            {1, 1, 0, 7, 9, 9, 9, 9, 7, 0, 7, 9, 9, 9, 9, 7, 0, 7, 0, 0, 0, 0, 0, 7, 7, 0, 7, 7, 0, 1, 1},
            {9, 9, 0, 7, 7, 7, 7, 7, 7, 0, 7, 7, 7, 7, 7, 7, 0, 7, 7, 7, 7, 7, 0, 0, 0, 7, 0, 0, 0, 9, 9},
            {1, 1, 0, 7, 0, 0, 0, 0, 7, 0, 7, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 7, 7, 0, 7, 7, 0, 1, 1},
            {9, 1, 0, 7, 0, 1, 1, 0, 7, 0, 7, 0, 1, 1, 1, 1, 0, 7, 0, 1, 1, 1, 0, 7, 0, 0, 0, 7, 0, 1, 9},
            {9, 1, 0, 7, 0, 1, 1, 0, 7, 0, 7, 0, 1, 0, 0, 0, 0, 7, 0, 1, 1, 1, 0, 7, 0, 1, 0, 7, 0, 1, 9},
            {9, 1, 0, 7, 0, 1, 1, 0, 7, 0, 7, 0, 1, 0, 1, 1, 0, 7, 0, 0, 0, 0, 0, 7, 0, 1, 0, 7, 0, 1, 9},
            {9, 1, 0, 7, 0, 1, 1, 0, 7, 0, 7, 0, 1, 0, 0, 0, 0, 7, 7, 7, 7, 7, 0, 7, 0, 1, 0, 7, 0, 1, 9},
            {9, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 9},
            {9, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 9},
            {9, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 9},
            {9, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 9, 9, 9, 9, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 9},
            {9, 1, 0, 2, 1, 0, 1, 0, 1, 0, 1, 9, 9, 9, 9, 1, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 2, 0, 1, 9},
            {9, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 9},
            {9, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 9},
            {9, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 9}
    };

    public int score = 0;

    /**
     * Constructor.
     */
    public Map() {
//        System.out.println("Map created");
//        int pointcount = 0;
        replaceZeroPointsWithMinusOne(map, 116);
        // we have total 356 points can be used, 240 is the default so 356-116=240
//        for (int[] row : map) {
//            for (int i : row) {
//                if (i == 0) {
//                    pointcount++;
//                }
//            }
//        }
//        System.out.println("pointcount: " + pointcount);
    }

    /**
     * Get the map.
     * @return map
     */
    public int[][] getMap() {
        return map;
    }

    /**
     * Move the pacman.
     * @return true if the pacman can eat ghost.
     */
    public boolean addScore(int x, int y) {
        if (map[y][x] == 0) {
            map[y][x] = -1;
            score += 10;
        } else if (map[y][x] == 2) {
            map[y][x] = -1;
            score += 50;
            return true;
        } else if (map[y][x] == 3) {
            map[y][x] = -1;
            score += 100;
            generateFruit();
        }
        return false;
    }

    /**
     * Move the pacman.
     */
    public int[][] setMap(int[][] map) {
        this.map = map;
        return this.map;
    }

    /**
     * Randomly generate a fruit(3) in the map.
     */
    public void generateFruit() {
        int x = (int) (Math.random() * 28);
        int y = (int) (Math.random() * 28);
        if (map[y][x] == -1 || map[y][x] == 0) {
            map[y][x] = 3;
        } else {
            generateFruit();
        }
    }

    /**
     * Randomly generate count(default 240, total possible 356) points in the map.
     */
    private static void replaceZeroPointsWithMinusOne(int[][] map, int n) {
        List<int[]> zeroPoints = new ArrayList<>();
        Random random = new Random();

        // Collect all the coordinates of '0' points
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == 0) {
                    zeroPoints.add(new int[]{i, j});
                }
            }
        }

        // Randomly choose n points to be replaced
        for (int i = 0; i < n; i++) {
            int randomIndex = random.nextInt(zeroPoints.size());
            int[] point = zeroPoints.remove(randomIndex);
            map[point[0]][point[1]] = -1;
        }
    }


    /**
     * Get the score.
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Set the score.
     * @param score the score numb.
     */
    public void setScore(int score) {
        this.score = score;
    }

}
