package pl.edu.agh.core;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Klasa gracza na serwerze.
 */
public class Player implements Serializable {

    /** Id uzytkownika */
    private final UUID userId = UUID.randomUUID();

    /** Login uzytkownika */
    private final String username;

    /** Data wywloania ostatniej komendy */
    private Date lastCommandDate;

    /** Wspolrzedna x gracza */
    private int x;

    /** Wspolrzedna y gracza */
    private int y;

    /** Predkosc gracza wzdloz osi x */
    private int vx;

    /** Predkosc gracza wzdloz osi y */
    private int vy;

    /** Kolor gracza */
    private String color = "red";

    /** Czy uzytkownik jeszcze moze sie poruszac */
    private boolean alive;

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public String getUsername() {
        return username;
    }

    public Player(String username) {
        this.username = username;
    }

    public UUID getUserId() {
        return userId;
    }

    public Date getLastCommandDate() {
        return lastCommandDate;
    }

    public void setLastCommandDate(final Date lastCommandDate) {
        this.lastCommandDate = lastCommandDate;
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

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
