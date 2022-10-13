package Gui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ControllerWriterDelete {
	@FXML
	public TextField usernameTF;
	
	
	Client client = Client.getInstance();
	public void deleteWriter()
	{
		try {
			client.getOutput().writeObject("deleteWriter\n" + usernameTF.getText() + "\n" );
			
			String result = (String) client.getInput().readObject();
			if(result.equals("SUCCESS"))
				AlertBox.display("Success", "Account Deleted");
			else
				AlertBox.display("Error", "Account not found");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
