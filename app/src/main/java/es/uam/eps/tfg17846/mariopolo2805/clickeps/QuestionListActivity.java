package es.uam.eps.tfg17846.mariopolo2805.clickeps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays; // TODO remove mocked data
import java.util.List;

import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Question;

public class QuestionListActivity extends AppCompatActivity {

    //    private List<Question> questions = new ArrayList<>();
    // TODO remove mocked data
    private Question mock[] = {
            new Question("id1", "AAA"),
            new Question("id2", "BBB"),
            new Question("id3", "C CCCC"),
            new Question("id4", "D fdsag"),
            new Question("id5", "E dfhsajgh82 32")
    };
    private List<Question> questions = Arrays.asList(mock);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        ListView lv = (ListView) findViewById(R.id.question_list);
        QuestionItemAdapter adapter = new QuestionItemAdapter(this, questions);
        lv.setAdapter(adapter);
    }

}
