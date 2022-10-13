package com.example.rici.trialcaloriecalculator;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
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

    @Test
    public void login_isCorrect() throws Exception {
        String result;
        String un="Rici";
        String pw="1234";
        String date= "11.01.2016";
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
        String post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(un,"UTF-8")+"&"
                +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(pw,"UTF-8")+"&"
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
        assertNotEquals(result, "LoginFail");
    }

    @Test
    public void accountTaken_isCorrect() throws Exception {
        String result;
        String name = "Name";
        String email = "Email";
        String username = "Rici";
        String password = "1234";
        String gender = "Male";
        String weight = "65";
        String height = "173";
        String age = "25";
        String exercise = "3";
        URL url = new URL(registerUser_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        OutputStream outputStream = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        String post_data =
                URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                        + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                        + URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                        + URLEncoder.encode("gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8") + "&"
                        + URLEncoder.encode("weight", "UTF-8") + "=" + URLEncoder.encode(weight, "UTF-8") + "&"
                        + URLEncoder.encode("height", "UTF-8") + "=" + URLEncoder.encode(height, "UTF-8") + "&"
                        + URLEncoder.encode("age", "UTF-8") + "=" + URLEncoder.encode(age, "UTF-8") + "&"
                        + URLEncoder.encode("exercise", "UTF-8") + "=" + URLEncoder.encode(exercise, "UTF-8");
        bufferedWriter.write(post_data);
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
        result = "";
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }
        bufferedReader.close();
        inputStream.close();
        httpURLConnection.disconnect();
        assertEquals(result, "UsernameAlreadyExists");
    }

    @Test
    public void foodExists_isCorrect() throws Exception {
        String result;
        String name = "cucumber";
        String calories = "16";
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
        assertEquals(result, "FoodAlreadyExists");
    }

    @Test
    public void addCalories_isCorrect() throws Exception {
        String result;
        String id = "1";
        String name = "cucumber";
        String grams = "250";
        URL url = new URL(addCalories_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        OutputStream outputStream = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        String post_data = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&"
                + URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&"
                + URLEncoder.encode("grams", "UTF-8") + "=" + URLEncoder.encode(grams, "UTF-8");
        bufferedWriter.write(post_data);
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
        result = "";
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }
        bufferedReader.close();
        inputStream.close();
        httpURLConnection.disconnect();
        assertNotEquals(result, "UpdateFail");
    }
    @Test
    public void loseGoal_isCorrect() throws Exception {
        String result;
        String id = "1";
        String maxcalories = "1880";
        URL url = new URL(lose_url);
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
        assertNotEquals(result, "UpdateFail");
        assertNotEquals(result, "NotWorking");
    }
}