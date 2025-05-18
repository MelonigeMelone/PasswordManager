package de.tobiaseberle.passwordmanager.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.tobiaseberle.passwordmanager.encryption.CipherProvider;
import de.tobiaseberle.passwordmanager.encryption.KeyGenerator;
import de.tobiaseberle.passwordmanager.json.EncryptedJsonFileWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EncryptedJsonFileWriterMockTest {

    @Mock
    private KeyGenerator mockKeyGenerator;

    @Mock
    private CipherProvider mockCipherProvider;

    @Mock
    private Cipher mockCipher;

    private EncryptedJsonFileWriter writer;

    private static final String TEST_PASSWORD = "test-password";
    private static final EncryptedJsonTestClass TEST_OBJECT = new EncryptedJsonTestClass("Alice", 123);

    private static final SecretKeySpec MOCK_KEY = new SecretKeySpec(new byte[32], "AES");

    @BeforeEach
    public void setup() throws Exception {
        when(mockKeyGenerator.deriveKey(anyString(), any())).thenReturn(MOCK_KEY);
        when(mockCipherProvider.getCipher(anyString())).thenReturn(mockCipher);
        writer = new EncryptedJsonFileWriter(mockKeyGenerator, mockCipherProvider);
    }

    @Test
    public void testEncryptObject_withMocks_returnsBase64String() throws Exception {
        byte[] mockOutput = "fake-encrypted-bytes".getBytes(StandardCharsets.UTF_8);

        doNothing().when(mockCipher).init(eq(Cipher.ENCRYPT_MODE), any(), any(IvParameterSpec.class));
        when(mockCipher.doFinal(any(byte[].class))).thenReturn(mockOutput);

        String encrypted = writer.encryptObject(TEST_OBJECT, TEST_PASSWORD);

        assertNotNull(encrypted);
        assertDoesNotThrow(() -> Base64.getDecoder().decode(encrypted));
    }

    @Test
    public void testDecryptObject_withMocks_returnsOriginalObject() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(TEST_OBJECT);
        byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8);

        // Prepare mock encrypted data
        byte[] salt = new byte[16];
        byte[] iv = new byte[16];
        byte[] encrypted = jsonBytes;
        byte[] combined = new byte[salt.length + iv.length + encrypted.length];

        System.arraycopy(salt, 0, combined, 0, salt.length);
        System.arraycopy(iv, 0, combined, salt.length, iv.length);
        System.arraycopy(encrypted, 0, combined, salt.length + iv.length, encrypted.length);

        String base64Encrypted = Base64.getEncoder().encodeToString(combined);

        doNothing().when(mockCipher).init(eq(Cipher.DECRYPT_MODE), any(), any(IvParameterSpec.class));
        when(mockCipher.doFinal(any(byte[].class))).thenReturn(jsonBytes);

        EncryptedJsonTestClass result = writer.decryptObject(base64Encrypted, TEST_PASSWORD, EncryptedJsonTestClass.class);
        assertEquals(TEST_OBJECT, result);
    }
}
