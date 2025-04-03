package de.tobiaseberle.passwordmanager.console.command.model.argument;

public class IntegerArgumentValue extends AbstractArgumentValue<Integer> {

    private final int parsedValue;

    public IntegerArgumentValue(String value) {
        super(value, ArgumentType.INTEGER);

        this.parsedValue = Integer.parseInt(value);
    }

    @Override
    public Integer getValue() {
        return parsedValue;
    }
}
