package de.tobiaseberle.passwordmanager.storage;

import de.tobiaseberle.passwordmanager.storage.model.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StorageHandler {

    private final List<Storage> loadedStorages;

    public StorageHandler() {
        this.loadedStorages = new ArrayList<>();
    }

    public boolean existsStorage(String identifier) {
        return loadedStorages.stream()
                .anyMatch(storage -> storage.getIdentifier().equalsIgnoreCase(identifier));
    }

    public Optional<Storage> getStorage(String identifier) {
        return loadedStorages.stream()
                .filter(storage -> storage.getIdentifier().equals(identifier))
                .findFirst();
    }

    public void addStorage(Storage storage) {
        this.loadedStorages.add(storage);
    }

    public void removeStorage(Storage storage) {
        this.loadedStorages.remove(storage);
    }
}
