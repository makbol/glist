package pl.edu.agh.core;

import pl.edu.agh.commands.HelloWorldCommand;
import pl.edu.agh.commands.SampleCommandWithErrorHandling;
import pl.edu.agh.commands.SampleCommandWithParam;

/**
 * Szablon komendy.
 */
public abstract class BaseCommand {

    /** Parametry wywolania komendy. Pod kluczem 0 zawsze bedzie nazwa komendy! */
    protected final String[] params;

    /** Kod bledu. */
    protected int errorNo = 0;

    /** Opis bledu */
    protected String errorDesc = "";

    /** Resultat wywolania komendy. */
    protected String result = "";

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
