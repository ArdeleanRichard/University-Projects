package PT2017.Homework2;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame
{
	private static final String INITIAL_VALUE = "0";
	
	public static JFrame frame;
	private JTextField minat=new JTextField(10); //minimum arriving time
	private JTextField maxat=new JTextField(10); //maximum arriving time
	private JTextField minst=new JTextField(10); //minimum service time
	private JTextField maxst=new JTextField(10); //minimum service time
	private JTextField nrq=new JTextField(10); //number of queues
	private JTextField sim=new JTextField(10); //simulation interval
	private JButton start= new JButton("Start");
	public static JTextArea logs=new JTextArea();
	public static JTextField output1=new JTextField();
	public static JTextField output2=new JTextField();
	private JScrollPane scroll1,scroll2;
	public static JTextField[] text;
	
	public static int at1,at2,st1,st2,q,s;
	
	GUI()
	{
		minat.setText(INITIAL_VALUE);
		maxat.setText(INITIAL_VALUE);
		minst.setText(INITIAL_VALUE);
		maxst.setText(INITIAL_VALUE);
		nrq.setText("1");
		sim.setText(INITIAL_VALUE);		
		
		frame=new JFrame("Queue");
		frame.setLayout(null);
		frame.setSize(900,700);
		
		JLabel l1=new JLabel("Minimum arriving time: ");
		l1.setBounds(10, 10, 150, 20);
		frame.add(l1);
		minat.setBounds(160, 10, 200, 20);
		frame.add(minat);
		
		JLabel l2=new JLabel("Maximum arriving time: ");
		l2.setBounds(410, 10, 150, 20);
		frame.add(l2);
		maxat.setBounds(560, 10, 200, 20);
		frame.add(maxat);

		JLabel l3=new JLabel("Minimum service time: ");
		l3.setBounds(10, 30, 150, 20);
		frame.add(l3);
		minst.setBounds(160, 30, 200, 20);
		frame.add(minst);
		
		JLabel l4=new JLabel("Maximum service time: ");
		l4.setBounds(410, 30, 200, 20);
		frame.add(l4);
		maxst.setBounds(560, 30, 200, 20);
		frame.add(maxst);

		JLabel l5=new JLabel("Number of queues: ");
		l5.setBounds(10, 50, 150, 20);
		frame.add(l5);
		nrq.setBounds(160, 50, 200, 20);
		frame.add(nrq);
		
		JLabel l6=new JLabel("Simulation interval: ");
		l6.setBounds(10, 70, 150, 20);
		frame.add(l6);
		sim.setBounds(160, 70, 200, 20);
		frame.add(sim);
		
		start.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
	            try 
	            {
					at1=Integer.parseInt(minat.getText());
					at2=Integer.parseInt(maxat.getText());
					if(at2<at1)
					{
						at2=0;
						maxat.setText("0");
						showError("Arriving time max>min");
					}
					st1=Integer.parseInt(minst.getText());
					st2=Integer.parseInt(maxst.getText());
					if(st2<st1)
					{
						st2=0;
						maxst.setText("0");
						showError("Service time max>min");
					}
					s=Integer.parseInt(sim.getText());
					if(s==0)
					{
						s=0;
						sim.setText("0");
						showError("Simulation interval>0");
					}
					q=Integer.parseInt(nrq.getText());
					if(q==0)
					{
						q=0;
						nrq.setText("0");
						showError("Number of queues>0");
					}
						
	            } 
	            catch (NumberFormatException nfex) 
	            {
	            	 showError("The inputs should be integer numbers.");
	            }
	            	      
				Queue queue[] = new Queue[q];
				text=new JTextField[q];
				for(int i=0; i<q; i++)
				{	
					queue[i]=new Queue(i);
					queue[i].start();
				}
			
				if(at2!=0 && st2!=0 && q!=0 && s!=0)
				{
					Generate g = new Generate(q, queue);
					g.start();
				}

			}
		});

		logs.setEditable(false);
		logs.setOpaque(true);
		scroll1=new JScrollPane(logs);
		
		output1.setBounds(460, 120, 400, 20);
		frame.add(output1);
		output2.setBounds(460, 150, 400, 20);
		frame.add(output2);
		
		start.setBounds(10, 100, 100, 20);
		frame.add(start);
		scroll1.setBounds(10, 120, 400, 500);
		frame.add(scroll1);
		
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	void showError(String errMessage)
	{
		JOptionPane.showMessageDialog(this, errMessage);
	}
	
	
	public static void main(String[] args) 
	{
		new GUI();
	}
}