package de.tobiaseberle.passwordmanager.console.command.model.argument;

public class OptionArgumentValue extends AbstractArgumentValue<String[]> {

    private final String[] value;

    public OptionArgumentValue(String value) {
        super(value, ArgumentType.OPTION);

        this.value = value.split(" ");
    }

    public OptionArgumentValue(String[] value) {
        super(String.join(" ", value), ArgumentType.OPTION);

        this.value = value;
    }

    @Override
    public String[] getValue() {
        return value;
    }

    public boolean containsOption(String option) {
        for (String value : this.value) {
            if (value.equals(option)) {
                return true;
            }
        }
        return false;
    }
}
