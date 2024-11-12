package de.tobiaseberle.passwordmanager.console.command;

import de.tobiaseberle.passwordmanager.console.Console;
import de.tobiaseberle.passwordmanager.console.command.model.ConsoleCommandExecutor;
import de.tobiaseberle.passwordmanager.console.command.model.argument.Argument;
import de.tobiaseberle.passwordmanager.console.command.model.argument.ArgumentType;
import de.tobiaseberle.passwordmanager.console.command.model.argument.IntegerArgument;
import de.tobiaseberle.passwordmanager.console.command.model.argument.StringArgument;
import de.tobiaseberle.passwordmanager.generation.PasswordGenerator;
import de.tobiaseberle.passwordmanager.generation.exception.PasswordGenerationException;

public class GeneratePasswordCommand implements ConsoleCommandExecutor {
    private final Console console;


    public GeneratePasswordCommand(Console console) {
        this.console = console;
    }

    @Override
    public String[] getCommandIdentifiers() {
        return new String[] {
                "passwortGenerieren",
        };
    }

    @Override
    public String getHelpText() {
        return "passwortGenerieren [LAENGE] [OPTIONEN] - Generiert ein Passwort mit der angegebenen Länge und den angegebenen Optionen. " +
                "Es muss mindestens eine Option angegeben sein.\n" +
                "Optionen:\n" +
                "-g Großbuchstaben\n" +
                "-k Kleinbuchstaben\n" +
                "-s Sonderzeichen\n" +
                "-z Zahlen\n";
    }

    @Override
    public void onCommand(Argument<?>[] args) {
        if(args.length < 2 || !args[0].getArgumentType().equals(ArgumentType.INTEGER)) {
            this.console.sendMessage(getHelpText());
            return;
        }

        IntegerArgument lengthArgument = (IntegerArgument) args[0];
        int length = lengthArgument.getValue();

        boolean includeUppercase = false;
        boolean includeLowercase = false;
        boolean includeSpecialChars = false;
        boolean includeNumbers = false;

        for (int i = 1; i < args.length; i++) {
            if (!(args[i] instanceof StringArgument)) {
                this.console.sendMessage("Das Argument " + args[i].getValue() + " ist keine korrekte Option.\n" + getHelpText());
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
                default:
                    this.console.sendMessage("Das Argument " + option + " ist keine korrekte Option.\n" + getHelpText());
                    return;
            }
        }

        try {
            String generatedPassword = PasswordGenerator.generatePassword(length, includeLowercase, includeUppercase,
                    includeNumbers, includeSpecialChars);
            this.console.sendMessage("Das generierte Passwort lautet: " + generatedPassword);
        } catch (PasswordGenerationException exception) {
            this.console.sendMessage("Beim Generieren des Passworts ist folgender Fehler aufgetreten" +
                    exception.getMessage() + "\n" + getHelpText());

        }

    }
}
