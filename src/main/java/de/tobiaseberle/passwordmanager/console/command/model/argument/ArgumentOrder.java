package de.tobiaseberle.passwordmanager.console.command.model.argument;

public class ArgumentOrder {

    private final ArgumentType[] argumentTypes;
    private final String[] argumentIds;

    public ArgumentOrder(ArgumentType[] argumentTypes, String[] argumentIds) {
        this.argumentTypes = argumentTypes;
        this.argumentIds = argumentIds;
    }

    public boolean matchesOrder(Argument<?>[] args) {
        if (args.length != argumentTypes.length) {
            return false;
        }

        for (int i = 0; i < args.length; i++) {
            if (args[i].getArgumentType() != argumentTypes[i]) {
                return false;
            }
        }

        return true;
    }

    public String[] getArgumentIds() {
        return argumentIds;
    }
}
