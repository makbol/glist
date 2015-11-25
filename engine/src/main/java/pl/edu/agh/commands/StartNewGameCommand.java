package pl.edu.agh.commands;

import com.google.gson.Gson;
import java.util.Properties;
import pl.edu.agh.core.BaseCommand;
import static pl.edu.agh.core.BaseCommand.COMMAND_NAME_PARAM;
import static pl.edu.agh.core.BaseCommand.RESULT_PARAM;
import pl.edu.agh.core.BroadcastCommand;
import pl.edu.agh.core.Room;
import pl.edu.agh.model.Player;

/**
 * Rozpoczyna nowa gre w zadanym pokoju
 */
public class StartNewGameCommand extends BroadcastCommand {

    /** Nazwa komendy */
    public static final String COMMAND_NAME = "startNewGame";
    
    private GetActiveUsersListCommand cmd;

    public StartNewGameCommand(String[] params) {
        super(params);
    }

    @Override
    protected void execute(Room room, Player player) {
        room.startNewGame();
        cmd = new GetActiveUsersListCommand(new String[]{GetActiveUsersListCommand.COMMAND_NAME});
        cmd.execute(room, player);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    protected String getResponseFor(Player p) {
        return cmd.getResultResponse();
    }

    
}
