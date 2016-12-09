package es.uam.eps.tfg17846.mariopolo2805.clickeps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Constants;
import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Group;
import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Section;

public class SectionActivity extends AppCompatActivity implements OnClickListener {

    private Spinner sectionSpinner;
    private ArrayAdapter<Section> sectionAdapter;
    private List<Section> sections;

    private String userId;
    private Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);

        userId = getIntent().getExtras().getString(Constants.USERID_KEY);
        group = (Group) getIntent().getExtras().getSerializable(Constants.GROUP_KEY);

        sectionSpinner = (Spinner) findViewById(R.id.section);

        sections = new ArrayList<>();
// TODO mock data, make request to complete this
        sections.add(new Section("1", "Tema 1"));
        sections.add(new Section("2", "Tema 2"));
        sections.add(new Section("3", "Tema 3"));
        sections.add(new Section("4", "Tema 4"));
        sections.add(new Section("5", "Tema 5"));
    }

    @Override
    protected void onResume() {
        super.onResume();

        sectionAdapter = new ArrayAdapter<>(SectionActivity.this, android.R.layout.simple_spinner_dropdown_item, sections);
        sectionSpinner.setAdapter(sectionAdapter);
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
