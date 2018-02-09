import org.apache.commons.collections4.list.GrowthList;


/**
 * Exercise 7: Testing a Dependency
 *
 * @author Dominik Harmim harmim6@gmail.com
 */
public class Main
{
	public static void main(String[] args)
	{
		GrowthList<BankAccount> bank = new GrowthList<BankAccount>();
		for (int i = 0; i < 3; i++) {
			bank.add(new BankAccount((i + 1) * 100));
		}
		System.out.println(bank);
	}
}
