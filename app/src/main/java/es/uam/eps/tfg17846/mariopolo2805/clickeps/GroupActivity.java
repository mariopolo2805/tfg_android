package es.uam.eps.tfg17846.mariopolo2805.clickeps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Constants;
import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Group;
import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Question;
import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.ServerInterface;

public class GroupActivity extends AppCompatActivity implements OnClickListener {

    private ProgressBar progressBar;
    private Spinner groupSpinner;
    private ArrayAdapter<Group> groupAdapter;
    private List<Group> groups;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        groupSpinner = (Spinner) findViewById(R.id.group);
        groups = new ArrayList<>();

        userId = getIntent().getExtras().getString(Constants.USERID_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ServerInterface server = ServerInterface.getServer(GroupActivity.this);
        server.groupsOfStudent(
                userId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressBar.setVisibility(View.INVISIBLE);
                        try {
                            groups.clear();
                            JSONArray groupJSONList = new JSONArray(s);
                            for (int i = 0; i < groupJSONList.length(); i++) {
                                JSONObject groupJSON = groupJSONList.getJSONObject(i);
                                Group g = new Group(
                                        groupJSON.getString("idGroup"),
                                        groupJSON.getString("code"),
                                        groupJSON.getString("group"));
                                groups.add(g);
                            }

                            groupAdapter = new ArrayAdapter<>(GroupActivity.this, android.R.layout.simple_spinner_dropdown_item, groups);
                            groupSpinner.setAdapter(groupAdapter);
                        } catch (Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(GroupActivity.this, "Hubo un problema al solicitar los grupos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_button:
                Group selected = (Group) groupSpinner.getSelectedItem();
                Intent intent = new Intent("es.uam.eps.tfg17846.mariopolo2805.clickeps.SECTIONACTIVITY");
                intent.putExtra(Constants.USERID_KEY, userId);
                intent.putExtra(Constants.GROUP_KEY, selected);
                startActivity(intent);
                break;
        }
    }

}
