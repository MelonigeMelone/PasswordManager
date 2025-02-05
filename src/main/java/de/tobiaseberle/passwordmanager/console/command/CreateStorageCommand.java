package de.tobiaseberle.passwordmanager.console.command;

import de.tobiaseberle.passwordmanager.console.Console;
import de.tobiaseberle.passwordmanager.console.command.model.ConsoleCommandExecutor;
import de.tobiaseberle.passwordmanager.console.command.model.argument.Argument;
import de.tobiaseberle.passwordmanager.console.command.model.argument.StringArgument;

public class CreateStorageCommand implements ConsoleCommandExecutor {

    private final Console console;

    public CreateStorageCommand(Console console) {
        this.console = console;
    }

    @Override
    public String[] getCommandIdentifiers() {
        return new String[] {
                "tresorErstellen",
                "createStorage"
        };
    }

    @Override
    public String getHelpText(String usedCommandName) {
        return usedCommandName + """
                [IDENTIFIER] [OPTIONEN] - Erstellt ein neuen Tresor der zum Speichern von Passwörtern verwendet werden kann. \
                
                ACHTUNG: IST KEIN PASSWORT GESETZT IST DER TRESOR ZUGÄNGLICH.
                
                Optionen:
                -p [PASSWORT] - Setzt ein Passwort mit dem der Tresor verschlüsselt werden kann
                """;
    }

    @Override
    public void onCommand(String commandName, Argument<?>[] args) {
        if(args.length < 1 || !(args[0] instanceof StringArgument)) {
            this.console.sendMessage(getHelpText(commandName));
            return;
        }

        String identifer = ((StringArgument) args[0]).getValue();





    }
}
