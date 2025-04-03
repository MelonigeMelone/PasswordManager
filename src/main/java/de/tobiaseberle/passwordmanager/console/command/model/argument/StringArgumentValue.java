package de.tobiaseberle.passwordmanager.console.command.model.argument;

public class StringArgumentValue extends AbstractArgumentValue<String> {

    public StringArgumentValue(String value) {
        super(value, ArgumentType.STRING);
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
