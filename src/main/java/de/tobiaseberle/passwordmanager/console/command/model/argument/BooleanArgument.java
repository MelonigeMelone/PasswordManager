package de.tobiaseberle.passwordmanager.console.command.model.argument;

public class BooleanArgument extends Argument<Boolean> {

    private final boolean parsedValue;

    public BooleanArgument(String value) {
        super(value, ArgumentType.BOOLEAN);

        this.parsedValue = parseBoolean(value);
    }

    @Override
    public Boolean getValue() {
        return parsedValue;
    }

    private boolean parseBoolean(String value) {
        return value.equalsIgnoreCase("true") || value.equalsIgnoreCase("1") || value.equalsIgnoreCase("yes");
    }
}
