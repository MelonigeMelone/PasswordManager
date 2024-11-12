package de.tobiaseberle.passwordmanager.console.command.model;

import de.tobiaseberle.passwordmanager.console.command.model.argument.Argument;

public interface ConsoleCommandExecutor {

    String[] getCommandIdentifiers();

    String getHelpText();

    void onCommand(Argument<?>[] args);
}
