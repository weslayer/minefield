package minefield;

import mvc.*;

public class MineFieldFactory implements AppFactory {

    @Override
    public String[] getHelp() {
        return new String[]{
                "N --> Move one space North",
                "E --> Move one space East",
                "S --> Move one space South",
                "W --> Move one space west",
                "NW --> Move one space Northwest",
                "NE --> Move one space Northeast",
                "SW --> Move one space Southwest",
                "SE --> Move one space Southeast",
        };
    }

    @Override
    public String[] getEditCommands() {
        return new String[]{"N","W","NW","NE","E","S","SW","SE"};
    }

    @Override
    public Model newModel() {
        return new MineFieldModel();
    }

    @Override
    public View newView(Model model) {
        return new MineFieldView((MineFieldModel) model);
    }

    @Override
    public String getTitle() {
        return "MineField";
    }

    @Override
    public Command newEditCommand(Model model, Heading command, Object object) {
        /*if(command.equals("N")
            || command.equals("NW")
            || command.equals("NE")
            || command.equals("SW")
            || command.equals("SE")
            || command.equals("E")
            || command.equals("W")
            || command.equals("S"))
        {*/
            return new MoveCommand(model, command);
        //}
        //return null;
    }

    public String about() {
        return "Cyberdellic Designs MineField, 2025. All rights reserved.";
    }
}