
public class SpendingAccount extends Account 
{
	public SpendingAccount(int ID, double money) 
	{
		super(ID, money);
	}

	@Override
	public void depositMoney(double money) 
	{
		setMoney(getMoney() + money);
		setChanged();
		notifyObservers(money);
		
	}
	
	@Override
	public void withdrawMoney(double money) 
	{
		setMoney(getMoney() - money);
		setChanged();
		notifyObservers(money);
	}
}
