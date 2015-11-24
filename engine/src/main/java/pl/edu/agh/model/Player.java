package pl.edu.agh.model;

import pl.edu.agh.util.EnumIdOutOfBoundsException;

public class Player {
    private int id;
    private int x;
    private int y;
    private int timeOfDeath;
    private Direction direction;

    public Player(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

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
        S;

        public static Direction parse(int n) throws EnumIdOutOfBoundsException {
            switch (n) {
                case 0:
                    return N;
                case 1:
                    return E;
                case 2:
                    return S;
                case 3:
                    return W;
                default:
                    throw new EnumIdOutOfBoundsException("Selected direction id doesn't make sense.");
            }
        }
    }
}
