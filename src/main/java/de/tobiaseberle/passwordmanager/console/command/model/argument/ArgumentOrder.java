package de.tobiaseberle.passwordmanager.console.command.model.argument;

public class ArgumentOrder {

    private final ArgumentData[] arguments;

    public ArgumentOrder(ArgumentData[] arguments) {
        this.arguments = arguments;
    }

    public boolean matchesOrder(AbstractArgumentValue<?>[] args) {
        if (!lengthCheck(args.length)) {
            return false;
        }

        ArgumentData lastArgumentData = null;
        for (int i = 0; i < args.length; i++) {
            if(!(i >= arguments.length && lastArgumentData != null && lastArgumentData.getArgumentType().equals(ArgumentType.OPTION))) {
                lastArgumentData = arguments[i];
                if (args[i].getArgumentType() != arguments[i].getArgumentType()) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean lengthCheck(int length) {
        int minLength = 0, maxLength = 0;

        for(ArgumentData argumentData : arguments) {
            if(argumentData.hasCommandOptions()) {
               minLength+=1;
               maxLength+=argumentData.getCommandOptions().length;
            } else {
                minLength+=1;
                maxLength+=1;
            }
        }


        return length >= minLength && length<= maxLength;
    }

    public ArgumentData[] getArguments() {
        return arguments;
    }

    public String getArgumentId(int index) {
        return arguments[index].getArgumentId();
    }

}
