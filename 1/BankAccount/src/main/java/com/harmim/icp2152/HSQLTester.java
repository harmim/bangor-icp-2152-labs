package com.harmim.icp2152;


import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Exercises 9, 10
 *
 * @author Dominik Harmim harmim6@gmail.com
 */
public class HSQLTester
{
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException
	{
		InputStream stream = HSQLTester.class.getResourceAsStream("/database.properties");
		SimpleDataSource.init(stream);
		Connection connection = SimpleDataSource.getConnection();

		Statement statement = connection.createStatement();

		try {
			statement.execute("CREATE TABLE IF NOT EXISTS accounts (balance DECIMAL(5, 2))");
			statement.execute("INSERT INTO accounts VALUES (999.99)");
			statement.execute("INSERT INTO accounts VALUES (666.66)");

			ResultSet rs = statement.executeQuery("SELECT * FROM accounts");
			while (rs.next()) {
				System.out.println(rs.getString("balance"));
			}

			statement.execute("DROP TABLE accounts");
		} finally {
			System.out.println("Table created and then dropped!");
			statement.close();
			connection.close();
		}
	}
}
