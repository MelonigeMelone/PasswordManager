package de.tobiaseberle.passwordmanager.encryption;

import javax.crypto.Cipher;

public class DefaultCipherProvider implements CipherProvider {

    @Override
    public Cipher getCipher(String algorithm) throws Exception {
        return Cipher.getInstance(algorithm);
    }
}
