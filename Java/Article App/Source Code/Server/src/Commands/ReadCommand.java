package Commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Model.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReadCommand implements Command {
	private static String configFileName = "C:\\Users\\Rici\\Desktop\\Rici\\Facultate\\AN III\\Sem2\\SD\\Lab\\A3\\ThirdAssignment\\Server\\config.json";
	
	public ReadCommand()
	{
		
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
                JSONArray articles = obj.getJSONArray("article");
                
                ArrayList<Article> as = new ArrayList<>();
                
                for(int i=0;i<articles.length();i++)
                {
                	//System.out.println(articles.getJSONObject(i));
                	JSONObject ceva = articles.getJSONObject(i);

            		
                	as.add(new Article(ceva.get("title").toString(), ceva.get("abstr").toString(), ceva.get("author").toString(), ceva.get("body").toString(), ceva.get("related").toString()));
                }
                
                return as;
            }
        }

        catch (IOException ex)
        {
            System.out.println("Error reading file config");
        }	
		return "NOPE";
	}
}
