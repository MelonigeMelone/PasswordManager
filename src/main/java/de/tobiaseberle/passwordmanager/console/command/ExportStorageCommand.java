package de.tobiaseberle.passwordmanager.console.command;

import de.tobiaseberle.passwordmanager.console.Console;
import de.tobiaseberle.passwordmanager.console.command.model.ConsoleCommandExecutorHelper;
import de.tobiaseberle.passwordmanager.console.command.model.argument.*;
import de.tobiaseberle.passwordmanager.storage.StorageHandler;
import de.tobiaseberle.passwordmanager.storage.model.Storage;

import java.util.List;
import java.util.Optional;

public class ExportStorageCommand extends ConsoleCommandExecutorHelper {

    private final StorageHandler storageHandler;

    public ExportStorageCommand(Console console, StorageHandler storageHandler) {
        super(console);
        this.storageHandler = storageHandler;
    }

    @Override
    public String[] getCommandIdentifiers() {
        return new String[]{
                "exportStorage"
        };
    }

    @Override
    public String getCommandDescription(String usedCommandName) {
        return """
                Exportiert den Tresor in eine Datei, welche mit dem Passwort des Tresors verschlüsselt is, kann wieder importiert werden.
                """;
    }

    @Override
    public List<ArgumentOrder> getAllowedOrderOfArguments() {
        return List.of(new ArgumentOrder(
                new ArgumentData[]{
                        new ArgumentData("id", "STORAGE-IDENTIFIER", ArgumentType.STRING),
                        new ArgumentData("path", "Pfad", ArgumentType.STRING)
                }
        ));
    }

    @Override
    protected void onCommandHelper(ArgumentMap argumentMap) {
        String identifier = ((StringArgumentValue) argumentMap.get("id")).getValue();
        String path = ((StringArgumentValue) argumentMap.get("path")).getValue();

        Optional<Storage> optionalStorage = storageHandler.getStorage(identifier);
        if (optionalStorage.isEmpty()) {
            this.console.sendMessage("Es konnte kein Tresor mit dem Identifier " + identifier + " gefunden werden!");
            return;
        }

        Storage storage = optionalStorage.get();

        try {
            storageHandler.encryptStorage(storage, path, identifier);
            this.console.sendMessage("Der Tresor mit dem Identifier '" + identifier + "' wurde erfolgreich nach " + path + " exportiert!");
        } catch (Exception exception) {
            this.console.sendMessage("Beim Exportieren des Tresors trat ein Fehler auf." + exception.getMessage());
        }
    }
}

