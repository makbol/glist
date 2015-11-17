package pl.edu.agh.model;

import java.security.Timestamp;

/**
 * Created by mkostrzewa on 2015-11-17.
 */
public class Player {
    private int x;
    private int y;
    private Timestamp timeOfDeath;
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

    public Timestamp getTimeOfDeath() {
        return timeOfDeath;
    }

    public void setTimeOfDeath(Timestamp timeOfDeath) {
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
        S;
    }
}
