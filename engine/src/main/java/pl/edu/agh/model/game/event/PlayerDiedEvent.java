/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.model.game.event;

import pl.edu.agh.model.Player;
import pl.edu.agh.model.PlayerGameEvent;

/**
 *
 * @author uriel
 */
public class PlayerDiedEvent extends PlayerGameEvent {

    public PlayerDiedEvent(Player player) {
        super(player);
    }
    
}
