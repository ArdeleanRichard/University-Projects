package com.example.rici.mobilebenchmark;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity
{
    private String testString;
    private TextView result;


    Button chartBtn;

    MemoryTest memoryTest;
    ProcessingTest processingTest;

    Bundle processingBundle;
    Bundle memoryBundle;

    Boolean cpu = false;
    Boolean mem = false;

    public void onCalculateProcessing(View view) {
        cpu=true;
        processingBundle = processingTest.calculate(testString, result);
        if(cpu==true && mem==true)
            chartBtn.setVisibility(View.VISIBLE);
    }

    public void onCalculateMemory(View view) {
        mem=true;
        memoryBundle=memoryTest.calculate(result);
        if(cpu==true && mem==true)
            chartBtn.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.outputText);
        testString = getResources().getString(R.string.teststring);
        chartBtn = findViewById(R.id.chartBtn);

        memoryTest = new MemoryTest();
        processingTest = new ProcessingTest();

        chartBtn.setVisibility(View.INVISIBLE);

    }

    public void onChart(View view)
    {

        Intent intent = new Intent(getApplicationContext(), Chart.class);

        ArrayList<Integer> SHA = processingBundle.getIntegerArrayList("SHA");
        ArrayList<Integer> MD5 = processingBundle.getIntegerArrayList("MD5");
        ArrayList<Integer> forcetimes = processingBundle.getIntegerArrayList("FORCE");
        ArrayList<Integer> ints = processingBundle.getIntegerArrayList("INT");
        ArrayList<Integer> floats = processingBundle.getIntegerArrayList("FLOAT");

        ArrayList<Integer> copy1 = memoryBundle.getIntegerArrayList("COPY1");
        ArrayList<Integer> copy2 = memoryBundle.getIntegerArrayList("COPY2");

        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("SHA", SHA);
        bundle.putIntegerArrayList("MD5", MD5);
        bundle.putIntegerArrayList("FORCE", forcetimes);
        bundle.putIntegerArrayList("COPY1", copy1);
        bundle.putIntegerArrayList("COPY2", copy2);
        bundle.putIntegerArrayList("INT", ints);
        bundle.putIntegerArrayList("FLOAT", floats);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}
