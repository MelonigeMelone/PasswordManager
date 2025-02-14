package de.tobiaseberle.passwordmanager.storage.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Entry {

    private final String identifier;
    private String name;

    private final List<Field> fields;

    public Entry(String identifier, String name) {
        this.identifier = identifier;
        this.name = name;
        this.fields = new ArrayList<>();
    }

    public Entry(String identifier, String name, List<Field> fields) {
        this.identifier = identifier;
        this.name = name;
        this.fields = fields;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<Field> getField(String name) {
        return fields.stream().filter(field -> field.getName().equals(name)).findFirst();
    }

    public List<Field> getFields() {
        return fields;
    }

    public void addField(Field field) {
        fields.add(field);
    }
}
