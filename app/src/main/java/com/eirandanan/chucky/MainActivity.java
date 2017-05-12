package com.eirandanan.chucky;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
   private final String [] difficulty = {"Easy","Medium","Hard"};
    private final String [] type = {"Any","Multiple", "True /False"};
    private final String [] numOfQuestions = {"5","6","7","8","9","10"};
    private String choosenDifficulty, choosenType, choosenNumOfQuestions;
    private TextView tv;
    private Button startButton;
    SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initalizeSpinners(difficulty, R.id.difficultySpinner);
        initalizeSpinners(type, R.id.typeSpinner);
        initalizeSpinners(numOfQuestions, R.id.questionsSpinner);
        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,choosenDifficulty + " " + choosenNumOfQuestions + " " + choosenType,Toast.LENGTH_LONG).show();
            }
        });
       // tv = (TextView) findViewById(R.id.textView);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getJoke();
            }
        });
        getJoke();
    }

    void getJoke(){
        //swipeRefresh.setRefreshing(true);
        //AsyncHttpClient client = new AsyncHttpClient();
        //client.get("http://api.icndb.com/jokes/random", new JsonHttpResponseHandler() {
          //  @Override
           // public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //try {
                 //   tv.setText(Html.fromHtml(response.getJSONObject("value").getString("joke")));
                //    swipeRefresh.setRefreshing(false);
               // } catch (JSONException e) {
               //     e.printStackTrace();
             //   }
       //     }
     //   });
    }
//initalizeSpinners(difficulty, R.id.difficultySpinner)
    public void initalizeSpinners(String[] objects, int id) {
        Spinner spinner = (Spinner) findViewById(id);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, objects);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        switch(parent.getId()) {
            case R.id.difficultySpinner:
                choosenDifficulty = parent.getItemAtPosition(position).toString();
                break;
            case R.id.typeSpinner:
                choosenType = parent.getItemAtPosition(position).toString();
                break;

            case R.id.questionsSpinner:
                choosenNumOfQuestions = parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
