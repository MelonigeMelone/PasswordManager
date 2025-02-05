package de.tobiaseberle.passwordmanager.console;

import de.tobiaseberle.passwordmanager.console.command.handler.ConsoleCommandHandler;
import de.tobiaseberle.passwordmanager.storage.StorageHandler;
import de.tobiaseberle.passwordmanager.util.DateMethods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Console implements Runnable {

    private final Scanner localScanner;

    private final ConsoleCommandHandler consoleCommandHandler;

    private boolean running = true;

    public Console() {
        this.consoleCommandHandler = new ConsoleCommandHandler(this, new StorageHandler());
        this.localScanner = new Scanner(System.in);
    }


    @Override
    public void run() {
        read();
    }

    public void sendMessage(String message) {
        System.out.print(getCommandLinePrefix() + message);
        System.out.println();
    }

    private String getCommandLinePrefix() {
        String formattedDate = DateMethods.getFormattedDate(System.currentTimeMillis(), "HH:mm:ss");
        return "[" + formattedDate + "] PasswordManager > ";

    }

    private void read() {

        System.out.print(getCommandLinePrefix());

        String str1 = localScanner.nextLine();
        if (str1 != null) {
            String[] args = str1.split(" ");
            consoleCommandHandler.dispatchCommand(args);
        }

        if (!running) {
            return;
        }

        read();
    }
}
