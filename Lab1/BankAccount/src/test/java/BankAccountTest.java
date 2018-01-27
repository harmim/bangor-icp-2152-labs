import static org.junit.Assert.*;


/**
 * @author Dominik Harmim <harmim6@gmail.com>
 */
public class BankAccountTest
{

	@org.junit.Before
	public void setUp()
	{
	}


	@org.junit.After
	public void tearDown()
	{
	}


	@org.junit.Test
	public void deposit()
	{
		BankAccount account = new BankAccount();

		assertEquals(0.0, account.getBalance(), 0.0);

		account.deposit(200.0);
		assertEquals(200.0, account.getBalance(), 0.0);

		account.deposit(600.0);
		assertEquals(800.0, account.getBalance(), 0.0);

		account.deposit(42.42);
		assertEquals(842.42, account.getBalance(), 0.0);
	}


	@org.junit.Test
	public void withdraw()
	{
		BankAccount account = new BankAccount(500.0);

		assertEquals(500.0, account.getBalance(), 0.0);

		account.withdraw(300.0);
		assertEquals(200.0, account.getBalance(), 0.0);

		account.withdraw(42.42);
		assertEquals(157.58, account.getBalance(), 0.1);

		account.withdraw(157.58);
		assertEquals(0.0, account.getBalance(), 0.1);

		account.withdraw(100.0);
		assertEquals(-100.0, account.getBalance(), 0.1);
	}


	@org.junit.Test
	public void getBalance()
	{
		BankAccount account1 = new BankAccount(500.0);
		assertEquals(500.0, account1.getBalance(), 0.0);

		BankAccount account2 = new BankAccount(0.0);
		assertEquals(0.0, account2.getBalance(), 0.0);

		BankAccount account3 = new BankAccount();
		assertEquals(0.0, account3.getBalance(), 0.0);

		BankAccount account4 = new BankAccount(-500.0);
		assertEquals(-500.0, account4.getBalance(), 0.0);

		BankAccount account5 = new BankAccount(420000.422222);
		assertEquals(420000.422222, account5.getBalance(), 0.0);
	}
}
