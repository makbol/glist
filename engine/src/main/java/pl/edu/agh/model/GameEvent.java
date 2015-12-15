/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.model;

/**
 * Klasa bazowa dla zdarzeń gry
 *
 * @author uriel
 */
public class GameEvent {
    // Czas utworzenia zdarzenia
    private final long timestamp;

    public GameEvent() {
        this.timestamp = System.currentTimeMillis();
    }

}
