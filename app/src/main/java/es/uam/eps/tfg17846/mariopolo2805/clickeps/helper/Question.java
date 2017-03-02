package es.uam.eps.tfg17846.mariopolo2805.clickeps.helper;

import java.io.Serializable;
import java.util.Date;

public class Question implements Serializable {

    private String id;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String solution;
    private Date expiration;
    private Date activation;
    // Secondary atributes
    private String selection;

    public Question(String id, String question, String optionA, String optionB, String optionC, String optionD, String solution, Date expiration, Date activation) {
        this.id = id;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.solution = solution;
        this.expiration = expiration;
        this.activation = activation;
        this.selection = null;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getSolution() {
        return solution;
    }

    public Date getExpiration() {
        return expiration;
    }

    public Date getActivation() {
        return activation;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }
}
