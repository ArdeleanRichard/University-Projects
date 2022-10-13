package com.example.rici.trialcaloriecalculator;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FoodMenu extends AppCompatActivity {

    Button addFoodBtn,deleteFoodBtn,updateFoodBtn, viewFoodBtn;
    EditText foodNameText, foodCaloriesText, foodIdText;
    String admin="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);

        //acquiring the information that was sent from the application with a bundle
        final Bundle bundle= getIntent().getExtras();
        admin=bundle.getString("Link0");

        //linking the components
        addFoodBtn=(Button) findViewById(R.id.addFoodBtn);
        updateFoodBtn=(Button) findViewById(R.id.updateFoodBtn);
        deleteFoodBtn=(Button) findViewById(R.id.deleteFoodBtn);
        viewFoodBtn=(Button) findViewById(R.id.viewFoodBtn);
        foodNameText=(EditText) findViewById(R.id.foodNameText);
        foodCaloriesText=(EditText) findViewById(R.id.foodCaloriesText);
        foodIdText=(EditText) findViewById(R.id.foodIdText);

        //setting some components invisible for users
        foodIdText.setVisibility(View.INVISIBLE);
        deleteFoodBtn.setVisibility(View.INVISIBLE);
        updateFoodBtn.setVisibility(View.INVISIBLE);

        //showing certain parts of the application only for consultants
        if(admin.equals("admin"))
        {
            foodIdText.setVisibility(View.VISIBLE);
            deleteFoodBtn.setVisibility(View.VISIBLE);
            updateFoodBtn.setVisibility(View.VISIBLE);
        }

        /*
        viewFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = db.getAllFood();
                if(res.getCount() == 0) {
                    // show message
                    showMessage("Error","Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("Id :"+ res.getString(0)+"\n");
                    buffer.append("Name :"+ res.getString(1)+"\n");
                    buffer.append("Calories :"+ res.getString(2)+"\n");
                }

                // Show all data
                showMessage("Food:",buffer.toString());
            }
        });*/
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    //add food click listener
    public void onAddFood(View v)
    {
        String name = foodNameText.getText().toString();
        String cal= foodCaloriesText.getText().toString();
        String type = "insertfood";

        addFoodBtn.setEnabled(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                addFoodBtn.setEnabled(true);

            }
        }, 2000);

        new BackgroundWorker(this).execute(type, name, cal);
    }

    //view food click listener
    public void onViewFood(View v)
    {
        String type = "viewfood";

        viewFoodBtn.setEnabled(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                viewFoodBtn.setEnabled(true);

            }
        }, 2000);

        new BackgroundWorker(this).execute(type);
    }

    //update food click listener
    public void onUpdateFood(View v)
    {
        String id = foodIdText.getText().toString();
        String name = foodNameText.getText().toString();
        String cal= foodCaloriesText.getText().toString();
        String type = "updatefood";

        updateFoodBtn.setEnabled(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                updateFoodBtn.setEnabled(true);

            }
        }, 2000);

        new BackgroundWorker(this).execute(type, id, name, cal);

    }

    //delete food click listener
    public void onDeleteFood(View v)
    {
        String id = foodIdText.getText().toString();
        String type = "deletefood";

        deleteFoodBtn.setEnabled(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                deleteFoodBtn.setEnabled(true);

            }
        }, 2000);

        new BackgroundWorker(this).execute(type, id);
    }
}
