package com.example.rici.trialcaloriecalculator;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MessageMenu extends AppCompatActivity {

    Button viewQuestionBtn, addQuestionBtn, yourQuestionBtn, addAnswerBtn;
    EditText questionText,answerText,questionIdText;

    String admin="";
    String userid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_menu);

        //linking the components
        viewQuestionBtn = (Button) findViewById(R.id.viewQuestionBtn);
        addQuestionBtn = (Button) findViewById(R.id.addQuestionBtn);
        yourQuestionBtn = (Button) findViewById(R.id.yourQuestionBtn);
        addAnswerBtn = (Button) findViewById(R.id.addAnswerBtn);

        answerText = (EditText) findViewById(R.id.answerText);
        questionText = (EditText) findViewById(R.id.questionText);
        questionIdText = (EditText) findViewById(R.id.questionIdText);

        //acquiring the information that was sent from the application with a bundle
        final Bundle bundle= getIntent().getExtras();
        admin=bundle.getString("Link0");
        userid=bundle.getString("Link1");

        //choosing componenets to be seen by user/consultant
        if(admin.equals("admin"))
        {
            viewQuestionBtn.setVisibility(View.VISIBLE);
            addQuestionBtn.setVisibility(View.INVISIBLE);
            yourQuestionBtn.setVisibility(View.INVISIBLE);
            addAnswerBtn.setVisibility(View.VISIBLE);
            answerText.setVisibility(View.VISIBLE);
            questionText.setVisibility(View.INVISIBLE);
            questionIdText.setVisibility(View.VISIBLE);
        }
        else
        {
            viewQuestionBtn.setVisibility(View.INVISIBLE);
            addQuestionBtn.setVisibility(View.VISIBLE);
            yourQuestionBtn.setVisibility(View.VISIBLE);
            addAnswerBtn.setVisibility(View.INVISIBLE);
            answerText.setVisibility(View.INVISIBLE);
            questionText.setVisibility(View.VISIBLE);
            questionIdText.setVisibility(View.INVISIBLE);
        }
    }

    //add question click listener
    public void onAddQuestion(View v)
    {
        String type = "insertquestion";

        addQuestionBtn.setEnabled(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                addQuestionBtn.setEnabled(true);

            }
        }, 2000);

        if(questionText.getText().toString().equals(""))
            Toast.makeText(this, "Enter question", Toast.LENGTH_LONG).show();
        else
            new BackgroundWorker(this).execute(type, userid, questionText.getText().toString());
    }

    //user can view his questions click listener
    public void onViewYourQuestion(View v)
    {
        String type = "viewyourquestion";

        yourQuestionBtn.setEnabled(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                yourQuestionBtn.setEnabled(true);

            }
        }, 2000);

        new BackgroundWorker(this).execute(type, userid);
    }

    //consultant add answer click listener
    public void onAddAnswer(View v)
    {
        String type = "insertanswer";

        addAnswerBtn.setEnabled(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                addAnswerBtn.setEnabled(true);

            }
        }, 2000);

        new BackgroundWorker(this).execute(type, questionIdText.getText().toString(), answerText.getText().toString());
    }

    //consultant view unanswered questions click listener
    public void onViewQuestion(View v)
    {
        String type = "viewquestion";

        viewQuestionBtn.setEnabled(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                viewQuestionBtn.setEnabled(true);

            }
        }, 2000);

        new BackgroundWorker(this).execute(type);
    }
}
