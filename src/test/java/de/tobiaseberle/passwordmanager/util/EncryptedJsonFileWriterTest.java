package de.tobiaseberle.passwordmanager.util;

import de.tobiaseberle.passwordmanager.encryption.DefaultCipherProvider;
import de.tobiaseberle.passwordmanager.encryption.DefaultKeyGenerator;
import de.tobiaseberle.passwordmanager.json.EncryptedJsonFileWriter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EncryptedJsonFileWriterTest {

    @Test
    public void testEncryptAndDecrypt() throws Exception {
        EncryptedJsonFileWriter encryptedJsonFileWriter = new EncryptedJsonFileWriter(
                new DefaultKeyGenerator(), new DefaultCipherProvider());
        EncryptedJsonTestClass original = new EncryptedJsonTestClass("Alice", 42);
        String password = "correct-password";

        String encrypted = encryptedJsonFileWriter.encryptObject(original, password);
        EncryptedJsonTestClass decrypted = encryptedJsonFileWriter.decryptObject(encrypted, password, EncryptedJsonTestClass.class);

        assertEquals(original, decrypted);
    }

    @Test
    public void testDecryptWithWrongPassword() throws Exception {
        EncryptedJsonFileWriter encryptedJsonFileWriter = new EncryptedJsonFileWriter(
                new DefaultKeyGenerator(), new DefaultCipherProvider());
        EncryptedJsonTestClass original = new EncryptedJsonTestClass("Bob", 100);
        String correctPassword = "secret";
        String wrongPassword = "wrong";

        String encrypted = encryptedJsonFileWriter.encryptObject(original, correctPassword);

        Exception exception = assertThrows(Exception.class, () -> {
            encryptedJsonFileWriter.decryptObject(encrypted, wrongPassword, EncryptedJsonTestClass.class);
        });

        assertTrue(exception.getMessage() != null && !exception.getMessage().isEmpty());
    }

    @Test
    public void testDecryptCorruptedData() {
        EncryptedJsonFileWriter encryptedJsonFileWriter = new EncryptedJsonFileWriter(
                new DefaultKeyGenerator(), new DefaultCipherProvider());
        String corrupted = "invalid-encrypted-data";

        assertThrows(Exception.class, () -> {
            encryptedJsonFileWriter.decryptObject(corrupted, "anyPassword", EncryptedJsonTestClass.class);
        });
    }

    @Test
    public void testEncryptWithNullPassword() {
        EncryptedJsonFileWriter encryptedJsonFileWriter = new EncryptedJsonFileWriter(
                new DefaultKeyGenerator(), new DefaultCipherProvider());
        EncryptedJsonTestClass obj = new EncryptedJsonTestClass("Eve", 77);

        assertThrows(NullPointerException.class, () -> {
            encryptedJsonFileWriter.encryptObject(obj, null);
        });
    }

    @Test
    public void testDecryptToWrongType() throws Exception {
        EncryptedJsonFileWriter encryptedJsonFileWriter = new EncryptedJsonFileWriter(
                new DefaultKeyGenerator(), new DefaultCipherProvider());
        EncryptedJsonTestClass original = new EncryptedJsonTestClass("Carol", 88);
        String password = "pass";

        String encrypted = encryptedJsonFileWriter.encryptObject(original, password);

        assertThrows(Exception.class, () -> {
            encryptedJsonFileWriter.decryptObject(encrypted, password, String.class);
        });
    }
}
