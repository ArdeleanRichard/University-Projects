package PT2017.Homework2;

import java.util.*;

import javax.swing.JTextField;

public class Queue extends Thread {
	private Vector<Client> queue = new Vector<Client>();
	private int queueID;
	public static double servTime;
	public static long size=0;
	
	public Queue(int ID)
	{
		this.queueID=ID;
	}

	// overrided method of the Thread class
	public void run() 
	{
		
		//try - catch structure to avoid interruption errors
		try 
		{
			while ( true ) // the condition is always true because you want to delete as fast as you are allowed
			{
				// servTime is a random value between the minimum and maximum service time
				servTime = (Math.random() *( GUI.st2 - GUI.st1 ) + GUI.st1 );
				
				// sleep waits for the indicated amount of seconds before going to the next line
				sleep( ((int) servTime) * 1000 );
				// deleteClient deletes according to FIFO behaviour
				deleteClient();
				
			}
			
		}
		catch (InterruptedException e) 
		{
			System.out.println("Intrerupere");
		}
	}
	
	public synchronized void addClient(Client client) throws InterruptedException 
	{
		queue.addElement(client);
		GUI.logs.setText(GUI.logs.getText()+"Client " + client.getClient() + " arrived at Queue " + queueID+"\n");
		
        GUI.text[queueID]= new JTextField();
        GUI.text[queueID].setBounds(460, 180+queueID*20, 350, 20);
        GUI.frame.add(GUI.text[queueID]);
		GUI.text[queueID].setText(this.toString());
		
		size++;
		
		notifyAll(); 
	}

	public synchronized void deleteClient() throws InterruptedException 
	{
		while (queue.size() == 0) // verifies if the queue is empty
		{
			wait(); // if it is empty it waits for the notify
		}
		Client client = (Client) queue.elementAt(0); //saves the removed element in order to print it later
		queue.removeElementAt(0); // removes the beginning of the vector
		
		size--;
		
		// prints in the textarea every time a client leaves a queue
		GUI.logs.setText(GUI.logs.getText()+"Client " + Integer.toString(client.getClient()) + " left Queue " + queueID +"\n");
        
		GUI.text[queueID] = new JTextField();
		GUI.text[queueID].setBounds(460, 180+queueID*20, 350, 20);
        GUI.frame.add(GUI.text[queueID]);
		GUI.text[queueID].setText(this.toString());
		
		// wakes all threads in the waiting set
		notifyAll();
	}

	public synchronized long sizeQueue() throws InterruptedException 
	{
		notifyAll();
		long size = queue.size();
		return size;
	}
	
	public String toString()
	{
		String str = "Queue " + queueID + ": ";
		if(queue==null)
			System.out.println(str);
		else
		{
			for(Client i: this.queue)
			{
				str=str + i.getClient() + " ";
			}
		}
		return str;
	}
}
