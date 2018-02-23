import java.io.InputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;


/**
 * A simple data source for getting database connections.
 *
 * @author Dominik Harmim harmim6@gmail.com
 */
public class SimpleDataSource
{
	/**
	 * Database URL.
	 */
	private static String url;

	/**
	 * Database user.
	 */
	private static String user;

	/**
	 * Database password.
	 */
	private static String password;


	/**
	 * Initializes the data source.
	 *
	 * @param inputStream the property file resource that contains the database driver, URL, user, and password
	 *
	 * @throws IOException if properties file couldn't be opend
	 * @throws ClassNotFoundException if there is problem with mysql driver
	 */
	public static void init(InputStream inputStream) throws IOException, ClassNotFoundException
	{
		Properties properties = new Properties();
		properties.load(inputStream);

		String driver = properties.getProperty("mysql.driver");
		if (driver != null) {
			Class.forName(driver);
		}

		url = properties.getProperty("mysql.url");

		user = properties.getProperty("mysql.user");
		if (user == null) {
			user = "";
		}

		password = properties.getProperty("mysql.password");
		if (password == null) {
			password = "";
		}
	}


	/**
	 * Gets a connection to the database.
	 *
	 * @return the database Connection instance
	 */
	public static Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection(url, user, password);
	}
}
