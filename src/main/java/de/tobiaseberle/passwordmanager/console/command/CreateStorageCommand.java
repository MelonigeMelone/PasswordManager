package de.tobiaseberle.passwordmanager.console.command;

import de.tobiaseberle.passwordmanager.console.Console;
import de.tobiaseberle.passwordmanager.console.command.model.ConsoleCommandExecutor;
import de.tobiaseberle.passwordmanager.console.command.model.argument.Argument;
import de.tobiaseberle.passwordmanager.console.command.model.argument.StringArgument;
import de.tobiaseberle.passwordmanager.storage.StorageHandler;
import de.tobiaseberle.passwordmanager.storage.model.Storage;

public class CreateStorageCommand implements ConsoleCommandExecutor {

    private final Console console;
    private final StorageHandler storageHandler;

    public CreateStorageCommand(Console console, StorageHandler storageHandler) {
        this.console = console;
        this.storageHandler = storageHandler;
    }

    @Override
    public String[] getCommandIdentifiers() {
        return new String[] {
                "tresorErstellen",
                "createStorage"
        };
    }

    @Override
    public String getHelpText(String usedCommandName) {
        return usedCommandName + """
                [IDENTIFIER] [PASSWORD] - Erstellt ein neuen Tresor der zum Speichern von Passwörtern verwendet werden kann. \
                ACHTUNG: OHNE DAS PASSWORT SIND ALLE INFORMATIONEN IM TRESOR VERLOREN!
                """;
    }

    @Override
    public void onCommand(String commandName, Argument<?>[] args) {
        if(args.length != 2 || !(args[0] instanceof StringArgument) || !(args[1] instanceof StringArgument)) {
            this.console.sendMessage(getHelpText(commandName));
            return;
        }

        String identifier = ((StringArgument) args[0]).getValue();
        String password = ((StringArgument) args[0]).getValue();

        if(storageHandler.existsStorage(identifier)) {
            this.console.sendMessage("Es ist bereits ein Tresor mit dem Identifer '" + identifier + "' geladen, bitte wähle einen anderen Identifier!");
            return;
        }

        Storage storage = new Storage(identifier, password);
        storageHandler.addStorage(storage);

        this.console.sendMessage("Der Tresor mit dem Identifier '" + identifier + "' wurde erfolgreich erstellt!");
    }
}
