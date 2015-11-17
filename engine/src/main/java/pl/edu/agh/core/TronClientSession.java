/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author uriel
 */
public class TronClientSession extends Thread {
    
    private static final Logger l = Logger.getLogger(TronClientSession.class.getSimpleName());
    private static final String SESSION_THREAD_NAME_PREFIX = "ClientSession";
    
   
    private final Socket socket;
    private final Function<TronClientSession,Void> terminationCallback;
    private int sessionID = 0; 
    private Pattern quitPattern;
    
    private PrintStream clientOut;
    private BufferedReader clientInput;
    private Player player;
    
    /**
     * Inicjalizuje wątek sesji.
     * 
     * Konstruktor musi wykonywać minimalną ilość pracy  aby nie obciażać œątku 
     * nasłuchujacego. wszelka inicjalizacja musi się odbyć we funkcji init();
     * 
     * Callback jest wywoływany gdy połączenie zostanie zamknięte na dobre.
     * Przewidzaine dla TronListener do usuwania połączeń
     * 
     * @param socket
     * @param sessionID 
     */
    public TronClientSession(Socket socket,
                             int sessionID,
                             Function<TronClientSession, Void> tc )
    {
        super(createName(sessionID));
        this.socket = socket;
        this.sessionID = sessionID;
        terminationCallback = tc;
    }
    
    private static String createName( int sessionID ) {
        return new StringBuilder(SESSION_THREAD_NAME_PREFIX)
               .append(sessionID)
               .toString();
    }

    public int getSessionID() {
        return sessionID;
    }

    @Override
    public int hashCode() {
        int r = 5;
        r = 37 * r + sessionID;
        return r;
    }

    /**
     * Porównanie.
     * 
     * Prównuję tylko po sessionID, bo zakłąda że będzie unikalne. 
     * 
     * @param o
     * @return 
     */
    @Override
    public boolean equals(Object o) {
        if( this == o ) {
            return true;
        }
        if( o.getClass() == TronClientSession.class ) {
            TronClientSession tcs = (TronClientSession)o;
            return tcs.sessionID == sessionID;
        }
        
        return false;
    }
    
    
          
    
    @Override
    public void run() {
        try {
            init();
        } catch( IOException e ) {
            l.severe("Failed to access client I/O");
        }
        
        String input = null;
        String[] command = null;
        List<String> lines = new LinkedList();
        
        // Co by się dało przerwać sessję
        while( !Thread.interrupted() ) {
           try {
                input = clientInput.readLine();
                if( input == null ) {
                    // Koniec danych -> koniec strumienia -> koniec połączenia
                    break;
                }
                if(quitPattern.matcher(input).matches()) {
                    // Przerwa na rządanie
                    break;
                }
                
                if( command == null ) {
                    command = input.split(",");
                } else {
                    lines.add(input);
                }
                
                if( handleCommand(command, lines) ) {
                    lines.clear();
                    command=null;
                } 
           }catch(IOException e) {
               l.severe("failed to read input");
               clientOut.println("Communication error. Terminating");
               break;
           }
        }
        
        try {
            close();
        } catch( IOException e ) {
            l.warning(" cannot close client connection ");
        }
        terminationCallback.apply(this);
    }
    
    protected void init() throws IOException {
        try {
            clientOut = new PrintStream(socket.getOutputStream(), false, "UTF-8");
        } catch( UnsupportedEncodingException uee ) {
            throw new IllegalStateException(uee);
        }
        clientInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        quitPattern = Pattern.compile("^q$|^e&|^exit&|^quit$");
    }
    protected void close() throws IOException {
        clientInput.close();
        clientOut.close();
        socket.close();
    }
    
    protected boolean handleCommand(String[] command,List<String> additionalLines) {
        switch(command[0]) {
            
            default :
               BaseCommand cmd = BaseCommand.getCommand(command);
               cmd.execute(TronServer.getInstance().getRoom(), player);
               
               return true;
        }
    }
    
    protected void authorize() {
        
    }
    
    protected void authorizeAs( String userID  ) {
        
    }
    
    
}
