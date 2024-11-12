package de.tobiaseberle.passwordmanager.console.command.handler;

import de.tobiaseberle.passwordmanager.console.Console;
import de.tobiaseberle.passwordmanager.console.command.GeneratePasswordCommand;
import de.tobiaseberle.passwordmanager.console.command.HelloWorldCommand;
import de.tobiaseberle.passwordmanager.console.command.model.ConsoleCommandExecutor;
import de.tobiaseberle.passwordmanager.console.command.model.argument.Argument;
import de.tobiaseberle.passwordmanager.console.command.model.argument.ArgumentType;

import java.util.*;

public class ConsoleCommandHandler {

    private final Console console;

    private final List<ConsoleCommandExecutor> registeredConsoleCommands = new ArrayList<>();

    public ConsoleCommandHandler(Console console) {
        this.console = console;

        registerCommand(new HelloWorldCommand(console));
        registerCommand(new GeneratePasswordCommand(console));
    }

    public void registerCommand(ConsoleCommandExecutor commandExecutor) {
        registeredConsoleCommands.add(commandExecutor);
    }

    public void dispatchCommand(String[] args) {
        String commandIdentifier = args[0];

        Optional<ConsoleCommandExecutor> optionalConsoleCommandExecutor = findCommand(commandIdentifier);

        if(optionalConsoleCommandExecutor.isEmpty()) {
            console.sendMessage("Es wurde kein Befehl mit dem Name '" + args[0] + "' gefunden!");
            return;
        }

        ConsoleCommandExecutor commandExecutor = optionalConsoleCommandExecutor.get();

        String[] argsAfterCommand = new String[args.length - 1];
        for (int i = 0; i < args.length; i++) {
            if (i == 0) continue;
            argsAfterCommand[i - 1] = args[i];
        }

        commandExecutor.onCommand(parseArguments(argsAfterCommand));
    }

    private Optional<ConsoleCommandExecutor> findCommand(String commandIdentifier) {
        return registeredConsoleCommands.stream()
                .filter(command -> Arrays.asList(command.getCommandIdentifiers()).contains(commandIdentifier))
                .findFirst();
    }

    private Argument<?>[] parseArguments(String[] args) {
        Argument<?>[] arguments = new Argument<?>[args.length];
        for (int i = 0; i < args.length; i++) {
           ArgumentType argumentType = ArgumentType.fromString(args[i]);
            arguments[i] = argumentType.parse(args[i]);
        }
        return arguments;
    }
}
