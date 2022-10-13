
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Bank implements BankProc{
	private Map<Person,Set<Account>> bank;
	
	public Bank()
	{
		bank = new HashMap<Person,Set<Account>>();
	}
	
	public Map<Person, Set<Account>> getBank() 
	{
		return bank;
	}

	public void setBank(Map<Person, Set<Account>> bank)
	{
		this.bank = bank;
	}
	
	
	public String toString() 
	{
		return "Bank [bank=" + bank + "]";
	}
	
	public void createPerson(String name, String type, int money)
	{
		assert isWellFormed() : "Bank is not well formed";
		
		int id = this.getMaxPersonID() + 1;
		Person person = new Person(id,name);
		
		if (type.equals("spending")) 
		{
			SpendingAccount acc = new SpendingAccount(1, money);
			this.createAccount(person, acc);
			File.writeBank(this.getBank(), "bank.txt");
		} 
		else if (type.equals("saving")) 
		{
			SavingAccount acc = new SavingAccount(1, money);
			this.createAccount(person, acc);
			File.writeBank(this.getBank(), "bank.txt");
		} 
		else 
		{
			GUI.showError("Account Type has to be spending or saving");
		}
		
		assert isWellFormed() : "Bank is not well formed";
	}
	
	public void removePerson(Person person) 
	{
		assert isWellFormed() : "Bank is not well formed";
		int i=0;
		List<Person> persons = getPersons();
		for(Person p : persons)
			if(p.equals(person))
				bank.remove(person);
			else
				i++;
		
		if(i==persons.size())
			GUI.showError("Person not in bank");
		
		assert isWellFormed() : "Bank is not well formed";
		
	}
	
	public void createAccount(Person person, Account account)
	{
		assert isWellFormed() : "Bank is not well formed"; // asserts that the bank is well formed
		assert person != null: "The person must not be NULL"; // asserts that the person is not a null value
		
		// if the bank contains the person, it adds the account to the list of accounts of that person
		List<Person> persons = getPersons();
		for(Person p : persons)
			if(p.equals(person))
			{
				bank.get(person).add(account);
			}
			// if the bank does not contain that person, it puts the person in the bank
			else
			{
				Set<Account> accounts = new HashSet<Account>(); 
				accounts.add(account);
				bank.put(person, accounts);
			}
		
		// the account observers the person to see if any changes are made
		account.addObserver(person);
		
		assert isWellFormed(): "Bank is not well formed"; // just to be sure we did not ruin the bank, we verify again if it is well formed
	}
	
	public void removeAccount(int accID, Person person) 
	{
		assert isWellFormed(): "Bank is not well formed";
		
		int i=0,j=0;
		List<Person> persons = getPersons();
		for(Person p : persons)
			if(p.equals(person))
			{
				Set<Account> accounts = this.getAccounts(person);
				for (Account a: accounts){
					if (a.getId() == accID)
					{
						accounts.remove(a);
						i++;
						break;
					}
				}
				if(i==accounts.size()+1)
				{
					GUI.showError("Account not in bank");
				}
			}
			else
				j++;
		
		if(j==persons.size())
			GUI.showError("Person not in bank");
		
		assert isWellFormed() : "Bank is not well formed";	
	}

	
	public void editAccount(int apid, int aid, String type, int money)
	{
		assert isWellFormed() : "Bank is not well formed";	
		assert money>=0: "Money has to be positive";
		
		Person p = this.getPersons().get(0);
		int i;
		for (i = 0; i < this.getPersons().size(); i++) 
		{
			if (this.getPersons().get(i).getId() == apid) 
			{
				p = this.getPersons().get(i);
				break;
			}
		}
		
		if (i == this.getPersons().size()) 
		{
			GUI.showError("Person id not found.");
			return;
		}
		
		i = 0;
		Set<Account> accounts = this.getAccounts(p);
		for (Account a:accounts)
		{
			if (a.getId() == aid)
			{
				if (type.equals("spending") && (a instanceof SpendingAccount)) 
				{
					a.setMoney(money);
				} 
				else if (type.equals("saving") && (a instanceof SavingAccount)) 
				{
					a.setMoney(money);
				}
				else if (type.equals("saving") && (a instanceof SpendingAccount)) 
				{
					GUI.showError("Cannot change type.");
				} 
				else if (type.equals("spending") && (a instanceof SavingAccount)) 
				{
					GUI.showError("Cannot change type.");
				} 
				else 
				{
					GUI.showError("Type has to be spending or saving");
					return;
				}	
				break;
			}
			i++;
		}
		
		if (i == accounts.size()) 
		{
			GUI.showError("Account id not found");
			return;
		}
		
		assert isWellFormed() : "Bank is not well formed";	
	}
	
	
	public void depositMoney(Person person, int accID, double money)
	{
		// verify if the bank is well formed
		assert isWellFormed() : "Bank is not well formed";
		
		// verify if the person exists in the bank and if the sum is positive
		assert person!= null : "The person must not be NULL";
		assert money >= 0 : "The sum must be positive.";
		
		// if the person exists in the bank
		List<Person> persons = getPersons();
		for(Person p : persons)
			if(p.equals(person))
			{
				Set<Account> accounts = this.getAccounts(person);
				// the for goes through all the accounts of the person
				for (Account a: accounts)
				{
					// find the account you want to deposit into
					if (a.getId() == accID)
					{
						a.depositMoney(money); // sets the new sum with interest		
					}
				}
			}
			else
				GUI.showError("Person not in bank");
		
		//just to be sure we verify again if the bank is well formed
		assert isWellFormed() : "Bank is not well formed";
	}
	
	
	public void withdrawMoney(Person person, int accID,double money) 
	{
		assert isWellFormed() : "Bank is not well formed";
		assert person!= null: "The person must not be NULL";
		assert money >= 0 : "The sum must be positive.";
		
		double initialMoney=0;
		List<Person> persons = getPersons();
		for(Person p : persons)
			if(p.equals(person))
			{
				Set<Account> accounts = this.getAccounts(person);
				for (Account a: accounts)
				{
					if (a.getId() == accID)
					{
						initialMoney = a.getMoney();
						if(money<=initialMoney)
						{	
							a.withdrawMoney(money);
						}
						else
						{
							GUI.showError("Not enough money");
							break;
						}
					}
				}
			}
		
		assert isWellFormed() : "Bank is not well formed";
	}
	
	/**
	 * create a new account to a person
	 * @pre bank is well formeds
	 * @post bank is well formed
	 */
	public List<Person> getPersons() 
	{
		assert isWellFormed() : "Bank is not well formed";
		
		List<Person> persons = new ArrayList<Person>();
		for(Person person : bank.keySet()) 
		{
			persons.add(person);
		}
		
		assert isWellFormed() : "Bank is not well formed";
		return persons;
	}
	
	/**
	 * create a new account to a person
	 * @pre bank is well formed
	 * @pre p!=null
	 * @post bank is well formed
	 */
	public Set<Account> getAccounts(Person person) 
	{
		assert isWellFormed() : "Bank is not well formed";
		assert person != null : "The person cannot be NULL";
		
		List<Person> persons = getPersons();
		int i=0;
		for(Person p: persons)
		{
			if(p.equals(person))
				break;
			else
				i++;
		}
		
		if(i==persons.size())
			return null;
		
		assert isWellFormed() : "Bank is not well formed";
		return bank.get(person);
	}
	
	public int getMaxPersonID() 
	{
		int maxID = 0;
		for (Person person: getPersons()) 
		{
			if (maxID < person.getId()) 
				maxID = person.getId();
		}
		return maxID;
	}
	
	public int getMaxAccountID(Person person) 
	{
		int maxID = 0;
		for (Account account: getAccounts(person)) 
		{
			if (maxID < account.getId()) 
				maxID = account.getId();
		}
		return maxID;
	}
	
	/**
	* @invariant isWellFormed()
	*/
	public boolean isWellFormed()
	{
		if(bank.keySet()==null)
			return false;
		return true;
	}
}

