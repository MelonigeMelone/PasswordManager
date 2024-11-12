package de.tobiaseberle.passwordmanager.console.command;

import de.tobiaseberle.passwordmanager.console.Console;
import de.tobiaseberle.passwordmanager.console.command.model.ConsoleCommandExecutor;
import de.tobiaseberle.passwordmanager.console.command.model.argument.Argument;

public class HelloWorldCommand implements ConsoleCommandExecutor {

    private final Console console;

    public HelloWorldCommand(Console console) {
        this.console = console;
    }

    @Override
    public String[] getCommandIdentifiers() {
        return new String[] {"helloworld"};
    }

    @Override
    public String getHelpText() {
        return "/helloworld - Gibt 'Hello World' aus.";
    }

    @Override
    public void onCommand(Argument<?>[] args) {
        this.console.sendMessage("Hello World");
    }
}
