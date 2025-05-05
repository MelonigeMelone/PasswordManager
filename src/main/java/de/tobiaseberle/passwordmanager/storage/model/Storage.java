package de.tobiaseberle.passwordmanager.storage.model;

import java.util.ArrayList;
import java.util.Optional;

public class Storage extends ArrayList<Entry> {

    private String identifier;
    private String password;

    public Storage() {
    }

    public Storage(String identifier, String password) {
        this.identifier = identifier;
        this.password = password;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Optional<Entry> getEntry(String id) {
        return this.stream().filter(entry -> entry.getIdentifier().equals(id)).findFirst();
    }
}
