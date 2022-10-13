package com.example.rici.mobilebenchmark;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Rici on 05-Apr-18.
 */

public class MemoryTest {

    private long pastTime;
    private long NR_OF_RUNS=20000;
    private long NR_TO_GO=5000;

    Bundle bundle = new Bundle();

    private Long sumList(ArrayList<Long> times)
    {
        Long sum=0L;
        for(Long i: times)
            sum=sum+i;
        return sum;
    }

    public Bundle calculate(TextView result)
    {
        //pastTime = System.nanoTime();
        ArrayList<Long> copy1times = new ArrayList<Long>();
        ArrayList<Long> copy2times = new ArrayList<Long>();
        for (Integer i = 0; i<NR_OF_RUNS; i++) {
            copy1times.add(computeCopy1());
            copy2times.add(computeCopy2());

        }
        Collections.sort(copy1times);
        Collections.sort(copy2times);
        for (Integer i = 0; i<NR_TO_GO; i++) {
            copy1times.remove(i);
            copy1times.remove(NR_OF_RUNS-(i+1));
            copy2times.remove(i);
            copy2times.remove(NR_OF_RUNS-(i+1));
        }

        ArrayList<Integer> copy1 = new ArrayList<>();
        ArrayList<Integer> copy2 = new ArrayList<>();

        for(int i=0;i<10000;i++)
            if(i%500==0)
            {
                copy1.add((int)(copy1times.get(i)/1000));
                copy2.add((int)(copy2times.get(i)/1000));
            }

        bundle.putIntegerArrayList("COPY1", copy1);
        bundle.putIntegerArrayList("COPY2", copy2);

        Long Copy1TestTime = sumList(copy1times);
        Long Copy2TestTime = sumList(copy2times);

        //VARIAZA MULT
//        pastTime = System.nanoTime();
//        for (Integer i = 0; i<200000; i++) {
//            computeCopy1();
//        }
//        Long Copy1TestTime = System.nanoTime() - pastTime;
//
//        pastTime = System.nanoTime();
//        for (Integer i = 0; i<200000; i++) {
//            computeCopy2();
//        }
//        Long Copy2TestTime = System.nanoTime() - pastTime;

        String memoryScore = String.format("%.2f", ((Double)Math.pow((Copy1TestTime/1000000)*(Copy2TestTime/1000000), ((float)1.0/2))));
        result.setText(
            "Memory:" +
            "\nCopy Arrays.copyOf: " + Copy1TestTime/1000000 + " milliseconds" +
            "\nCopy System.arraycopy: " + Copy2TestTime/1000000 + " milliseconds" +
            "\nMemory Score: "+ memoryScore);

        return bundle;
    }

//--------------------------------------------------------
//MEMORY
//--------------------------------------------------------
    public Long computeCopy1()
    {
        Integer[] src = {1, 2, 3, 4};
        pastTime = System.nanoTime();
        Integer[] dst = Arrays.copyOf(src, src.length);
        Long testTime = System.nanoTime() - pastTime;
        return testTime;
    }
    public Long computeCopy2()
    {
        Integer[] source = {1, 2, 3, 4};
        pastTime = System.nanoTime();
        Integer[] target = new Integer[source.length];
        System.arraycopy(source, 0, target, 0, source.length);
        Long testTime = System.nanoTime() - pastTime;
        return testTime;
    }
}
