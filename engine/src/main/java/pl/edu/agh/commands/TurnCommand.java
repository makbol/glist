package pl.edu.agh.commands;

import java.util.Arrays;
import pl.edu.agh.core.BaseCommand;
import pl.edu.agh.core.Room;
import pl.edu.agh.model.Player;

/**
 * @author Bartosz Sadel
 */
public class TurnCommand extends BaseCommand {

    /** Nazwa komendy */
    public static final String COMMAND_NAME = "turnCommand";

    private String direction;

    public TurnCommand(String[] params) {
        super(params);
        direction = params[1];
    }

    @Override
    protected void execute(Room room, Player player) {
        System.out.println("turnCommand: "+Arrays.toString(params));
        player.setDirection(Player.Direction.parse(direction));
    }

    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
}
