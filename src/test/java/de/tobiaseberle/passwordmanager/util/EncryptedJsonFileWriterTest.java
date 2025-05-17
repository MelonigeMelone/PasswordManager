package de.tobiaseberle.passwordmanager.util;

import de.tobiaseberle.passwordmanager.json.EncryptedJsonFileWriter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EncryptedJsonFileWriterTest {

    @Test
    public void testEncryptAndDecrypt() throws Exception {
        EncryptedJsonTestClass original = new EncryptedJsonTestClass("Alice", 42);
        String password = "correct-password";

        String encrypted = EncryptedJsonFileWriter.encryptObject(original, password);
        EncryptedJsonTestClass decrypted = EncryptedJsonFileWriter.decryptObject(encrypted, password, EncryptedJsonTestClass.class);

        assertEquals(original, decrypted);
    }

    @Test
    public void testDecryptWithWrongPassword() throws Exception {
        EncryptedJsonTestClass original = new EncryptedJsonTestClass("Bob", 100);
        String correctPassword = "secret";
        String wrongPassword = "wrong";

        String encrypted = EncryptedJsonFileWriter.encryptObject(original, correctPassword);

        Exception exception = assertThrows(Exception.class, () -> {
            EncryptedJsonFileWriter.decryptObject(encrypted, wrongPassword, EncryptedJsonTestClass.class);
        });

        assertTrue(exception.getMessage() != null && !exception.getMessage().isEmpty());
    }

    @Test
    public void testDecryptCorruptedData() {
        String corrupted = "invalid-encrypted-data";

        assertThrows(Exception.class, () -> {
            EncryptedJsonFileWriter.decryptObject(corrupted, "anyPassword", EncryptedJsonTestClass.class);
        });
    }

    @Test
    public void testEncryptWithNullPassword() {
        EncryptedJsonTestClass obj = new EncryptedJsonTestClass("Eve", 77);

        assertThrows(NullPointerException.class, () -> {
            EncryptedJsonFileWriter.encryptObject(obj, null);
        });
    }

    @Test
    public void testDecryptToWrongType() throws Exception {
        EncryptedJsonTestClass original = new EncryptedJsonTestClass("Carol", 88);
        String password = "pass";

        String encrypted = EncryptedJsonFileWriter.encryptObject(original, password);

        assertThrows(Exception.class, () -> {
            EncryptedJsonFileWriter.decryptObject(encrypted, password, String.class);
        });
    }
}
