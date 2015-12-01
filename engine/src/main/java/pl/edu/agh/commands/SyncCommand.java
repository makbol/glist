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

/**
 *
 * @author uriel
 */
public class SyncCommand extends BaseCommand {

    private static final String COMMAND_NAME = "sync";

    public SyncCommand() {
        super(new String[]{COMMAND_NAME});
    }    
    public SyncCommand(String[] params) {
        super(params);
    }

    @Override
    protected void execute(Room room, Player player) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        result = gson.toJson(player);
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
    
    
}
