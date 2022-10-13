package Gui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import Listener.ServerListener;

public class Client implements Observer{
	private static Client instance;
	private Socket socketClient;
	private ObjectOutputStream output;
	private ObjectInputStream input;
//	private ServerListener listener;
	
	private Client() {
		super();

		try {
			socketClient = new Socket("localhost", 9981);
			output = new ObjectOutputStream(socketClient.getOutputStream());
			input = new ObjectInputStream(socketClient.getInputStream());
//			listener = new ServerListener(input, this);
//			listener.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Client getInstance(){
		if(instance==null){
			instance = new Client();
		}
		return instance;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	public ObjectOutputStream getOutput() {
		return output;
	}

	public void setOutput(ObjectOutputStream output) {
		this.output = output;
	}

	public ObjectInputStream getInput() {
		return input;
	}

	public void setInput(ObjectInputStream input) {
		this.input = input;
	}

}
