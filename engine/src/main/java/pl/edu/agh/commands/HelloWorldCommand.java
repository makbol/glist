package pl.edu.agh.commands;

import pl.edu.agh.core.BaseCommand;
import pl.edu.agh.core.Room;
import pl.edu.agh.model.Player;

/**
 * Przykladowa komedna.<br><br>
 * Opowiada wywolaniu z przegladarki komendy "HellowWorld;param1;param2,..."
 */
public class HelloWorldCommand extends BaseCommand {

    /**
     * Nazwa komendy
     */
    public static final String COMMAND_NAME = "HelloWorld";

    public HelloWorldCommand(String[] params) {
        super(params);
    }

    @Override
    protected void execute(Room room, Player player) {
        result = "Hello World!" +
                "Called on room: " + room.getRoomNo() +
                "Called by player: " + player.getUsername();
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
}
