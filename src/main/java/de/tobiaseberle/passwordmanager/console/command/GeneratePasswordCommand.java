package de.tobiaseberle.passwordmanager.console.command;

import de.tobiaseberle.passwordmanager.console.Console;
import de.tobiaseberle.passwordmanager.console.command.model.ConsoleCommandExecutor;
import de.tobiaseberle.passwordmanager.console.command.model.argument.Argument;
import de.tobiaseberle.passwordmanager.console.command.model.argument.ArgumentType;
import de.tobiaseberle.passwordmanager.console.command.model.argument.IntegerArgument;
import de.tobiaseberle.passwordmanager.console.command.model.argument.StringArgument;
import de.tobiaseberle.passwordmanager.generation.PasswordGenerator;
import de.tobiaseberle.passwordmanager.generation.exception.PasswordGenerationException;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class GeneratePasswordCommand implements ConsoleCommandExecutor {

    private final Console console;

    public GeneratePasswordCommand(Console console) {
        this.console = console;
    }

    @Override
    public String[] getCommandIdentifiers() {
        return new String[] {
                "passwortGenerieren",
                "genPw"
        };
    }

    @Override
    public String getHelpText(String usedCommandName) {
        return usedCommandName + """
                 [LAENGE] [OPTIONEN] - Generiert ein Passwort mit der angegebenen Länge und den angegebenen Optionen. \
                Es muss mindestens eine Option angegeben sein.
                Optionen:
                -g Großbuchstaben
                -k Kleinbuchstaben
                -s Sonderzeichen
                -z Zahlen
                
                -v Das Passwort verstecken, in die Zwischenablage kopieren und nicht in der Konsole anzeigen
                """;
    }

    @Override
    public void onCommand(String commandName, Argument<?>[] args) {
        if(args.length < 2 || !args[0].getArgumentType().equals(ArgumentType.INTEGER)) {
            this.console.sendMessage(getHelpText(commandName));
            return;
        }

        IntegerArgument lengthArgument = (IntegerArgument) args[0];
        int length = lengthArgument.getValue();

        boolean includeUppercase = false;
        boolean includeLowercase = false;
        boolean includeSpecialChars = false;
        boolean includeNumbers = false;

        boolean hidePassword = false;

        for (int i = 1; i < args.length; i++) {
            if (!(args[i] instanceof StringArgument)) {
                this.console.sendMessage("Das Argument '" + args[i].getValue() + "' ist keine korrekte Option.\n" + getHelpText(commandName));
                return;
            }

            String option = ((StringArgument) args[i]).getValue();
            switch (option) {
                case "-g":
                    includeUppercase = true;
                    break;
                case "-k":
                    includeLowercase = true;
                    break;
                case "-s":
                    includeSpecialChars = true;
                    break;
                case "-z":
                    includeNumbers = true;
                    break;
                case "-v":
                    hidePassword = true;
                    break;
                default:
                    this.console.sendMessage("Das Argument '" + option + "' ist keine korrekte Option.\n" + getHelpText(commandName));
                    return;
            }
        }

        try {
            String generatedPassword = PasswordGenerator.generatePassword(length, includeLowercase, includeUppercase,
                    includeNumbers, includeSpecialChars);
            if(hidePassword) {
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

                StringSelection selectionPassword = new StringSelection(generatedPassword);
                clipboard.setContents(selectionPassword, null);

                this.console.sendMessage("Das generierte Passwort wurde erfolgreich generiert und in die Zwischenablage kopiert!");
            } else {
                this.console.sendMessage("Das generierte Passwort lautet: '" + generatedPassword + "'");
            }
        } catch (PasswordGenerationException exception) {
            this.console.sendMessage("Beim Generieren des Passworts ist folgender Fehler aufgetreten" +
                    exception.getMessage() + "\n" + getHelpText(commandName));

        }

    }
}
