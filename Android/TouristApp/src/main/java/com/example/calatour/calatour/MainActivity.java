package com.example.calatour.calatour;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button signIn;
    EditText inputUsername;
    EditText inputPassword;
    TextView errorUsername;
    TextView errorPassword;
    TextView errorSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("CalaTour App");

        signIn =(Button) findViewById(R.id.btnSignIn);
        inputUsername =(EditText) findViewById(R.id.inputUsername);
        inputPassword =(EditText) findViewById(R.id.inputPassword);
        errorUsername =(TextView) findViewById(R.id.errorUsername);
        errorPassword =(TextView) findViewById(R.id.errorPassword);
        errorSignIn =(TextView) findViewById(R.id.errorSignIn);
        errorUsername.setTextColor(Color.RED);
        errorPassword.setTextColor(Color.RED);
        errorSignIn.setTextColor(Color.RED);


//        signIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                }
//            });
    }

    public void SignInClick(View v)
    {
        boolean username = false;
        boolean password = false;
        if(inputUsername.length()==0)
        {
            errorUsername.setText("Username cannot be empty");
        }
        else
            if(inputUsername.length()<4)
            {
                errorUsername.setText("Username is too short");
            }
            else {
                errorUsername.setText("");
                username=true;
            }

        if(inputPassword.length()==0)
        {
            errorPassword.setText("Password cannot be empty");
        }
        else
            if(inputPassword.length()<7)
            {
                errorPassword.setText("Password is too short");
            }
            else {
                errorPassword.setText("");
                password=true;
            }

        if(password==true && username==true)
            if (inputUsername.getText().toString().equals("admin") && inputPassword.getText().toString().equals("password")) {
                errorSignIn.setTextColor(Color.GREEN);
                errorSignIn.setText("Login Successfull!");
                Intent intent =	new	Intent(this, OfferActivity.class);
                intent.putExtra("username", inputUsername.getText().toString());
                startActivity(intent);
            } else {
                errorSignIn.setTextColor(Color.RED);
                errorSignIn.setText("Login failed! Username or password are incorrect!");
            }
    }


}
