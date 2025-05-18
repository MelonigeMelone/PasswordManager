package de.tobiaseberle.passwordmanager.storage.model;

public class Field {

    private String name;
    private String value;

    private boolean isPassword;

    public Field() {}

    public Field(String name, String value, boolean isPassword) {
        this.name = name;
        this.value = value;
        this.isPassword = isPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setPassword(boolean password) {
        isPassword = password;
    }
}
