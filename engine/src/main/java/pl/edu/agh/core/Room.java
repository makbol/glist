package pl.edu.agh.core;

import pl.edu.agh.core.BaseCommand;
import pl.edu.agh.core.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Klasa pokoju na serwerze.
 */
public class Room {

    /** Numer pokoju */
    private static long roomCounter = 0;

    /** Licznik pokoi */
    private final long roomNo = ++roomCounter;

    /** Uczestnicy w pokoju */
    private List<User> users = new ArrayList<User>();

    /**
     * Wywolanie komendy w danym pokoju. Logika zalezna od komendy.
     * @param command komenda do wylowania
     * @param user uzytkownik wolajacy komende (null jesli komenda dla wszystkich userow)
     * @return zwraca komende po wywolaniu
     */
    public <T extends BaseCommand> BaseCommand executeCommand(T command, User user) {
        command.execute(this, user);
        if(user != null) {
            user.setLastCommandDate(new Date());
        }
        return command;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public long getRoomNo() {
        return roomNo;
    }
}
