package pl.edu.agh.core;

import com.google.gson.Gson;
import pl.edu.agh.commands.*;

import java.util.Properties;

/**
 * Szablon komendy.
 */
public abstract class BaseCommand {

    /** Nazwa parametru wynikowego zawierajacego nazwe komendy. */
    public static final String COMMAND_NAME_PARAM = "commandName";

    /** Nazwa parametru wynikowego zawierajacego wynik komendy. */
    public static final String RESULT_PARAM = "result";

     /** Nazwa parametru wynikowego zaierającego kod błędu. */
    public static final String COMMAND_ERROR_CODE_PARAM = "errCode";
    
     /** Nazwa parametru wynikowego zaierającego opis błędu */
    public static final String COMMAND_ERROR_DESC_PARAM = "err";
    
    /** Parametry wywolania komendy. Pod kluczem 0 zawsze bedzie nazwa komendy! */
    protected final String[] params;

    /** Kod bledu. */
    protected int errorNo = 0;

    /** Opis bledu */
    protected String errorDesc = "";

    /** Resultat wywolania komendy. Domyslnie null - czyli brak rezultatu. */
    protected String result = null;

    public BaseCommand(String[] params) {
        this.params = params;
    }

    /** Metoda zwraca odpowiednia implementacje komendy dla komendy w postaci stringa. */
    public static BaseCommand getCommand(String[] command) {
        if(command.length < 1) {
            return null;
        }
        switch (command[0]) {
            case HelloWorldCommand.COMMAND_NAME:
                return new HelloWorldCommand(command);
            case SampleCommandWithParam.COMMAND_NAME:
                return new SampleCommandWithParam(command);
            case SampleCommandWithErrorHandling.COMMAND_NAME:
                return new SampleCommandWithErrorHandling(command);
            case JoinGameCommand.COMMAND_NAME :
                return new JoinGameCommand(command);
            case GetActiveUsersListCommand.COMMAND_NAME:
                return new GetActiveUsersListCommand(command);
            case StartNewGameCommand.COMMAND_NAME:
                return new StartNewGameCommand(command);
            default:
                return new HelloWorldCommand(command);
        }
    }

    public static String createNoPlayerResponse() {
        Properties result = new Properties();
        result.put(COMMAND_ERROR_CODE_PARAM, -69);
        result.put(COMMAND_ERROR_DESC_PARAM, "Wykonaj joinGame najpierw!");
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
        return new Gson().toJson(result);
    }
    public String getErrorResponse() {
        if( wasSuccessful() ) {
            throw new IllegalStateException(" Command did not fail. There is no error ");
        }
         Properties result = new Properties();
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
