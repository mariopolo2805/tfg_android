package es.uam.eps.tfg17846.mariopolo2805.clickeps;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import es.uam.eps.tfg17846.mariopolo2805.clickeps.helper.Question;

public class QuestionItemAdapter extends BaseAdapter {

    private Activity activity;
    private List<Question> items;

    public QuestionItemAdapter(Activity activity, List<Question> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_question, null);
        }

        Question question = items.get(i);

        TextView title = (TextView) view.findViewById(R.id.question_title);
        title.setText(question.getTitle());

        return view;
    }

    // TODO check if necessary
    public void clear() {
        items.clear();
    }

}
