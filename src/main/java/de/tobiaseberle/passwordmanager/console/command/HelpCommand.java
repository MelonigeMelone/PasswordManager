package de.tobiaseberle.passwordmanager.console.command;

import de.tobiaseberle.passwordmanager.console.command.model.ConsoleCommandExecutor;
import de.tobiaseberle.passwordmanager.console.command.model.argument.Argument;

public class HelpCommand implements ConsoleCommandExecutor {

    @Override
    public String[] getCommandIdentifiers() {
        return new String[0];
    }

    @Override
    public String getHelpText(String usedCommandName) {
        return "";
    }

    @Override
    public void onCommand(String commandName, Argument<?>[] args) {

    }
}
