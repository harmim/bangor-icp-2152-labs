import java.io.InputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * Exercises 9, 10
 *
 * A simple data source for getting database connections.
 *
 * @author Dominik Harmim harmim6@gmail.com
 */
public class SimpleDataSource
{
	private static String url;
	private static String username;
	private static String password;


	/**
	 * Initializes the data source.
	 *
	 * @param in the name of the property file that
	 * contains the database driver, URL, username, and password
	 */
	public static void init(InputStream in) throws IOException, ClassNotFoundException
	{
		Properties props = new Properties();
		props.load(in);

		String driver = props.getProperty("jdbc.driver");
		url = props.getProperty("jdbc.url");
		username = props.getProperty("jdbc.username");
		if (username == null) {
			username = "";
		}
		password = props.getProperty("jdbc.password");
		if (password == null) {
			password = "";
		}
		if (driver != null) {
			Class.forName(driver);
		}
	}


	/**
	 * Gets a connection to the database.
	 *
	 * @return the database connection
	 */
	public static Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection(url, username, password);
	}
}
