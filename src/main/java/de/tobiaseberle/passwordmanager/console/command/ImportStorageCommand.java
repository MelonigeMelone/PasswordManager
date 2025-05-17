package de.tobiaseberle.passwordmanager.console.command;

import de.tobiaseberle.passwordmanager.console.Console;
import de.tobiaseberle.passwordmanager.console.command.model.ConsoleCommandExecutorHelper;
import de.tobiaseberle.passwordmanager.console.command.model.argument.*;
import de.tobiaseberle.passwordmanager.storage.StorageHandler;
import de.tobiaseberle.passwordmanager.storage.model.Storage;

import java.io.File;
import java.util.List;

public class ImportStorageCommand extends ConsoleCommandExecutorHelper {

    private final StorageHandler storageHandler;

    public ImportStorageCommand(Console console, StorageHandler storageHandler) {
        super(console);
        this.storageHandler = storageHandler;
    }

    @Override
    public String[] getCommandIdentifiers() {
        return new String[]{
                "importStorage"
        };
    }

    @Override
    public String getCommandDescription(String usedCommandName) {
        return """
                Importiert einen verschlüsselten Tresor aus einer Datei.
                Die Datei muss mit dem Passwort des Tresors verschlüsselt worden sein.
                """;
    }

    @Override
    public List<ArgumentOrder> getAllowedOrderOfArguments() {
        return List.of(new ArgumentOrder(
                new ArgumentData[]{
                        new ArgumentData("id", "STORAGE-ID", ArgumentType.STRING),
                        new ArgumentData("path", "PATH", ArgumentType.STRING),
                        new ArgumentData("password", "PASSWORD", ArgumentType.STRING)
                }
        ));
    }

    @Override
    protected void onCommandHelper(ArgumentMap argumentMap) {
        String filePath = ((StringArgumentValue) argumentMap.get("path")).getValue();
        String password = ((StringArgumentValue) argumentMap.get("password")).getValue();
        String storageIdentifier = ((StringArgumentValue) argumentMap.get("id")).getValue();

        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            this.console.sendMessage("Die angegebene Datei existiert nicht: " + filePath);
            return;
        }

        if(storageHandler.existsStorage(storageIdentifier)) {
            this.console.sendMessage("Es existiert bereits ein Tresor mit dem Identifier '" + storageIdentifier + "'!");
            return;
        }

        try {
            Storage storage = storageHandler.decryptStorage(file, password);

            storageHandler.addStorage(storage);
            this.console.sendMessage("Der Tresor '" + storageIdentifier + "' wurde erfolgreich importiert.");
        } catch (Exception exception) {
            this.console.sendMessage("Fehler beim Entschlüsseln oder Importieren des Tresors: " + exception.getMessage());
        }
    }

}
