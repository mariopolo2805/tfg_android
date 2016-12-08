package es.uam.eps.tfg17846.mariopolo2805.clickeps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Question;

import static es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Constants.QUESTION_KEY;

public class QuestionActivity extends AppCompatActivity {

    private TextView questionTitle;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questionTitle = (TextView) findViewById(R.id.question_title);

        question = (Question) getIntent().getExtras().getSerializable(QUESTION_KEY);
        questionTitle.setText(question.getTitle());
    }


}
