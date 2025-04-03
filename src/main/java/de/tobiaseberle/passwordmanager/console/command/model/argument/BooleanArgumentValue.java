package de.tobiaseberle.passwordmanager.console.command.model.argument;

public class BooleanArgumentValue extends AbstractArgumentValue<Boolean> {

    private final boolean parsedValue;

    public BooleanArgumentValue(String value) {
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
