package de.tobiaseberle.passwordmanager.console.command;

import de.tobiaseberle.passwordmanager.console.Console;
import de.tobiaseberle.passwordmanager.console.command.model.ConsoleCommandExecutor;
import de.tobiaseberle.passwordmanager.console.command.model.argument.Argument;
import de.tobiaseberle.passwordmanager.console.command.model.argument.StringArgument;
import de.tobiaseberle.passwordmanager.storage.StorageHandler;
import de.tobiaseberle.passwordmanager.storage.model.Entry;
import de.tobiaseberle.passwordmanager.storage.model.Storage;

import java.util.Arrays;
import java.util.Optional;

public class AddStorageEntryCommand implements ConsoleCommandExecutor {

    private final Console console;
    private final StorageHandler storageHandler;

    public AddStorageEntryCommand(Console console, StorageHandler storageHandler) {
        this.console = console;
        this.storageHandler = storageHandler;
    }

    @Override
    public String[] getCommandIdentifiers() {
        return new String[] {
                "addStorageEntry",
                "tresorEintragErstellen"
        };
    }

    @Override
    public String getHelpText(String usedCommandName) {
        return usedCommandName + """
                [STORAGE-IDENTIFIER] [ENTRY-IDENTIFIER] [ENTRY-NAME] - Erstellt ein neuen Tresor Eintrag. Jeder Identifier kann nur einmal pro Tresor vergeben werden
                """;
    }

    @Override
    public void onCommand(String commandName, Argument<?>[] args) {
        if(args.length != 3 || !Arrays.stream(args).allMatch(argument -> argument instanceof StringArgument)) {
            this.console.sendMessage(getHelpText(commandName));
            return;
        }

        String storageIdentifier = ((StringArgument) args[0]).getValue();

        Optional<Storage> optionalStorage = storageHandler.getStorage(storageIdentifier);
        if(optionalStorage.isEmpty()) {
            this.console.sendMessage("Es konnte kein Tresor mit dem Identifier " + storageIdentifier + " gefunden werden!");
            return;
        }

        String entryIdentifier = ((StringArgument) args[1]).getValue();

        Storage storage = optionalStorage.get();
        Optional<Entry> optionalEntry = storage.getEntry(entryIdentifier);
        if(optionalEntry.isPresent()) {
            this.console.sendMessage("Es existiert bereits ein Eintrag mit dem Identifier " + entryIdentifier + " im Tresor " + storageIdentifier + "!");
            return;
        }

        String entryName = ((StringArgument) args[2]).getValue();
        Entry entry = new Entry(entryIdentifier, entryName);
        storage.add(entry);
        this.console.sendMessage("Der Eintrag " + entryIdentifier + " wurde erfolgreich im Tresor " + storageIdentifier + " erstellt!");
    }
}
