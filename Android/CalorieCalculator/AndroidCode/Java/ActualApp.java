package com.example.rici.trialcaloriecalculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ActualApp extends AppCompatActivity {
    Button addCaloriesBtn; //eat food button
    Button deleteUserBtn; //delete user button
    Button updateUserActivityBtn; //open update user activity
    Button maintainBtn, loseBtn, loseAlotBtn; //goal buttons
    Button foodActivityBtn, messageActivityBtn, historyBtn, viewBtn, newDayBtn; //on-screen buttons
    static public EditText foodGramsInput; //food grams input
    EditText foodNameInput; // food name input
    static TextView caloriesText; //for progressbar
    static public ProgressBar daily; //progress bar
    static public int dayCalories=0,maxCalories=2000, weight, height, age;
    double BMI=0, BFP=0,burn=0; //mathematical values
    String idSent;
    String admin=""; //string that determines if consultant or user
    String exerciseSent="";
    AlertDialog alertDialog, alertDialogMax;
    static public String todayFood="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_app);

        //acquiring the information that was sent from the application with a bundle
        final Bundle bundle= getIntent().getExtras();
        admin=bundle.getString("Link0");
        idSent=bundle.getString("Link1");
        final String genderSent=bundle.getString("Link2");
        final String weightSent=bundle.getString("Link3");
        final String heightSent=bundle.getString("Link4");
        final String ageSent=bundle.getString("Link5");
        exerciseSent=bundle.getString("Link6");
        final String daySent=bundle.getString("Link7");
        final String maxSent=bundle.getString("Link8");
        final String goalSent=bundle.getString("Link9");
        dayCalories=Integer.parseInt(daySent);


        //mathematics
        weight=Integer.parseInt(weightSent);
        height=Integer.parseInt(heightSent);
        age=Integer.parseInt(ageSent);

        //BMI calculation
        BMI=weight/((height/100)^2);

        //the number of calories that the body burns and the BFP based on gender
        if(genderSent.equals("F") || genderSent.equals("Female")) {
            burn = 9.99 * weight + 6.25 * height - 4.92 * age - 161;//for females
            BFP=1.2*BMI+0.23*age-16.2;
        }
        else {
            burn = 9.99 * weight + 6.25 * height - 4.92 * age + 5;//for males
            BFP=1.2*BMI+0.23*age-5.4;
        }

        maxCalories=Integer.parseInt(maxSent);
        //end mathematics

        //dialog box initialization
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Information");
        alertDialog.setMessage("Your weight is: "+weightSent+"\nYour height is: "+heightSent+"\nYour age is: "+ageSent+"\nExercise/week: "+exerciseSent+ "\nYour BMI is: "+String.format("%.2f", BMI)+"\n"+"Your BFP is: "+String.format("%.2f", BFP)+"%\n"+"Your body burns "+String.format("%.2f", burn)+" calories/day"+"\n"+"Your current goal is: "+goalSent);

        //linking the components
        deleteUserBtn =(Button) findViewById(R.id.deleteUserBtn);
        updateUserActivityBtn =(Button) findViewById(R.id.updateUserBtn);
        foodActivityBtn =(Button) findViewById(R.id.foodBtn);
        messageActivityBtn =(Button) findViewById(R.id.messageBtn);
        historyBtn =(Button) findViewById(R.id.historyBtn);
        newDayBtn =(Button) findViewById(R.id.newDayBtn);
        viewBtn =(Button) findViewById(R.id.viewBtn);

        maintainBtn = (Button) findViewById(R.id.maintainBtn);
        loseBtn = (Button) findViewById(R.id.loseBtn);
        loseAlotBtn = (Button) findViewById(R.id.loseAlotBtn);


        addCaloriesBtn = (Button) findViewById(R.id.addCaloriesBtn);

        foodGramsInput =(EditText) findViewById(R.id.foodGramsText);
        foodNameInput = (EditText) findViewById(R.id.foodNameText);

        caloriesText= (TextView) findViewById(R.id.caloriesText);

        daily=(ProgressBar) findViewById(R.id.dailyProgress);
        daily.setIndeterminate(false);
        daily.setMax(maxCalories);
        daily.setProgress(dayCalories);
        //end linking

        //disables the button that represents the chosen goal
        if(goalSent.equals("lose"))
            loseBtn.setEnabled(false);
        if(goalSent.equals("loseplus"))
            loseAlotBtn.setEnabled(false);
        if(goalSent.equals("maintain"))
            maintainBtn.setEnabled(false);

        //setting up the progress bar numbers
        caloriesText.setText(dayCalories +"/"+maxCalories);

        //setting the dialog title
        alertDialogMax = new AlertDialog.Builder(this).create();
        alertDialogMax.setTitle("Status");

        //food name input click listener, this will open a google site for information about that food
        foodNameInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!foodNameInput.getText().toString().equals("")) {
                    Toast.makeText(ActualApp.this, foodNameInput.getText(), Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/#q=" + foodNameInput.getText() + " food")));
                }
            }
        });

        //update user click listener, this will open a new activity and send important information
        updateUserActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateIntent = new Intent(getApplicationContext(), UpdateAccount.class);
                bundle.putString("Link0", admin);
                bundle.putString("Link1", idSent);
                updateIntent.putExtras(bundle);
                startActivity(updateIntent);
            }
        });

        //food activity click listener, will open the food menu
        foodActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent foodIntent = new Intent(getApplicationContext(), FoodMenu.class);
                bundle.putString("Link0", admin);
                foodIntent.putExtras(bundle);
                startActivity(foodIntent);
            }
        });

        //message activity click listener, will open the message menu
        messageActivityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messageIntent = new Intent(getApplicationContext(), MessageMenu.class);
                bundle.putString("Link0", admin);
                bundle.putString("Link1", idSent);
                messageIntent.putExtras(bundle);
                startActivity(messageIntent);
            }
        });
    }

    //view history of food for today click listener
    public void onHistory(View v)
    {
        String type = "history";

        historyBtn.setEnabled(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                historyBtn.setEnabled(true);

            }
        }, 2000);

        if(admin.equals("admin"))
            new BackgroundWorker(this, "consultant").execute(type, idSent);
        else
            new BackgroundWorker(this, "user").execute(type, idSent);
    }

    //delete account click listener
    public void onDelete(View v)
    {
        String type = "delete";

        if(admin.equals("admin"))
            new BackgroundWorker(this, "consultant").execute(type, idSent);
        else
            new BackgroundWorker(this, "user").execute(type, idSent);


        Toast.makeText(ActualApp.this, "Data Deleted", Toast.LENGTH_LONG).show();
        finish();
    }

    //lose plus goal click listener
    public void onLosePlus(View v)
    {
        String type = "loseplus";
        maxCalories=(int)burn+Integer.parseInt(exerciseSent)*300/7;

        loseAlotBtn.setEnabled(false);
        loseBtn.setEnabled(true);
        maintainBtn.setEnabled(true);

        if(admin.equals("admin"))
            new BackgroundWorker(this, "consultant").execute(type,idSent, Integer.toString(maxCalories));
        else
            new BackgroundWorker(this, "admin").execute(type,idSent, Integer.toString(maxCalories));
    }

    //lose goal click listener
    public void onLose(View v)
    {
        String type = "lose";
        maxCalories=(int)burn+Integer.parseInt(exerciseSent)*300/7;

        loseAlotBtn.setEnabled(true);
        loseBtn.setEnabled(false);
        maintainBtn.setEnabled(true);

        if(admin.equals("admin"))
            new BackgroundWorker(this, "consultant").execute(type,idSent, Integer.toString(maxCalories));
        else
            new BackgroundWorker(this, "admin").execute(type,idSent, Integer.toString(maxCalories));
    }

    //maintain goal click listener
    public void onMaintain(View v)
    {
        String type = "maintain";
        maxCalories=(int)burn+Integer.parseInt(exerciseSent)*300/7;

        loseAlotBtn.setEnabled(true);
        loseBtn.setEnabled(true);
        maintainBtn.setEnabled(false);

        if(admin.equals("admin"))
            new BackgroundWorker(this, "consultant").execute(type,idSent, Integer.toString(maxCalories));
        else
            new BackgroundWorker(this, "admin").execute(type,idSent, Integer.toString(maxCalories));
    }

    //eat food click listener
    public void onAddCalories(View v)
    {
        String type = "addcalories";
        if(foodGramsInput.getText().toString().equals(""))
        {
            Toast.makeText(ActualApp.this, "No name", Toast.LENGTH_LONG).show();
        }
        if(foodGramsInput.getText().toString().equals("")) {
            Toast.makeText(ActualApp.this, "No grams", Toast.LENGTH_LONG).show();
        }
        else {
            addCaloriesBtn.setEnabled(false);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    addCaloriesBtn.setEnabled(true);

                }
            }, 2000);

            if (admin.equals("admin"))
                new BackgroundWorker(this, "consultant").execute(type, idSent, foodNameInput.getText().toString(), foodGramsInput.getText().toString());
            else
                new BackgroundWorker(this, "user").execute(type, idSent, foodNameInput.getText().toString(), foodGramsInput.getText().toString());
        }
    }

    //new day click listener
    public void onNewDay(View v)
    {
        String type = "reset";

        newDayBtn.setEnabled(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                newDayBtn.setEnabled(true);

            }
        }, 2000);

        if(admin.equals("admin"))
            new BackgroundWorker(this, "consultant").execute(type, idSent);
        else
            new BackgroundWorker(this, "user").execute(type, idSent);
    }

    //view information click listener
    public void onView(View v)
    {
        String type = "viewinfo";

        viewBtn.setEnabled(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                viewBtn.setEnabled(true);

            }
        }, 2000);

        if(admin.equals("admin"))
            new BackgroundWorker(this, "consultant").execute(type, idSent);
        else
            new BackgroundWorker(this, "user").execute(type, idSent);
    }
}
