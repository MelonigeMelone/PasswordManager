package de.tobiaseberle.passwordmanager.storage.model;

import java.util.ArrayList;
import java.util.List;

public class Entry {

    private final int id;
    private String name;

    private final List<Field> fields;

    public Entry(int id, String name) {
        this.id = id;
        this.name = name;
        this.fields = new ArrayList<>();
    }

    public Entry(int id, String name, List<Field> fields) {
        this.id = id;
        this.name = name;
        this.fields = fields;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void addField(Field field) {
        fields.add(field);
    }
}
