import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GUI  extends JFrame
{
	private static JFrame content=new JFrame("Bank");
	
	private JButton personOp = new JButton("Person Operations");
	private JButton accountOp = new JButton("Account Operations");
	
	private JButton depositOp = new JButton("Deposit Operation");
	private JButton withdrawOp = new JButton("Withdraw Operation");
	private JTextField dpersonId = new JTextField("Type PersonID");
	private JTextField daccountId = new JTextField("Type AccountID");
	private JTextField dMoney = new JTextField("Type Money");
	private int dpId, daId, dm;
	private JButton deposit = new JButton("Deposit");
	private JButton withdraw = new JButton("Withdraw");
	
	private JButton addPerson = new JButton("<html>Add Person<br />All fields but personID</html>");
	private JButton deletePerson = new JButton("<html>Delete Person<br />Just personID</html>");
	private JButton editPerson = new JButton("<html>Edit Person<br />Type personID & new name</html>");
	private JTextField personId = new JTextField("Type PersonID");
	private JTextField personName = new JTextField("Type Name");
	private JTextField personAcc = new JTextField("Type Account Type");
	private JTextField personMoney = new JTextField("Type Money");
	private int pid, pMoney;
	private String pName, pAccType;
	
	private JButton addAccount = new JButton("<html>Add Account<br />All fields but accountID</html>");
	private JButton deleteAccount= new JButton("<html>Delete Account<br />personID & accountID</html>");
	private JButton editAccount = new JButton("<html>Edit Account<br />Type personID & new amount</html>");
	private JTextField accountId = new JTextField("Type AccountID");
	private JTextField accType = new JTextField("Type Account Type");
	private JTextField accMoney= new JTextField("Type Money");
	private JTextField accPerson = new JTextField("Type PersonID");
	private int aid,apid,aMoney;
	private String aType;

	private JTable persontable;
	private JScrollPane personscroll = new JScrollPane();
	private JTable accounttable;
	private JScrollPane accountscroll = new JScrollPane();

	Bank bank = new Bank();
	
	GUI() 
	{
		bank.setBank(File.readBank("bank.txt"));
		/*
		Person[] persons = new Person[20];
		persons[1] = new Person(1, "Ardelean");
		persons[2] = new Person(2, "Samarghitan");
		persons[3] = new Person(3, "Anghel");
		
		for (int i = 1; i <= 3; i++) 
		{
			SpendingAccount a = new SpendingAccount(1, 200);
			bank.createAccount(persons[i], a);
		}
		File.writeBank(bank.getBank(), "bank.txt");*/
		System.out.println(bank);
		
		content.setLayout(null);
		content.setSize(1500,1000);
		
		content.add(personOp).setBounds(10, 10, 200, 30);
		content.add(accountOp).setBounds(10, 40, 200, 30);
		content.add(depositOp).setBounds(10, 70, 200, 30);
		content.add(withdrawOp).setBounds(10, 100, 200, 30);
		

        content.add(addPerson).setBounds(250, 10, 200, 60);
        content.add(deletePerson).setBounds(450, 10, 200, 60);
        content.add(editPerson).setBounds(650, 10, 200, 60);
        content.add(personId).setBounds(250, 70, 200, 30);
	    content.add(personName).setBounds(450, 70, 200, 30);
    	content.add(personAcc).setBounds(650, 70, 200, 30);
    	content.add(personMoney).setBounds(850, 70, 200, 30);
        
        //product operation buttons
        content.add(addAccount).setBounds(250, 10, 200, 60);
        content.add(deleteAccount).setBounds(450, 10, 200, 60);
        content.add(editAccount).setBounds(650, 10, 200, 60);
    	content.add(accPerson).setBounds(250, 70, 200, 30);
        content.add(accountId).setBounds(450, 70, 200, 30);
        content.add(accType).setBounds(650, 70, 200, 30);
	    content.add(accMoney).setBounds(850, 70, 200, 30);

	    content.add(dpersonId).setBounds(250, 10, 200, 60);
	    content.add(daccountId).setBounds(450, 10, 200, 60);
	    content.add(dMoney).setBounds(650, 10, 200, 60);
	    content.add(deposit).setBounds(850, 10, 200, 60);
	    content.add(withdraw).setBounds(850, 70, 200, 60);
	    
    	
    	hidePerson();
    	hideAccount();
    	hideMoney();
    	showPerson();
    	showAccount();

	    personOp.addActionListener(new ActionListener() 
	    {
	        public void actionPerformed(ActionEvent e) 
	        {
	        	revealPerson();
	        	hideAccount();
	        	hideMoney();
	    		personId.setVisible(true);
	    		personName.setVisible(true);
	    		personAcc.setVisible(true);
	    		personMoney.setVisible(true);
	    	    addPerson.addActionListener(new ActionListener() 
	    	    {
	    	        public void actionPerformed(ActionEvent e) 
	    	        {
	    				pName = personName.getText();
	    				pAccType = personAcc.getText();
	    				
	    				if(isNumeric(personMoney.getText()))
	    					pMoney = Integer.parseInt(personMoney.getText());
	    				else
	    				{
	    					showError("Money has to be integer");
	    					return;
	    				}
	    				
	    				if(pMoney<0)
	    				{
	    					showError("Money>=0");
	    					return;
	    				}
	    				
	    				bank.createPerson(pName, pAccType, pMoney);
	    				
	    				System.out.println(bank.getPersons());
	    				showPerson();
	    				showAccount();
	    	        }
	    	    });
	    	    
	        	deletePerson.addActionListener(new ActionListener() 
	    	    {
	    	        public void actionPerformed(ActionEvent e) 
	    	        {
	    				if(isNumeric(personId.getText()))
	    				{
	    					pid = Integer.parseInt(personId.getText());
	    					int i;
	    					for (i = 0; i < bank.getPersons().size(); i++) 
	    					{
	    						if (bank.getPersons().get(i).getId() == pid) 
	    						{
	    							bank.removePerson(bank.getPersons().get(i));
	    							break;
	    						}
	    					}
		    				if(i == bank.getPersons().size())
		    					showError("Id not found");
	    				}
	    				else
	    					showError("Id has to be integer");

    					File.writeBank(bank.getBank(), "bank.txt");
    					System.out.println(bank.getPersons());
    					deletePerson.setVisible(true);
	    				showPerson();
	    				showAccount();
	    	        }
	    	    });
	        	 	    
	        	editPerson.addActionListener(new ActionListener() 
	    	    {
	    	        public void actionPerformed(ActionEvent e) 
	    	        {	
	    	        	if(isNumeric(personId.getText()))
	    	        	{
	    					pid = Integer.parseInt(personId.getText());
	    					pName = personName.getText();
		    				int i;
		    				for (i = 0; i < bank.getPersons().size(); i++) 
		    				{
		    					if (bank.getPersons().get(i).getId() == pid) 
		    					{
		    						bank.getPersons().get(i).setName(pName);
		    						break;
		    					}
		    				}
		    				if(i == bank.getPersons().size())
		    					showError("Id not found");
	    	        	}
	    	        	else
	    	        		showError("Id is an integer");
	    	        	
	    	        	
	    	        	editPerson.setVisible(false);
	    	        	
	    				File.writeBank(bank.getBank(), "bank.txt");
	    				System.out.println(bank.getPersons());
	    				showPerson();
	    	        }
	    	    });
	        }
	    });

	    accountOp.addActionListener(new ActionListener() 
	    {
	        public void actionPerformed(ActionEvent e) 
	        {
	        	revealAccount();
	        	hidePerson();
	        	hideMoney();
				accountId.setVisible(true);
				accType.setVisible(true);
				accMoney.setVisible(true);
				accPerson.setVisible(true);
	        	addAccount.addActionListener(new ActionListener() 
	    	    {
					public void actionPerformed(ActionEvent arg0) {
						if(isNumeric(accPerson.getText()))
						{
							if(isNumeric(accMoney.getText()))
							{
								if(Integer.parseInt(accMoney.getText())>=0)
								{
									apid = Integer.parseInt(accPerson.getText());
									aMoney = Integer.parseInt(accMoney.getText());
									aType = accType.getText();
									
									Person p = bank.getPersons().get(0);
									int i;
									for (i = 0; i < bank.getPersons().size(); i++) 
									{
										if (bank.getPersons().get(i).getId() == apid) 
										{
											p = bank.getPersons().get(i);
											break;
										}
									}
									
									if (i == bank.getPersons().size()) 
									{
										showError("Id not found");
									}
									else
									{
										int accID = bank.getMaxAccountID(p) + 1;

										if (aType.equals("spending")) 
										{
											SpendingAccount acc = new SpendingAccount(accID, pMoney);
											bank.createAccount(p, acc);
										} 
										else if (aType.equals("saving")) 
										{
											SavingAccount acc = new SavingAccount(accID, pMoney);
											bank.createAccount(p, acc);
										} 
										else 
										{
											showError("Type has to be spending or saving");
										}
									}
								}
								else
									showError("Money>=0");
							}
							else
								showError("Money has to be integer");
						}
						else
							showError("Id has to be integer");
						
						File.writeBank(bank.getBank(), "bank.txt");
	    				showPerson();
	    				showAccount();
					}
	    	    });
	            
	    	    deleteAccount.addActionListener(new ActionListener() 
	    	    {
	    	        public void actionPerformed(ActionEvent e) 
	    	        {
	    	        	if(isNumeric(accPerson.getText()))
	    	        		apid = Integer.parseInt(accPerson.getText());
	    	        	else
	    	        	{
	    	        		showError("Id has to be integer");
	    	        		return;
	    	        	}
	    	        	if(isNumeric(accountId.getText()))
	    	        		aid = Integer.parseInt(accountId.getText());
	    	        	else
	    	        	{
	    	        		showError("Id has to be integer");
	    	        		return;
	    	        	}
	    				
	    				Person p = bank.getPersons().get(0);
	    				int i;
	    				for (i = 0; i < bank.getPersons().size(); i++) 
	    				{
	    					if (bank.getPersons().get(i).getId() == apid) 
	    					{
	    						p = bank.getPersons().get(i);
	    						break;
	    					}
	    				}
	    				
	    				if (i == bank.getPersons().size()) 
	    				{
	    					showError("Person id not found");
	    					return;
	    				}
	    				
	    				Set<Account> accounts = bank.getAccounts(p);
	    				i = accounts.size();
	    				bank.removeAccount(aid, p);
	    				accounts = bank.getAccounts(p);	
	    				
	    				if (i - accounts.size() == 0) 
	    				{
	    					showError("Account id not found");
	    					return;
	    				}
	    				
	    				File.writeBank(bank.getBank(), "bank.txt");
	    				showPerson();
	    				showAccount();
	    	        }
	    	    });
    
	        	editAccount.addActionListener(new ActionListener() 
	    	    {
	    	        public void actionPerformed(ActionEvent e) 
	    	        {
	    	        	if(isNumeric(accountId.getText()))
	    	        		aid = Integer.parseInt(accountId.getText());
	    	        	else
	    	        	{
	    	        		showError("Id is integer");
	    	        	}
	    	        	
	    	        	if(isNumeric(accPerson.getText()))
	    	        		apid = Integer.parseInt(accPerson.getText());
	    	        	else
	    	        	{
	    	        		showError("Id is integer");
	    	        	}
	    	        	if(isNumeric(accMoney.getText()))
	    	        		if(Integer.parseInt(accMoney.getText())<0)
	    	        		{
	    	        			showError("Money>=0");
	    	        			return;
	    	        		}
	    	        		else
	    	        			aMoney = Integer.parseInt(accMoney.getText());
	    	        	else
	    	        	{
	    	        		showError("Money is an integer");
	    	        		return;
	    	        	}
	    				aType = accType.getText();
	    				
	    				bank.editAccount(apid, aid, aType, aMoney);
	    				
	    				File.writeBank(bank.getBank(), "bank.txt");
	    				showPerson();
	    				showAccount();
	    	        }
	    	    });
	        }
	    });

	    
	    depositOp.addActionListener(new ActionListener() 
	    {
	        public void actionPerformed(ActionEvent e) 
	        {
	        	hideAccount();
	        	hidePerson();
	        	revealMoney();
	        	deposit.setVisible(true);
	        	withdraw.setVisible(false);
	    	    dpersonId.setBounds(250, 10, 200, 60);
	    	    daccountId.setBounds(450, 10, 200, 60);
	    	    dMoney.setBounds(650, 10, 200, 60);
	    	    deposit.addActionListener(new ActionListener() 
	    	    {
	    	    	
	    	        public void actionPerformed(ActionEvent e) 
	    	        {
	    	        	if(isNumeric(dpersonId.getText()))
	    	        		dpId = Integer.parseInt(dpersonId.getText());
	    	        	else	
	    	        	{
	    	        		showError("Id is integer");
	    	        	}
	    	        	
	    	        	if(isNumeric(daccountId.getText()))
	    	        		daId = Integer.parseInt(daccountId.getText());
	    	        	else
	    	        	{
	    	        		showError("Id is integer");
	    	        	}
	    	        	
	    	        	if(isNumeric(dMoney.getText()))
	    	        		if(Integer.parseInt(dMoney.getText())<0)
	    	        		{
	    	        			showError("Money>=0");
	    	        			return;
	    	        		}
	    	        		else
	    	        			dm = Integer.parseInt(dMoney.getText());
	    	        	else
	    	        	{
	    	        		showError("Money is an integer");
	    	        		return;
	    	        	}
						
						Person p = null;
						int i;
						for (i = 0; i < bank.getPersons().size(); i++) 
						{
							if (bank.getPersons().get(i).getId() == dpId) 
							{
								p = bank.getPersons().get(i);
								break;
							}
						}
						
						if (i == bank.getPersons().size()) 
						{
							showError("Person id not found");
							return;
						}
						
						i = 0;
						Set<Account> accounts = bank.getAccounts(p);
						for (Account a:accounts)
						{
							if (a.getId() == daId)
							{
								bank.depositMoney(p, daId, dm);	
								break;
							}
							i++;
						}
						
						if (i == accounts.size()) 
						{
							showError("Account id not found.");
							return;
						}	
						
						File.writeBank(bank.getBank(), "bank.txt");
	    				showPerson();
	    				showAccount();
		    	    }
		        });
	        }
	    });
	    
	    withdrawOp.addActionListener(new ActionListener() 
	    {	
	        public void actionPerformed(ActionEvent e) 
	        {
	        	hideAccount();
	        	hidePerson();
	        	revealMoney();
	        	deposit.setVisible(false);
	        	withdraw.setVisible(true);
	    	    dpersonId.setBounds(250, 70, 200, 60);
	    	    daccountId.setBounds(450, 70, 200, 60);
	    	    dMoney.setBounds(650, 70, 200, 60);
	        	withdraw.addActionListener(new ActionListener() 
	    	    {
	    	        public void actionPerformed(ActionEvent e) 
	    	        {
	    	        	if(isNumeric(dpersonId.getText()))
	    	        		dpId = Integer.parseInt(dpersonId.getText());
	    	        	else
	    	        	{
	    	        		showError("Id is integer");
	    	        	}
	    	        	
	    	        	if(isNumeric(daccountId.getText()))
	    	        		daId = Integer.parseInt(daccountId.getText());
	    	        	else
	    	        	{
	    	        		showError("Id is integer");
	    	        	}
	    	        	
	    	        	if(isNumeric(dMoney.getText()))
	    	        		if(Integer.parseInt(dMoney.getText())<0)
	    	        		{
	    	        			showError("Money>=0");
	    	        			return;
	    	        		}
	    	        		else
	    	        			dm = Integer.parseInt(dMoney.getText());
	    	        	else
	    	        	{
	    	        		showError("Money is an integer");
	    	        		return;
	    	        	}
						
						Person p = null;
						int i;
						for (i = 0; i < bank.getPersons().size(); i++) 
						{
							if (bank.getPersons().get(i).getId() == dpId) 
							{
								p = bank.getPersons().get(i);
								break;
							}
						}
						
						if (i == bank.getPersons().size()) 
						{
							showError("Person id not found");
							return;
						}
					
						i = 0;
						Set<Account> accounts = bank.getAccounts(p);
						for (Account a:accounts){
							if (a.getId() == daId)
							{
								bank.withdrawMoney(p, daId, dm);
								break;
							}
							i++;
						}
						
						if (i == accounts.size()) {
							showError("Account id not found");
							return;
						}	
						
						File.writeBank(bank.getBank(), "bank.txt");
	    				showPerson();
	    				showAccount();
	    	        }
	    	    });
	        }
	    });
	    
		persontable.addMouseListener(new java.awt.event.MouseAdapter()
        {
			public void mouseClicked(java.awt.event.MouseEvent e)
			{
				int row=persontable.rowAtPoint(e.getPoint());
				int col=persontable.columnAtPoint(e.getPoint());
				JOptionPane.showMessageDialog(null,"Value in the cell clicked: " +persontable.getValueAt(row,col).toString());
			}
		});
		
		accounttable.addMouseListener(new java.awt.event.MouseAdapter()
        {
			public void mouseClicked(java.awt.event.MouseEvent e)
			{
				int row=accounttable.rowAtPoint(e.getPoint());
				int col=accounttable.columnAtPoint(e.getPoint());
				JOptionPane.showMessageDialog(null,"Value in the cell clicked: " +accounttable.getValueAt(row,col).toString());
			}
		});
		
	    
		content.setVisible(true);
		content.setResizable(false);
		content.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void showPerson()
	{
		Object personColumn[] = { "ID", "Name"};				
		List<Person> persons = bank.getPersons();
		System.out.println(bank.getPersons());
		Object rowData[][] = new Object[persons.size()][2];
		int i=0;
		for(Person p: persons)
		{
			rowData[i][0]=p.getId();
			rowData[i++][1]=p.getName();
		}
		persontable= new JTable(rowData,personColumn);
		persontable.setEnabled(false);
		personscroll = new JScrollPane(persontable);
		personscroll.setEnabled(false);
	    content.add(personscroll).setBounds(200,200,800,200);
	}
	
	public void showAccount()
	{
	    Object accountColumn[] = { "PersonID", "AccountID", "AccountType", "Money"};	
	    
	    class Everything
	    {
	    	public Person p;
	    	public Account a;
			public Everything(Person p, Account a) 
			{
				this.p = p;
				this.a = a;
			}
	    	
	    }
	    
		List<Everything> accounts = new ArrayList<>();
		for(Person p: bank.getPersons()) 
		{
			for(Account a: bank.getAccounts(p))
			{	
				Everything e=new Everything(p, a);
				accounts.add(e);
			}
		}
				
		Object rowData[][] = new Object[accounts.size()][4];
		int i=0;
		for(Everything e: accounts) 
		{
			rowData[i][0] = e.p.getId();
			rowData[i][1] = e.a.getId();
			rowData[i][2] = e.a.isSpending(e.a) ? "Spending" : "Saving";
			rowData[i++][3] = e.a.getMoney();
		}
		
		accounttable=new JTable(rowData,accountColumn);
		accounttable.setEnabled(false);
		accountscroll = new JScrollPane(accounttable);
		accountscroll.setEnabled(false);
	    content.add(accountscroll).setBounds(200,400,800,200);
	}
	
	public void revealPerson()
	{
		addPerson.setVisible(true);
		deletePerson.setVisible(true);
		editPerson.setVisible(true);
		personId.setVisible(true);
		personName.setVisible(true);
		personAcc.setVisible(true);
		personMoney.setVisible(true);
	}
	
	public void revealAccount()
	{
		addAccount.setVisible(true);
		deleteAccount.setVisible(true);
		editAccount.setVisible(true);
		accountId.setVisible(true);
		accType.setVisible(true);
		accMoney.setVisible(true);
		accPerson.setVisible(true);
	}

	public void hidePerson()
	{
		addPerson.setVisible(false);
		deletePerson.setVisible(false);
		editPerson.setVisible(false);
		personId.setVisible(false);
		personName.setVisible(false);
		personAcc.setVisible(false);
		personMoney.setVisible(false);
	}
	
	public void hideAccount()
	{
		addAccount.setVisible(false);
		deleteAccount.setVisible(false);
		editAccount.setVisible(false);
		accountId.setVisible(false);
		accType.setVisible(false);
		accMoney.setVisible(false);
		accPerson.setVisible(false);
	}
	
	public void revealMoney()
	{
		dpersonId.setVisible(true);
		daccountId.setVisible(true);
		dMoney.setVisible(true);
	}
	
	public void hideMoney()
	{
		dpersonId.setVisible(false);
		daccountId.setVisible(false);
		dMoney.setVisible(false);
		deposit.setVisible(false);
		withdraw.setVisible(false);
	}
	
	public static void showError(String errMessage)
	{
		JOptionPane.showMessageDialog(content, errMessage);
	}
	
	public static boolean isNumeric(String str)  
	{  
		try  
		{  
			int i = Integer.parseInt(str);  
		}  
		catch(NumberFormatException nfe)  
		{  
			return false;  
		}  
		return true;  
	}
	
	public static void main(String[] args) 
	{
		new GUI();
	}
}
