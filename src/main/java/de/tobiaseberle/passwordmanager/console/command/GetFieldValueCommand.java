package de.tobiaseberle.passwordmanager.console.command;

import de.tobiaseberle.passwordmanager.console.Console;
import de.tobiaseberle.passwordmanager.console.command.model.ConsoleCommandExecutorHelper;
import de.tobiaseberle.passwordmanager.console.command.model.argument.*;
import de.tobiaseberle.passwordmanager.storage.StorageHandler;
import java.util.List;

public class GetFieldValueCommand extends ConsoleCommandExecutorHelper {

    private final StorageHandler storageHandler;

    public GetFieldValueCommand(Console console, StorageHandler storageHandler) {
        super(console);
        this.storageHandler = storageHandler;
    }

    @Override
    public String[] getCommandIdentifiers() {
        return new String[] {
                "getFieldValue",
                "feldWert",
        };
    }

    @Override
    public String getCommandDescription(String usedCommandName) {
        return usedCommandName + """
                [STORAGE-IDENTIFIER] [ENTRY-IDENTIFIER] [FIELD-NAME]  - Gibt den Wert eines Feldes zurück
                """;
    }

    @Override
    public List<ArgumentOrder> getAllowedOrderOfArguments() {
        return List.of(
                new ArgumentOrder(
                        new ArgumentData[]{
                                new ArgumentData("storageID", "STORAGE-IDENTIFIER", ArgumentType.STRING),
                                new ArgumentData("entryIdentifier", "ENTRY-IDENTIFIER", ArgumentType.STRING),
                                new ArgumentData("fieldName", "FIELD-NAME", ArgumentType.STRING),
                        }
                )
        );
    }

    @Override
    protected void onCommandHelper(ArgumentMap argumentMap) {
        String storageIdentifier = ((StringArgumentValue) argumentMap.get("storageID")).getValue();
        String entryIdentifier = ((StringArgumentValue) argumentMap.get("entryIdentifier")).getValue();
        String fieldName = ((StringArgumentValue) argumentMap.get("fieldName")).getValue();

        if (storageHandler.getStorage(storageIdentifier).isEmpty()) {
            this.console.sendMessage("Es konnte kein Tresor mit dem Identifier " + storageIdentifier + " gefunden werden!");
            return;
        }

        if (storageHandler.getStorage(storageIdentifier).get().getEntry(entryIdentifier).isEmpty()) {
            this.console.sendMessage("Es konnte kein Eintrag mit dem Identifier " + entryIdentifier + " gefunden werden!");
            return;
        }

        if (storageHandler.getStorage(storageIdentifier).get().getEntry(entryIdentifier).get().getField(fieldName).isEmpty()) {
            this.console.sendMessage("Es konnte kein Feld mit dem Namen " + fieldName + " gefunden werden!");
            return;
        }

        String value = storageHandler.getStorage(storageIdentifier).get().getEntry(entryIdentifier).get().getField(fieldName).get().getValue();
        this.console.sendMessage("Der Wert des Feldes ist: " + value);
    }
}
