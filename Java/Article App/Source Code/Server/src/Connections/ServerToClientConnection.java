package Connections;

import java.io.IOException;import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Observable;

import Commands.Command;
import Commands.CommandFactory;
import Commands.ReadCommand;

public class ServerToClientConnection extends Observable implements Runnable{
	private Socket connection;
	private int ID;
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;
	private boolean opened=true;

	public ServerToClientConnection(Socket connection, int iD) {
		super();
		this.connection = connection;
		ID = iD;
	}

	@Override
	public void run() {
		try {
			inStream = new ObjectInputStream(connection.getInputStream());
			outStream = new ObjectOutputStream(connection.getOutputStream());
			while (true) {
				String received = (String) inStream.readObject();
				String[] args = received.split("\n");
				Command command = CommandFactory.getCommand(args);
				if (command != null) {
					outStream.writeObject(command.execute());
				} else {
					outStream.writeObject(null);
				}
				setChanged();
				notifyObservers();

			}
		} catch (SocketException e) {
			System.out.println("Client " + ID + " left");
			opened =  false;
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	public Socket getConnection() {
		return connection;
	}

	public void setConnection(Socket connection) {
		this.connection = connection;
	}

	public int getID() {
		return ID;
	}
	public boolean isOpened() {
		return opened;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	public void sendObj(Object msg) throws IOException{
		if(outStream!=null)
			outStream.writeObject(msg);
	}
	
	public void update() {
		//System.out.println("Updating user");
		ReadCommand rc = new ReadCommand();
		rc.execute();
	}
}
