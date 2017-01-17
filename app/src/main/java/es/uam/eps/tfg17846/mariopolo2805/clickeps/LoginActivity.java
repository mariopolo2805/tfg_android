package es.uam.eps.tfg17846.mariopolo2805.clickeps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.security.MessageDigest;

import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Constants;
import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.ServerInterface;

public class LoginActivity extends AppCompatActivity implements OnClickListener {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText = (EditText) findViewById(R.id.username_login_field);
        passwordEditText = (EditText) findViewById(R.id.password_login_field);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.accept_login_button:
                ServerInterface server = ServerInterface.getServer(LoginActivity.this);
                progressBar.setVisibility(View.VISIBLE);
                server.login(
                        usernameEditText.getText().toString(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                progressBar.setVisibility(View.INVISIBLE);
                                if (s.isEmpty()) {
                                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                                    dialogBuilder.setTitle("Login");
                                    dialogBuilder.setMessage("Usuario o contraseña incorrectos");
                                    dialogBuilder.setNeutralButton("Atrás", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                                    dialogBuilder.show();
                                } else {
                                    try {
                                        JSONObject jsonObject = new JSONObject(s);

                                        String emailResponse = jsonObject.getString("email");
                                        String passwordResponse = jsonObject.getString("password");
                                        String userId = jsonObject.getString("idUser");

                                        MessageDigest digest = MessageDigest.getInstance("SHA-256");
                                        byte[] bytes = digest.digest(passwordEditText.getText().toString().getBytes("UTF-8"));
                                        StringBuilder hexString = new StringBuilder();
                                        for (byte aByte : bytes) {
                                            String hex = Integer.toHexString(0xff & aByte);
                                            if (hex.length() == 1) hexString.append('0');
                                            hexString.append(hex);
                                        }

                                        if (hexString.toString().equals(passwordResponse)) {
                                            if (emailResponse.contains("@estudiante.uam")) {
                                                Intent intent = new Intent("es.uam.eps.tfg17846.mariopolo2805.clickeps.SUBJECTACTIVITY");
                                                intent.putExtra(Constants.USERID_KEY, userId);
                                                startActivity(intent);
                                            } else {
                                                Toast.makeText(LoginActivity.this, "Acceso solo autorizado a estudiantes", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                                            dialogBuilder.setTitle("Login");
                                            dialogBuilder.setMessage("Usuario o contraseña incorrectos");
                                            dialogBuilder.setNeutralButton("Atrás", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            });
                                            dialogBuilder.show();
                                        }
                                    } catch (Exception e) {
                                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                                        dialogBuilder.setTitle("Ooops!");
                                        dialogBuilder.setMessage("Algo fue mal en la petición");
                                        dialogBuilder.setNeutralButton("Atrás", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                        dialogBuilder.show();
                                    }

                                }
                            }
                        }
                        , new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        }

                );
                break;
        }
    }
}
