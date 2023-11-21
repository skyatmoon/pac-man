package edu.rice.comp504.model.objects;

public abstract class AObject {
    int x;
    int y;
    String direction = "up";

    /**
     * Constructor.
     */
    public AObject(int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    /**
     * Get the x coordinate.
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Get the y coordinate.
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Get the direction.
     * @return direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * set the x coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * set the y coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * set the direction.
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

}
