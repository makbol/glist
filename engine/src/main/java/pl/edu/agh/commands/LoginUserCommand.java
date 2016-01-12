package pl.edu.agh.commands;

import pl.edu.agh.core.BaseCommand;
import pl.edu.agh.core.Room;
import pl.edu.agh.db.DBManager;
import pl.edu.agh.model.Player;

/**
 * Komenda sluzaca do logowania uzytkownika. <br>
 * Zwraca id uzytkownika jesli udalo sie zalogowac,
 * -1 w przypadku gdy sie nie udalo.
 */
public class LoginUserCommand extends BaseCommand {

    /** Nazwa komendy */
    public static final String COMMAND_NAME = "loginUser";

    public LoginUserCommand(String[] params) {
        super(params);
    }

    @Override
    protected void execute(Room room, Player player) {
        result = DBManager.loginUser(params[1], params[2]);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }


}
