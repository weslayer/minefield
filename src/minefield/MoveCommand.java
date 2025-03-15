package minefield;

import mvc.*;

public class MoveCommand extends Command {
    private final Heading heading;

    public MoveCommand(Model model, Heading heading) {
        super(model);
        this.heading = heading;
    }

    @Override
    public void execute() throws Exception {
        ((MineFieldModel)model).move(heading);
    }
} 