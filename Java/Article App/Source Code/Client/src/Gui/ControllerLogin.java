package Gui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPasswordField;

import org.json.JSONArray;
import org.json.JSONObject;

import Model.Article;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ControllerLogin implements Observer{
	
	@FXML
	public Button loginButton;
	
	@FXML
	public Button readerButton;
	
	@FXML
	public TextField username;
	
	@FXML
	public TextField password;

	Client client = Client.getInstance();
	
	public void login()
	{
		try 
		{
			client.getOutput().writeObject("login\n" + username.getText() + "\n" + password.getText() +"\n");
			String result = (String) client.getInput().readObject();
			if(result.equals("ADMIN"))
			{
	            //changeScene(loginButton,"admin.fxml");
				GUI.changeScene(getClass().getResource("admin.fxml"));
			}
			else
			{
			if(result.equals("WRITER"))
			{
				//GUI.changeScene(getClass().getResource("writer.fxml"));
				changeScene(loginButton,"writer.fxml");
			}
			else
				AlertBox.display("ERROR", "Account does not exist");
			}
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
	
	public void reader()
	{
		try 
		{
			//GUI.changeScene(getClass().getResource("reader.fxml"));
			changeScene(readerButton,"reader.fxml");	
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
    }

	@Override
	public void update(Observable o, Object arg) {
		System.out.println ("ControllerLogin : Observable is " + o.getClass() + ", object passed is " + arg.getClass());
	}
	
	protected void changeScene(Node source, String fxmlPath) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene newScene = new Scene(parent);
        newScene.getStylesheets().add("Style.css");
        Stage window = (Stage)(source).getScene().getWindow();
        window.setScene(newScene);
        window.show();
    }

}
