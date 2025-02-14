package de.tobiaseberle.passwordmanager.console.command;

import de.tobiaseberle.passwordmanager.console.Console;
import de.tobiaseberle.passwordmanager.console.command.model.ConsoleCommandExecutorHelper;
import de.tobiaseberle.passwordmanager.console.command.model.argument.*;
import de.tobiaseberle.passwordmanager.storage.StorageHandler;
import de.tobiaseberle.passwordmanager.storage.model.Storage;

import java.util.List;

public class CreateStorageCommand extends ConsoleCommandExecutorHelper {

    private final StorageHandler storageHandler;

    public CreateStorageCommand(Console console, StorageHandler storageHandler) {
        super(console);
        this.storageHandler = storageHandler;
    }

    @Override
    public String[] getCommandIdentifiers() {
        return new String[]{
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
    public List<ArgumentOrder> getAllowedOrderOfArguments() {
        return List.of(new ArgumentOrder(
                new ArgumentType[]{ArgumentType.STRING, ArgumentType.STRING},
                new String[]{"IDENTIFIER", "PASSWORD"}
        ));
    }

    @Override
    protected void onCommandHelper(ArgumentMap argumentMap) {
        String identifier = ((StringArgument) argumentMap.get("IDENTIFIER")).getValue();
        String password = ((StringArgument) argumentMap.get("PASSWORD")).getValue();

        if (storageHandler.existsStorage(identifier)) {
            this.console.sendMessage("Es ist bereits ein Tresor mit dem Identifer '" + identifier + "' geladen, bitte wähle einen anderen Identifier!");
            return;
        }

        Storage storage = new Storage(identifier, password);
        storageHandler.addStorage(storage);

        this.console.sendMessage("Der Tresor mit dem Identifier '" + identifier + "' wurde erfolgreich erstellt!");
    }
}
