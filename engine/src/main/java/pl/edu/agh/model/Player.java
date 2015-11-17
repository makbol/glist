package pl.edu.agh.model;

public class Player {
    private int x;
    private int y;
    private int timeOfDeath;
    private Direction direction;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getTimeOfDeath() {
        return timeOfDeath;
    }

    public void setTimeOfDeath(int timeOfDeath) {
        this.timeOfDeath = timeOfDeath;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    enum Direction {
        N,
        W,
        E,
        S
    }
}
