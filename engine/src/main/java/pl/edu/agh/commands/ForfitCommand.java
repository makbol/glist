/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.commands;

import pl.edu.agh.core.BaseCommand;
import pl.edu.agh.core.BroadcastCommand;
import pl.edu.agh.core.Room;
import pl.edu.agh.model.Player;

/**
 *
 * @author uriel
 */
public class ForfitCommand extends BroadcastCommand {

    private static final String COMMAND_NAME = "forfit";
    
    private Player executor;
    
    public ForfitCommand() {
        super(new String[]{COMMAND_NAME});
    }
    public ForfitCommand(String[] params) {
        super(params);
       
    }

    @Override
    protected void execute(Room room, Player player) {
        result = "You Lost!";
        executor = player;
    }

    @Override
    protected String getResponseFor(Player p) {
        if( p.equals(executor) ) {
            return "You Lost!";
        } else {
            return p.getUsername()+" has lost.";
        }
        
    }

    
    
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
    
    
}
