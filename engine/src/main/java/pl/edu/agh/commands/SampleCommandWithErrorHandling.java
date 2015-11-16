package pl.edu.agh.commands;

import pl.edu.agh.core.BaseCommand;
import pl.edu.agh.core.Room;
import pl.edu.agh.core.User;

/**
 * W tej komendzie widzimy co zrobic jesli w wywowaniu komendy nastepuje blad lub lapiemy wyjatek.
 * Mianowice ustawiamy wtedy odpowiednio errorNo i errorDesc.
 */
public class SampleCommandWithErrorHandling extends BaseCommand {

    /** Nazwa komendy */
    public static final String COMMAND_NAME = "CommandWithError";

    public SampleCommandWithErrorHandling(String[] params) {
        super(params);
    }

    @Override
    protected void execute(Room room, User user) {
        try {
            if (!room.getUsers().contains(user)) {
                errorNo = 66;
                errorDesc = "W pokoju " + room.getRoomNo() + " nie ma uzytkownika " + user.getUsername();
                return;
            }
            result = new String[]{
                    "Uzytkownik jest w pokoju!"
            };
        } catch (Exception e) {
            errorNo = -1;
            errorDesc = e.getMessage();
        }
    }
}
