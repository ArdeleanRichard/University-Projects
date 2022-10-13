package Gui;

import java.io.IOException;

public class ControllerAdmin {
	
	public void openCreateWriter()
	{
		try {
			GUI.changeScene(getClass().getResource("writerCreate.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void openDeleteWriter()
	{
		try {
			GUI.changeScene(getClass().getResource("writerDelete.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void openUpdateWriter()
	{
		try {
			GUI.changeScene(getClass().getResource("writerUpdate.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
