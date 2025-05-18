package de.tobiaseberle.passwordmanager.encryption;

import javax.crypto.Cipher;

public interface CipherProvider {
    Cipher getCipher(String algorithm) throws Exception;
}
