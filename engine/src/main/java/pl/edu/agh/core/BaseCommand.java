package pl.edu.agh.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.edu.agh.commands.*;
import pl.edu.agh.model.Player;

import java.util.Properties;

/**
 * Szablon komendy.
 */
public abstract class BaseCommand {
    
    /** Nazwa parametru wynikowego zawierajacego nazwe komendy. */
    public static final String COMMAND_NAME_PARAM = "commandName";

    /** Nazwa parametru wynikowego zawierajacego wynik komendy. */
    public static final String RESULT_PARAM = "result";

     /** Nazwa parametru wynikowego zaierajacego kod bldu. */
    public static final String COMMAND_ERROR_CODE_PARAM = "errCode";
    
     /** Nazwa parametru wynikowego zaierajacego opis bldu */
    public static final String COMMAND_ERROR_DESC_PARAM = "err";
    
    /** Parametry wywolania komendy. Pod kluczem 0 zawsze bedzie nazwa komendy! */
    protected final String[] params;

    /** Kod bledu. */
    protected int errorNo = 0;

    /** Opis bledu */
    protected String errorDesc = "";

    /** Resultat wywolania komendy. Domyslnie null - czyli brak rezultatu. */
    protected String result = "";

    public BaseCommand(String[] params) {
        this.params = params;
    }

    /** Metoda zwraca odpowiednia implementacje komendy dla komendy w postaci stringa. */
    public static < T extends BaseCommand > T getCommand(String[] command) {
        if(command.length < 1) {
            return null;
        }
        switch (command[0]) {
            case HelloWorldCommand.COMMAND_NAME:
                return (T) new HelloWorldCommand(command);
            case JoinGameCommand.COMMAND_NAME :
                return (T) new JoinGameCommand(command);
            case GetActiveUsersListCommand.COMMAND_NAME:
                return (T) new GetActiveUsersListCommand(command);
            case StartNewGameCommand.COMMAND_NAME:
                return (T) new StartNewGameCommand(command);
            case KillServerCommand.COMMAND_NAME:
                return (T) new KillServerCommand(command);
            case TurnCommand.COMMAND_NAME:
                return (T) new TurnCommand(command);
            default:
                return (T) new HelloWorldCommand(command);
        }
    }

    public static String createNoPlayerResponse( String command ) {
        Properties result = new Properties();
        result.put(COMMAND_NAME_PARAM, command); 
        result.put(COMMAND_ERROR_CODE_PARAM, -69);
        result.put(COMMAND_ERROR_DESC_PARAM, "Wykonaj joinGame najpierw!");
        return new Gson().toJson(result);
    }
    public static String createRoomConsumed( String command ) {
        Properties result = new Properties();
        result.put(COMMAND_NAME_PARAM, command); 
        result.put(COMMAND_ERROR_CODE_PARAM, -8);
        result.put(COMMAND_ERROR_DESC_PARAM, "Pokój odmówił wykonania komendy");
        return new Gson().toJson(result);
    }
    public static String createNoSuchCommand( String command ) {
        Properties result = new Properties();
        result.put(COMMAND_NAME_PARAM, command); 
        result.put(COMMAND_ERROR_CODE_PARAM, -7);
        result.put(COMMAND_ERROR_DESC_PARAM, "Nieznana kodmenda");
        return new Gson().toJson(result);
    }
    
    /**
     * Implementuje logike komendy.
     * Jesli komenda powinna cos zwrocic wynik jej wywolania ladujemy do result.
     * @param room pokoj na ktorym wykonywana jest operacja
     * @param player uzytkownik wolajacy komende
     */
    abstract protected void execute(Room room, Player player);
   
   
    public boolean wasSuccessful() {
        return errorNo==0;
    }
    
    /** Zwraca wynik wywolania metody w postaci jsona */
    public String getResultResponse() {
        if( !wasSuccessful() )  {
            throw new IllegalStateException("There is no result when command failed");
        }
        Properties result = new Properties();
        result.put(COMMAND_NAME_PARAM, getCommandName());
        result.put(RESULT_PARAM, this.result);
        
        return new GsonBuilder().disableHtmlEscaping().create().toJson(result);
    }
    public String getErrorResponse() {
        if( wasSuccessful() ) {
            throw new IllegalStateException(" Command did not fail. There is no error ");
        }
         Properties result = new Properties();
        result.put(COMMAND_NAME_PARAM, getCommandName()); 
        result.put(COMMAND_ERROR_CODE_PARAM, errorNo);
        result.put(COMMAND_ERROR_DESC_PARAM, errorDesc);
        return new Gson().toJson(result);
    }
    
    

    public abstract String getCommandName();

    public int getErrorNo() {
        return errorNo;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public String getResult() {
        return result;
    }
}
