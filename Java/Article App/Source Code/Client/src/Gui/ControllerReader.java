package Gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import Listener.ReaderListener;
import Listener.ServerListener;
import Model.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class ControllerReader implements Initializable, Observer{

	@FXML
	public Button readButton;
	
	@FXML
	public ListView<Article> articleList;
	
	@FXML
	public ListView<Article> relatedArticles;
	
	@FXML
	public TextArea titleText;
	
	@FXML
	public TextArea authorText;
	
	@FXML
	public TextArea abstrText;
	
	@FXML
	public TextArea bodyText;
	
	
	

	
	ObservableList<Article> result;
	ReaderListener rl;
	//private ServerListener listener;
	Client client = Client.getInstance();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadData();
//		try {
//			listener = new ServerListener(client.getInput(), this);
//			listener.start();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
	}
	
	private void loadData() {	
		try {
			client.getOutput().writeObject("read\n");
			
			ArrayList<Article> res = (ArrayList<Article>) client.getInput().readObject();

			ArrayList<String> titles = new ArrayList<>();
			for(Article a: res)
				titles.add(a.getTitle());
				
			result = FXCollections.observableArrayList(res);
			articleList.getItems().addAll(result);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rl = new ReaderListener(client.getInput(), this);
		rl.start();
		
		
		relatedArticles.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent event) {
	        	Article sel = relatedArticles.getSelectionModel().getSelectedItem();
	    		authorText.setText(sel.getAuthor());
	    		titleText.setText(sel.getTitle());
	    		abstrText.setText(sel.getAbstr());
	    		bodyText.setText(sel.getBody());
	    		relatedArticles.getItems().clear();
	    		if(sel.getRelated()!=null)
	    			relatedArticles.getItems().addAll(sel.getRelated());
	        }
	    });

	}
	
	public void reading()
	{
		Article sel = articleList.getSelectionModel().getSelectedItem();
		authorText.setText(sel.getAuthor());
		titleText.setText(sel.getTitle());
		abstrText.setText(sel.getAbstr());
		bodyText.setText(sel.getBody());
		relatedArticles.getItems().clear();
		relatedArticles.getItems().addAll(sel.getRelated());
	}


	
	@Override
	public void update(Observable arg0, Object arg1) {
			//client.getOutput().writeObject("read\n");
			//ArrayList<Article> res = (ArrayList<Article>) client.getInput().readObject();
			
			ArrayList<Article> res = (ArrayList<Article>) arg1;

			ObservableList<Article> result = FXCollections.observableArrayList(res);
			System.out.println(result);
			articleList.getItems().clear();
			articleList.getItems().addAll(result);
	}



	
}
