public interface BankProc 
{
	/**
	 * adds a new person to the bank
	 * @pre bank is not empty
	 * @post bank is not empty
	 */
	void createPerson(String name, String type, int money);
	
	/**
	 * removes person from bank
	 * @pre bank is well formed
	 * @post bank is well formed
	 */
	void removePerson(Person p);

	/**
	 * create a new account to a person
	 * @pre bank is well formed
	 * @pre person != null
	 * @post bank is well formed
	 */
	void createAccount(Person person, Account account);
	
	/**
	 * create a new account to a person
	 * @pre bank is well formed
	 * @post bank is well formed
	 */
	void removeAccount(int accID, Person p);
	
	/**
	 * edit account with accID of a person p
	 * @pre bank is well formed
	 * @pre aMoney>=0
	 * @post bank is well formed
	 */
	void editAccount(int apid, int aid, String type, int money);
	
	/**
	 * deposit money in account with aid id of person with apid id
	 * @pre bank is well formed
	 * @pre person!=null
	 * @pre money>=0
	 * @post bank is well formed
	 */
	void depositMoney(Person p, int accID, double sum);
	
	/**
	 * withdraw money from account with aid id of person with apid id
	 * @pre bank is well formed
	 * @pre person!=null
	 * @pre money>=0
	 * @post bank is well formed
	 */
	void withdrawMoney(Person p, int accID, double sum);
}
