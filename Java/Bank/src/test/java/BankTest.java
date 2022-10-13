import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;

public class BankTest {

	public void test() 
	{
		Bank b=new Bank();
		Person p=new Person(1, "Vali");
		SpendingAccount sa=new SpendingAccount(1,100);
		b.createAccount(p, sa);
		
		boolean q=false;
		if(sa.getMoney()==100)
			q=true;
		else
			q=false;
		assertTrue(q);
	}
	
	public void test2() 
	{
		Bank b=new Bank();
		Person p=new Person(1, "Vali");
		SpendingAccount sa=new SpendingAccount(1,100);
		SavingAccount sa2=new SavingAccount(2,300);
		b.createAccount(p, sa);
		b.createAccount(p, sa2);
		b.removeAccount(2, p);
		
		boolean q=false;
		if(b.getMaxAccountID(p)==1)
			q=true;
		else
			q=false;
		assertTrue(q);
	}
	
	public void test3() 
	{
		Bank b=new Bank();
		Person p=new Person(1, "Vali");
		//b.createPerson("Cineva", "spending", 100);
		SpendingAccount sa=new SpendingAccount(1,100);
		b.createAccount(p, sa);
		b.depositMoney(p, 1, 100);
		
		boolean q=false;
		Set<Account> accounts = b.getAccounts(p);
		for(Account a: accounts)
			if(a.getMoney()==200)
				q=true;
			else
				q=false;
		assertTrue(q);
	}
	

	public void test4() 
	{
		Bank b=new Bank();
		Person p=new Person(1, "Vali");
		SpendingAccount sa=new SpendingAccount(1,100);
		b.createAccount(p, sa);
		b.createPerson("Vali", "saving", 100);
		
		boolean q=false;
		List<Person> persons=b.getPersons();
		for(Person person: persons)
			if(person.getName().equals("Vali"))
				q=true;
			else
				q=false;
		assertTrue(q);
	}

	public void test5() 
	{
		Bank b=new Bank();
		Person p=new Person(1, "NiciVali");
		SpendingAccount sa=new SpendingAccount(1,100);
		b.createAccount(p, sa);
		b.createPerson("NotVali", "saving", 100);
		
		boolean q=false;
		List<Person> persons=b.getPersons();
		for(Person person: persons)
		{
			if(person.getName().equals("Vali"))
				q=true;
			else
				q=false;
		}
		assertFalse(q);
	}
}	
