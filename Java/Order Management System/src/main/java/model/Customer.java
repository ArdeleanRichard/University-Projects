package model;

/**
 * @author Ardelean Eugen Richard
 * a class that represents an entry from the table of customers
 */
public class Customer 
{
	private int id;
	private String name;
	private String address;
	private String phone;
	
	/**
	 * @param id of the customer
	 * @param name of the customer
	 * @param address of the customer
	 * @param phone of the customer
	 */
	public Customer(int id, String name, String address, String phone) 
	{
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone + "]";
	}
	
}
