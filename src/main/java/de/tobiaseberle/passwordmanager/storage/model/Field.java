package de.tobiaseberle.passwordmanager.storage.model;

public class Field {

    private final String name;
    private String value;

    public Field(String name, String value) {
        this.name = name;
        this.value = value;
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
}
