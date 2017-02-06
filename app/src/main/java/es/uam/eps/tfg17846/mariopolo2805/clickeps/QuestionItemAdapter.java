package es.uam.eps.tfg17846.mariopolo2805.clickeps;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        title.setText(question.getQuestion());

        Date now = new Date();

        final ImageView image = (ImageView) view.findViewById(R.id.question_icon);
        final TextView timer = (TextView) view.findViewById(R.id.question_timer);
        if(question.getSelection() != null) {
            image.setImageResource(android.R.drawable.ic_lock_lock);
            if (question.getSelection().equals(question.getSolution())) {
                timer.setText("Respuesta correcta");
            } else if (question.getSelection().equals("null")) {
                timer.setText("No sabe / No contesta");
            } else {
                timer.setText("Respuesta incorrecta");
            }
        } else if (now.after(question.getExpiration())) {
            image.setImageResource(android.R.drawable.ic_lock_lock);
            timer.setText("No sabe / No contesta");
        } else {
            image.setImageResource(android.R.drawable.ic_menu_help);
            long millis = question.getExpiration().getTime() - now.getTime();
            new CountDownTimer(millis, 1000) {
                public void onTick(long millis) {
                    long days = TimeUnit.MILLISECONDS.toDays(millis);
                    if (days > 0) {
                        timer.setText("Tiempo: " + days + " días");
                    } else {
                        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

                        timer.setText("Tiempo: " + hms);
                    }
                }

                public void onFinish() {
                    image.setImageResource(android.R.drawable.ic_lock_lock);
                    timer.setText("No sabe / No contesta");
                }
            }.start();
        }

        return view;
    }

}
