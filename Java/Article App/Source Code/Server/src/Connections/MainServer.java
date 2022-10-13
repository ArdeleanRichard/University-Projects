package Connections;

import java.io.IOException;import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Observable;

public class MainServer extends Thread implements Observer{
	public static final int portNumber = 9981;
	public  List<ServerToClientConnection> connections;
	private static MainServer instance;

	private MainServer() {
		super();
		connections = new ArrayList<ServerToClientConnection>();
	}
	
	public static MainServer getInstance(){
		if(instance==null){
			instance = new MainServer();
		}
		return instance;
	}


	public void run() {
		int count = 0;
		System.out.println("Starting the multiple socket server at port: " + portNumber);
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(portNumber);
			System.out.println("Multiple Socket Server Initialized");

			while (true) {
				Socket client = serverSocket.accept();
				ServerToClientConnection runnable = new ServerToClientConnection(client, ++count);
				connections.add(runnable);
				runnable.addObserver(this);
				Thread thread = new Thread(runnable);
				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null)
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}

	public List<ServerToClientConnection> getConnections() {
		return connections;
	}

	public static void main(String[] args) {
		MainServer mainServer = MainServer.getInstance();
		mainServer.start();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		//System.out.println(connections);
		//connections.get(0).update();
		connections.stream().forEach(e->{
			if(e.isOpened())
				e.update();
			//System.out.println("mure "+ e);
		});
	}

}

