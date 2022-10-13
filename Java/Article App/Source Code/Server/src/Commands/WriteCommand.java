package Commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Observable;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Model.Article;
import javafx.collections.ObservableList;

public class WriteCommand implements Command{
	
	private String body, abstr, title, author;
	private List<Article> related;
	private static String configFileName = "C:\\Users\\Rici\\Desktop\\Rici\\Facultate\\AN III\\Sem2\\SD\\Lab\\A3\\ThirdAssignment\\Server\\config.json";
	
	public WriteCommand(String author, String title, String abstr, String body, String related) {
		super();
		this.body = body;
		this.abstr = abstr;
		this.title = title;
		this.author = author;
		
		Gson gson = new Gson();
		Type type = new TypeToken<List<Article>>(){}.getType(); 
		this.related = gson.fromJson(related, type);
	}
	
	@Override
	public Object execute() 
	{
		JSONObject art = new JSONObject();
		art.put("title", title);
		art.put("abstr", abstr);
		art.put("author", author);
		art.put("body", body);
        
		
        JSONArray relatedArticles = new JSONArray();
        for(Article a: related)
        {
        	JSONObject relatedArticle = new JSONObject();
        	relatedArticle.put("title", a.getTitle());
        	relatedArticle.put("abstr", a.getAbstr());
        	relatedArticle.put("author", a.getAuthor());
        	relatedArticle.put("body", a.getBody());
        	relatedArticles.put(relatedArticle);
        }
        art.put("related", relatedArticles);
        
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
	        
            for(int i=0; i<articles.length();i++)
            {
            	if(articles.getJSONObject(i).get("title").equals(title))
            	{
            		return "EXISTS";
            	}
            }
	        
	        articles.put(art);
	        
	    } catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// try-with-resources statement based on post comment below :)
		try (FileWriter file = new FileWriter(configFileName)) {
			file.write(obj.toString());
			System.out.println("Successfully Copied JSON Object to File...");
		} catch (IOException e) {
			return "ERROR";
		}
		return "SUCCESS";

	}

}
