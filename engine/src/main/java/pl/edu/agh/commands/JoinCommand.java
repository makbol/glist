/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.commands;

import pl.edu.agh.core.BaseCommand;
import pl.edu.agh.core.Player;
import pl.edu.agh.core.Room;

/**
 *
 * @author uriel
 */
public class JoinCommand extends BaseCommand {

    public static final String COMMAND_NAME = "join";
    
    private Player joinedPlayer;
    
    public JoinCommand(String[] params) {
        super(params);
    }

    public Player getJoinedPlayer() {
        return joinedPlayer;
    }

    @Override
    public String getCommandName() {
      return "join";
    }
    
    

    
    
    @Override
    protected void execute(Room room, Player player) {
        if( params.length < 2 ) {
            errorNo = -1;
            errorDesc = "brak parametru";
            return;
        }
        
        Player p = new Player(params[1]);
        room.addPlayer(player);
        joinedPlayer=p;
        result = p.getUserId().toString();
    }
}
