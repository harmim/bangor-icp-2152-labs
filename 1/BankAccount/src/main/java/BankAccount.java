/**
 * Exercises 3, 4
 *
 * A bank account has a balance that can be changed by
 * deposits and withdrawals.
 *
 * @author Dominik Harmim harmim6@gmail.com
 */
public class BankAccount
{
	private double balance;


	/**
	 * Constructs a bank account with a zero balance.
	 */
	public BankAccount()
	{
		balance = 0;
	}


	/**
	 * Constructs a bank account with a given balance.
	 *
	 * @param initialBalance the initial balance
	 */
	public BankAccount(double initialBalance)
	{
		balance = initialBalance;
	}


	/**
	 * Deposits money into the account.
	 *
	 * @param amount the amount of money to withdraw
	 */
	public void deposit(double amount)
	{
		balance = balance + amount;
	}


	/**
	 * Withdraws money from the account.
	 *
	 * @param amount the amount of money to deposit
	 */
	public void withdraw(double amount)
	{
		balance = balance - amount;
	}


	/**
	 * Gets the account balance.
	 *
	 * @return the account balance
	 */
	public double getBalance()
	{
		return balance;
	}


	/**
	 * Exercise 5: Modifying BankAccount
	 *
	 * Transfers from this to other.
	 * @param amount the sum to be transferred
	 * @param other the account into which money is transferred
	 */
	public void transfer(double amount, BankAccount other)
	{
		withdraw(amount);
		other.deposit(amount);
	}
}
