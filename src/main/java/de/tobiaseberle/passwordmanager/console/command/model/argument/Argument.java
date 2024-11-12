package de.tobiaseberle.passwordmanager.console.command.model.argument;

public abstract class Argument<T> {

    protected final String value;
    protected final ArgumentType argumentType;

    public Argument(String value, ArgumentType argumentType) {
        this.value = value;
        this.argumentType = argumentType;
    }

    public abstract T getValue();


    public ArgumentType getArgumentType() {
        return argumentType;
    }

}