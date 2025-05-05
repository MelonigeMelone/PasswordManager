package de.tobiaseberle.passwordmanager.util;

import java.util.Objects;

public class EncryptedJsonTestClass {

    public String name;
    public int value;

    public EncryptedJsonTestClass() {}

    public EncryptedJsonTestClass(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EncryptedJsonTestClass myData = (EncryptedJsonTestClass) o;
        return value == myData.value && name.equals(myData.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
