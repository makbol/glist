/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.core;

/**
 *
 * @author uriel
 */
public class RoomException extends Exception {

    public static enum CAUSE {
        GAME_RUNNING,
        INSUFFICENT_PLAYERS,
        WAIT_INTERRUPTED,
    }
    
    private final CAUSE cause;
    
    public static String formatMessage(CAUSE c) {
        switch( c ) {
            case GAME_RUNNING :
                return "Gra już działa";
            case INSUFFICENT_PLAYERS :
                return "Nie wystarczająca libcza graczy do rozpoczęcia";
            case WAIT_INTERRUPTED :
                return "Przerwan oczekiwanie na początek gry";
        }
        return null;
    } 
    
    
    public RoomException( CAUSE c ) {
        
        super(formatMessage(c));cause=c;
    }
    
    public CAUSE getCAUSE() {
        return cause;
    }
    
}
