package com.example.rici.trialcaloriecalculator;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    EditText name, email, username, password, password2, weight, height, age, gender, exercise;
    Button regBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //linking the components
        regBtn=(Button) findViewById(R.id.regBtn);
    }

    //registration click listener
    public void signUpClick(View v)
    {
        //linking the components
        name=(EditText) findViewById(R.id.nameText);
        email=(EditText) findViewById(R.id.emailText);
        username=(EditText) findViewById(R.id.usernameText);
        password=(EditText) findViewById(R.id.passwordText);
        password2=(EditText) findViewById(R.id.passwordText2);
        gender=(EditText) findViewById(R.id.genderText);
        weight=(EditText) findViewById(R.id.weightText);
        height=(EditText) findViewById(R.id.heightText);
        age=(EditText) findViewById(R.id.ageText);
        exercise=(EditText) findViewById(R.id.exerciseText);

        regBtn.setEnabled(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                regBtn.setEnabled(true);

            }
        }, 2000);

        if(!password.getText().toString().equals(password2.getText().toString()))
        {
            Toast.makeText(getApplicationContext(), "Passwords don't match",Toast.LENGTH_SHORT).show();
        }
        else
        {
            int w=Integer.parseInt(weight.getText().toString());
            int h=Integer.parseInt(height.getText().toString());
            int a=Integer.parseInt(age.getText().toString());
            int e=Integer.parseInt(exercise.getText().toString());
            String g= gender.getText().toString();
            if(e>=0 && e<14) {
                if (a > 0 && a < 126) {
                    if (h > 0 && h < 250) {
                        if (w > 0 && w < 400) {
                            if (g.equals("M") || g.equals("F") || g.equals("Male") || g.equals("Female")) {
                                String type = "register";
                                new BackgroundWorker(this).execute(type, name.getText().toString(), email.getText().toString(), username.getText().toString(), password.getText().toString(),
                                        g, weight.getText().toString(), height.getText().toString(), age.getText().toString(), exercise.getText().toString());

                            } else
                                Toast.makeText(getApplicationContext(), "Gender has to be M/F/Male/Female", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(getApplicationContext(), "That's a rather odd weight", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getApplicationContext(), "That's a rather odd height", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "That's a rather odd age", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(getApplicationContext(), "Now, now, isn't that a bit weird", Toast.LENGTH_SHORT).show();

        }
    }
}