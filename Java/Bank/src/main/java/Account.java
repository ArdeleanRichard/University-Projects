import java.io.Serializable;
import java.util.Observable;

public abstract class Account extends Observable implements Serializable{
	private int	accountID;
	private double money;
	private static final long serialVersionUID = 1953574556560861002L;
		
	public Account(int id, double money) {
		super();
		this.accountID = id;
		this.money = money;
	}

	public int getId() {
		return accountID;
	}

	public void setId(int id) {
		this.accountID = id;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public abstract void depositMoney(double money);
	public abstract void withdrawMoney(double money);
	
	public boolean isSpending(Account a)
	{
		if(a instanceof SpendingAccount)
			return true;
		return false;
	}
	
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + accountID;
		long temp;
		temp = Double.doubleToLongBits(money);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		Account other = (Account) obj;
		if (accountID != other.accountID)
			return false;
		if (Double.doubleToLongBits(money) != Double.doubleToLongBits(other.money))
			return false;
		return true;
	}
	
	@Override
	public String toString() 
	{
		return "Account [accountID=" + accountID + ", money=" + money + "]";
	}

}
