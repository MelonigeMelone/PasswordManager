package de.tobiaseberle.passwordmanager.console.command;

import de.tobiaseberle.passwordmanager.console.Console;
import de.tobiaseberle.passwordmanager.console.command.model.ConsoleCommandExecutorHelper;
import de.tobiaseberle.passwordmanager.console.command.model.argument.*;
import de.tobiaseberle.passwordmanager.generation.PasswordGenerator;
import de.tobiaseberle.passwordmanager.generation.exception.PasswordGenerationException;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;

public class GeneratePasswordCommand extends ConsoleCommandExecutorHelper {

    public GeneratePasswordCommand(Console console) {
        super(console);
    }

    @Override
    public String[] getCommandIdentifiers() {
        return new String[] {
                "passwortGenerieren",
                "genPw"
        };
    }

    @Override
    public String getCommandDescription(String usedCommandName) {
        return  """
                Generiert ein Passwort mit der angegebenen Länge und den angegebenen Optionen. \
                Es muss mindestens eine Option angegeben sein.
             
                """;
    }

    @Override
    public List<ArgumentOrder> getAllowedOrderOfArguments() {
        return List.of(
                new ArgumentOrder(
                        new ArgumentData[]{
                                new ArgumentData("length", "LAENGE", ArgumentType.INTEGER),
                                new ArgumentData("options", "OPTIONEN", ArgumentType.OPTION, new String[] {
                                        "-g",
                                        "-k",
                                        "-s",
                                        "-z",
                                        "-v"
                                }, new String[] {
                                        "Großbuchstaben",
                                        "Kleinbuchstaben",
                                        "Sonderzeichen",
                                        "Zahlen",
                                        "Das Passwort verstecken, in die Zwischenablage kopieren und nicht in der Konsole anzeigen"
                                }),
                        }
                )
        );
    }

    @Override
    protected void onCommandHelper(ArgumentMap argumentMap) {
        int length = ((IntegerArgumentValue) argumentMap.get("length")).getValue();

        OptionArgumentValue options = (OptionArgumentValue) argumentMap.get("options");

        boolean includeUppercase = options.containsOption("-g");
        boolean includeLowercase = options.containsOption("-k");
        boolean includeSpecialChars = options.containsOption("-s");
        boolean includeNumbers = options.containsOption("-z");

        boolean hidePassword = options.containsOption("-v");

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
                    exception.getMessage());

        }
    }
}
