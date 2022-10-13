
public class SavingAccount extends Account{
	private static final long serialVersionUID =4087210684456941723L;
	private double interest = 0.1;
	private int singleUse=0;
	
	public SavingAccount(int ID, double money) 
	{
		super(ID, money);
		if(money==0)
			singleUse=0;
		else
			singleUse=1;
	}

	@Override
	public void depositMoney(double money) 
	{
		if(singleUse==0)
		{
			setMoney(getMoney() + money + money * interest);
			setChanged();
			notifyObservers(money);
			singleUse=1;
		}
		else
			GUI.showError("Can only deposit once in a saving account.");
	}

	@Override
	public void withdrawMoney(double money) 
	{
		if(this.getMoney()!=0)
		{
			setMoney(0);
			setChanged();
			notifyObservers(money);
			singleUse=0;
			GUI.showError("Can only withdraw once, so all the money was withdrawn.");
		}
		else
			GUI.showError("Can only withdraw once in a saving account.");
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}
	
}
