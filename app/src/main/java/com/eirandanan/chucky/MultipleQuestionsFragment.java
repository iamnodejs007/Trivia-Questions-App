package com.eirandanan.chucky;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
public class MultipleQuestionsFragment extends Fragment {

    public static MultipleQuestionsFragment getInstance(String question, String correctAnswer, String wrongAnswer1,
                                                        String wrongAnswer2, String wrongAnswer3) {
        MultipleQuestionsFragment f = new MultipleQuestionsFragment();
        Bundle b = new Bundle();
        b.putString("question", question);
        b.putString("correctAnswer", correctAnswer);
        b.putString("wrongAnswer1", wrongAnswer1);
        b.putString("wrongAnswer2", wrongAnswer2);
        b.putString("wrongAnswer3", wrongAnswer3);

        f.setArguments(b);
        return f;
    }

    public MultipleQuestionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_multiple_questions, container, false);

        TextView tv = (TextView) v.findViewById(R.id.question);
        tv.setText(getArguments().getString("question"));
        int correctAnswerPlacement = (int) Math.random() * 4 + 1;
        switch (correctAnswerPlacement) {
            case 1:
                setAnswerButton(v,getArguments().getString("correctAnswer"),"1");
                break;
            case 2:
                setAnswerButton(v,getArguments().getString("correctAnswer"),"2");

                break;
            case 3:
                setAnswerButton(v,getArguments().getString("correctAnswer"),"3");

                break;
            case 4:
                setAnswerButton(v,getArguments().getString("correctAnswer"),"4");

                break;
        }
        int index=1;
        for (int i = 0; i < 4; i++) {
            if (i + 1 != correctAnswerPlacement) {
                setAnswerButton(v,getArguments().getString("wrongAnswer" + index),Integer.toString(i+1));
                index++;
            }
        }
//        TextView tv1 = (TextView) v.findViewById(R.id.answerOneButton);
//        Log.i("dsa2",getArguments().getString("wrongAnswer1"));
//        tv1.setText(getArguments().getString("wrongAnswer1"));

        v.findViewById(R.id.answer1Button).setOnClickListener(new MultipleQuestionsFragment.RadioButtonClickListener());
        v.findViewById(R.id.answer2Button).setOnClickListener(new MultipleQuestionsFragment.RadioButtonClickListener());
        v.findViewById(R.id.answer3Button).setOnClickListener(new MultipleQuestionsFragment.RadioButtonClickListener());
        v.findViewById(R.id.answer4Button).setOnClickListener(new MultipleQuestionsFragment.RadioButtonClickListener());
        return v;
    }

    public class RadioButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            if (getArguments().getString("correctAnswer").equalsIgnoreCase(b.getText().toString())) {
                Toast.makeText(getContext(), "Correct Answer!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Incorrect Answer!", Toast.LENGTH_LONG).show();
            }
            ((QuestionsActivity) getActivity()).getNextQuestion();

//            switch (v.getId()) {
//                case R.id.answerOneButton:
//                    if (getArguments().getString("correctAnswer").equalsIgnoreCase(b.getText().toString())) {
//                        Toast.makeText(getContext(), "Correct Answer!", Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(getContext(), "Incorrect Answer!", Toast.LENGTH_LONG).show();
//                    }
//                    ((QuestionsActivity) getActivity()).getNextQuestion();
//
//                    break;
//
//                case R.id.answerTwoButton:
//                    if (getArguments().getString("correctAnswer").equalsIgnoreCase(b.getText().toString())) {
//                        Toast.makeText(getContext(), "Correct Answer!", Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(getContext(), "Incorrect Answer!", Toast.LENGTH_LONG).show();
//                    }
//                    ((QuestionsActivity) getActivity()).getNextQuestion();
//
//                    break;
//            }
        }
    }

    public static interface QuestionFragmentActivity {
        public void updateActivity(String s);
    }

    private void setAnswerButton(View v, String text, String answerNumber) {
        String buttonID = "answer" + answerNumber + "Button";
        int resID = getResources().getIdentifier(buttonID, "id", getActivity().getPackageName());
        TextView tv = (TextView) v.findViewById(resID);
        tv.setText(text);
    }
}