package es.uam.eps.tfg17846.mariopolo2805.clickeps.helper;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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

    public void login(final String username,
                      Listener<String> callback,
                      ErrorListener errorCallback) {
        String url = Constants.BASE_URL + Constants.LOGIN_URI + "/" + username;

        StringRequest request = new StringRequest(Request.Method.POST, url, callback, errorCallback);
        queue.add(request);
    }

    public void groupsOfStudent(final String userId, Listener<String> callback,
                          ErrorListener errorCallback) {
        String url = Constants.BASE_URL + Constants.GROUPS_URI + "/" + userId;

        StringRequest request = new StringRequest(Request.Method.GET, url, callback, errorCallback);
        queue.add(request);
    }

    public void sectionsOfGroup(final String groupId, Listener<String> callback,
                                ErrorListener errorCallback) {
        String url = Constants.BASE_URL + Constants.SECTIONS_URI + "/" + groupId;

        StringRequest request = new StringRequest(Request.Method.GET, url, callback, errorCallback);
        queue.add(request);
    }

    public void questionsOfStudent(final String userId, final String sectionId,
                                   Listener<String> callback, ErrorListener errorCallback) {
        String url = Constants.BASE_URL + Constants.QUESTIONS_URI + "/" + userId + "/section/" + sectionId;

        StringRequest request = new StringRequest(Request.Method.GET, url, callback, errorCallback);
        queue.add(request);
    }

    public void answersOfStudent(final String userId, final String sectionId,
                                   Listener<String> callback, ErrorListener errorCallback) {
        String url = Constants.BASE_URL + Constants.ANSWERS_URI + "/" + userId + "/section/" + sectionId;

        StringRequest request = new StringRequest(Request.Method.GET, url, callback, errorCallback);
        queue.add(request);
    }

}
