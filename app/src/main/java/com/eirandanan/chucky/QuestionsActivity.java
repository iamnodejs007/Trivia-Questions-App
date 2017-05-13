package com.eirandanan.chucky;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import cz.msebera.android.httpclient.Header;

public class QuestionsActivity extends AppCompatActivity  implements QuestionsFragment.QuestionFragmentActivity {
    private int stage = 1;
    private final int QUESTIONS_MAX = 10;
    private String[] questions = new String[QUESTIONS_MAX];
    private String[] correctAnswer = new String[QUESTIONS_MAX];
    private String difficulty, numOfQuestions, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        numOfQuestions = getIntent().getStringExtra("numOfQuestions").toLowerCase();
        difficulty = getIntent().getStringExtra("difficulty").toLowerCase();
        type = getIntent().getStringExtra("type").toLowerCase();
        getJokes();
    }


    @Override
    public void updateActivity(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }


    void getJokes() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://opentdb.com/api.php?amount=" + numOfQuestions + "&difficulty=" + difficulty + "&type=" + type;

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    for (int i = 0; i < Integer.parseInt(numOfQuestions); i++) {
                        questions[i] = results.getJSONObject(i).getString("question").toString();
                        correctAnswer[i] = results.getJSONObject(i).getString("correct_answer").toString();
                    }
                    ((TextView) findViewById(R.id.stage)).
                            setText(stage + "/" + Integer.parseInt(numOfQuestions));
                    getSupportFragmentManager().
                            beginTransaction().
                            replace(R.id.bottom,
                                    QuestionsFragment.getInstance(questions[stage - 1], correctAnswer[stage - 1])).
                            commit();

                    pressNextQuestion();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void pressNextQuestion(){
        findViewById(R.id.nextBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stage < Integer.parseInt(numOfQuestions)) {
                    stage++;
                    ((TextView) findViewById(R.id.stage)).
                            setText(stage + "/" + Integer.parseInt(numOfQuestions));
                    getSupportFragmentManager().
                            beginTransaction().
                            replace(R.id.bottom,
                                    QuestionsFragment.getInstance(questions[stage - 1], correctAnswer[stage - 1])).
                            commit();
                } else {
                    findViewById(R.id.nextBtn).setEnabled(false);
                }

            }
        });
    }
}