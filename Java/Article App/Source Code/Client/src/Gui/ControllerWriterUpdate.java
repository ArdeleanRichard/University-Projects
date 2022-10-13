package Gui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ControllerWriterUpdate {
	@FXML
	public TextField usernameTF;
	
	@FXML
	public TextField passwordTF;
	
	Client client = Client.getInstance();
	public void updateWriter()
	{
		try {
			client.getOutput().writeObject("updateWriter\n" + usernameTF.getText() + "\n" + passwordTF.getText() +"\n" );
			
			String result = (String) client.getInput().readObject();
			if(result.equals("SUCCESS"))
				AlertBox.display("Success", "Account Updated");
			else
				AlertBox.display("Error", "Account could not be found");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
