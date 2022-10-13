package Listener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.UnknownHostException;
import java.util.Observer;

import Gui.Client;



public class ServerListener extends Thread {
	private ObjectInputStream input;
	private Observer view;
	
	public ServerListener(ObjectInputStream input, Observer view) throws UnknownHostException, IOException {
		this.input=input;
		this.view=view;
		
	}

	
	public void run() {
		try {
			while(true){
				Object o= input.readObject();
				//System.out.println(o.toString());
				view.update(null, o);
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}


	public ObjectInputStream getInput() {
		return input;
	}


	public void setInput(ObjectInputStream input) {
		this.input = input;
	}


	public Observer getView() {
		return view;
	}


	public void setView(Observer view) {
		this.view = view;
	}
}
