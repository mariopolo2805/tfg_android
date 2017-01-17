package es.uam.eps.tfg17846.mariopolo2805.clickeps.helper;

import java.io.Serializable;
import java.util.Date;

public class Answer implements Serializable {

    private String id;
    private String idStudent;
    private String idQuestion;
    private String selection;
    private Date time;

    public Answer(String id, String idStudent, String idQuestion, String selection, Date time) {
        this.id = id;
        this.idStudent = idStudent;
        this.idQuestion = idQuestion;
        this.selection = selection;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public String getIdQuestion() {
        return idQuestion;
    }

    public String getSelection() {
        return selection;
    }

    public Date getTime() {
        return time;
    }
}
