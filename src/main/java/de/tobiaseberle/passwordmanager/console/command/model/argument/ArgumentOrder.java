package de.tobiaseberle.passwordmanager.console.command.model.argument;

public class ArgumentOrder {

    private final ArgumentData[] arguments;

    public ArgumentOrder(ArgumentData[] arguments) {
        this.arguments = arguments;
    }

    public boolean matchesOrder(AbstractArgumentValue<?>[] args) {
        if (args.length != arguments.length) {
            return false;
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i].getArgumentType() != arguments[i].getArgumentType()) {
                return false;
            }
        }

        return true;
    }

    public ArgumentData[] getArguments() {
        return arguments;
    }

    public String getArgumentId(int index) {
        return arguments[index].getArgumentId();
    }

}
