package es.uam.eps.tfg17846.mariopolo2805.clickeps;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Question;
import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.ServerInterface;

import static es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Constants.QUESTION_KEY;
import static es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Constants.USERID_KEY;

public class QuestionActivity extends AppCompatActivity implements OnClickListener {

    private TextView questionTitle;
    private RadioButton answerARadio;
    private RadioButton answerBRadio;
    private RadioButton answerCRadio;
    private RadioButton answerDRadio;
    private Button ncBtn;
    private Button submitBtn;
    private ProgressBar progressBar;
    private Question question;
    private String userId;

    private String response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questionTitle = (TextView) findViewById(R.id.question_title);
        answerARadio = (RadioButton) findViewById(R.id.answerA);
        answerBRadio = (RadioButton) findViewById(R.id.answerB);
        answerCRadio = (RadioButton) findViewById(R.id.answerC);
        answerDRadio = (RadioButton) findViewById(R.id.answerD);
        ncBtn = (Button) findViewById(R.id.nc);
        submitBtn = (Button) findViewById(R.id.submit);
        submitBtn.setEnabled(false);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        question = (Question) getIntent().getExtras().getSerializable(QUESTION_KEY);
        userId = getIntent().getExtras().getString(USERID_KEY);

        questionTitle.setText(question.getQuestion());
        answerARadio.setText(question.getOptionA());
        answerBRadio.setText(question.getOptionB());
        answerCRadio.setText(question.getOptionC());
        answerDRadio.setText(question.getOptionD());

        Date now = new Date();

        final Drawable successImg = ContextCompat.getDrawable(this, android.R.drawable.presence_online);

        if(question.getSelection() != null || now.after(question.getExpiration())) {
            answerARadio.setEnabled(false);
            answerBRadio.setEnabled(false);
            answerCRadio.setEnabled(false);
            answerDRadio.setEnabled(false);
            ncBtn.setEnabled(false);
            submitBtn.setEnabled(false);

            successImg.setBounds(0, 0, 60, 60);
            switch (question.getSolution()) {
                case "A":
                    answerARadio.setCompoundDrawables(null, null, successImg, null);
                    break;
                case "B":
                    answerBRadio.setCompoundDrawables(null, null, successImg, null);
                    break;
                case "C":
                    answerCRadio.setCompoundDrawables(null, null, successImg, null);
                    break;
                case "D":
                    answerDRadio.setCompoundDrawables(null, null, successImg, null);
                    break;
            }

            if(question.getSelection() != null) {
                RadioButton answered = null;
                switch (question.getSelection()) {
                    case "A":
                        answered = answerARadio;
                        break;
                    case "B":
                        answered = answerBRadio;
                        break;
                    case "C":
                        answered = answerCRadio;
                        break;
                    case "D":
                        answered = answerDRadio;
                        break;
                }

                if (answered != null) {
                    answered.toggle();
                    answered.setEnabled(true);
                    if (!question.getSelection().equals(question.getSolution())) {
                        Drawable failureImg = ContextCompat.getDrawable(this, android.R.drawable.ic_delete);
                        failureImg.setBounds(0, 0, 60, 60);
                        answered.setCompoundDrawables(null, null, failureImg, null);
                    }
                }
            }
        }

        final TextView timer = (TextView) findViewById(R.id.question_timer);
        if(question.getSelection() != null) {
            if (question.getSelection().equals(question.getSolution())) {
                timer.setText("Respuesta correcta");
            } else if (question.getSelection().equals("null")) {
                timer.setText("No sabe / No contesta");
            } else {
                timer.setText("Respuesta incorrecta");
            }
        } else if (now.after(question.getExpiration())) {
            timer.setText("No sabe / No contesta");
        } else {
            long millis = question.getExpiration().getTime() - now.getTime();
            new CountDownTimer(millis, 1000) {
                public void onTick(long millis) {
                    long days = TimeUnit.MILLISECONDS.toDays(millis);
                    if (days > 0) {
                        timer.setText("Tiempo: " + days + " días");
                    } else {
                        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

                        timer.setText("Tiempo: " + hms);
                    }
                }

                public void onFinish() {
                    answerARadio.setEnabled(false);
                    answerBRadio.setEnabled(false);
                    answerCRadio.setEnabled(false);
                    answerDRadio.setEnabled(false);
                    ncBtn.setEnabled(false);
                    submitBtn.setEnabled(false);

                    successImg.setBounds(0, 0, 60, 60);
                    switch (question.getSolution()) {
                        case "A":
                            answerARadio.setCompoundDrawables(null, null, successImg, null);
                            break;
                        case "B":
                            answerBRadio.setCompoundDrawables(null, null, successImg, null);
                            break;
                        case "C":
                            answerCRadio.setCompoundDrawables(null, null, successImg, null);
                            break;
                        case "D":
                            answerDRadio.setCompoundDrawables(null, null, successImg, null);
                            break;
                    }
                    timer.setText("No sabe / No contesta");
                }
            }.start();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.answerA:
                response = "A";
                submitBtn.setEnabled(true);
                break;
            case R.id.answerB:
                response = "B";
                submitBtn.setEnabled(true);
                break;
            case R.id.answerC:
                response = "C";
                submitBtn.setEnabled(true);
                break;
            case R.id.answerD:
                response = "D";
                submitBtn.setEnabled(true);
                break;
            case R.id.nc:
                AlertDialog.Builder ncDialogBuilder = new AlertDialog.Builder(this);
                ncDialogBuilder.setTitle("No contestar");
                ncDialogBuilder.setMessage("¿Está seguro que desea no contestar a la pregunta? (Esta acción no se podrá modificar)");
                ncDialogBuilder.setPositiveButton("Seguro, no contestar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ServerInterface server = ServerInterface.getServer(QuestionActivity.this);
                        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        progressBar.setVisibility(View.VISIBLE);
                        server.newAnswer(userId, question.getId(), "null", time,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(QuestionActivity.this, "Respuesta enviada: No sabe / No contesta", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                                , new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(QuestionActivity.this, "Ooops! Algo fue mal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                    }
                });
                ncDialogBuilder.setNegativeButton("Volver", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog ncDialog = ncDialogBuilder.create();
                ncDialog.show();
                break;
            case R.id.submit:
                AlertDialog.Builder submitDialogBuilder = new AlertDialog.Builder(this);
                submitDialogBuilder.setTitle("Enviar respuesta");
                submitDialogBuilder.setMessage("¿Desea enviar la respuesta? (Esta acción no se podrá modificar)");
                submitDialogBuilder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ServerInterface server = ServerInterface.getServer(QuestionActivity.this);
                        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        progressBar.setVisibility(View.VISIBLE);
                        server.newAnswer(userId, question.getId(), response, time,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String s) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(QuestionActivity.this, "Respuesta enviada: " + response, Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                }
                                , new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(QuestionActivity.this, "Ooops! Algo fue mal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );
                    }
                });
                submitDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog submitDialog = submitDialogBuilder.create();
                submitDialog.show();
                break;
        }
    }

}
