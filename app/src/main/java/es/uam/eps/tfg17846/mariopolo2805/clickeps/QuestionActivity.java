package es.uam.eps.tfg17846.mariopolo2805.clickeps;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Question;

import static es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Constants.QUESTION_KEY;
import static es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Constants.USERID_KEY;

public class QuestionActivity extends AppCompatActivity implements OnClickListener {

    private TextView questionTitle;
    private RadioButton answerARadio;
    private RadioButton answerBRadio;
    private RadioButton answerCRadio;
    private RadioButton answerDRadio;
    private Question question;
    private String userId;

    private String response;
    // TODO check if was answered and disable if it was but show answer
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        questionTitle = (TextView) findViewById(R.id.question_title);
        answerARadio = (RadioButton) findViewById(R.id.answerA);
        answerBRadio = (RadioButton) findViewById(R.id.answerB);
        answerCRadio = (RadioButton) findViewById(R.id.answerC);
        answerDRadio = (RadioButton) findViewById(R.id.answerD);

        question = (Question) getIntent().getExtras().getSerializable(QUESTION_KEY);
        userId = getIntent().getExtras().getString(USERID_KEY);

        questionTitle.setText(question.getQuestion());
        answerARadio.setText(question.getOptionA());
        answerBRadio.setText(question.getOptionB());
        answerCRadio.setText(question.getOptionC());
        answerDRadio.setText(question.getOptionD());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.answerA:
                response = "A";
                break;
            case R.id.answerB:
                response = "B";
                break;
            case R.id.answerC:
                response = "C";
                break;
            case R.id.answerD:
                response = "D";
                break;
            case R.id.nc:
                AlertDialog.Builder ncDialogBuilder = new AlertDialog.Builder(this);
                ncDialogBuilder.setTitle("No contestar");
                ncDialogBuilder.setMessage("¿Está seguro que no desea contestar a la pregunta? (Esta acción no se podrá modificar)");
                ncDialogBuilder.setPositiveButton("Seguro, no contestar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish(); // TODO complete with request
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
                // TODO make request
                AlertDialog.Builder submitDialogBuilder = new AlertDialog.Builder(this);
                submitDialogBuilder.setTitle("Enviar respuesta");
                submitDialogBuilder.setMessage("¿Desea enviar la respuesta? (Esta acción no se podrá modificar)");
                submitDialogBuilder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(QuestionActivity.this, "Respuesta al servidor " + response, Toast.LENGTH_SHORT).show();
                        finish(); // TODO complete with request
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
