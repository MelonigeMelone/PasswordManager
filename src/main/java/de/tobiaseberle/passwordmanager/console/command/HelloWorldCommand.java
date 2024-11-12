package de.tobiaseberle.passwordmanager.console.command;

import de.tobiaseberle.passwordmanager.console.Console;
import de.tobiaseberle.passwordmanager.console.command.model.ConsoleCommandExecutor;

public class HelloWorldCommand implements ConsoleCommandExecutor {

    private final Console console;

    public HelloWorldCommand(Console console) {
        this.console = console;
    }

    @Override
    public void onCommand(String[] args) {
        this.console.sendMessage("Hello World");
    }

    @Override
    public String[] getCommandIdentifiers() {
        return new String[] {"helloworld"};
    }

    @Override
    public String getHelpText() {
        return "/helloworld - Gibt 'Hello World' aus.";
    }
}
