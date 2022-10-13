package com.example.rici.trialcaloriecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    //EditTexts
    EditText username, password;
    //Buttons
    Button loginBtn,exitBtn, regBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //component linking
        loginBtn = (Button) findViewById(R.id.loginBtn);
        regBtn = (Button) findViewById(R.id.registerBtn);
        exitBtn=(Button) findViewById(R.id.exitBtn);

        username = (EditText) findViewById(R.id.usernameText);
        password = (EditText) findViewById(R.id.passwordText);

        //register button on click listener, goes to a new activity=screen
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), Registration.class);
                startActivity(startIntent);
            }
        });

        //exit button on click listener, it closes application
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //login button on click listener
    public void onLogin(View v)
    {
        //we will take the text that is written in the edittexts and convert into strings
        String un=username.getText().toString();
        String pw=password.getText().toString();


        //this part is just for aesthetics, it blocks the button from being clicked for 4seconds so that it cannot be clicked more than once
        loginBtn.setEnabled(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                loginBtn.setEnabled(true);

            }
        }, 4000);

        //get the current date and convert it into a string
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        String date=today.monthDay+"."+today.month+"."+today.year;

        //set type to "login", type is a string that represents what kind of action will be made in backgroundworker
        String type = "login";
        //we send also the username, password and date, these are parameters that will be used in the PHP part of the application
        new BackgroundWorker(this).execute(type, un, pw, date);

    }
}
