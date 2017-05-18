package com.saif.chucky;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;



public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
   private final String [] difficulty = {"Easy","Medium","Hard"};
    private final String [] type = {"Multiple", "Boolean"};
    private final String [] numOfQuestions = {"5","6","7","8","9","10"};
    private String choosenDifficulty, choosenType, choosenNumOfQuestions;
    private Button startButton;


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
                Intent intent = new Intent(MainActivity.this, QuestionsActivity.class);
                intent.putExtra("numOfQuestions",choosenNumOfQuestions);
                intent.putExtra("difficulty",choosenDifficulty);
                intent.putExtra("type",choosenType);
                startActivity(intent);
            }
        });
    }



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
