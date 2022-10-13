package model;

/**
 * @author Ardelean Eugen Richard
 * a class that represents an entry from the table of products
 */
public class Product 
{
	private int id;
	private String name;
	private int price;
	private String description;
	private int stock;
	
	/**
	 * @param id of the product
	 * @param name of the product
	 * @param price of the product
	 * @param description of the product
	 * @param stock of the product
	 */
	public Product(int id, String name, int price, String description, int stock) 
	{
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.stock = stock;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public String getDescription() {
		return description;
	}

	public int getStock() {
		return stock;
	}

	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description + ", stock=" + stock + "]";
	}
	
	
}
