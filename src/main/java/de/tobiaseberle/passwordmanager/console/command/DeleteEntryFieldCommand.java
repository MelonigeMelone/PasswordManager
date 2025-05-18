package de.tobiaseberle.passwordmanager.console.command;

import de.tobiaseberle.passwordmanager.console.Console;
import de.tobiaseberle.passwordmanager.console.command.model.ConsoleCommandExecutorHelper;
import de.tobiaseberle.passwordmanager.console.command.model.argument.*;
import de.tobiaseberle.passwordmanager.storage.StorageHandler;
import de.tobiaseberle.passwordmanager.storage.model.Entry;
import de.tobiaseberle.passwordmanager.storage.model.Field;
import de.tobiaseberle.passwordmanager.storage.model.Storage;

import java.util.List;
import java.util.Optional;

public class DeleteEntryFieldCommand extends ConsoleCommandExecutorHelper {

    private final StorageHandler storageHandler;

    public DeleteEntryFieldCommand(Console console, StorageHandler storageHandler) {
        super(console);
        this.storageHandler = storageHandler;
    }


    @Override
    public String[] getCommandIdentifiers() {
        return new String[] {
                "deleteEntryField"
        };
    }

    @Override
    public String getCommandDescription(String usedCommandName) {
        return """
                Entfernt ein Feld aus einem Tresor Eintrag
                """;
    }

    @Override
    public List<ArgumentOrder> getAllowedOrderOfArguments() {
        return List.of(
                new ArgumentOrder(
                        new ArgumentData[]{
                                new ArgumentData("storageId", "STORAGE-IDENTIFIER", ArgumentType.STRING),
                                new ArgumentData("entryId", "ENTRY-IDENTIFIER", ArgumentType.STRING),
                                new ArgumentData("fieldName", "FIELD-NAME", ArgumentType.STRING),
                        }
                )
        );
    }

    @Override
    protected void onCommandHelper(ArgumentMap argumentMap) {
        String storageId = ((StringArgumentValue) argumentMap.get("storageId")).getValue();

        Optional<Storage> optionalStorage = storageHandler.getStorage(storageId);
        if(optionalStorage.isEmpty()) {
            this.console.sendMessage("Es konnte kein Tresor mit dem Identifier " + storageId + " gefunden werden!");
            return;
        }

        String entryId = ((StringArgumentValue) argumentMap.get("entryId")).getValue();
        Storage storage = optionalStorage.get();

        Optional<Entry> optionalEntry = storage.getEntry(entryId);
        if(optionalEntry.isEmpty()) {
            this.console.sendMessage("Es konnte kein Eintrag mit dem Identifier " + entryId + " im Tresor " +
                    storage.getIdentifier() + " gefunden werden!");
            return;
        }

        Entry entry = optionalEntry.get();
        String fieldName = ((StringArgumentValue) argumentMap.get("fieldName")).getValue();

        Optional<Field> optionalField = entry.getField(fieldName);
        if(optionalField.isEmpty()) {
            this.console.sendMessage("Es konnte kein Feld mit dem Namen " + fieldName + " gefunden werden!");
            return;
        }

        entry.removeField(optionalField.get());

        this.console.sendMessage("Das Feld " + fieldName + " wurde erfolgreich aus dem Eintrag " + entry.getIdentifier() + " im Tresor " +
                storage.getIdentifier() + " entfernt!");
    }
}
