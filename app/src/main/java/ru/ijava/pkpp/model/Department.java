package ru.ijava.pkpp.model;

/**
 * Created by rele on 5/21/17.
 */
public class Department {
    private int id;
    private int parent;
    private String name;

    public Department()
    {
    }

    public Department(int id, int parent, String name)
    {
        this.setId(id);
        this.setParent(parent);
        this.setName(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

