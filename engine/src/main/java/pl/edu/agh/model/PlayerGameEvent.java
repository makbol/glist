/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.model;

/**
 * Zdarzenie gdy które dotyczy tylko konkretengo gracza.
 * @author uriel
 */
public class PlayerGameEvent extends GameEvent {
    private final Player player;

    public PlayerGameEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

}
