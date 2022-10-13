package Gui;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GUI extends Application
{
	Stage window;
	
    public static void main( String[] args )
    {
        launch(args); 
    }
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
		window=primaryStage;		
		
		Scene mainScene = new Scene(root);
		mainScene.getStylesheets().add("Style.css");
		window.setScene(mainScene);
		window.setTitle("Assignment3");
		window.show();
		
		
	}
		

	public static Scene changeScene(URL fxml) throws IOException{
		Parent pane = FXMLLoader.load(fxml);
		Stage stage = new Stage();
        Scene newScene = new Scene(pane);
        newScene.getStylesheets().add("Style.css");
		stage.setScene(newScene);
		stage.show();
		
		return newScene;
	}
}
