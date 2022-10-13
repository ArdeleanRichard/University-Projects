package model;

/**
 * @author Ardelean Eugen Richard
 * a class that represents an entry from the table of order details, a table used to link orders and products
 * so they have a many-to-many relationship
 */
public class OrderDetail 
{
	private int orderID;
	private int productID;
	private int quantity;
	
	/**
	 * @param orderID id of the order
	 * @param productID id of the product
	 * @param quantity quantity of the product
	 */
	public OrderDetail(int orderID, int productID, int quantity) 
	{
		super();
		this.orderID = orderID;
		this.productID = productID;
		this.quantity = quantity;
	}

	public int getOrderID() 
	{
		return orderID;
	}

	public int getProductID() 
	{
		return productID;
	}

	public int getQuantity() 
	{
		return quantity;
	}
}
