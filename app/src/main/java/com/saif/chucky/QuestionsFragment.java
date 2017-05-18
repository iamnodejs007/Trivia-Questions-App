package com.saif.chucky;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
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
            Handler handler = new Handler();
            if (((QuestionsActivity) getActivity()).getNumberOfQuestionsRelatedToStage() == 0) {
                final Runnable r = new Runnable() {
                    public void run() {
                        onModeFinished();
                    }
                };
                handler.postDelayed(r, 1000);
                return;
            }

            if (getArguments().getString("correctAnswer").equalsIgnoreCase(b.getText().toString())) {
                Toast.makeText(getContext(), "Correct Answer!", Toast.LENGTH_LONG).show();
                ((QuestionsActivity) getActivity()).increaseScore();

            } else {
                Toast.makeText(getContext(), "Incorrect Answer!", Toast.LENGTH_LONG).show();
            }
            final Runnable r2 = new Runnable() {
                public void run() {
                    ((QuestionsActivity) getActivity()).getNextQuestion();

                }
            };
            handler.postDelayed(r2, 1100);

        }
    }

    private void onModeFinished() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder((QuestionsActivity) getActivity());

        if (!((QuestionsActivity) getActivity()).getDifficulty().equals("hard")) {
            alertDialogBuilder.setMessage("Finished this mode, click OK to go to the next mode");
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            ((QuestionsActivity) getActivity()).getNextDifficulty();
                            ((QuestionsActivity) getActivity()).getQuestions();
                        }
                    });
        } else {
            alertDialogBuilder.setMessage("Finished the game(all modes), click OK to exit");
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            ((QuestionsActivity) getActivity()).finish();
                        }
                    });
        }
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}