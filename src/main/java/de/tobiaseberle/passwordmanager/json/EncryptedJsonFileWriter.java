package de.tobiaseberle.passwordmanager.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.tobiaseberle.passwordmanager.encryption.CipherProvider;
import de.tobiaseberle.passwordmanager.encryption.KeyGenerator;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptedJsonFileWriter {

    private static final String ENCRYPTION_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int IV_SIZE = 16;

    private final KeyGenerator keyGenerator;
    private final CipherProvider cipherProvider;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public EncryptedJsonFileWriter(KeyGenerator keyGenerator, CipherProvider cipherProvider) {
        this.keyGenerator = keyGenerator;
        this.cipherProvider = cipherProvider;
    }

    public String encryptObject(Object obj, String password) throws Exception {
        String json = objectMapper.writeValueAsString(obj);
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

        SecretKeySpec key = keyGenerator.deriveKey(password, salt);
        Cipher cipher = cipherProvider.getCipher(ENCRYPTION_ALGORITHM);

        byte[] iv = new byte[IV_SIZE];
        random.nextBytes(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] encrypted = cipher.doFinal(json.getBytes(StandardCharsets.UTF_8));

        byte[] combined = new byte[salt.length + iv.length + encrypted.length];
        System.arraycopy(salt, 0, combined, 0, salt.length);
        System.arraycopy(iv, 0, combined, salt.length, iv.length);
        System.arraycopy(encrypted, 0, combined, salt.length + iv.length, encrypted.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    public <T> T decryptObject(String encryptedData, String password, Class<T> valueType) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encryptedData);

        byte[] salt = new byte[16];
        byte[] iv = new byte[16];
        byte[] encrypted = new byte[combined.length - 32];

        System.arraycopy(combined, 0, salt, 0, 16);
        System.arraycopy(combined, 16, iv, 0, 16);
        System.arraycopy(combined, 32, encrypted, 0, encrypted.length);

        SecretKeySpec key = keyGenerator.deriveKey(password, salt);
        Cipher cipher = cipherProvider.getCipher(ENCRYPTION_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));

        byte[] decryptedBytes = cipher.doFinal(encrypted);
        String json = new String(decryptedBytes, StandardCharsets.UTF_8);

        return objectMapper.readValue(json, valueType);
    }
}
