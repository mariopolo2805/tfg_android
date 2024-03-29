package es.uam.eps.tfg17846.mariopolo2805.clickeps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
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
import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Section;
import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.ServerInterface;

public class SectionActivity extends AppCompatActivity implements OnClickListener {

    private ProgressBar progressBar;
    private Spinner sectionSpinner;
    private ArrayAdapter<Section> sectionAdapter;
    private List<Section> sections;

    private String userId;
    private Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        sectionSpinner = (Spinner) findViewById(R.id.section);
        sections = new ArrayList<>();

        userId = getIntent().getExtras().getString(Constants.USERID_KEY);
        group = (Group) getIntent().getExtras().getSerializable(Constants.GROUP_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ServerInterface server = ServerInterface.getServer(SectionActivity.this);
        server.sectionsOfGroup(
                group.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressBar.setVisibility(View.INVISIBLE);
                        try {
                            sections.clear();
                            JSONArray sectionJSONList = new JSONArray(s);
                            for (int i = 0; i < sectionJSONList.length(); i++) {
                                JSONObject sectionJSON = sectionJSONList.getJSONObject(i);
                                Section sec = new Section(
                                        sectionJSON.getString("idSection"),
                                        sectionJSON.getString("name"),
                                        sectionJSON.getString("idGroup"));
                                sections.add(sec);
                            }

                            sectionAdapter = new ArrayAdapter<>(SectionActivity.this, android.R.layout.simple_spinner_dropdown_item, sections);
                            sectionSpinner.setAdapter(sectionAdapter);
                        } catch (Exception e) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(SectionActivity.this, "Hubo un problema al solicitar los temas", Toast.LENGTH_SHORT).show();
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
                Section selected = (Section) sectionSpinner.getSelectedItem();
                Intent intent = new Intent("es.uam.eps.tfg17846.mariopolo2805.clickeps.QUESTIONLISTACTIVITY");
                intent.putExtra(Constants.USERID_KEY, userId);
                intent.putExtra(Constants.GROUP_KEY, group);
                intent.putExtra(Constants.SECTION_KEY, selected);
                startActivity(intent);
                break;
        }
    }

}
