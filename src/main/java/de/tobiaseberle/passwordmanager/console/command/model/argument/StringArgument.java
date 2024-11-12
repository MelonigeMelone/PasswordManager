package de.tobiaseberle.passwordmanager.console.command.model.argument;

public class StringArgument extends Argument<String>{

    public StringArgument(String value) {
        super(value, ArgumentType.STRING);
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
