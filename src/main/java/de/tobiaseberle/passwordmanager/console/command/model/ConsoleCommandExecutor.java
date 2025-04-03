package de.tobiaseberle.passwordmanager.console.command.model;

import de.tobiaseberle.passwordmanager.console.command.model.argument.AbstractArgumentValue;

public interface ConsoleCommandExecutor {

    String[] getCommandIdentifiers();

    String getCommandDescription(String usedCommandName);

    void onCommand(String commandName, AbstractArgumentValue<?>[] args);
}
