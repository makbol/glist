/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.core;

import org.java_websocket.WebSocket;
import pl.edu.agh.model.Player;

/**
 * @author uriel
 */
public interface ICommandExecutedHandler<T extends BaseCommand> {
    boolean onCommandExecuted(WebSocket socket, Player player, T command);
}
