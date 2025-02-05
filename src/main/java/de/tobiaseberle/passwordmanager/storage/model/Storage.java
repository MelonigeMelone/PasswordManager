package de.tobiaseberle.passwordmanager.storage.model;

import java.util.ArrayList;

public class Storage extends ArrayList<Entry> {

    private final String identifier;

    public Storage(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
