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
public class KillServerCommand extends BaseCommand {

    public static final String COMMAND_NAME = "kill";
    private static final String KILL_PASSWORD = "die"; 
    
    public KillServerCommand(String[] params) {
        super(params);
    }
    
    @Override
    protected void execute(Room room, Player player) {
      if( params.length < 2 || params[1].isEmpty()  ) {
          errorNo = -2;
          errorDesc = "Wymagane jest hasło";
          return;
      }
      if( !KILL_PASSWORD.equals(params[1]) ) {
          errorNo = -5;
          errorDesc = "błędne hasło";
          return;
      }
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
    
}
