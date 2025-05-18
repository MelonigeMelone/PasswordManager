package de.tobiaseberle.passwordmanager.console.command.handler;

import de.tobiaseberle.passwordmanager.console.Console;
import de.tobiaseberle.passwordmanager.console.command.*;
import de.tobiaseberle.passwordmanager.console.command.model.ConsoleCommandExecutor;
import de.tobiaseberle.passwordmanager.console.command.model.argument.AbstractArgumentValue;
import de.tobiaseberle.passwordmanager.console.command.model.argument.ArgumentType;
import de.tobiaseberle.passwordmanager.console.command.model.argument.OptionArgumentValue;
import de.tobiaseberle.passwordmanager.storage.StorageHandler;

import java.util.*;

public class ConsoleCommandHandler {

    private final Console console;

    private final List<ConsoleCommandExecutor> registeredConsoleCommands = new ArrayList<>();

    public ConsoleCommandHandler(Console console, StorageHandler storageHandler) {
        this.console = console;

        registerCommand(new AddEntryFieldCommand(console, storageHandler));
        registerCommand(new AddStorageEntryCommand(console, storageHandler));
        registerCommand(new CreateStorageCommand(console, storageHandler));
        registerCommand(new GeneratePasswordCommand(console));
        registerCommand(new GetFieldValueCommand(console, storageHandler));
        registerCommand(new ExportStorageCommand(console, storageHandler));
        registerCommand(new ImportStorageCommand(console, storageHandler));
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

        commandExecutor.onCommand(commandIdentifier, parseArguments(argsAfterCommand));
    }

    private Optional<ConsoleCommandExecutor> findCommand(String commandIdentifier) {
        return registeredConsoleCommands.stream()
                .filter(command -> Arrays.asList(command.getCommandIdentifiers()).contains(commandIdentifier))
                .findFirst();
    }

    private AbstractArgumentValue<?>[] parseArguments(String[] args) {
        AbstractArgumentValue<?>[] abstractArgumentValues = new AbstractArgumentValue<?>[args.length];
        for (int i = 0; i < args.length; i++) {
           ArgumentType argumentType = ArgumentType.fromString(args[i]);
           if(argumentType.equals(ArgumentType.OPTION)) {
               String[] leftArgs = new String[args.length-i];
               for(int j = 0; j<leftArgs.length; j++){
                   leftArgs[j] = args[j+i];
               }
               abstractArgumentValues[i] = new OptionArgumentValue(leftArgs);
               AbstractArgumentValue<?>[] newAbstractArgumentValues =
                       new AbstractArgumentValue<?>[(int) Arrays.stream(abstractArgumentValues)
                               .filter(Objects::nonNull).count()];

               int offset = 0;
               for(int k = 0; k<abstractArgumentValues.length; k++) {
                   if(abstractArgumentValues[k] != null) {
                       newAbstractArgumentValues[k] = abstractArgumentValues[k-offset];
                   } else {
                       offset++;
                   }
               }

               return newAbstractArgumentValues;
           }
            abstractArgumentValues[i] = argumentType.parse(args[i]);
        }
        return abstractArgumentValues;
    }

    public List<ConsoleCommandExecutor> getRegisteredConsoleCommands() {
        return registeredConsoleCommands;
    }
}
