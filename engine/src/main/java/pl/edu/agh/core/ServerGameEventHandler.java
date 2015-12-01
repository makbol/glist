/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.edu.agh.commands.ForfitCommand;
import pl.edu.agh.commands.UpdateCommand;
import pl.edu.agh.model.GameEvent;
import pl.edu.agh.model.IGameEventHandler;
import pl.edu.agh.model.PlayerGameEvent;
import pl.edu.agh.model.game.event.GameEndEvent;
import pl.edu.agh.model.game.event.PlayerDiedEvent;
import pl.edu.agh.model.game.event.PlayerPositionChangedEvent;

/**
 *
 * @author uriel
 */
public class ServerGameEventHandler implements IGameEventHandler {
    private static Logger l = LogManager.getLogger(ServerGameEventHandler.class);
    private final TronServer server;

    public ServerGameEventHandler(TronServer server) {
        this.server = server;
    }

    protected BaseCommand createCommandForEvent( GameEvent ge ) {
        if( ge instanceof PlayerPositionChangedEvent ) {
            return new UpdateCommand();
        }
        if( ge instanceof PlayerDiedEvent ) {
            return new ForfitCommand();
        }
        return null;
    }
    
    @Override
    public void handleEvent(GameEvent e) {
       if( e instanceof PlayerGameEvent ) {
           PlayerGameEvent pge = (PlayerGameEvent)e;
           BaseCommand command = createCommandForEvent(e);
           if( command != null ) {
                server.invokeForPlayer(pge.getPlayer(), command);
           }
       }
       if( e instanceof GameEndEvent ) {
//           server.brodcastMessage("The GAME ended!");
       }
    }
    
    
    
}
