package de.tobiaseberle.passwordmanager.storage;

import de.tobiaseberle.passwordmanager.storage.model.Storage;

public class StorageHandler {

    private final Storage storage;

    public StorageHandler() {
        this.storage = new Storage();
    }

    public Storage getStorage() {
        return this.storage;
    }
}
