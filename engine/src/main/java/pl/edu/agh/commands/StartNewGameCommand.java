package pl.edu.agh.commands;

import pl.edu.agh.core.BroadcastCommand;
import pl.edu.agh.core.Room;
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
        if( room.isGameRunning() ) {
          errorNo = -66;
          errorDesc = "Game is running";
        } 
        result="";
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    protected String getResponseFor(Player p) {
//        if( p.equals(executor) ) {
//            return "You stared the game";
//        } else {
//            return p.getUsername()+" started the game";
//        }
        return accessResultResponse();
    }

    
}
