package de.tobiaseberle.passwordmanager.encryption;

import javax.crypto.spec.SecretKeySpec;

public interface KeyGenerator {
    SecretKeySpec deriveKey(String password, byte[] salt) throws Exception;
}
