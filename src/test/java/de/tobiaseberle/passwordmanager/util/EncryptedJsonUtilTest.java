package de.tobiaseberle.passwordmanager.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EncryptedJsonUtilTest {

    @Test
    public void testEncryptAndDecrypt() throws Exception {
        EncryptedJsonTestClass original = new EncryptedJsonTestClass("Alice", 42);
        String password = "correct-password";

        String encrypted = EncryptedJsonUtil.encryptObject(original, password);
        EncryptedJsonTestClass decrypted = EncryptedJsonUtil.decryptObject(encrypted, password, EncryptedJsonTestClass.class);

        assertEquals(original, decrypted);
    }

    @Test
    public void testDecryptWithWrongPassword() throws Exception {
        EncryptedJsonTestClass original = new EncryptedJsonTestClass("Bob", 100);
        String correctPassword = "secret";
        String wrongPassword = "wrong";

        String encrypted = EncryptedJsonUtil.encryptObject(original, correctPassword);

        Exception exception = assertThrows(Exception.class, () -> {
            EncryptedJsonUtil.decryptObject(encrypted, wrongPassword, EncryptedJsonTestClass.class);
        });

        assertTrue(exception.getMessage() != null && !exception.getMessage().isEmpty());
    }

    @Test
    public void testDecryptCorruptedData() {
        String corrupted = "invalid-encrypted-data";

        assertThrows(Exception.class, () -> {
            EncryptedJsonUtil.decryptObject(corrupted, "anyPassword", EncryptedJsonTestClass.class);
        });
    }

    @Test
    public void testEncryptWithNullPassword() {
        EncryptedJsonTestClass obj = new EncryptedJsonTestClass("Eve", 77);

        assertThrows(NullPointerException.class, () -> {
            EncryptedJsonUtil.encryptObject(obj, null);
        });
    }

    @Test
    public void testDecryptToWrongType() throws Exception {
        EncryptedJsonTestClass original = new EncryptedJsonTestClass("Carol", 88);
        String password = "pass";

        String encrypted = EncryptedJsonUtil.encryptObject(original, password);

        assertThrows(Exception.class, () -> {
            EncryptedJsonUtil.decryptObject(encrypted, password, String.class);
        });
    }
}
