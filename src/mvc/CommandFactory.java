package mvc;

public interface CommandFactory {
    Command createCommand(String commandName, Model model);
} 