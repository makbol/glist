package pl.edu.agh.commands;

import pl.edu.agh.core.BaseCommand;
import pl.edu.agh.core.Room;
import pl.edu.agh.model.Player;

/**
 * Rozpoczyna nowa gre w zadanym pokoju
 */
public class StartNewGameCommand extends BaseCommand {

    /** Nazwa komendy */
    public static final String COMMAND_NAME = "startNewGame";

    private Player addedPlayer;

    public StartNewGameCommand(String[] params) {
        super(params);
    }

    @Override
    protected void execute(Room room, Player player) {
        room.startNewGame();
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    public Player getAddedPlayer() {
        return addedPlayer;
    }
}
