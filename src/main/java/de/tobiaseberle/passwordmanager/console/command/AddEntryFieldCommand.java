package de.tobiaseberle.passwordmanager.console.command;

import de.tobiaseberle.passwordmanager.console.Console;
import de.tobiaseberle.passwordmanager.console.command.model.ConsoleCommandExecutor;
import de.tobiaseberle.passwordmanager.console.command.model.argument.Argument;
import de.tobiaseberle.passwordmanager.console.command.model.argument.StringArgument;
import de.tobiaseberle.passwordmanager.storage.StorageHandler;
import de.tobiaseberle.passwordmanager.storage.model.Entry;
import de.tobiaseberle.passwordmanager.storage.model.Field;
import de.tobiaseberle.passwordmanager.storage.model.Storage;

import java.util.Arrays;
import java.util.Optional;

public class AddEntryFieldCommand implements ConsoleCommandExecutor {

    private final Console console;
    private final StorageHandler storageHandler;

    public AddEntryFieldCommand(Console console, StorageHandler storageHandler) {
        this.console = console;
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
    public String getHelpText(String usedCommandName) {
        return usedCommandName + """
                [STORAGE-IDENTIFIER] [ENTRY-IDENTIFIER] [FIELD-NANE] [FIELD-VALUE] - Erstellt ein neuen Tresor Eintrag jeder Identifier kann nur einmal pro Tresor vergeben werden
                Optionen:
                -p Definiert das Feld als Passwort Feld, dementsprechend wird der Wert nicht als Klartext angezeigt
                """;
    }

    @Override
    public void onCommand(String commandName, Argument<?>[] args) {
        if(args.length < 4 || args.length > 6 || !Arrays.stream(args).allMatch(argument -> argument instanceof StringArgument)) {
            this.console.sendMessage(getHelpText(commandName));
            return;
        }

        String storageIdentifier = ((StringArgument) args[0]).getValue();
        Optional<Storage> optionalStorage = storageHandler.getStorage(storageIdentifier);
        if(optionalStorage.isEmpty()) {
            this.console.sendMessage("Es konnte kein Tresor mit dem Identifier " + storageIdentifier + " gefunden werden!");
            return;
        }

        Storage storage = optionalStorage.get();

        String entryIdentifier = ((StringArgument) args[1]).getValue();
        Optional<Entry> optionalEntry = storage.getEntry(entryIdentifier);
        if(optionalEntry.isEmpty()) {
            this.console.sendMessage("Es konnte kein Eintrag mit dem Identifier " + entryIdentifier + " im Tresor " +
                    storage.getIdentifier() + " gefunden werden!");
            return;
        }

        Entry entry = optionalEntry.get();
        String fieldName = ((StringArgument) args[2]).getValue();

        Optional<Field> optionalField = entry.getField(fieldName);
        if(optionalField.isPresent()) {
            this.console.sendMessage("Es existiert bereits ein Feld mit dem Namen " + ((StringArgument) args[2]).getValue() +
                    " im Eintrag " + entry.getIdentifier() + " im Tresor " + storage.getIdentifier() + "!");
            return;
        }

        String fieldValue = ((StringArgument) args[3]).getValue();
        boolean isPassword = false;

        if(args.length > 4) {
            if(args[4] instanceof StringArgument) {
                if(((StringArgument) args[4]).getValue().equals("-p")) {
                    isPassword = true;
                }
            }
        }

        Field field = new Field(fieldName, fieldValue, isPassword);
        entry.addField(field);

        this.console.sendMessage("Das Feld " + fieldName + " wurde erfolgreich zum Eintrag " + entry.getIdentifier() + " im Tresor " +
                storage.getIdentifier() + " hinzugefügt!");
    }
}
