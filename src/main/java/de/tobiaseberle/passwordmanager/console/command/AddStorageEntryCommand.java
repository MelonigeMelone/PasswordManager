package de.tobiaseberle.passwordmanager.console.command;

import de.tobiaseberle.passwordmanager.console.Console;
import de.tobiaseberle.passwordmanager.console.command.model.ConsoleCommandExecutor;
import de.tobiaseberle.passwordmanager.console.command.model.argument.AbstractArgumentValue;
import de.tobiaseberle.passwordmanager.console.command.model.argument.StringArgumentValue;
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
    public String getCommandDescription(String usedCommandName) {
        return usedCommandName + """
                [STORAGE-IDENTIFIER] [ENTRY-IDENTIFIER] [ENTRY-NAME] - Erstellt ein neuen Tresor Eintrag. Jeder Identifier kann nur einmal pro Tresor vergeben werden
                """;
    }

    @Override
    public void onCommand(String commandName, AbstractArgumentValue<?>[] args) {
        if(args.length != 3 || !Arrays.stream(args).allMatch(argument -> argument instanceof StringArgumentValue)) {
            this.console.sendMessage(getCommandDescription(commandName));
            return;
        }

        String storageIdentifier = ((StringArgumentValue) args[0]).getValue();

        Optional<Storage> optionalStorage = storageHandler.getStorage(storageIdentifier);
        if(optionalStorage.isEmpty()) {
            this.console.sendMessage("Es konnte kein Tresor mit dem Identifier " + storageIdentifier + " gefunden werden!");
            return;
        }

        String entryIdentifier = ((StringArgumentValue) args[1]).getValue();

        Storage storage = optionalStorage.get();
        Optional<Entry> optionalEntry = storage.getEntry(entryIdentifier);
        if(optionalEntry.isPresent()) {
            this.console.sendMessage("Es existiert bereits ein Eintrag mit dem Identifier " + entryIdentifier + " im Tresor " + storageIdentifier + "!");
            return;
        }

        String entryName = ((StringArgumentValue) args[2]).getValue();
        Entry entry = new Entry(entryIdentifier, entryName);
        storage.add(entry);
        this.console.sendMessage("Der Eintrag " + entryIdentifier + " wurde erfolgreich im Tresor " + storageIdentifier + " erstellt!");
    }
}
