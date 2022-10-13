import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

public class Person implements Observer , Serializable{
	private int personID;
	private String name;
	private static final long serialVersionUID = 7819611035271298814L;
	public Person(int id, String name) 
	{
		super();
		this.personID = id;
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + personID;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (personID != other.personID)
			return false;
		return true;
	}
	
	@Override
	public void update(Observable obs, Object arg1) 
	{
		if (obs instanceof Account)
		{
			Account acc = (Account) obs;
		}		
	}
	
	@Override
	public String toString() 
	{
		return "Person [personID=" + personID + ", name=" + name + "]";
	}

	public int getId() {
		return personID;
	}
	public void setId(int id) {
		this.personID = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
