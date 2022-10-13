package com.example.rici.trialcaloriecalculator;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Rici on 20-Nov-17.
 */

public class BackgroundWorker extends AsyncTask<String,Void,String> {
    Context context;
    AlertDialog alertDialog;
    String result = "", table="";
    static final Bundle bundle= new Bundle();
    String str="";

    //2 constructors, one where you only send the context(class from where you send), and when you also send what kind of user you are
    BackgroundWorker(Context ctx)
    {
        context = ctx;
    }
    BackgroundWorker(Context ctx, String str)
    {
        context = ctx;
        this.table=str;
    }


    String type;
    @Override
    protected String doInBackground(String... params) {
        type = params[0];

        //these are the links that the application uses when buttons are pressed, based on the type sent from the button
        String loginUser_url = "https://ccalculator.000webhostapp.com/cclogin.php";
        String registerUser_url = "https://ccalculator.000webhostapp.com/ccuserinsert.php";
        String deleteUser_url = "https://ccalculator.000webhostapp.com/ccuserdelete.php";
        String updateUser_url = "https://ccalculator.000webhostapp.com/ccuserupdate.php";
        String deleteCons_url = "https://ccalculator.000webhostapp.com/ccconsultantdelete.php";
        String updateCons_url = "https://ccalculator.000webhostapp.com/ccconsultantupdate.php";
        String insertFood_url = "https://ccalculator.000webhostapp.com/ccfoodinsert.php";
        String updateFood_url = "https://ccalculator.000webhostapp.com/ccfoodupdate.php";
        String deleteFood_url = "https://ccalculator.000webhostapp.com/ccfooddelete.php";
        String viewFood_url = "https://ccalculator.000webhostapp.com/ccfoodview.php";
        String addCalories_url = "https://ccalculator.000webhostapp.com/ccfoodaddcalories.php";
        String addCaloriesCons_url = "https://ccalculator.000webhostapp.com/ccfoodaddcaloriescons.php";
        String insertQuestion_url = "https://ccalculator.000webhostapp.com/ccquestioninsert.php";
        String yourQuestion_url = "https://ccalculator.000webhostapp.com/ccyourquestionview.php";
        String insertAnswer_url = "https://ccalculator.000webhostapp.com/ccanswerinsert.php";
        String viewQuestion_url = "https://ccalculator.000webhostapp.com/ccquestionview.php";
        String lose_url = "https://ccalculator.000webhostapp.com/ccgoallose.php";
        String losePlus_url = "https://ccalculator.000webhostapp.com/ccgoalloseplus.php";
        String maintain_url = "https://ccalculator.000webhostapp.com/ccgoalmaintain.php";
        String loseCons_url = "https://ccalculator.000webhostapp.com/ccgoallosecons.php";
        String losePlusCons_url = "https://ccalculator.000webhostapp.com/ccgoallosepluscons.php";
        String maintainCons_url = "https://ccalculator.000webhostapp.com/ccgoalmaintaincons.php";
        String resetUser_url = "https://ccalculator.000webhostapp.com/ccreset.php";
        String resetCons_url = "https://ccalculator.000webhostapp.com/ccresetconsultant.php";
        String history_url = "https://ccalculator.000webhostapp.com/ccfoodhistory.php";
        String historyCons_url = "https://ccalculator.000webhostapp.com/ccfoodhistorycons.php";
        String viewCurrent_url = "https://ccalculator.000webhostapp.com/cccurrent.php";
        String viewCurrentCons_url = "https://ccalculator.000webhostapp.com/cccurrentcons.php";
        String viewInfo_url = "https://ccalculator.000webhostapp.com/ccuserinfoview.php";
        String viewInfoCons_url = "https://ccalculator.000webhostapp.com/ccconsinfoview.php";
        /*
        String loginUser_url = "http://172.27.129.0/cclogin.php";
        String registerUser_url = "http://172.27.129.0/ccuserinsert.php";
        String deleteUser_url = "http://172.27.129.0/ccuserdelete.php";
        String updateUser_url = "http://172.27.129.0/ccuserupdate.php";
        String deleteCons_url = "http://172.27.129.0/ccconsultantdelete.php";
        String updateCons_url = "http://172.27.129.0/ccconsultantupdate.php";
        String insertFood_url = "http://172.27.129.0/ccfoodinsert.php";
        String updateFood_url = "http://172.27.129.0/ccfoodupdate.php";
        String deleteFood_url = "http://172.27.129.0/ccfooddelete.php";
        String viewFood_url = "http://172.27.129.0/ccfoodview.php";
        String addCalories_url = "http://172.27.129.0/ccfoodaddcalories.php";
        String addCaloriesCons_url = "http://172.27.129.0/ccfoodaddcaloriescons.php";
        String insertQuestion_url = "http://172.27.129.0/ccquestioninsert.php";
        String yourQuestion_url = "http://172.27.129.0/ccyourquestionview.php";
        String insertAnswer_url = "http://172.27.129.0/ccanswerinsert.php";
        String viewQuestion_url = "http://172.27.129.0/ccquestionview.php";
        String lose_url = "http://172.27.129.0/ccgoallose.php";
        String losePlus_url = "http://172.27.129.0/ccgoalloseplus.php";
        String maintain_url = "http://172.27.129.0/ccgoalmaintain.php";
        String loseCons_url = "http://172.27.129.0/ccgoallosecons.php";
        String losePlusCons_url = "http://172.27.129.0/ccgoallosepluscons.php";
        String maintainCons_url = "http://172.27.129.0/ccgoalmaintaincons.php";
        String resetUser_url = "http://172.27.129.0/ccreset.php";
        String resetCons_url = "http://172.27.129.0/ccresetconsultant.php";
        String history_url = "http://172.27.129.0/ccfoodhistory.php";
        String historyCons_url = "http://172.27.129.0/ccfoodhistorycons.php";
        String viewCurrent_url = "http://172.27.129.0/cccurrent.php";
        String viewCurrentCons_url = "http://172.27.129.0/cccurrentcons.php";
*/
        //if type is login then it will call the login part
        if(type.equals("login")) {
            try {
                //when clicking the login button, we also sent these 3 values as parameters, the username, password and date, so we acquire them
                String user_name = params[1];
                String password = params[2];
                String date= params[3];
                //for clicking login we use the URL we defined above for login
                URL url = new URL(loginUser_url);
                //we setup the connection to the URL
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                //we sent the values we have to the PHP file, with the accurate name of the PHP file and the name of the variable
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"
                        +URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(date,"UTF-8");
                bufferedWriter.write(post_data);
                //we close the buffers
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                //we read the result we chose to send them from the PHP and save it in a string "result"
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                //again we close the connections and buffers
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                //and finally we return the result
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("register"))
        {
            try {
                String name = params[1];
                String email = params[2];
                String username = params[3];
                String password = params[4];
                String gender = params[5];
                String weight = params[6];
                String height = params[7];
                String age = params[8];
                String exercise = params[9];
                URL url = new URL(registerUser_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                        +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                        +URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"
                        +URLEncoder.encode("gender","UTF-8")+"="+URLEncoder.encode(gender,"UTF-8")+"&"
                        +URLEncoder.encode("weight","UTF-8")+"="+URLEncoder.encode(weight,"UTF-8")+"&"
                        +URLEncoder.encode("height","UTF-8")+"="+URLEncoder.encode(height,"UTF-8")+"&"
                        +URLEncoder.encode("age","UTF-8")+"="+URLEncoder.encode(age,"UTF-8")+"&"
                        +URLEncoder.encode("exercise","UTF-8")+"="+URLEncoder.encode(exercise,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("delete"))
        {
            try {
                String id=params[1];
                URL url= new URL(deleteUser_url);
                if(table.equals("consultant"))
                    url = new URL(deleteCons_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("update"))
        {
            try {
                String id=params[1];
                String name = params[2];
                String email = params[3];
                String password = params[4];
                String weight = params[5];
                String height = params[6];
                String age = params[7];
                String exercise = params[8];
                URL url = new URL(updateUser_url);
                if(table.equals("consultant"))
                    url = new URL(updateCons_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&"
                                +URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                                +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                                +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"
                                +URLEncoder.encode("weight","UTF-8")+"="+URLEncoder.encode(weight,"UTF-8")+"&"
                                +URLEncoder.encode("height","UTF-8")+"="+URLEncoder.encode(height,"UTF-8")+"&"
                                +URLEncoder.encode("age","UTF-8")+"="+URLEncoder.encode(age,"UTF-8")+"&"
                                +URLEncoder.encode("exercise","UTF-8")+"="+URLEncoder.encode(exercise,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("current"))
        {
            try {
                String id=params[1];
                URL url = new URL(viewCurrent_url);
                if(table.equals("consultant"))
                    url = new URL(viewCurrentCons_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("insertfood")) {
            try {
                String name = params[1];
                String calories = params[2];
                URL url = new URL(insertFood_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                        +URLEncoder.encode("calories","UTF-8")+"="+URLEncoder.encode(calories,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("updatefood")) {
            try {
                String id = params[1];
                String name = params[2];
                String calories = params[3];
                URL url = new URL(updateFood_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&"
                        +URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                        +URLEncoder.encode("calories","UTF-8")+"="+URLEncoder.encode(calories,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("deletefood")) {
            try {
                String id = params[1];
                URL url = new URL(deleteFood_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("addcalories")) {
            try {
                String id = params[1];
                String name = params[2];
                String grams = params[3];
                URL url = new URL(addCalories_url);
                if(table.equals("consultant"))
                    url = new URL(addCaloriesCons_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&"
                        +URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                        +URLEncoder.encode("grams","UTF-8")+"="+URLEncoder.encode(grams,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                ActualApp.todayFood=ActualApp.todayFood+"Name: "+name+"\n";
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("history")) {
            try {
                String id = params[1];
                URL url = new URL(history_url);
                if(table.equals("consultant"))
                    url = new URL(historyCons_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("viewfood"))
        {
            try {
                URL url = new URL(viewFood_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        "";
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("insertquestion")) {
            try {
                String userid = params[1];
                String question = params[2];
                URL url = new URL(insertQuestion_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("userid","UTF-8")+"="+URLEncoder.encode(userid,"UTF-8")+"&"
                        +URLEncoder.encode("question","UTF-8")+"="+URLEncoder.encode(question,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("viewyourquestion"))
        {
            try {
                String userid = params[1];
                URL url = new URL(yourQuestion_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        URLEncoder.encode("userid","UTF-8")+"="+URLEncoder.encode(userid,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("insertanswer")) {
            try {
                String id = params[1];
                String answer = params[2];
                URL url = new URL(insertAnswer_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&"
                        +URLEncoder.encode("answer","UTF-8")+"="+URLEncoder.encode(answer,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("viewquestion"))
        {
            try {
                URL url = new URL(viewQuestion_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =
                        "";
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("loseplus")) {
            try {
                String id = params[1];
                String maxcalories = params[2];
                URL url = new URL(losePlus_url);
                if(table.equals("consultant"))
                    url = new URL(losePlusCons_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&"
                        +URLEncoder.encode("maxcalories","UTF-8")+"="+URLEncoder.encode(maxcalories,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("lose")) {
            try {
                String id = params[1];
                String maxcalories = params[2];
                URL url = new URL(lose_url);
                if(table.equals("consultant"))
                    url = new URL(loseCons_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&"
                        +URLEncoder.encode("maxcalories","UTF-8")+"="+URLEncoder.encode(maxcalories,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("maintain")) {
            try {
                String id = params[1];
                String maxcalories = params[2];
                URL url = new URL(maintain_url);
                if(table.equals("consultant"))
                    url = new URL(maintainCons_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&"
                        +URLEncoder.encode("maxcalories","UTF-8")+"="+URLEncoder.encode(maxcalories,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("reset"))
        {
            try {
                String id = params[1];
                URL url = new URL(resetUser_url);
                if(table.equals("consultant"))
                    url = new URL(resetCons_url);

                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("viewinfo")) {
            try {
                String id = params[1];
                URL url = new URL(viewInfo_url);
                if(table.equals("consultant"))
                    url = new URL(viewInfoCons_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();

    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("NoSuchFood"))
        {
            alertDialog.setTitle("Food Insertion Fail");
            alertDialog.setMessage(result);
            alertDialog.show();
        }
        if(!type.equals("login")&& (!type.equals("update")) && (!type.equals("addcalories")) && (!type.equals("viewfood")) && (!type.equals("viewyourquestion")) && (!type.equals("insertanswer"))&& (!type.equals("viewquestion")) && (!type.equals("lose"))&& (!type.equals("loseplus"))&& (!type.equals("maintain"))&& (!type.equals("history"))&& (!type.equals("current")))
        {
            alertDialog.setTitle("Status");
            alertDialog.setMessage(result);
            alertDialog.show();
        }
        if(type.equals("current"))
        {
            String[] values = result.split("-");
            alertDialog.setTitle("Status");
            alertDialog.setMessage("Name: "+values[0]+"\nEmail: "+values[1]+"\nUsername: "+values[2]+"\nPassword: "+values[3]+"\nGender: "+values[4]+"\nWeight: "+values[5]+"\nHeight: "+values[6]+"\nAge: "+values[7]+"\nExercises: "+values[8]);
            alertDialog.show();
        }
        if((!result.equals("NoSuchFood")) && type.equals("addcalories"))
        {
            ActualApp.dayCalories = Integer.parseInt(result);
            ActualApp.daily.setProgress(ActualApp.dayCalories);
            ActualApp.caloriesText.setText(ActualApp.dayCalories +"/"+ActualApp.maxCalories);
            ActualApp.todayFood=ActualApp.todayFood+"Calories: "+Integer.parseInt(result)*Integer.parseInt(ActualApp.foodGramsInput.getText().toString())/100+"\n\n";
            if(ActualApp.dayCalories>ActualApp.maxCalories)
            {
                alertDialog.setTitle("Daily max");
                alertDialog.setMessage("You have reached your daily maximum");
                alertDialog.show();
            }
        }
        if(type.equals("loseplus")) {
            ActualApp.daily.setMax(Integer.parseInt(result));
            ActualApp.daily.setProgress(ActualApp.dayCalories);
            ActualApp.caloriesText.setText(ActualApp.dayCalories +"/"+Integer.parseInt(result));
            if(ActualApp.dayCalories>ActualApp.daily.getMax())
            {
                alertDialog.setTitle("Daily max");
                alertDialog.setMessage("You have reached your daily maximum");
                alertDialog.show();
            }
        }
        if(type.equals("lose")) {
            ActualApp.daily.setMax(Integer.parseInt(result));
            ActualApp.daily.setProgress(ActualApp.dayCalories);
            ActualApp.caloriesText.setText(ActualApp.dayCalories +"/"+Integer.parseInt(result));
            if(ActualApp.dayCalories>ActualApp.daily.getMax())
            {
                alertDialog.setTitle("Daily max");
                alertDialog.setMessage("You have reached your daily maximum");
                alertDialog.show();
            }
        }
        if(type.equals("maintain")) {
            ActualApp.daily.setMax(Integer.parseInt(result));
            ActualApp.daily.setProgress(ActualApp.dayCalories);
            ActualApp.caloriesText.setText(ActualApp.dayCalories +"/"+Integer.parseInt(result));
            if(ActualApp.dayCalories>ActualApp.daily.getMax())
            {
                alertDialog.setTitle("Daily max");
                alertDialog.setMessage("You have reached your daily maximum");
                alertDialog.show();
            }
        }
        if(type.equals("viewinfo"))
        {
            String[] values=result.split(" ");

            double BMI, BFP, burn;
            int weight=Integer.parseInt(values[2]);
            int height=Integer.parseInt(values[3]);
            int age=Integer.parseInt(values[4]);
            int exercise=Integer.parseInt(values[5]);
            String goal=values[6];
            String gender=values[1];
            BMI=weight/((height/100)^2);
            if(gender.equals("F") || gender.equals("Female")) {
                burn = 9.99 * weight + 6.25 * height - 4.92 * age - 161;//for females
                BFP=1.2*BMI+0.23*age-16.2;
            }
            else {
                burn = 9.99 * weight + 6.25 * height - 4.92 * age + 5;//for males
                BFP=1.2*BMI+0.23*age-5.4;
            }

            if(values[0].equals("ViewSuccess"))
            {
                alertDialog.setTitle("Information");
                alertDialog.setMessage("Your weight is: "+weight+"\nYour height is: "+height+"\nYour age is: "+age+"\nExercise/week: "+exercise+ "\nYour BMI is: "+String.format("%.2f", BMI)+"\n"+"Your BFP is: "+String.format("%.2f", BFP)+"%\n"+"Your body burns "+String.format("%.2f", burn)+" calories/day"+"\n"+"Your current goal is: "+goal);
                alertDialog.show();
            }
            else
            {
                alertDialog.setTitle("Information");
                alertDialog.setMessage("Fail");
                alertDialog.show();
            }
        }
        if(type.equals("login"))
        {
            //the result we get is one string, we split it so we can use it effficiently
            String[] values=result.split(" ");

            //the first word (values[0]) will tell us what happened in the PHP
            //for userlogin we send one type of information to the application
            //for UserLoginSucces, a user has been found that matches the username and password
            if(values[0].equals("UserLoginSuccess"))
            {
                Intent startIntent = new Intent(context, ActualApp.class);
                bundle.putString("Link0", "user");
                bundle.putString("Link1", values[1]);
                bundle.putString("Link2", values[2]);
                bundle.putString("Link3", values[3]);
                bundle.putString("Link4", values[4]);
                bundle.putString("Link5", values[5]);
                bundle.putString("Link6", values[6]);
                bundle.putString("Link7", values[7]);
                bundle.putString("Link8", values[8]);
                bundle.putString("Link9", values[9]);
                startIntent.putExtras(bundle);
                context.startActivity(startIntent);
            }
            //for ConsultantLoginSucces, a consultant has been found that matches the username and password
            //and for consutant we send other values
            if(values[0].equals("ConsultantLoginSuccess"))
            {
                Intent startIntent = new Intent(context, ActualApp.class);
                bundle.putString("Link0", "admin");
                bundle.putString("Link1", values[1]);
                bundle.putString("Link2", values[2]);
                bundle.putString("Link3", values[3]);
                bundle.putString("Link4", values[4]);
                bundle.putString("Link5", values[5]);
                bundle.putString("Link6", values[6]);
                bundle.putString("Link7", values[7]);
                bundle.putString("Link8", values[8]);
                bundle.putString("Link9", values[9]);
                startIntent.putExtras(bundle);
                context.startActivity(startIntent);
            }
            //in the case of LoginFail, neither user, nor consultant that match those username and password has been found
            //so we open a dialog box that shows the error
            if(values[0].equals("LoginFail"))
            {
                alertDialog.setTitle("Login Fail");
                alertDialog.setMessage(values[0]);
                alertDialog.show();
            }

        }
        if(type.equals("update"))
        {
            String[] values = result.split(" ");
            alertDialog.setTitle("Status");
            alertDialog.setMessage(values[0]);
            alertDialog.show();
            if (values[0].equals("UserUpdateSuccess")) {
                ActualApp.daily.setMax(Integer.parseInt(values[1]));
                ActualApp.daily.setProgress(ActualApp.dayCalories);
                ActualApp.caloriesText.setText(ActualApp.dayCalories +"/"+Integer.parseInt(values[1]));
                if(ActualApp.dayCalories>ActualApp.daily.getMax())
                {
                    alertDialog.setTitle("Daily max");
                    alertDialog.setMessage("You have reached your daily maximum");
                    alertDialog.show();
                }
            }
            if (values[0].equals("ConsultantUpdateSuccess")) {
                ActualApp.daily.setMax(Integer.parseInt(values[1]));
                ActualApp.daily.setProgress(ActualApp.dayCalories);
                ActualApp.caloriesText.setText(ActualApp.dayCalories +"/"+Integer.parseInt(values[1]));
                if(ActualApp.dayCalories>ActualApp.daily.getMax())
                {
                    alertDialog.setTitle("Daily max");
                    alertDialog.setMessage("You have reached your daily maximum");
                    alertDialog.show();
                }
            }
        }
        if(type.equals("reset"))
        {
            String values[]=result.split(" ");
            alertDialog.setTitle("New Day");
            alertDialog.setMessage(values[0]);
            alertDialog.show();
            ActualApp.maxCalories=Integer.parseInt(values[1]);
            if (result.equals("NewDaySuccess")) {
                ActualApp.dayCalories = 0;
                ActualApp.daily.setProgress(ActualApp.dayCalories);
                ActualApp.caloriesText.setText(ActualApp.dayCalories +"/"+ActualApp.maxCalories);

            }
        }

        if(type.equals("viewfood")) {
            String[] values = result.split("-");
            String sol = "";
            int i = 0;

            while (!values[i].trim().equals("Gata")) {
                String value=values[i].replace("0","");
                sol = sol + "Name: " + value+ "\nCalories: " + values[i + 1] + "\n\n";
                i =i+2;
            }
            alertDialog.setTitle("Food Database");
            alertDialog.setMessage(sol);
            alertDialog.show();
        }

        if(type.equals("history")) {
            String[] values = result.split("-");
            String sol = "";
            int i = 0;

            while (!values[i].trim().equals("Gata")) {
                sol = sol + "Name: " + values[i] + "\nCalories: " + (int)Float.parseFloat(values[i + 1]) + "\n\n";
                i =i+2;
            }
            alertDialog.setTitle("Food History");
            alertDialog.setMessage(sol);
            alertDialog.show();
        }

        if(type.equals("viewyourquestion")) {

            String[] values = result.split("-");
            String sol = "";
            int i = 0;

            while (!values[i].trim().equals("Gata")) {
                sol = sol + "Question: " + values[i] + "\nAnswer: " + values[i + 1] + "\n\n";
                i =i+2;
            }
            alertDialog.setTitle("Your Questions");
            alertDialog.setMessage(sol);
            alertDialog.show();


        }
        if(type.equals("viewquestion")) {
            String[] values = result.split("-");
            String sol = "";
            int i = 0;

            while (!values[i].trim().equals("Gata")) {
                sol = sol + "ID: " + values[i] + "\nQuestion: " + values[i + 1] + "\n\n";
                i =i+2;
            }
            alertDialog.setTitle("Unanswered Questions");
            alertDialog.setMessage(sol);
            alertDialog.show();
        }

    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
