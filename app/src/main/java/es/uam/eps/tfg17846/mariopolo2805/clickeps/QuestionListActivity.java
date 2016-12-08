package es.uam.eps.tfg17846.mariopolo2805.clickeps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.Arrays;
import java.util.List;

import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Question;

import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Constants;

public class QuestionListActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ListView questionList;
    //    private List<Question> questions = new ArrayList<>();
    // TODO mocked data START ZONE
    private Question mock[] = {
            new Question("id1", "AAA"),
            new Question("id2", "BBB"),
            new Question("id3", "C CCCC"),
            new Question("id4", "D fdsag"),
            new Question("id6", "E vaafewg"),
            new Question("id7", "F c23"),
            new Question("id8", "G gew"),
            new Question("id9", "H vaw"),
            new Question("id10", "I va"),
            new Question("id11", "J wnab"),
            new Question("id12", "K vmak"),
            new Question("id13", "L gma"),
            new Question("id14", "M bsdfl"),
            new Question("id15", "N bnsel"),
            new Question("id16", "O mbreal"),
            new Question("id17", "P hwne"),
            new Question("id18", "Q g43q"),
            new Question("id19", "R ewqg"),
            new Question("id20", "S fdb")
    };
    private List<Question> questions = Arrays.asList(mock);
    // TODO mocked data END ZONE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        questionList = (ListView) findViewById(R.id.question_list);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // TODO make request
        progressBar.setVisibility(View.VISIBLE);

        progressBar.setVisibility(View.INVISIBLE);

        final QuestionItemAdapter adapter = new QuestionItemAdapter(this, questions);
        questionList.setAdapter(adapter);

        questionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Question selectedQuestion = (Question) adapter.getItem(i);
                Intent intent = new Intent("es.uam.eps.tfg17846.mariopolo2805.clickeps.QUESTIONACTIVITY");
                intent.putExtra(Constants.QUESTION_KEY, selectedQuestion);
                startActivity(intent);
            }
        });
    }


}
