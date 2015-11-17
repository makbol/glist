package pl.edu.agh.commands;

import pl.edu.agh.core.BaseCommand;
import pl.edu.agh.core.Player;
import pl.edu.agh.core.Room;

/**
 * W tej komendzie widzimy co zrobic jesli w wywowaniu komendy nastepuje blad lub lapiemy wyjatek.
 * Mianowice ustawiamy wtedy odpowiednio errorNo i errorDesc.<br><br>
 * Opowiada wywolaniu z przegladarki komendy "CommandWithError;param1;param2,..."
 */
public class SampleCommandWithErrorHandling extends BaseCommand {

    /** Nazwa komendy */
    public static final String COMMAND_NAME = "CommandWithError";

    public SampleCommandWithErrorHandling(String[] params) {
        super(params);
    }

    @Override
    protected void execute(Room room, Player player) {
        try {
            if (!room.getPlayers().contains(player)) {
                errorNo = 66;
                errorDesc = "W pokoju " + room.getRoomNo() + " nie ma uzytkownika " + player.getUsername();
                return;
            }
            result = "Uzytkownik jest w pokoju!";
        } catch (Exception e) {
            errorNo = -1;
            errorDesc = e.getMessage();
        }
    }
}
