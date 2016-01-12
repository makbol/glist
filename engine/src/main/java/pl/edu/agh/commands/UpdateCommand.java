/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.edu.agh.core.BaseCommand;
import pl.edu.agh.core.Room;
import pl.edu.agh.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author uriel
 */
public class UpdateCommand extends BaseCommand {

    private static final String COMMAND_NAME = "UPDATE";

    public UpdateCommand() {
        super(new String[]{COMMAND_NAME});
    }    
    public UpdateCommand(String[] params) {
        super(params);
    }

    @Override
    protected void execute(Room room, Player player) {
        List<Player> players = room.getGamePlayer();
        List<Player> activePlayers = new ArrayList<>();
        for(Player player1 : players) {
            if(player1.getTimeOfDeath() == null) {
                activePlayers.add(player1);
            }
        }

//        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        System.out.println(activePlayers);
        result = activePlayers;
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
    
    
}
