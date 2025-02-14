package de.tobiaseberle.passwordmanager.storage.model;

public class Field {

    private final String name;
    private String value;

    private final boolean isPassword;

    public Field(String name, String value, boolean isPassword) {
        this.name = name;
        this.value = value;
        this.isPassword = isPassword;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isPassword() {
        return isPassword;
    }
}
