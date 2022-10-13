package Commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.*;

public class LoginCommand implements Command {

	
	private String username, password;
	private static String configFileName = "C:\\Users\\Rici\\Desktop\\Rici\\Facultate\\AN III\\Sem2\\SD\\Lab\\A3\\ThirdAssignment\\Server\\config.json";

	public LoginCommand(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Object execute() {
        String str = new String();
        String line = new String();
		try
        {
            try (FileReader fileReader = new FileReader(configFileName);
                 BufferedReader bufferedReader = new BufferedReader(fileReader))
            {
                while ((line = bufferedReader.readLine()) != null)
                {
                    str += line;
                }
                JSONObject obj = new JSONObject(str);	
                JSONArray writers = obj.getJSONArray("writer");
                JSONArray admins = obj.getJSONArray("admin");

                for(int i=0; i<writers.length();i++)
                {
                	if(writers.getJSONObject(i).get("username").equals(username) && writers.getJSONObject(i).get("password").equals(password))
                	{
                		return "WRITER";
                	}
                }
                for(int i=0; i<admins.length();i++)
                {
                	if(admins.getJSONObject(i).get("username").equals(username) && admins.getJSONObject(i).get("password").equals(password))
                	{
                		return "ADMIN";
                	}
                }
            }
        }

        catch (IOException ex)
        {
            System.out.println("Error reading file config");
        }
		return "ERROR";	
	}

//    public static void main( String[] args )
//    {
//    	try {
//			FileWriter fileWriter = new FileWriter(configFileName);
//			fileWriter.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
	
}
