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

public class AddEntryFieldCommand extends ConsoleCommandExecutorHelper {

    private final StorageHandler storageHandler;

    public AddEntryFieldCommand(Console console, StorageHandler storageHandler) {
        super(console);
        this.storageHandler = storageHandler;
    }


    @Override
    public String[] getCommandIdentifiers() {
        return new String[] {
                "addEntryField",
                "EinatrgsFeld"
        };
    }

    @Override
    public String getCommandDescription(String usedCommandName) {
        return """
                Erstellt ein neuen Tresor Eintrag jeder Identifier kann nur einmal pro Tresor vergeben werden
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
                            new ArgumentData("fieldValue", "FIELD-VALUE", ArgumentType.STRING),
                            new ArgumentData("option", "OPTIONEN", ArgumentType.OPTION,
                                new String[] {
                                        "-p"
                                },
                                new String[]{
                                        "Definiert das Feld als Passwort Feld, dementsprechend wird der Wert nicht als Klartext angezeigt"
                                }
                            )
                    }
                ),
                new ArgumentOrder(
                        new ArgumentData[]{
                                new ArgumentData("storageId", "STORAGE-IDENTIFIER", ArgumentType.STRING),
                                new ArgumentData("entryId", "ENTRY-IDENTIFIER", ArgumentType.STRING),
                                new ArgumentData("fieldName", "FIELD-NAME", ArgumentType.STRING),
                                new ArgumentData("fieldValue", "FIELD-VALUE", ArgumentType.STRING)
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
        if(optionalField.isPresent()) {
            this.console.sendMessage("Es existiert bereits ein Feld mit dem Namen " + fieldName +
                    " im Eintrag " + entry.getIdentifier() + " im Tresor " + storage.getIdentifier() + "!");
            return;
        }

        String fieldValue = ((StringArgumentValue) argumentMap.get("fieldValue")).getValue();
        boolean isPassword = false;

        if(argumentMap.size() == 5) {
            if(((OptionArgumentValue) argumentMap.get("option")).containsOption("-p")) {
                isPassword = true;
            }
        }

        Field field = new Field(fieldName, fieldValue, isPassword);
        entry.addField(field);

        this.console.sendMessage("Das Feld " + fieldName + " wurde erfolgreich zum Eintrag " + entry.getIdentifier() + " im Tresor " +
                storage.getIdentifier() + " hinzugefügt!");
    }
}
