package es.uam.eps.tfg17846.mariopolo2805.clickeps.helper;

import android.content.Context;
import android.util.Log;

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

    public void newAnswer(final String idStudent, final String idQuestion, final String selection, final String time,
                      Listener<String> callback,
                      ErrorListener errorCallback) {
        String url = Constants.BASE_URL + Constants.NEW_ANSWER_URI;

        StringRequest request = new StringRequest(Request.Method.POST, url, callback, errorCallback) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("idStudent", idStudent);
                params.put("idQuestion", idQuestion);
                if(!selection.equals("null")) {
                    params.put("selection", selection);
                }
                params.put("time", time);

                return params;
            }
        };
        queue.add(request);
    }

}
