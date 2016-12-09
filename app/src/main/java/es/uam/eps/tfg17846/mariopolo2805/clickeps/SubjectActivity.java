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

public class SubjectActivity extends AppCompatActivity implements OnClickListener {

    private Spinner groupSpinner;
    private ArrayAdapter<Group> groupAdapter;
    private List<Group> groups;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        userId = getIntent().getExtras().getString(Constants.USERID_KEY);

        groupSpinner = (Spinner) findViewById(R.id.group);
// TODO mock data, make request to complete this
        groups = new ArrayList<>();
        groups.add(new Group("1", "G121"));
        groups.add(new Group("2", "G122"));
        groups.add(new Group("3", "G123"));
        groups.add(new Group("4", "G131"));
    }

    @Override
    protected void onResume() {
        super.onResume();

        groupAdapter = new ArrayAdapter<>(SubjectActivity.this, android.R.layout.simple_spinner_dropdown_item, groups);
        groupSpinner.setAdapter(groupAdapter);
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
