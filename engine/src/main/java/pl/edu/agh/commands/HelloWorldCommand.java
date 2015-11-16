package pl.edu.agh.commands;

import pl.edu.agh.core.BaseCommand;
import pl.edu.agh.core.Room;
import pl.edu.agh.core.User;

/**
 * Przykladowa komedna.
 */
public class HelloWorldCommand extends BaseCommand {

    /** Nazwa komendy */
    public static final String COMMAND_NAME = "HelloWorld";

    public HelloWorldCommand(String[] params) {
        super(params);
    }

    @Override
    protected void execute(Room room, User user) {
        result = new String[] {
                "Hello World!",
                "Called on room: " + room.getRoomNo(),
                "Called by user: " + user.getUsername()
        };
    }
}
