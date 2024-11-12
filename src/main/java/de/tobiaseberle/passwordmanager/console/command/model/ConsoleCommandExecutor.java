package de.tobiaseberle.passwordmanager.console.command.model;

public interface ConsoleCommandExecutor {

    String[] getCommandIdentifiers();

    String getHelpText();

    void onCommand(String[] args);
}
