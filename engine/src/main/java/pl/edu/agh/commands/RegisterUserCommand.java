package pl.edu.agh.commands;

import pl.edu.agh.core.BaseCommand;
import pl.edu.agh.core.Room;
import pl.edu.agh.db.DBManager;
import pl.edu.agh.model.Player;

/**
 * Komenda sluzaca do rejestracji uzytkownikow. <br>
 * Zwraca 1 jesli dodano uzytkownika, 0 jesli operacja sie nie powiodla.
 */
public class RegisterUserCommand extends BaseCommand {

    /** Nazwa komendy */
    public static final String COMMAND_NAME = "registerUser";

    public RegisterUserCommand(String[] params) {
        super(params);
    }

    @Override
    protected void execute(Room room, Player player) {
        result = DBManager.registerUser(params[1], params[2]);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }


}
