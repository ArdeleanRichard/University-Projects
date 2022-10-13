package com.example.rici.mobilebenchmark;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Random;

public class Chart extends AppCompatActivity  {

    Random random;

    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        final Bundle bundle= getIntent().getExtras();
        ArrayList<Integer> shaInts = bundle.getIntegerArrayList("SHA");
        ArrayList<Integer> md5Ints = bundle.getIntegerArrayList("MD5");
        ArrayList<Integer> forceInts = bundle.getIntegerArrayList("FORCE");
        ArrayList<Integer> intOpInts = bundle.getIntegerArrayList("INT");
        ArrayList<Integer> floatOpInts = bundle.getIntegerArrayList("FLOAT");
        ArrayList<Integer> copy1Ints = bundle.getIntegerArrayList("COPY1");
        ArrayList<Integer> copy2Ints = bundle.getIntegerArrayList("COPY2");

        lineChart = (LineChart) findViewById(R.id.linechart);

        //lineChart.setScaleEnabled(false);

        ArrayList<Entry> yValues = new ArrayList<>();

        for(int i=0;i<20;i++) {
            random = new Random();
            float value = random.nextFloat()*40+60;
            yValues.add(new Entry(i, value));
        }


        LineDataSet set = new LineDataSet(yValues, "Data set");

        set.setFillAlpha(110);
        set.setColor(Color.RED);
        set.setLineWidth(3f);

//SHA
        ArrayList<Entry> shaValues = new ArrayList<>();
        int i=0;
        for(Integer val: shaInts) {
            shaValues.add(new Entry(i, val));
            i++;
        }

        LineDataSet shaSet = new LineDataSet(shaValues, "SHA");

        shaSet.setFillAlpha(110);
        shaSet.setColor(Color.RED);
        shaSet.setLineWidth(3f);

//MD5
        ArrayList<Entry> md5Values = new ArrayList<>();
        i=0;
        for(Integer val: md5Ints) {
            md5Values.add(new Entry(i, val));
            i++;
        }

        LineDataSet md5Set = new LineDataSet(md5Values, "MD5");

        md5Set.setFillAlpha(110);
        md5Set.setColor(Color.BLUE);
        md5Set.setLineWidth(3f);

//FORCE
        ArrayList<Entry> forceValues = new ArrayList<>();
        i=0;
        for(Integer val: forceInts) {
            forceValues.add(new Entry(i, val));
            i++;
        }

        LineDataSet forceSet = new LineDataSet(forceValues, "FORCE");

        forceSet.setFillAlpha(110);
        forceSet.setColor(Color.GREEN);
        forceSet.setLineWidth(3f);

//INT OPS
        ArrayList<Entry> intOpValues = new ArrayList<>();
        i=0;
        for(Integer val: intOpInts) {
            intOpValues.add(new Entry(i, val));
            i++;
        }

        LineDataSet intOpSet = new LineDataSet(forceValues, "INT OP");

        intOpSet.setFillAlpha(110);
        intOpSet.setColor(Color.YELLOW);
        intOpSet.setLineWidth(3f);

//FLOAT OPS
        ArrayList<Entry> floatOpValues = new ArrayList<>();
        i=0;
        for(Integer val: floatOpInts) {
            floatOpValues.add(new Entry(i, val));
            i++;
        }

        LineDataSet floatOpSet = new LineDataSet(forceValues, "FL OP");

        floatOpSet.setFillAlpha(110);
        floatOpSet.setColor(Color.BLACK);
        floatOpSet.setLineWidth(3f);

//COPY1
        ArrayList<Entry> copy1Values = new ArrayList<>();
        i=0;
        for(Integer val: copy1Ints) {
            copy1Values.add(new Entry(i, val));
            i++;
        }

        LineDataSet copy1Set = new LineDataSet(copy1Values, "COPY1");

        copy1Set.setFillAlpha(110);
        copy1Set.setColor(Color.CYAN);
        copy1Set.setLineWidth(3f);

//COPY2
        ArrayList<Entry> copy2Values = new ArrayList<>();
        i=0;
        for(Integer val: copy2Ints) {
            copy2Values.add(new Entry(i, val));
            i++;
        }

        LineDataSet copy2Set = new LineDataSet(copy2Values, "COPY2");

        copy2Set.setFillAlpha(110);
        copy2Set.setColor(Color.MAGENTA);
        copy2Set.setLineWidth(3f);

//LINES
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        //sets.add(set);
        //sets.add(set2);
        sets.add(shaSet);
        sets.add(md5Set);
        sets.add(forceSet);
        sets.add(copy1Set);
        sets.add(copy2Set);
        sets.add(intOpSet);
        sets.add(floatOpSet);



        LineData data = new LineData(sets);
        lineChart.setData(data);


    }
}
