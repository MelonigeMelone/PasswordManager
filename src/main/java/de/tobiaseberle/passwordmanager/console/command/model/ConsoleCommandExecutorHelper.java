package de.tobiaseberle.passwordmanager.console.command.model;

import de.tobiaseberle.passwordmanager.console.Console;
import de.tobiaseberle.passwordmanager.console.command.model.argument.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class ConsoleCommandExecutorHelper implements ConsoleCommandExecutor {

    protected final Console console;

    public ConsoleCommandExecutorHelper(Console console) {
        this.console = console;
    }

    @Override
    public void onCommand(String commandName, AbstractArgumentValue<?>[] args) {
        Optional<ArgumentOrder> argumentOrder = matchesArgumentOrder(args);
        if(argumentOrder.isEmpty()) {
            this.console.sendMessage(buildHelpMessage(commandName, true, true));
            return;
        }

        ArgumentOrder order = argumentOrder.get();
        ArgumentMap argumentMap = parseArguments(args, order);

        onCommandHelper(argumentMap);
    }

    protected abstract void onCommandHelper(ArgumentMap argumentMap);

    private Optional<ArgumentOrder> matchesArgumentOrder(AbstractArgumentValue<?>[] args) {
        return getAllowedOrderOfArguments().stream()
                .filter(argumentOrder -> argumentOrder.matchesOrder(args))
                .findFirst();
    }


    private ArgumentMap parseArguments(AbstractArgumentValue<?>[] args, ArgumentOrder argumentOrder) {
        ArgumentMap argumentMap = new ArgumentMap();
        for (int i = 0; i < args.length; i++) {
            AbstractArgumentValue<?> abstractArgumentValue = args[i];

            argumentMap.put(argumentOrder.getArgumentId(i), abstractArgumentValue);
        }
        return argumentMap;
    }

    public String buildHelpMessage(String commandName, boolean includeCommandDescription, boolean includeCommandOptions) {
        StringBuilder description = new StringBuilder(commandName);

        ArgumentOrder argumentOrder = getAllowedOrderOfArguments().get(0);

        Arrays.stream(argumentOrder.getArguments()).forEach(argumentData -> {
            description.append(" [");
            description.append(argumentData.getReadableArgumentId());
            description.append("]");
        });

        if(includeCommandDescription) {
            description.append(" - ");
            description.append(getCommandDescription(commandName));
        }

        if(includeCommandOptions) {
            description.append("Optionen:");
            Arrays.stream(argumentOrder.getArguments())
                    .filter(argumentData -> argumentData.getArgumentType().equals(ArgumentType.OPTION)
                            && argumentData.hasCommandOptions())
                    .forEach(argumentData -> {
                        for(int i = 0; i < argumentData.getCommandOptions().length; i++) {
                            description.append("\n");
                            description.append(argumentData.getCommandOptions()[i]);
                            description.append(" ");
                            description.append(argumentData.getCommandOptionsDescription()[i]);
                        }
            });
        }

        return  description.toString();
    }

    public abstract List<ArgumentOrder> getAllowedOrderOfArguments();

}
