package pl.edu.agh.core;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Klasa gracza na serwerze.
 */
public class User implements Serializable {

    /** Id uzytkownika */
    private final UUID userId = UUID.randomUUID();

    /** Login uzytkownika */
    private final String username;

    /** Data wywloania ostatniej komendy */
    private Date lastCommandDate;

    public String getUsername() {
        return username;
    }

    public User(String username) {
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
}
