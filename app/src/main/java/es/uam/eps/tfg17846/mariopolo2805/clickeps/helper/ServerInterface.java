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

    private static final String BASE_URL = "http://192.168.1.83:9000";
    private static final String LOGIN_URI = "/login";

    private static final String JSON_KEY = "json";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";

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
        String url = BASE_URL + LOGIN_URI;

        StringRequest request = new StringRequest(Request.Method.POST, url, callback, errorCallback) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(USERNAME_KEY, username);
                params.put(PASSWORD_KEY, password);
                return params;
            }
        };
        queue.add(request);
    }

}
