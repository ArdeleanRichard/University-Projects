package PT2017.Homework2;

import java.util.*;

public class Generate extends Thread {
	private Queue queue[];
	private int queueNumber;
	private static int client = 0;
	
	//class constructor
	public Generate(int queueNumber, Queue queue[])
	{
		this.queueNumber = queueNumber;
		this.queue = new Queue[queueNumber];
		for(int i=0; i<queueNumber; i++)
		{
			this.queue[i] =queue[i] ; 
		}
	}

	//function that returns the index of the queue with the smallest number of elements
	private int minQueue() 
	{
		int index = 0;
		try
		{
			long min = queue[0].sizeQueue();
			for (int i = 1; i < queueNumber; i++) 
			{
				long size = queue[i].sizeQueue();
				if (size < min) 
				{
					min = size;
					index = i;
				}
			}
		} 
		catch (InterruptedException e) 
		{
			System.out.println(e.toString());
		}
		return index;
	}

	public void run() 
	{
		client=0;
		long maxsize=0;
		double peaktime=0;double sum=0,nr=0;
		try 
		{
			double time= 0;
			while ((int)time<GUI.s) 
			{
				int q=minQueue();
				Client c = new Client(++client);
				queue[q].addClient(c);

				double arrTime=Math.random()*(GUI.at2-GUI.at1)+GUI.at1;
				sleep((int) (arrTime * 1000));
				
				if(maxsize<Queue.size)
				{
					maxsize=Queue.size;
					peaktime=time;
				}
				
				sum=sum+Queue.servTime;nr++;
				GUI.output2.setText("");
				GUI.output2.setText("Average waiting time: "+sum/nr);
				
				GUI.output1.setText("Peak time: "+ peaktime +"\n");
	
				time=time+arrTime;
			}
		} 
		catch (InterruptedException e) 
		{
			System.out.println("Intrerupere");
		}
	}
}
