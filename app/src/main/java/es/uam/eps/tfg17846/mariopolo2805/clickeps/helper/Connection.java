package es.uam.eps.tfg17846.mariopolo2805.clickeps.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public abstract class Connection {

    private static boolean connected = false;

    public static boolean isConnected (Context context) {
        // Tratamiento de la conexion wifi
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        connected = networkInfo != null && networkInfo.isConnected();
        Log.d("NETWORK_STATE", "Wifi: " + connected);
        // Tratamiento de la conexion mediante red movil (solo en caso de no estar wifi activo)
        if (!connected) {
            networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            connected = networkInfo != null && networkInfo.isConnected();
            Log.d("NETWORK_STATE", "Mobile: " + connected);
        }
        return connected;
    }

}