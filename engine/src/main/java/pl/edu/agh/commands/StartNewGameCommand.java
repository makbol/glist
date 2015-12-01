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
    private Player executor;

    public StartNewGameCommand(String[] params) {
        super(params);
    }

    @Override
    protected void execute(Room room, Player player) {
        executor = player;
        if( room.isGameRunning() ) {
          errorNo = -66;
          errorDesc = "Game is running";
        } 
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    protected String getResponseFor(Player p) {
        if( p.equals(executor) ) {
            return "You stared the game";
        } else {
            return p.getUsername()+" started the game";
        }
    }

    
}
