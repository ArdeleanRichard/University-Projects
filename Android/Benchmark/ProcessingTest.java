package com.example.rici.mobilebenchmark;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Rici on 05-Apr-18.
 */

public class ProcessingTest {

    private long pastTime;

    Bundle bundle = new Bundle();

    Long BFTestTime=0L;
    Long IOpATestTime=0L;
    Long IOpSTestTime=0L;
    Long IOpMTestTime=0L;
    Long IOpDTestTime=0L;
    Long FOpATestTime=0L;
    Long FOpSTestTime=0L;
    Long FOpMTestTime=0L;
    Long FOpDTestTime=0L;
    Long PrimeTestTime=0L;

    private int howManyPrimes = 100000;

    ArrayList<Integer> intAdd = new ArrayList<>();
    ArrayList<Integer> intSub = new ArrayList<>();
    ArrayList<Integer> intMul = new ArrayList<>();
    ArrayList<Integer> intDiv = new ArrayList<>();

    ArrayList<Integer> floatAdd = new ArrayList<>();
    ArrayList<Integer> floatSub = new ArrayList<>();
    ArrayList<Integer> floatMul = new ArrayList<>();
    ArrayList<Integer> floatDiv = new ArrayList<>();

    private double floatData[];
    private int intData[];
    private int dataSize=10000000;


    public Bundle calculate(String testString, TextView result)
    {
        ArrayList<Integer> SHAtimes = new ArrayList<>();
        ArrayList<Integer> MD5times = new ArrayList<>();
        ArrayList<Integer> inttimes = new ArrayList<>();
        ArrayList<Integer> floattimes = new ArrayList<>();

        Random generator = new Random();
        floatData = new double[dataSize];
        intData = new int[dataSize];
        for (int i = 0; i < dataSize; i++) {
            floatData[i] = generator.nextDouble();
            intData[i]=generator.nextInt();
        }

        //SHA1 encoding

        long timp=0;

        pastTime = System.nanoTime();
        for (Integer i = 0; i<20000; i++)
        {
            if(i%1000==0)
                timp = System.nanoTime();
            computeSHAHash(testString);
            if(i%1000==0)
                SHAtimes.add((int)((System.nanoTime() - timp)/1000));
        }
        Long SHATestTime = System.nanoTime() - pastTime;
        bundle.putIntegerArrayList("SHA", SHAtimes);


        //MD5 encoding
        pastTime = System.nanoTime();
        for (Integer i = 0; i<20000; i++) {
            if(i%1000==0)
                timp = System.nanoTime();
            computeMD5Hash(testString);
            if(i%1000==0)
                MD5times.add((int)((System.nanoTime() - timp)/1000));
        }
        Long MDTestTime = System.nanoTime() - pastTime;
        bundle.putIntegerArrayList("MD5", MD5times);

        pastTime = System.nanoTime();
        bruteForce();
        BFTestTime = System.nanoTime() - pastTime;

        pastTime = System.nanoTime();
        findPrimes();
        PrimeTestTime = System.nanoTime() - pastTime;

        pastTime = System.nanoTime();
        integerAddition();
        IOpATestTime = System.nanoTime() - pastTime;

        pastTime = System.nanoTime();
        integerSubtraction();
        IOpSTestTime = System.nanoTime() - pastTime;

        pastTime = System.nanoTime();
        integerMultiplication();
        IOpMTestTime = System.nanoTime() - pastTime;


        pastTime = System.nanoTime();
        integerDivision();
        IOpDTestTime = System.nanoTime() - pastTime;

        pastTime = System.nanoTime();
        floatingPointAddition();
        FOpATestTime = System.nanoTime() - pastTime;

        pastTime = System.nanoTime();
        floatingPointSubtraction();
        FOpSTestTime = System.nanoTime() - pastTime;

        pastTime = System.nanoTime();
        floatingPointMultiplication();
        FOpMTestTime = System.nanoTime() - pastTime;

        pastTime = System.nanoTime();
        floatingPointDivision();
        FOpDTestTime = System.nanoTime() - pastTime;


        for(int i=0;i<20;i++)
            inttimes.add((intAdd.get(i)+intSub.get(i)+intMul.get(i)+intDiv.get(i))/4);

        for(int i=0;i<20;i++)
            floattimes.add((floatAdd.get(i)+floatSub.get(i)+floatMul.get(i)+floatDiv.get(i))/4);
        bundle.putIntegerArrayList("INT", inttimes);
        bundle.putIntegerArrayList("FLOAT", floattimes);



        long IOpTestTime = (IOpATestTime + IOpMTestTime + IOpSTestTime + IOpDTestTime)/4;
        long FOpTestTime = (FOpATestTime + FOpMTestTime + FOpSTestTime + FOpDTestTime)/4;

        String CPUScore =  String.format("%.2f", ((Double) Math.pow(
                (SHATestTime/1000000)*(MDTestTime/1000000)*(BFTestTime/1000000)*(IOpTestTime/1000000)*(FOpTestTime/1000000)*(PrimeTestTime/1000000),
                ((float)1.0/6))));

        result.setText(
            "CPU:" +
            "\nSHA-1: " + SHATestTime/1000000 + " milliseconds" +
            "\nMD5: " + MDTestTime/1000000 + " milliseconds" +
            "\nPrime: " + PrimeTestTime/1000000 + " milliseconds" +
            "\nCode Broken: " + BFTestTime/1000000 + " milliseconds" +
            "\n\nInteger Operations: " + IOpTestTime/1000000+ " milliseconds" +
            "\nAddition:" + IOpATestTime/1000000 + " milliseconds" +
            "\nSubtraction:" + IOpSTestTime/1000000 + " milliseconds" +
            "\nMultiplication:" + IOpMTestTime/1000000 + " milliseconds" +
            "\nDivision:" + IOpDTestTime/1000000 + " milliseconds" +
            "\n\nFloating Point Operations: " + FOpTestTime/1000000+ " milliseconds" +
            "\nAddition:" + FOpATestTime/1000000 + " milliseconds" +
            "\nSubtraction:" + FOpSTestTime/1000000 + " milliseconds" +
            "\nMultiplication:" + FOpMTestTime/1000000 + " milliseconds" +
            "\nDivision:" + FOpDTestTime/1000000 + " milliseconds" +
            "\n\nCPU Score: " + CPUScore);

        return bundle;
    }
//--------------------------------------------------------
//CPU
//--------------------------------------------------------
//ENCRYPTIONS
    public void computeSHAHash(String password)
    {
        
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(password.getBytes("ASCII"));

            byte[] hash = md.digest();

            StringBuffer SHA1Hash = new StringBuffer();

//            for (byte b : hash)
//            {
//                int halfbyte = (b >>> 4) & 0x0F;
//                int two_halfs = 0;
//                do {
//                    SHA1Hash.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
//                    halfbyte = b & 0x0F;
//                } while (two_halfs++ < 1);
//            }

            String result = bytesToHex(hash);

            //String hex=null;
            //hex = Base64.encodeToString(hash, 0, hash.length, 0);
            //SHA1Hash.append(hex);


        }
        catch (UnsupportedEncodingException e)
        {
            Log.e("Benchmark", "Error encoding SHA1");
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e1)
        {
            Log.e("Benchmark", "Error initializing SHA1");
        }
    }

    public static String bytesToHex(byte[] b) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuffer buf = new StringBuffer();
        for (int j=0; j<b.length; j++) {
            buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
            buf.append(hexDigit[b[j] & 0x0f]);
        }
        return buf.toString();
    }

    public void computeMD5Hash(String password)
    {
        
        try
        {
            byte[] data = password.getBytes("UTF-8");
            MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            md.update(data);
            byte hash[] = md.digest();

            StringBuffer MD5Hash = new StringBuffer();
            for (int i = 0; i < hash.length; i++)
            {
                if ((0xFF & hash[i]) < 0x10)
                {
                    MD5Hash.append("0" + Integer.toHexString((0xFF & hash[i])));
                }
                else
                {
                    MD5Hash.append(Integer.toHexString(0xFF & hash[i]));
                }

                //for (byte bytes : messageDigestMD5) {
                //    stringBuffer.append(String.format("%02x", bytes & 0xff));
                //}

            }

            /*
            byte messageDigest[] = md.digest();
            StringBuffer MD5Hash = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
            {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                MD5Hash.append(h);
            }*/

        }
        catch (NoSuchAlgorithmException e)
        {
            Log.e("Benchmark", "Error initializing MD5");
        }
        catch (UnsupportedEncodingException e)
        {
            Log.e("Benchmark", "Error encoding MD5");
            e.printStackTrace();
        }
    }

    public int bruteForce()
    {

        ArrayList<Integer> forcetimes = new ArrayList<>();
        //SHA1 encoding

        long pastTim=0;

        Integer code = 0;
        for (Integer i = 0; i < 9999999; i++)
        {
            if(i%(dataSize/20)==0)
                pastTim = System.nanoTime();
            code = i;
            if(i%(dataSize/20)==0)
                forcetimes.add((int)((System.nanoTime() - pastTim))/1000);
        }

        bundle.putIntegerArrayList("FORCE", forcetimes);
        return code;
    }

    //INTEGER OPERATIONS
    public long integerAddition()
    {
        long pastTim=0;
        long res = 0;
        for (int i = 0; i < dataSize; i++) {
            if(i%(dataSize/20)==0)
                pastTim = System.nanoTime();
            res = res + intData[i];
            if(i%(dataSize/20)==0)
                intAdd.add((int)((System.nanoTime() - pastTim)/1000));
        }
        return res;
    }
    public long integerSubtraction()
    {
        long pastTim=0;
        long res = 0;
        for (int i = 0; i < dataSize; i++) {
            if(i%(dataSize/20)==0)
                pastTim = System.nanoTime();
            res = res - intData[i];
            if(i%(dataSize/20)==0)
                intSub.add((int)((System.nanoTime() - pastTim)/1000));
        }
        return res;
    }
    public long integerMultiplication()
    {
        long pastTim=0;
        long res = 1;
        for (int i = 0; i < dataSize; i++) {
            if(i%(dataSize/20)==0)
                pastTim = System.nanoTime();
            res = res *intData[i];
            if(i%(dataSize/20)==0)
                intMul.add((int)((System.nanoTime() - pastTim)/1000));
        }
        return res;
    }
    public long integerDivision()
    {
        long pastTim=0;
        long res = Integer.MAX_VALUE;
        for (int i = 0; i < dataSize; i++) {
            if(i%(dataSize/20)==0)
                pastTim = System.nanoTime();
            res = res / intData[i];
            if(i%(dataSize/20)==0)
                intDiv.add((int)((System.nanoTime() - pastTim)/1000));
        }
        return res;
    }

    //FLOATING POINT OPERATIONS
    public double floatingPointAddition()
    {
        long pastTim=0;
        double res = 0;
        for (int i = 0; i < dataSize; i++) {
            if(i%(dataSize/20)==0)
                pastTim = System.nanoTime();
            res = res + floatData[i];
            if(i%(dataSize/20)==0)
                floatAdd.add((int)((System.nanoTime() - pastTim)/1000));
        }
        return res;
    }
    public double floatingPointSubtraction()
    {
        long timp =0;
        double res = 0;
        for (int i = 0; i < dataSize; i++) {
            if(i%(dataSize/20)==0)
                timp = System.nanoTime();
            res = res - floatData[i];
            if(i%(dataSize/20)==0)
                floatSub.add((int)((System.nanoTime() - timp)/1000));
        }
        return res;
    }
    public double floatingPointMultiplication()
    {
        long timp =0;
        double res = 0;
        for (int i = 0; i < dataSize; i++) {
            if(i%(dataSize/20)==0)
                timp = System.nanoTime();
            res = res * floatData[i];
            if(i%(dataSize/20)==0)
                floatMul.add((int)((System.nanoTime() - timp)/1000));
        }
        return res;
    }
    public double floatingPointDivision()
    {
        long timp =0;
        double res = Double.MAX_VALUE;
        for (int i = 0; i < dataSize; i++) {
            if(i%(dataSize/20)==0)
                timp = System.nanoTime();
            res = res / floatData[i];
            if(i%(dataSize/20)==0)
                floatDiv.add((int)((System.nanoTime() - timp)/1000));
        }
        return res;
    }

    private boolean isPrime(int nr)
    {
        if(nr<2)
            return false;
        if(nr==2)
            return true;
        if(nr%2==0)
            return false;
        for (int i = 3; i*i<=nr; i+=2)
        {
            if (nr % i == 0)
            {
                return false;
            }
        }
        return true;
    }


    private int findPrimes()
    {
        int count=0;
        int i=3;
        while(count<howManyPrimes)
        {
            if(isPrime(i))
                count++;
            i+=2;
        }
        return count;
    }

}
