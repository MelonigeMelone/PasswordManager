package de.tobiaseberle.passwordmanager.console.command.model.argument;

public class IntegerArgument extends Argument<Integer> {

    private final int parsedValue;

    public IntegerArgument(String value) {
        super(value, ArgumentType.INTEGER);

        this.parsedValue = Integer.parseInt(value);
    }

    @Override
    public Integer getValue() {
        return parsedValue;
    }
}
