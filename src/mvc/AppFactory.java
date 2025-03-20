package mvc;

import minefield.Heading;

public interface AppFactory {
    String[] getHelp();
    String [] getEditCommands();
    Model newModel();
    View newView(Model model);
    String getTitle();
    Command newEditCommand(Model model, Heading command, Object object);
    String about();
}
