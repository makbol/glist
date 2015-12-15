package pl.edu.agh.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.edu.agh.core.BaseCommand;
import pl.edu.agh.core.Room;
import pl.edu.agh.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Komenda pobiera liste aktywnych (zyjacych) graczy
 */
public class GetActiveUsersListCommand extends BaseCommand {

    /** Nazwa komendy */
    public static final String COMMAND_NAME = "getActiveUsersList";

    public GetActiveUsersListCommand(String[] params) {
        super(params);
    }

    @Override
    protected void execute(Room room, Player player) {
        List<Player> players = room.getPlayers();
        List<Player> activePlayers = new ArrayList<>();
        for(Player player1 : players) {
            if(player1.getTimeOfDeath() == null) {
                activePlayers.add(player1);
            }
        }

//        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        result = activePlayers;
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
}
