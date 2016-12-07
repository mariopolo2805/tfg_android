package es.uam.eps.tfg17846.mariopolo2805.clickeps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;

public class LoginActivity extends AppCompatActivity implements OnClickListener{

    private EditText usernameEditText;
    private EditText passwordEditText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.accept_login_button:
                Log.e("PUTAMIERDA", "Hacemos login");
                // TODO completar con peticion REST a servidor
        }
    }

}
