package de.tobiaseberle.passwordmanager.storage;

import de.tobiaseberle.passwordmanager.json.EncryptedJsonFileWriter;
import de.tobiaseberle.passwordmanager.storage.model.Storage;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
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

    public void encryptStorage(Storage storage, String path,  String identifier) throws Exception{
        String password = storage.getPassword();

        String encryptedJson = EncryptedJsonFileWriter.encryptObject(storage, password);

        File file = new File(path + "tresor_" + identifier + ".json");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(encryptedJson);
        }
    }

    public Storage decryptStorage(File file, String password) throws Exception{
        String encryptedContent = Files.readString(file.toPath());
        return EncryptedJsonFileWriter.decryptObject(encryptedContent, password, Storage.class);
    }
}
