package es.uam.eps.tfg17846.mariopolo2805.clickeps.helper;

import java.io.Serializable;

public class Group implements Serializable {

    private String id;
    private String name;

    public Group(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
