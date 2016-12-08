package es.uam.eps.tfg17846.mariopolo2805.clickeps.helper;

import java.io.Serializable;

public class Question implements Serializable {

    private String id;
    private String title;

    public Question(String id, String title) {
        super();
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
