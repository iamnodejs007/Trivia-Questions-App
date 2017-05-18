package com.eirandanan.chucky;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionsFragment extends Fragment {


    public static QuestionsFragment getInstance(String question, String correctAnswer) {
        QuestionsFragment f = new QuestionsFragment();
        Bundle b = new Bundle();
        b.putString("question", question);
        b.putString("correctAnswer", correctAnswer);
        f.setArguments(b);
        return f;
    }

    public QuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_questions, container, false);

        TextView tv = (TextView) v.findViewById(R.id.question);
        tv.setText(getArguments().getString("question"));


        RadioButton rbTrue = (RadioButton) v.findViewById(R.id.trueButton);
        RadioButton rbFalse = (RadioButton) v.findViewById(R.id.falseButton);

        rbTrue.setOnClickListener(new RadioButtonClickListener());
        rbFalse.setOnClickListener(new RadioButtonClickListener());


        return v;
    }

    public class RadioButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Button b = (Button) v;

            if (getArguments().getString("correctAnswer").equalsIgnoreCase(b.getText().toString())) {
                Toast.makeText(getContext(), "Correct Answer!", Toast.LENGTH_LONG).show();
                ((QuestionsActivity) getActivity()).increaseScore();

            } else {
                Toast.makeText(getContext(), "Incorrect Answer!", Toast.LENGTH_LONG).show();
            }
            ((QuestionsActivity) getActivity()).getNextQuestion();

        }
    }

    public static interface QuestionFragmentActivity {
        public void updateActivity(String s);
    }

}