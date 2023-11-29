package edu.rice.comp504.model.response;

public class ResponseGhost {
    private int x;
    private int y;
    private String direction;
    private String color;
    private boolean canEaten;
    private boolean beEaten;

    /**
     * Constructor.
     */
    public ResponseGhost makeResponse(int x, int y, String direction, String color, boolean canEaten, boolean beEaten) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.color = color;
        this.canEaten = canEaten;
        this.beEaten = beEaten;
        return this;
    }
}
