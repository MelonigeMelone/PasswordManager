package de.tobiaseberle.passwordmanager.storage.model;

import java.util.ArrayList;

public class Storage extends ArrayList<Entry> {

    private final String identifier;
    private final String password;

    public Storage(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }

    public String getIdentifier() {
        return identifier;
    }


    public String getPassword() {
        return password;
    }
}
