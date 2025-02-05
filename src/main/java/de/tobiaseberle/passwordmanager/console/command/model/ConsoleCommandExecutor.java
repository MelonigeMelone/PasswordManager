package de.tobiaseberle.passwordmanager.console.command.model;

import de.tobiaseberle.passwordmanager.console.command.model.argument.Argument;

public interface ConsoleCommandExecutor {

    String[] getCommandIdentifiers();

    String getHelpText(String usedCommandName);

    void onCommand(String commandName, Argument<?>[] args);
}
