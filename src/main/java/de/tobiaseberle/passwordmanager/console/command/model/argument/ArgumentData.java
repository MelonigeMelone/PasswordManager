package de.tobiaseberle.passwordmanager.console.command.model.argument;

public class ArgumentData {

    private final String argumentId;
    private final String readableArgumentId;

    private final ArgumentType argumentType;
    private final String[] commandOptions;
    private final String[] commandOptionsDescription;

    public ArgumentData(String argumentId, String readableArgumentId, ArgumentType argumentType) {
        this.argumentId = argumentId;
        this.readableArgumentId = readableArgumentId;
        this.argumentType = argumentType;
        this.commandOptions = null;
        this.commandOptionsDescription = null;
    }

    public ArgumentData(String argumentId, String readableArgumentId, ArgumentType argumentType, String[] commandOptions, String[] commandOptionsDescription) {
        this.argumentId = argumentId;
        this.readableArgumentId = readableArgumentId;
        this.argumentType = argumentType;
        this.commandOptions = commandOptions;
        this.commandOptionsDescription = commandOptionsDescription;
    }


    public String getArgumentId() {
        return argumentId;
    }

    public String getReadableArgumentId() {
        return readableArgumentId;
    }

    public ArgumentType getArgumentType() {
        return argumentType;
    }

    public boolean hasCommandOptions() {
        return commandOptions != null;
    }

    public String[] getCommandOptions() {
        return commandOptions;
    }

    public String[] getCommandOptionsDescription() {
        return commandOptionsDescription;
    }
}
