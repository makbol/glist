package pl.edu.agh.commands;

import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.edu.agh.core.BroadcastCommand;
import pl.edu.agh.core.Room;
import pl.edu.agh.core.RoomException;
import pl.edu.agh.model.Player;

/**
 * Rozpoczyna nowa gre w zadanym pokoju
 */
public class StartNewGameCommand extends BroadcastCommand {

    /** Nazwa komendy */
    public static final String COMMAND_NAME = "startNewGame";

    private Player executor;

    public StartNewGameCommand(String[] params) {
        super(params);
    }

    @Override
    protected void execute(Room room, Player player) {
        executor = player;
        
        try {
            room.startNewGame();
        } catch (RoomException ex) {
            switch(ex.getCAUSE()) {
                case GAME_RUNNING :
                    errorNo = -6;
                    errorDesc = ex.getMessage();
                case INSUFFICENT_PLAYERS :
                    errorNo = -7;
                    errorDesc = ex.getMessage();
            }
            return;
        }
        
        try {
            room.waitForGameInit();
        }catch( RoomException re ) {
            switch(re.getCAUSE()) {
                case WAIT_INTERRUPTED :
                    errorNo = -8;
                    errorDesc = re.getMessage();
            }
            return;
        }
        
        
        result = room.getGamePlayer();
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    protected String getResponseFor(Player p) {
        return accessResultResponse();
    }

    
}
