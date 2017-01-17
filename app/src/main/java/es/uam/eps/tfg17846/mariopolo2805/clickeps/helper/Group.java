package es.uam.eps.tfg17846.mariopolo2805.clickeps.helper;

import java.io.Serializable;

public class Group implements Serializable {

    private String id;
    private String code;
    private String group;

    public Group(String id, String code, String group) {
        this.id = id;
        this.code = code;
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return code + " - " + group;
    }
}
