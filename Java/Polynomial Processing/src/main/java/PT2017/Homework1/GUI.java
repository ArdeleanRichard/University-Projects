package PT2017.Homework1;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame
{

	private static final String INITIAL_VALUE = "0";
	
	//... Components
	private JTextField m_Polynomial1 = new JTextField(20);
	private JTextField m_Polynomial2= new JTextField(20);
	private JTextField m_result = new JTextField(50);
	
	private JButton m_multiplyBtn = new JButton("Multiply");
	private JButton m_subBtn = new JButton("Subtract");
	private JButton m_subBtn1 = new JButton("p1-p2");
	private JButton m_subBtn2 = new JButton("p2-p1");
	private JButton m_divideBtn = new JButton("Divide");
	private JButton m_divideBtn1 = new JButton("p1/p2");
	private JButton m_divideBtn2 = new JButton("p2/p1");
	private JButton m_addBtn = new JButton("Add");
	private JButton m_derivBtn = new JButton("Derivate");
	private JButton m_derivBtn1 = new JButton("p1'");
	private JButton m_derivBtn2 = new JButton("p2'");
	private JButton m_integBtn = new JButton("Integrate");
	private JButton m_integBtn1 = new JButton("Sp1");
	private JButton m_integBtn2 = new JButton("Sp2");
	private JButton m_clearBtn = new JButton("Clear");
	
	GUI() 
	{
		//... Initialize components
		m_result.setText(INITIAL_VALUE);
		m_result.setEditable(false);
		
		//... Layout the components.
		JPanel content = new JPanel();
		Dimension d = new Dimension(1000,100);
		content.setPreferredSize(d);
		
		//first input panel
		JPanel input1 = new JPanel();
		input1.add(new JLabel("Input p1: "));
		input1.add(m_Polynomial1);
		input1.setLayout(new BoxLayout(input1, BoxLayout.X_AXIS));
		
		//second input panel
		JPanel input2 = new JPanel();
		input2.add(new JLabel("Input p2: "));
		input2.add(m_Polynomial2);
		input2.setLayout(new BoxLayout(input2, BoxLayout.X_AXIS));

		//operation panel
		JPanel Operation = new JPanel();JPanel OpSub = new JPanel();JPanel OpDeriv = new JPanel();JPanel OpInteg = new JPanel();JPanel OpDivide=new JPanel();
		Operation.add(new JLabel("Operation: "));
		Operation.add(m_addBtn);
		m_addBtn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
	        {
	            String userInput1 = "";
	            String userInput2 = "";
	            try 
	            {
	                userInput1 = getUserInput1();
	                userInput2 = getUserInput2();
	                String[] parts1 = userInput1.split("[x^+]");
	                String[] parts2 = userInput2.split("[x^+]");
	                Polynomial x = new Polynomial();
	                Polynomial y = new Polynomial();
	                for(int i=0;i<parts1.length;i=i+3)
	                {
	                	x.insertMonomial(new Monomial(Float.parseFloat(parts1[i]),Integer.parseInt(parts1[i+2])));
	                }
	                for(int i=0;i<parts2.length;i=i+3)
	                {
	                	y.insertMonomial(new Monomial(Float.parseFloat(parts2[i]),Integer.parseInt(parts2[i+2])));
	                }
	                x.add(y);
	                x.simplify();
	                setResult(x.toString());
	            } 
	            catch (NumberFormatException nfex) 
	            {
	                showError("Bad input.\nInput should look like this:\n2x^3+3x^1+1x^0");
	            }
	        }
		});
		
		OpSub.add(m_subBtn);
			m_subBtn.addActionListener(new ActionListener()
			{	
			    	 public void actionPerformed(ActionEvent e)
			    	 {
			    			m_subBtn1.setVisible(true);
			    			m_subBtn2.setVisible(true);
			    			m_subBtn.setVisible(false);
			    	 }
			});
			OpSub.add(m_subBtn1);
			m_subBtn1.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
		             String userInput1 = "";
		             String userInput2 = "";
		             try 
		             {
		                 userInput1 = getUserInput1();
		                 userInput2 = getUserInput2();
		                 String[] parts1 = userInput1.split("[x^+]");
		                 String[] parts2 = userInput2.split("[x^+]");
		                 Polynomial x = new Polynomial();
		                 Polynomial y = new Polynomial();
		                 for(int i=0;i<parts1.length;i=i+3)
		                 {
		                 	x.insertMonomial(new Monomial(Integer.parseInt(parts1[i]),Integer.parseInt(parts1[i+2])));
		                 }
		                 for(int i=0;i<parts2.length;i=i+3)
		                 {
		                 	y.insertMonomial(new Monomial(Integer.parseInt(parts2[i]),Integer.parseInt(parts2[i+2])));
		                 }
		                 x.sub(y);
		                 x.simplify();
		                 setResult(x.toString());

		             } 
		             catch (NumberFormatException nfex) 
		             {
		                 showError("Bad input.\nInput should look like this:\n2x^3+3x^1+1x^0");
		             }
		    	 }
			});
			
			OpSub.add(m_subBtn2);
			m_subBtn2.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
		             String userInput1 = "";
		             String userInput2 = "";
		             try 
		             {
		                 userInput1 = getUserInput1();
		                 userInput2 = getUserInput2();
		                 String[] parts1 = userInput1.split("[x^+]");
		                 String[] parts2 = userInput2.split("[x^+]");
		                 Polynomial x = new Polynomial();
		                 Polynomial y = new Polynomial();
		                 for(int i=0;i<parts1.length;i=i+3)
		                 {
		                 	y.insertMonomial(new Monomial(Integer.parseInt(parts1[i]),Integer.parseInt(parts1[i+2])));
		                 }
		                 for(int i=0;i<parts2.length;i=i+3)
		                 {
		                 	x.insertMonomial(new Monomial(Integer.parseInt(parts2[i]),Integer.parseInt(parts2[i+2])));
		                 }
		                 x.sub(y);
		                 x.simplify();
		                 setResult(x.toString());
		             } 
		             catch (NumberFormatException nfex) 
		             {
		                 showError("Bad input.\nInput should look like this:\n2x^3+3x^1+1x^0");
		             }
				}
			});
			m_subBtn1.setVisible(false);
			m_subBtn2.setVisible(false);
			OpSub.setLayout(new BoxLayout(OpSub, BoxLayout.X_AXIS));
		Operation.add(OpSub);
		
		Operation.add(m_multiplyBtn);
		m_multiplyBtn.addActionListener(new ActionListener()
		{
	        public void actionPerformed(ActionEvent e) 
	        {
	            String userInput1 = "";
	            String userInput2 = "";
	            try 
	            {
	                userInput1 = getUserInput1();
	                userInput2 = getUserInput2();
	                String[] parts1 = userInput1.split("[x^+]");
	                String[] parts2 = userInput2.split("[x^+]");
	                Polynomial x = new Polynomial();
	                Polynomial y = new Polynomial();
	                Polynomial z = new Polynomial();
	                for(int i=0;i<parts1.length;i=i+3)
	                {
	                	x.insertMonomial(new Monomial(Integer.parseInt(parts1[i]),Integer.parseInt(parts1[i+2])));
	                }
	                for(int i=0;i<parts2.length;i=i+3)
	                {
	                	y.insertMonomial(new Monomial(Integer.parseInt(parts2[i]),Integer.parseInt(parts2[i+2])));
	                }
	                z.mul(x,y);
	                z.simplify();
	                setResult(z.toString());
	            }
	            catch (NumberFormatException nfex) 
	            {
	                showError("Bad input.\nInput should look like this:\n2x^3+3x^1+1x^0");
	            }
	        }
		});
			OpDivide.add(m_divideBtn);
			OpDivide.add(m_divideBtn1);
			m_divideBtn1.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
		             String userInput1 = "";
		             String userInput2 = "";
		             try 
		             {
		                 userInput1 = getUserInput1();
		                 userInput2 = getUserInput2();
		                 String[] parts1 = userInput1.split("[x^+]");
		                 String[] parts2 = userInput2.split("[x^+]");
		                 Polynomial x = new Polynomial();
		                 Polynomial y = new Polynomial();
		                 for(int i=0;i<parts1.length;i=i+3)
		                 {
		                 	x.insertMonomial(new Monomial(Integer.parseInt(parts1[i]),Integer.parseInt(parts1[i+2])));
		                 }
		                 for(int i=0;i<parts2.length;i=i+3)
		                 {
		                 	y.insertMonomial(new Monomial(Integer.parseInt(parts2[i]),Integer.parseInt(parts2[i+2])));
		                 }
		                 Polynomial rem = new Polynomial();
		                 x.div(y,rem);
		                 x.simplify();
		                 rem.simplify();
		                 setResult("Q: "+x.toString()+" and R: "+rem.toString());

		             } 
		             catch (NumberFormatException nfex) 
		             {
		                 showError("Bad input.\nInput should look like this:\n2x^3+3x^1+1x^0");
		             }
		    	 }
			});
			OpDivide.add(m_divideBtn2);
			m_divideBtn2.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
			         String userInput1 = "";
			         String userInput2 = "";
			         try 
			         {
			             userInput1 = getUserInput1();
			             userInput2 = getUserInput2();
			             String[] parts1 = userInput1.split("[x^+]");
			             String[] parts2 = userInput2.split("[x^+]");
			             Polynomial x = new Polynomial();
			             Polynomial y = new Polynomial();
			             for(int i=0;i<parts1.length;i=i+3)
			             {
			             	y.insertMonomial(new Monomial(Integer.parseInt(parts1[i]),Integer.parseInt(parts1[i+2])));
			             }
			             for(int i=0;i<parts2.length;i=i+3)
			             {
			             	x.insertMonomial(new Monomial(Integer.parseInt(parts2[i]),Integer.parseInt(parts2[i+2])));
			             }
		                 Polynomial rem = new Polynomial();
		                 x.div(y,rem);
		                 x.simplify();
		                 rem.simplify();
		                 setResult("Q: "+x.toString()+" and R: "+rem.toString());
			
			         } 
			         catch (NumberFormatException nfex) 
			         {
			             showError("Bad input.\nInput should look like this:\n2x^3+3x^1+1x^0");
			         }
				}
			});
			m_divideBtn1.setVisible(false);
			m_divideBtn2.setVisible(false);
			m_divideBtn.addActionListener(new ActionListener()
			{	
			    	 public void actionPerformed(ActionEvent e)
			    	 {
			    			m_divideBtn1.setVisible(true);
			    			m_divideBtn2.setVisible(true);
			    			m_divideBtn.setVisible(false);
			    	 }
			});
			OpDivide.setLayout(new BoxLayout(OpDivide, BoxLayout.X_AXIS));
		Operation.add(OpDivide);
			
			OpDeriv.add(m_derivBtn);
			m_derivBtn.addActionListener(new ActionListener()
			{	
			    	 public void actionPerformed(ActionEvent e)
			    	 {
			    			m_derivBtn1.setVisible(true);
			    			m_derivBtn2.setVisible(true);
			    			m_derivBtn.setVisible(false);
			    			
			    	 }
			});
			OpDeriv.add(m_derivBtn1);
			m_derivBtn1.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
		             String userInput = "";
		             try 
		             {
		                 userInput = getUserInput1();
		                 String[] parts = userInput.split("[x^+]");
		                 Polynomial x = new Polynomial();
		                 for(int i=0;i<parts.length;i=i+3)
		                 {
		                 	x.insertMonomial(new Monomial(Integer.parseInt(parts[i]),Integer.parseInt(parts[i+2])));
		                 }
		                 x.deriv();
		                 x.simplify();
		                 setResult(x.toString());
		             } 
		             catch (NumberFormatException nfex) 
		             {
		                 showError("Bad input.\nInput should look like this:\n2x^3+3x^1+1x^0");
		             }
		    	 }
			});
			OpDeriv.add(m_derivBtn2);
			m_derivBtn2.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
		             String userInput = "";
		             try 
		             {
		                 userInput = getUserInput2();
		                 String[] parts = userInput.split("[x^+]");
		                 Polynomial x = new Polynomial();
		                 for(int i=0;i<parts.length;i=i+3)
		                 {
		                 	x.insertMonomial(new Monomial(Integer.parseInt(parts[i]),Integer.parseInt(parts[i+2])));
		                 }
		                 x.deriv();
		                 x.simplify();
		                 setResult(x.toString());
		             } 
		             catch (NumberFormatException nfex) 
		             {
		                 showError("Bad input.\nInput should look like this:\n2x^3+3x^1+1x^0");
		             }
		    	 }
			});
			m_derivBtn1.setVisible(false);
			m_derivBtn2.setVisible(false);
			OpDeriv.setLayout(new BoxLayout(OpDeriv, BoxLayout.X_AXIS));
		Operation.add(OpDeriv);
			OpInteg.add(m_integBtn);
			m_integBtn.addActionListener(new ActionListener()
			{	
			    	 public void actionPerformed(ActionEvent e)
			    	 {
			    			m_integBtn1.setVisible(true);
			    			m_integBtn2.setVisible(true);
			    			m_integBtn.setVisible(false);
			    	 }
			});
			OpInteg.add(m_integBtn1);
			m_integBtn1.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
		             String userInput = "";
		             try 
		             {
		                 userInput = getUserInput1();
		                 String[] parts1 = userInput.split("[x^+]");
		                 Polynomial x = new Polynomial();
		                 for(int i=0;i<parts1.length;i=i+3)
		                 {
		                 	x.insertMonomial(new Monomial(Integer.parseInt(parts1[i]),Integer.parseInt(parts1[i+2])));
		                 }
		                 x.integ();
		                 x.simplify();
		                 setResult(x.toString());
		             } 
		             catch (NumberFormatException nfex) 
		             {
		                 showError("Bad input.\nInput should look like this:\n2x^3+3x^1+1x^0");
		             }
		    	 }
			});
			m_integBtn2.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) {
		             String userInput = "";
		             try 
		             {
		                 userInput = getUserInput2();
		                 String[] parts = userInput.split("[x^+]");
		                 Polynomial x = new Polynomial();
		                 for(int i=0;i<parts.length;i=i+3)
		                 {
		                 	x.insertMonomial(new Monomial(Integer.parseInt(parts[i]),Integer.parseInt(parts[i+2])));
		                 }
		                 x.integ();
		                 x.simplify();
		                 setResult(x.toString());
		             } 
		             catch (NumberFormatException nfex) 
		             {
		                 showError("Bad input.\nInput should look like this:\n2x^3+3x^1+1x^0");
		             }
		    	 }
			});
			OpInteg.add(m_integBtn2);
			m_integBtn1.setVisible(false);
			m_integBtn2.setVisible(false);
			OpInteg.setLayout(new BoxLayout(OpInteg, BoxLayout.X_AXIS));
		Operation.add(OpInteg);
		Operation.setLayout(new BoxLayout(Operation, BoxLayout.X_AXIS));
		
		//result panel
		JPanel Result = new JPanel();
		Result.add(new JLabel("Result: "));
		Result.add(m_result);
		Result.add(m_clearBtn);
		m_clearBtn.addActionListener(new ActionListener()
		{	
		    	 public void actionPerformed(ActionEvent e)
		    	 {
		    		 m_subBtn.setVisible(true);
		    		 m_subBtn1.setVisible(false);
		    		 m_subBtn2.setVisible(false);
		    		 m_divideBtn.setVisible(true);
		    		 m_divideBtn1.setVisible(false);
		    		 m_divideBtn2.setVisible(false);
		    		 m_derivBtn.setVisible(true);
		    		 m_derivBtn1.setVisible(false);
		    		 m_derivBtn2.setVisible(false);
		    		 m_integBtn.setVisible(true);
		    		 m_integBtn1.setVisible(false);
		    		 m_integBtn2.setVisible(false);
		    	 }
		});
		Result.setLayout(new BoxLayout(Result, BoxLayout.X_AXIS));

		//putting everything in the main panel
		content.add(input1);
		content.add(input2);
		content.add(Operation);
		content.add(Result);
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		content.setLayout(new FlowLayout());
		

		//... finalize layout
		this.setContentPane(content);
		this.pack();
		this.setTitle("Polynomial Processing");
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        
		//The window closing event should probably be passed to the
		//Controller in a real program, but this is a short example.
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	void reset() 
	{
		m_result.setText(INITIAL_VALUE);
		m_Polynomial1.setText("");
		m_Polynomial2.setText("");
	}
	
	String getUserInput1() 
	{
		return m_Polynomial1.getText();
	}
	
	String getUserInput2() 
	{
		return m_Polynomial2.getText();
	}
	
	void setResult(String newRes) 
	{
		m_result.setText(newRes);
	}
	
	void showError(String errMessage)
	{
		JOptionPane.showMessageDialog(this, errMessage);
	}
	
	public static void main(String[] args) 
	{
		GUI gui=new GUI();
		gui.setVisible(true);
	}
}
