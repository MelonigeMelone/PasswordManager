package de.tobiaseberle.passwordmanager.storage.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Entry {

    private String identifier;
    private String name;

    private List<Field> fields;

    public Entry() {}

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

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public void addField(Field field) {
        fields.add(field);
    }

    public void removeField(Field field) {
        fields.remove(field);
    }
}
