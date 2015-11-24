package pl.edu.agh.commands;

import pl.edu.agh.core.BaseCommand;
import pl.edu.agh.core.Room;
import pl.edu.agh.model.Player;

/**
 * Przykladowa komenda korzystajaca z parametrow. Parsuje ona parametry z konstruktora i zapisuje w odpowiednich polach.
 * Mozna tez korzystac z parametrow ktore same zapisuja sie w polu {@link BaseCommand#params} jednak tak jest czytelniej.
 * <b>Pod kluczem 0 w parametrach zawsze bedzie nazwa komendy!</b><br><br>
 * Opowiada wywolaniu z przegladarki komendy "CommandWithParam;x;y"
 */
public class SampleCommandWithParam extends BaseCommand{

    /** Nazwa komendy */
    public static final String COMMAND_NAME = "CommandWithParam";

    private int x;

    private int y;

    public SampleCommandWithParam(String[] params) {
        super(params);
        x = Integer.parseInt(params[1]);
        y = Integer.parseInt(params[2]);
    }

    @Override
    protected void execute(Room room, Player player) {
        result = "Uzytkownika przesunieto o x:" + x + " y:" + y;
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
}
