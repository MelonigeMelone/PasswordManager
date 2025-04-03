package de.tobiaseberle.passwordmanager.console.command;

import de.tobiaseberle.passwordmanager.console.Console;
import de.tobiaseberle.passwordmanager.console.command.model.ConsoleCommandExecutor;
import de.tobiaseberle.passwordmanager.console.command.model.argument.AbstractArgumentValue;
import de.tobiaseberle.passwordmanager.console.command.model.argument.StringArgumentValue;
import de.tobiaseberle.passwordmanager.storage.StorageHandler;

import java.util.Arrays;

public class GetFieldValueCommand implements ConsoleCommandExecutor {

    private final Console console;
    private final StorageHandler storageHandler;

    public GetFieldValueCommand(Console console, StorageHandler storageHandler) {
        this.console = console;
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
    public void onCommand(String commandName, AbstractArgumentValue<?>[] args) {
        if(args.length != 3 || !Arrays.stream(args).allMatch(argument -> argument instanceof StringArgumentValue)) {
            this.console.sendMessage(getCommandDescription(commandName));
            return;
        }
    }
}
