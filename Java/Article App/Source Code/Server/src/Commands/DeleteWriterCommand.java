package Commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class DeleteWriterCommand implements Command{
	private String username;
	private static String configFileName = "C:\\Users\\Rici\\Desktop\\Rici\\Facultate\\AN III\\Sem2\\SD\\Lab\\A3\\ThirdAssignment\\Server\\config.json";
	
	public DeleteWriterCommand(String username) {
		super();
		this.username = username;
	}

	@Override
	public Object execute() {
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
            		writers.remove(i);
            		// try-with-resources statement based on post comment below :)
            		try (FileWriter file = new FileWriter(configFileName)) {
            			file.write(obj.toString());
            			System.out.println("Successfully Deleted JSON Object from File...");
            		} catch (IOException e) {
            			return null;
            		}
            		return "SUCCESS";
            	}
            }
            

	    } catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}


		return "NO";
	}

}
