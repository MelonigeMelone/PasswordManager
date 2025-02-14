package de.tobiaseberle.passwordmanager.console.command.model;

import de.tobiaseberle.passwordmanager.console.Console;
import de.tobiaseberle.passwordmanager.console.command.model.argument.Argument;
import de.tobiaseberle.passwordmanager.console.command.model.argument.ArgumentMap;
import de.tobiaseberle.passwordmanager.console.command.model.argument.ArgumentOrder;

import java.util.List;
import java.util.Optional;

public abstract class ConsoleCommandExecutorHelper implements ConsoleCommandExecutor {

    protected final Console console;

    public ConsoleCommandExecutorHelper(Console console) {
        this.console = console;
    }

    @Override
    public void onCommand(String commandName, Argument<?>[] args) {
        Optional<ArgumentOrder> argumentOrder = matchesArgumentOrder(args);
        if(argumentOrder.isEmpty()) {
            this.console.sendMessage(getHelpText(commandName));
            return;
        }

        ArgumentOrder order = argumentOrder.get();
        ArgumentMap argumentMap = parseArguments(args, order);

        onCommandHelper(argumentMap);
    }

    protected abstract void onCommandHelper(ArgumentMap argumentMap);

    private Optional<ArgumentOrder> matchesArgumentOrder(Argument<?>[] args) {
        return getAllowedOrderOfArguments().stream()
                .filter(argumentOrder -> argumentOrder.matchesOrder(args))
                .findFirst();
    }


    private ArgumentMap parseArguments(Argument<?>[] args, ArgumentOrder argumentOrder) {
        ArgumentMap argumentMap = new ArgumentMap();
        for (int i = 0; i < args.length; i++) {
            Argument<?> argument = args[i];
            argumentMap.put(argumentOrder.getArgumentIds()[i], argument);
        }
        return argumentMap;
    }

    public abstract List<ArgumentOrder> getAllowedOrderOfArguments();

}
