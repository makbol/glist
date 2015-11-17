package pl.edu.agh.core;

import com.google.gson.Gson;
import pl.edu.agh.commands.GetActiveUsersListCommand;
import pl.edu.agh.commands.HelloWorldCommand;
import pl.edu.agh.commands.JoinCommand;
import pl.edu.agh.commands.SampleCommandWithErrorHandling;
import pl.edu.agh.commands.SampleCommandWithParam;

import java.util.Properties;

/**
 * Szablon komendy.
 */
public abstract class BaseCommand {

    /** Nazwa parametru wynikowego zawierajacego nazwe komendy. */
    public static final String COMMAND_NAME_PARAM = "commandName";

    /** Nazwa parametru wynikowego zawierajacego wynik komendy. */
    public static final String RESULT_PARAM = "result";

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
            case JoinCommand.COMMAND_NAME :
                return new JoinCommand(command);
            case GetActiveUsersListCommand.COMMAND_NAME:
                return new GetActiveUsersListCommand(command);
            default:
                return new HelloWorldCommand(command);
        }
    }

    /**
     * Implementuje logike komendy.
     * Jesli komenda powinna cos zwrocic wynik jej wywolania ladujemy do result.
     * @param room pokoj na ktorym wykonywana jest operacja
     * @param player uzytkownik wolajacy komende
     */
    abstract protected void execute(Room room, Player player);
   
   
    /** Zwraca wynik wywolania metody w postaci jsona */
    public String getJsonResponse() {
        Properties result = new Properties();
        result.put(COMMAND_NAME_PARAM, getCommandName());
        result.put(RESULT_PARAM, this.result);
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
