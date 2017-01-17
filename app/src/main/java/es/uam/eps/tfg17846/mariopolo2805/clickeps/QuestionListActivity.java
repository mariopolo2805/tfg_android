package es.uam.eps.tfg17846.mariopolo2805.clickeps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Answer;
import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Constants;
import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Group;
import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Question;
import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Section;
import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.ServerInterface;

public class QuestionListActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ListView questionList;
    private List<Question> questions;
    private List<Answer> answers;
    private QuestionItemAdapter adapter;

    private String userId;
    private Group group;
    private Section section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        questionList = (ListView) findViewById(R.id.question_list);
        questions = new ArrayList<>();
        answers = new ArrayList<>();

        userId = getIntent().getExtras().getString(Constants.USERID_KEY);
        group = (Group) getIntent().getExtras().getSerializable(Constants.GROUP_KEY);
        section = (Section) getIntent().getExtras().getSerializable(Constants.SECTION_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();

        final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");

        progressBar.setVisibility(View.VISIBLE);

        ServerInterface server = ServerInterface.getServer(QuestionListActivity.this);

        server.answersOfStudent(
                userId, section.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressBar.setVisibility(View.INVISIBLE);
                        try {
                            answers.clear();
                            JSONArray answerJSONList = new JSONArray(s);
                            for (int i = 0; i < answerJSONList.length(); i++) {
                                JSONObject answerJSON = answerJSONList.getJSONObject(i);
                                Answer a = new Answer(
                                        answerJSON.getString("idAnswer"),
                                        answerJSON.getString("idStudent"),
                                        answerJSON.getString("idQuestion"),
                                        answerJSON.getString("selection"),
                                        fmt.parse(answerJSON.getString("time")));
                                answers.add(a);
                            }
                        } catch (Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(QuestionListActivity.this, "Hubo un problema al solicitar las respuestas del alumno", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });

        server.questionsOfStudent(
                userId, section.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressBar.setVisibility(View.INVISIBLE);
                        try {
                            questions.clear();
                            JSONArray questionJSONList = new JSONArray(s);
                            for (int i = 0; i < questionJSONList.length(); i++) {
                                JSONObject questionJSON = questionJSONList.getJSONObject(i);
                                Question q = new Question(
                                        questionJSON.getString("idQuestion"),
                                        questionJSON.getString("text"),
                                        questionJSON.getString("answerA"),
                                        questionJSON.getString("answerB"),
                                        questionJSON.getString("answerC"),
                                        questionJSON.getString("answerD"),
                                        questionJSON.getString("solution"),
                                        fmt.parse(questionJSON.getString("expiration")));
                                questions.add(q);

                                // Match with answer
                                Iterator<Answer> itr = answers.iterator();
                                while(itr.hasNext()) {
                                    Answer a = itr.next();
                                    if (a.getIdQuestion() == q.getId()) {
                                        q.setSelection(a.getSelection());
                                    }
                                }
                            }

                            adapter = new QuestionItemAdapter(QuestionListActivity.this, questions);
                            questionList.setAdapter(adapter);

                            questionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Question selectedQuestion = (Question) adapter.getItem(i);
                                    Intent intent = new Intent("es.uam.eps.tfg17846.mariopolo2805.clickeps.QUESTIONACTIVITY");
                                    intent.putExtra(Constants.USERID_KEY, userId);
                                    intent.putExtra(Constants.QUESTION_KEY, selectedQuestion);
                                    startActivity(intent);
                                }
                            });
                        } catch (Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(QuestionListActivity.this, "Hubo un problema al solicitar las preguntas", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

}
