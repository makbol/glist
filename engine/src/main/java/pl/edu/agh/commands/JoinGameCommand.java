package pl.edu.agh.commands;

import pl.edu.agh.core.BaseCommand;
import pl.edu.agh.core.Room;
import pl.edu.agh.model.Player;

/**
 * Komenda dodaje nowego gracza do zadanego pokoju
 */
public class JoinGameCommand extends BaseCommand {

    /**
     * Nazwa komendy
     */
    public static final String COMMAND_NAME = "joinGame";

    private Player addedPlayer;

    public JoinGameCommand(String[] params) {
        super(params);
    }

    @Override
    protected void execute(Room room, Player player) {
        if (params.length < 2 || params[1].isEmpty()) {
            errorNo = -1;
            errorDesc = "User name not provided.";
            return;
        }

        addedPlayer = new Player(params[1]);
        room.addPlayer(addedPlayer);
        result = addedPlayer.getUserId().toString();
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    public Player getAddedPlayer() {
        return addedPlayer;
    }
}
