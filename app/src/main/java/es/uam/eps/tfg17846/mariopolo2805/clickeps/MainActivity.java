package es.uam.eps.tfg17846.mariopolo2805.clickeps;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Connection;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                if (!Connection.isConnected(this)) {
                    new AlertDialog.Builder(this).setMessage("No hay conexión")
                            .setNeutralButton("Atrás", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).create().show();
                } else {
                    startActivity(new Intent("es.uam.eps.tfg17846.mariopolo2805.clickeps.LOGINACTIVITY"));
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Salir");
        dialogBuilder.setMessage("¿Desea salir de la aplicación?");
        dialogBuilder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

}
