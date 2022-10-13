import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class File {
	
	public static Map<Person,Set<Account>> readBank(String filename)
	{
		 try
		 {
			 FileInputStream file= new FileInputStream (filename);
			 ObjectInputStream objects= new ObjectInputStream(file);
			 Map<Person,Set<Account>> bank = (HashMap<Person,Set<Account>>) objects.readObject();
			 return bank;
		 }
		 catch(Exception e)
		 {
			 GUI.showError("Cannot read from bank.");
			 return null;
		 }
	 }
	
	public static void writeBank(Map<Person,Set<Account>> bank, String filename)
	{
		 try
		 {
			 FileOutputStream file = new FileOutputStream (filename);
			 ObjectOutputStream objects= new ObjectOutputStream(file);
			 objects.writeObject(bank);
		 }
		 catch(Exception e){
			 GUI.showError("Cannot write into bank.");
		 }
	 }
}

