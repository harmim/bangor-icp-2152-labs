import java.io.IOException;
import java.io.InputStream;
import java.sql.*;


/**
 * MySQL tester class.
 *
 * @author Dominik Harmim harmim6@gmail.com
 */
public class MySqlTester
{
	public static void main(String[] args)
	{
		// Loads configuration file and initialize data source.
		InputStream stream = MySqlTester.class.getResourceAsStream("/database.properties");
		try {
			SimpleDataSource.init(stream);
		} catch (IOException e) {
			System.out.println(e.toString());
			return;
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
			return;
		}

		// Executes testing queries in database.
		try {
			Connection connection = SimpleDataSource.getConnection();
			Statement statement = connection.createStatement();

			statement.execute("DROP TABLE IF EXISTS `test`");
			statement.execute(
				"CREATE TABLE `test` ("
				+ "`id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,"
				+ "`name` VARCHAR(255) COLLATE utf8_unicode_ci NOT NULL,"
				+ "PRIMARY KEY (`id`)"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;");

			statement.execute("INSERT INTO `test` (`name`) VALUES ('Test1'), ('Test2')");

			ResultSet resultSet = statement.executeQuery("SELECT * FROM `test`");
			while (resultSet.next()) {
				System.out.println(resultSet.getString("name"));
			}

			statement.execute("DROP TABLE `test`");

			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.toString());
			return;
		}

		System.out.println("Table created and then dropped!");
	}
}
