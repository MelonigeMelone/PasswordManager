package de.tobiaseberle.passwordmanager.generation;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordGeneratorTest {

    @Test
    void testGeneratePasswordInvalidLength() {
        Assertions.assertThrows(PasswordGenerationException.class, () ->
                PasswordGenerator.generatePassword(0, true, true, true, true));
    }

    @Test
    void testGeneratePasswordMinimumLengthRequirement() {
        Assertions.assertThrows(PasswordGenerationException.class, () -> {
            PasswordGenerator.generatePassword(2, true, true, true, true); // requires at least 4 characters
        });
    }

    @Test
    void testGeneratePasswordEmptyCharacterSet() {
        Assertions.assertThrows(PasswordGenerationException.class, () -> {
            PasswordGenerator.generatePassword(10, false, false, false, false);
        });
    }

    @Test
    void testGeneratePasswordLength() throws PasswordGenerationException {
        int length = 10;
        String password = PasswordGenerator.generatePassword(length, true, true, true, true);
        Assertions.assertEquals(length, password.length());
    }

    @Test
    void testGeneratePasswordWithLowercaseOnly() throws PasswordGenerationException {
        String password = PasswordGenerator.generatePassword(10, true, false, false, false);
        Assertions.assertTrue(password.matches("[a-z]+"));
    }

    @Test
    void testGeneratePasswordWithUppercaseOnly() throws PasswordGenerationException {
        String password = PasswordGenerator.generatePassword(10, false, true, false, false);
        Assertions.assertTrue(password.matches("[A-Z]+"));
    }

    @Test
    void testGeneratePasswordWithNumbersOnly() throws PasswordGenerationException {
        String password = PasswordGenerator.generatePassword(10, false, false, true, false);
        Assertions.assertTrue(password.matches("[0-9]+"));
    }

    //TODO Test schlägt manchmal fehl, irgendwas mit Sonderzeichen bei Generierung uind Überprüdung stimmt nicht noch kein muster gefunden
    @Test
    void testGeneratePasswordWithSpecialCharactersOnly() throws PasswordGenerationException {
        String password = PasswordGenerator.generatePassword(10, false, false, false, true);
        Assertions.assertTrue(password.matches("[!\"#$%&'()*+,-./:;<=>?@\\\\^_`{|}~]+"));
    }

    @Test
    void testGeneratePasswordWithMixedCharacters() throws PasswordGenerationException {
        String password = PasswordGenerator.generatePassword(20, true, true, true, true);

        Assertions.assertTrue(password.matches(".*[a-z].*"));
        Assertions.assertTrue(password.matches(".*[A-Z].*"));
        Assertions.assertTrue(password.matches(".*[0-9].*"));
        Assertions.assertTrue(password.matches(".*[!\"#$%&'()*+,-./:;<=>?@\\\\^_`{|}~].*"));
    }

}
