package de.tobiaseberle.passwordmanager.console.command.model;

public interface ConsoleCommandExecutor {

    void onCommand(String[] args);

    String getHelpText();
}
