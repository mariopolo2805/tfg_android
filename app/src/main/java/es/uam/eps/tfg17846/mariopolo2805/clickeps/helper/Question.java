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
    private Date endDate;
// TODO add selected answer if it was answered

    public Question(String id, String question) {
        super();
        this.id = id;
        this.question = question;
    }

    public Question(String id, String question, String optionA, String optionB, String optionC, String optionD, String solution/*, Date endDate*/) {
        this.id = id;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.solution = solution;
//        this.endDate = endDate;
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

    public Date getEndDate() {
        return endDate;
    }
}
