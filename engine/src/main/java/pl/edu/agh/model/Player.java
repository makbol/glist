package pl.edu.agh.model;

import com.google.gson.annotations.Expose;
import pl.edu.agh.util.EnumIdOutOfBoundsException;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Player implements Serializable {
    private static final double POINTS_PER_FRAME = 0.1666667;

    /**
     * Id uzytkownika
     */
    @Expose
    private final UUID userId = UUID.randomUUID();

    /**
     * Login uzytkownika
     */
    @Expose
    private final String username;

    /**
     * Kolor gracza
     */
    @Expose
    private String color = "red";

    /**
     * Wspolrzedna x gracza
     */
    @Expose
    private int x;

    /**
     * Wspolrzedna y gracza
     */
    @Expose
    private int y;

    /**
     * Numer ticka w którm gracz zginal
     * - dopoki gracz zyje, referencja jest nullem
     * - w momencie smierci jest ustawiana na obecny tick gry
     **/
    @Expose
    private Integer timeOfDeath;

    /**
     * Data wywloania ostatniej komendy
     */
    private Date lastCommandDate;

    /**
     * Liczba punktow zdobytych przez gracza
     **/
    @Expose
    private int score;

    /**
     * Kierunek w ktorym porusza sie gracz
     */
    @Expose
    private Direction direction;

    public Player(String username) {
        this.username = username;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int deathTime) {
        score += calculatePointForTheRound(deathTime);
    }

    private int calculatePointForTheRound(int deathTime) {
        return (int) (deathTime * POINTS_PER_FRAME);
    }

    public String getUsername() {
        return username;
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

    public Integer getTimeOfDeath() {
        return timeOfDeath;
    }

    public void setTimeOfDeath(Integer timeOfDeath) {
        this.timeOfDeath = timeOfDeath;
    }

    public Date getLastCommandDate() {
        return lastCommandDate;
    }

    public void setLastCommandDate(final Date lastCommandDate) {
        this.lastCommandDate = lastCommandDate;
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
