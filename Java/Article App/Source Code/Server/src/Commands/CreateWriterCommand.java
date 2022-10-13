package Commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class CreateWriterCommand implements Command{

	private String username, password;
	private static String configFileName = "C:\\Users\\Rici\\Desktop\\Rici\\Facultate\\AN III\\Sem2\\SD\\Lab\\A3\\ThirdAssignment\\Server\\config.json";
	
	public CreateWriterCommand(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	public Object execute() {
		
		JSONObject writer = new JSONObject();
		writer.put("username", username);
		writer.put("password", password);
        
		JSONObject obj = null;
        String str = new String();
        String line = new String();
		try (FileReader fileReader = new FileReader(configFileName);
                BufferedReader bufferedReader = new BufferedReader(fileReader))
        {
	        while ((line = bufferedReader.readLine()) != null)
	        {
	            str += line;
	        }
	        obj = new JSONObject(str);	
	        JSONArray writers = obj.getJSONArray("writer");
	        JSONArray admins = obj.getJSONArray("admin");
	        JSONArray articles = obj.getJSONArray("article");
	        
            for(int i=0; i<writers.length();i++)
            {
            	if(writers.getJSONObject(i).get("username").equals(username))
            	{
            		return "EXISTS";
            	}
            }
            writers.put(writer);
            
	    } catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try (FileWriter file = new FileWriter(configFileName)) {
			file.write(obj.toString());
			System.out.println("Successfully Copied JSON Object to File...");
		} catch (IOException e) {
			return null;
		}
		return "SUCCESS";

	}
}
