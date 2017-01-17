package es.uam.eps.tfg17846.mariopolo2805.clickeps;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
    private Button ncBtn;
    private Button submitBtn;
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

        question = (Question) getIntent().getExtras().getSerializable(QUESTION_KEY);
        userId = getIntent().getExtras().getString(USERID_KEY);

        questionTitle.setText(question.getQuestion());
        answerARadio.setText(question.getOptionA());
        answerBRadio.setText(question.getOptionB());
        answerCRadio.setText(question.getOptionC());
        answerDRadio.setText(question.getOptionD());

        if(question.getSelection() != null) {
            answerARadio.setEnabled(false);
            answerBRadio.setEnabled(false);
            answerCRadio.setEnabled(false);
            answerDRadio.setEnabled(false);
            ncBtn.setEnabled(false);
            submitBtn.setEnabled(false);

            Drawable img = ContextCompat.getDrawable(this, android.R.drawable.presence_online);
            img.setBounds(0, 0, 60, 60);
            switch (question.getSolution()) {
                case "A":
                    answerARadio.setCompoundDrawables(null, null, img, null);
                    break;
                case "B":
                    answerBRadio.setCompoundDrawables(null, null, img, null);
                    break;
                case "C":
                    answerCRadio.setCompoundDrawables(null, null, img, null);
                    break;
                case "D":
                    answerDRadio.setCompoundDrawables(null, null, img, null);
                    break;
            }

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

            if(answered != null) {
                answered.toggle();
                answered.setEnabled(true);
                if(!question.getSelection().equals(question.getSolution())) {
                    img = ContextCompat.getDrawable(this, android.R.drawable.ic_delete);
                    img.setBounds(0, 0, 60, 60);
                    answered.setCompoundDrawables(null, null, img, null);
                }
            }
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
