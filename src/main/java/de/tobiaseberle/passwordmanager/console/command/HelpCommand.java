package de.tobiaseberle.passwordmanager.console.command;

import de.tobiaseberle.passwordmanager.console.command.handler.ConsoleCommandHandler;
import de.tobiaseberle.passwordmanager.console.command.model.ConsoleCommandExecutor;
import de.tobiaseberle.passwordmanager.console.command.model.argument.AbstractArgumentValue;

public class HelpCommand implements ConsoleCommandExecutor {

    private final ConsoleCommandHandler consoleCommandHandler;

    public HelpCommand(ConsoleCommandHandler consoleCommandHandler) {
        this.consoleCommandHandler = consoleCommandHandler;
    }

    @Override
    public String[] getCommandIdentifiers() {
        return new String[0];
    }

    @Override
    public String getCommandDescription(String usedCommandName) {
        return "";
    }

    @Override
    public void onCommand(String commandName, AbstractArgumentValue<?>[] args) {
    }
}
