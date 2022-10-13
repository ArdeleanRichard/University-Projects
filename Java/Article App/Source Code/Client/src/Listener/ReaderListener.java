package Listener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Observer;

import Model.Article;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;

public class ReaderListener extends Thread {

	private ObjectInputStream input;
	public Observer view;
	
	public ReaderListener(ObjectInputStream input, Observer view) 
	{
		super();
		this.input = input;
		this.view=view;
	}



	@Override
	public void run() {
		try 
		{
			ArrayList<Article> res;
			while(true) 
			{
				Object o= input.readObject();
				view.update(null, o);
			}
		} 
		catch (ClassNotFoundException | IOException e) 
		{
			e.printStackTrace();
		}
		
//		 Platform.runLater(() ->
//         {
//            
//         });
	}
}
