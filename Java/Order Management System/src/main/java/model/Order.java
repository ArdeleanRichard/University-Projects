package model;

/**
 * @author Ardelean Eugen Richard
 * a class that represents an entry from the table of orders
 */
public class Order 
{
	private int id;
	private int customerId;
	private String paymentMethod;

	/**
	 * @param id of the order
	 * @param customerId id of the customer that made the order
	 * @param paymentMethod of the order
	 */
	public Order(int id, int customerId, String paymentMethod) 
	{
		super();
		this.id = id;
		this.customerId = customerId;
		this.paymentMethod = paymentMethod;
	}
	public int getCustomerId() {
		return customerId;
	}
	public int getId() {
		return id;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public String toString() {
		return "Order [id=" + id + ", customerId=" + customerId + ", paymentMethod=" + paymentMethod + "]";
	}
	
}
