package pl.edu.agh.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.edu.agh.core.BaseCommand;
import pl.edu.agh.core.Player;
import pl.edu.agh.core.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bartosz Sadel
 */
public class GetUsersList extends BaseCommand {

    /** Nazwa komendy */
    public static final String COMMAND_NAME = "getUsersList";

    public GetUsersList(String[] params) {
        super(params);
    }

    @Override
    protected void execute(Room room, Player player) {
        List<Player> players = room.getPlayers();
        List<Player> activePlayers = new ArrayList<>();
        for(Player player1 : players) {
            if(player1.isAlive()) {
                activePlayers.add(player1);
            }
        }

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        result = gson.toJson(activePlayers);
    }
}
