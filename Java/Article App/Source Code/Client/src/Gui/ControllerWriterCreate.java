package Gui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ControllerWriterCreate {
	@FXML
	public TextField usernameTF;
	
	@FXML
	public TextField passwordTF;
	

	Client client = Client.getInstance();
	public void createWriter()
	{
		try {
			client.getOutput().writeObject("createWriter\n" + usernameTF.getText() + "\n" + passwordTF.getText() +"\n" );
			
			String result = (String) client.getInput().readObject();
			if(result.equals("SUCCESS"))
				AlertBox.display("Success", "Account Created");
			else
				if(result.equals("EXISTS"))
					AlertBox.display("Error", "Account already exists");
				else
					AlertBox.display("Error", "Account could not be created");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
