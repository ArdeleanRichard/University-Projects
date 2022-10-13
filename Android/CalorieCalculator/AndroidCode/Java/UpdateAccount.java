package com.example.rici.trialcaloriecalculator;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class UpdateAccount extends AppCompatActivity {

    EditText name, email, password, password2, weight, height, age,gender, exercise;
    Button updateBtn, viewCurrentBtn;
    String admin="";
    String idSent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        //acquiring the information that was sent from the application with a bundle
        final Bundle bundle= getIntent().getExtras();
        admin=bundle.getString("Link0");
        idSent=bundle.getString("Link1");

        //linking the components
        name = (EditText) findViewById(R.id.nameText);
        email = (EditText) findViewById(R.id.emailText);
        password = (EditText) findViewById(R.id.passwordText);
        password2 = (EditText) findViewById(R.id.passwordText2);
        weight = (EditText) findViewById(R.id.weightText);
        height = (EditText) findViewById(R.id.heightText);
        age = (EditText) findViewById(R.id.ageText);
        gender=(EditText) findViewById(R.id.genderText);
        exercise=(EditText) findViewById(R.id.exerciseText);

        updateBtn = (Button) findViewById(R.id.updateAccountBtn);
        viewCurrentBtn = (Button) findViewById(R.id.viewCurrentBtn);
    }

    //update information click listener
    public void onUpdate(View view)
    {

        updateBtn.setEnabled(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                updateBtn.setEnabled(true);

            }
        }, 2000);

        if (password.getText().toString().equals(password2.getText().toString())) {
            if(admin.equals("admin"))
                new BackgroundWorker(this, "consultant").execute("update", idSent, name.getText().toString(), email.getText().toString(), password.getText().toString(), weight.getText().toString(),height.getText().toString(), age.getText().toString(), exercise.getText().toString());
            else
                new BackgroundWorker(this, "user").execute("update", idSent, name.getText().toString(), email.getText().toString(), password.getText().toString(), weight.getText().toString(),height.getText().toString(), age.getText().toString(), exercise.getText().toString());
        }
        else {
            Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
        }
    }

    //view current information click listener
    public void onCurrent(View view)
    {
        viewCurrentBtn.setEnabled(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                viewCurrentBtn.setEnabled(true);
            }
        }, 2000);

        if(admin.equals("admin"))
            new BackgroundWorker(this, "consultant").execute("current",idSent );
        else
            new BackgroundWorker(this, "user").execute("current", idSent );
    }
}
