package Gui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import org.json.*;

import com.google.gson.*;

import Model.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class ControllerWriter extends Observable implements Initializable{
	
	@FXML
	public TextField titleTF;
	
	@FXML
	public TextField authorTF;
	
	@FXML
	public TextField abstractTF;
	
	@FXML
	public TextField bodyTF;
	
	@FXML
	public ListView<Article> relatedArticles;
	

	Client client = Client.getInstance();
	public void writing()
	{
		try {
			
			ObservableList<Article> sel = relatedArticles.getSelectionModel().getSelectedItems();
			
			List<Article> selected = new ArrayList<>(sel);
			
			Gson gson = new Gson();
			String muie = gson.toJson(selected);
			
			
			client.getOutput().writeObject("write\n" + authorTF.getText() + "\n" + titleTF.getText() +"\n" + abstractTF.getText() + "\n" + bodyTF.getText() +"\n" + muie + "\n");
			
			try {
				String res = (String) client.getInput().readObject();
				if(res.equals("SUCCESS"))
					AlertBox.display("Created", "Article has been written");
				else
					if(res.equals("EXISTS"))
						AlertBox.display("Error", "An article already has that name");
					else
						AlertBox.display("Error", "An error has occured");
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} 

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadData();
		
	}
	
	private void loadData() {
		try {
			client.getOutput().writeObject("read\n");
			ArrayList<Article> res = (ArrayList<Article>) client.getInput().readObject();
			
			ArrayList<String> titles = new ArrayList<>();
			for(Article a: res)
				titles.add(a.getTitle());
			
			ObservableList<Article> result = FXCollections.observableArrayList(res);
			
			relatedArticles.getItems().addAll(result);
			relatedArticles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		

		
	}
}
