import java.io.IOException;


/**
 * Entry point to the application.
 *
 * @author Dominik Harmim harmim6@gmail.com
 */
public class DatabaseMain
{
	public static void main(String args[])
	{
		DatabaseTextInterface textInterface;
		try {
			textInterface = new DatabaseTextInterface();
		} catch (IOException e) {
			System.out.println(e.toString());
			return;
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
			return;
		}

		// Displays menu and processing user actions until an error will occur or user want to quit.
		do {
			textInterface.displayMenu();
		} while (textInterface.processUserAction());
	}
}
