package es.uam.eps.tfg17846.mariopolo2805.clickeps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

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
        progressBar = (ProgressBar) findViewById(R.id.round_progress);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.accept_login_button:
                ServerInterface server = ServerInterface.getServer(LoginActivity.this);
                progressBar.setVisibility(View.VISIBLE);
                server.login(
                        usernameEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                progressBar.setVisibility(View.INVISIBLE);
                                // TODO el servidor debe devolver algo para que sea valido el usuario (un id, un ok), si cadena vacia, usuario incorrecto
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
                                    // TODO guardar al usuario loggeado en una preferencia (estas estan ocultas)
                                    Log.e("TODO", "guardar al usuario para siguientes pantallas");
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(LoginActivity.this, "Hubo un problema al realizar la conexión", Toast.LENGTH_LONG).show();
                            }
                        });
                break;
        }
    }


}
