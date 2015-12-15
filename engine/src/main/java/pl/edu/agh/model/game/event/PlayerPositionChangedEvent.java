/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.model.game.event;

import pl.edu.agh.model.Player;
import pl.edu.agh.model.PlayerGameEvent;

/**
 * @author uriel
 */
public class PlayerPositionChangedEvent extends PlayerGameEvent {

    private final int newX;
    private final int newY;

    public PlayerPositionChangedEvent(Player p, int x, int y) {
        super(p);
        newX = x;
        newY = y;
    }

    public int getNewX() {
        return newX;
    }

    public int getNewY() {
        return newY;
    }


}
