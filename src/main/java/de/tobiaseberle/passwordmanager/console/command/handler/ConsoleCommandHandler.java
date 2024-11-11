package de.tobiaseberle.passwordmanager.console.command.handler;

import de.tobiaseberle.passwordmanager.console.Console;
import de.tobiaseberle.passwordmanager.console.command.HelloWorldCommand;
import de.tobiaseberle.passwordmanager.console.command.model.ConsoleCommandExecutor;

import java.util.HashMap;
import java.util.Map;

public class ConsoleCommandHandler {

    private final Console console;

    private final Map<String, ConsoleCommandExecutor> registeredConsoleCommands = new HashMap<>();

    public ConsoleCommandHandler(Console console) {
        this.console = console;

        registerCommand("helloworld", new HelloWorldCommand(console));
    }

    public void registerCommand(String command, ConsoleCommandExecutor commandExecutor) {
        registeredConsoleCommands.put(command, commandExecutor);
    }

    public void dispatchCommand(String[] args) {
        if (registeredConsoleCommands.containsKey(args[0])) {
            String[] argsAfterCommand = new String[args.length - 1];
            for (int i = 0; i < args.length; i++) {
                if (i == 0) continue;
                argsAfterCommand[i - 1] = args[i];
            }
            registeredConsoleCommands.get(args[0]).onCommand(argsAfterCommand);
        } else {
           console.sendMessage("Es wurde kein Befehl mit dem Name '" + args[0] + "' gefunden!");
        }
    }
}
