package es.uam.eps.tfg17846.mariopolo2805.clickeps.helper;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ServerInterface {

    private static ServerInterface serverInterface;
    private RequestQueue queue;

    private ServerInterface(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public static ServerInterface getServer(Context context) {
        if (serverInterface == null)
            serverInterface = new ServerInterface(context);
        return serverInterface;
    }

    public void login(final String username, final String password,
                      Listener<String> callback,
                      ErrorListener errorCallback) {
        String url = Constants.BASE_URL + Constants.LOGIN_URI;

        StringRequest request = new StringRequest(Request.Method.POST, url, callback, errorCallback) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.USERNAME_KEY, username);
                params.put(Constants.PASSWORD_KEY, password);
                return params;
            }
        };
        queue.add(request);
    }

}
