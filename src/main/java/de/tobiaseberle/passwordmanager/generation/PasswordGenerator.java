package de.tobiaseberle.passwordmanager.generation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGenerator {

    public static String generatePassword(int length, boolean useLowercase, boolean useUppercase,
                                          boolean useNumbers, boolean useSpecialCharacters) throws PasswordGenerationException {

        if (length < 1) {
            throw new PasswordGenerationException("Die Länge des Passworts muss mindestens 1 sein.");
        }

        if(!(useLowercase || useUppercase || useNumbers || useSpecialCharacters)) {
            throw new PasswordGenerationException("Es muss mindestens ein Zeichensatz ausgewählt werden.");
        }

        StringBuilder characters = new StringBuilder();
        List<Character> password = new ArrayList<>();

        if (useLowercase) {
            String lowercase = "abcdefghijklmnopqrstuvwxyz";
            characters.append(lowercase);
            password.add(lowercase.charAt((int) (Math.random() * lowercase.length())));
        }
        if (useUppercase) {
            String uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            characters.append(uppercase);
            password.add(uppercase.charAt((int) (Math.random() * uppercase.length())));
        }
        if (useNumbers) {
            String numbers = "0123456789";
            characters.append(numbers);
            password.add(numbers.charAt((int) (Math.random() * numbers.length())));
        }
        if (useSpecialCharacters) {
            String specialChars = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
            characters.append(specialChars);
            password.add(specialChars.charAt((int) (Math.random() * specialChars.length())));
        }

        if (password.size() > length) {
            throw new PasswordGenerationException("Die Länge des Passworts ist zu kurz, um alle Anforderungen zu erfüllen.");
        }

        while (password.size() < length) {
            password.add(characters.charAt((int) (Math.random() * characters.length())));
        }

        Collections.shuffle(password);

        StringBuilder finalPassword = new StringBuilder();
        for (char c : password) {
            finalPassword.append(c);
        }

        return finalPassword.toString();
    }
}
