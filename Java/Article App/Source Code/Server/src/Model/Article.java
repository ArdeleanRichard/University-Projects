package Model;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class Article implements Serializable {
	private static final long serialVersionUID = 1L;
	String title;
	String abstr;
	String author;
	String body;
	List<Article> related;
	
	public Article(String title, String abstr, String author, String body) {
		super();
		this.title = title;
		this.abstr = abstr;
		this.author = author;
		this.body = body;
	}
	
	public Article(String title, String abstr, String author, String body, String related) {
		super();
		this.title = title;
		this.abstr = abstr;
		this.author = author;
		this.body = body;
		
    	Gson gson = new Gson();
		Type type = new TypeToken<List<Article>>(){}.getType(); 
		this.related= gson.fromJson(related, type);
	}

	@Override
	public String toString() {
		return "Title=" + title + ", Abstract=" + abstr /*+ ", Author=" + author + ", Body=" + body + ", Related=" + related*/;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAbstr() {
		return abstr;
	}

	public void setAbstr(String abstr) {
		this.abstr = abstr;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<Article> getRelated() {
		return related;
	}

	public void setRelated(List<Article> related) {
		this.related = related;
	}
	
}
