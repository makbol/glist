/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.core;

import pl.edu.agh.model.Player;

/**
 *
 * @author uriel
 */
public abstract class BroadcastCommand  extends BaseCommand {

    public BroadcastCommand(String[] params) {
        super(params);
    }
    
    protected String getResponseFor( Player p ) {
        return super.getResultResponse();
    }

    /**
     * Przesłaniam wytwarzanie odpowiedzi.
     * Musi być null aby ten kto wywołał brodcast nie dostał wiadomosci 2 razy
     * @return 
     */
    @Override
    public String getResultResponse() {
        return null;
    }
    
    
}
