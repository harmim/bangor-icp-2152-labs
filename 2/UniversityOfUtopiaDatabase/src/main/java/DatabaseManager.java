import java.io.IOException;
import java.sql.*;
import java.util.*;


/**
 * Respond to requests, received via the interface, for the execution of specifc SQL commands.
 *
 * @author Dominik Harmim harmim6@gmail.com
 */
public class DatabaseManager
{
	/**
	 * Creates and initialize databbase manager.
	 *
	 * @throws IOException if properties file couldn't be opend
	 * @throws ClassNotFoundException if there is problem with mysql driver
	 */
	public DatabaseManager() throws IOException, ClassNotFoundException
	{
		SimpleDataSource.init(DatabaseManager.class.getResourceAsStream("/database.properties"));
	}


	/**
	 * Get all rows from particular table.
	 *
	 * @param table database table name
	 * @return ArrayList with all rows from table
	 *
	 * @throws SQLException if there is connection or any other database access error
	 */
	public ArrayList<Map<String, Object>> getAllFromTable(String table) throws SQLException
	{
		Connection connection = SimpleDataSource.getConnection();
		Statement statement = connection.createStatement();

		String query = "SELECT * FROM `" + table + "`;";

		ArrayList<Map<String, Object>> list = resultSetToArrayList(statement.executeQuery(query));

		statement.close();
		connection.close();

		return list;
	}


	/**
	 * Inserts given values to given table.
	 *
	 * @param values values to be inserted
	 * @param table database table name
	 * @return number of affected rows
	 *
	 * @throws SQLException if there is connection or any other database access error
	 */
	public int insert(List<Column> values, String table) throws SQLException
	{
		Connection connection = SimpleDataSource.getConnection();

		StringBuilder query = new StringBuilder("INSERT INTO `" + table + "` (");
		StringBuilder parameters = new StringBuilder("VALUES (");
		for (int i = 0; i < values.size(); i++) {
			query.append("`").append(values.get(i).getKey()).append("`");
			parameters.append("?");

			if (i != values.size() - 1) {
				query.append(", ");
				parameters.append(", ");
			}
		}
		query.append(")");
		parameters.append(")");
		query.append(parameters).append(";");

		PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
		for (int i = 1; i <= values.size(); i++) {
			preparedStatement.setObject(i, values.get(i - 1).getValue());
		}
		int result = preparedStatement.executeUpdate();

		preparedStatement.close();
		connection.close();

		return result;
	}


	/**
	 * Deletes row in given table according to given where condition.
	 *
	 * @param where condition which tells which row should be deleted
	 * @param table database table name
	 * @return number of affected rows
	 *
	 * @throws SQLException if there is connection or any other database access error
	 */
	public int delete(Column where, String table) throws SQLException
	{
		Connection connection = SimpleDataSource.getConnection();

		String query = "DELETE FROM `" + table + "` WHERE `" + where.getKey() + "` = ?;";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setObject(1, where.getValue());
		int result = preparedStatement.executeUpdate();

		preparedStatement.close();
		connection.close();

		return result;
	}


	/**
	 * Updates given table with given values according to given where condition.
	 *
	 * @param values new values for update
	 * @param where condition which tells which row should be updated
	 * @param table database table name
	 * @return number of affected rows
	 *
	 * @throws SQLException if there is connection or any other database access error
	 */
	public int update(List<Column> values, Column where, String table) throws SQLException
	{
		Connection connection = SimpleDataSource.getConnection();

		StringBuilder query = new StringBuilder("UPDATE `" + table + "` SET ");
		for (int i = 0; i < values.size(); i++) {
			query.append("`").append(values.get(i).getKey()).append("` = ?");

			if (i != values.size() - 1) {
				query.append(", ");
			}
		}
		query.append(" WHERE `").append(where.getKey()).append("` = ?;");

		PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
		for (int i = 1; i <= values.size(); i++) {
			preparedStatement.setObject(i, values.get(i - 1).getValue());
		}
		preparedStatement.setObject(values.size() + 1, where.getValue());
		int result = preparedStatement.executeUpdate();

		preparedStatement.close();
		connection.close();

		return result;
	}


	/**
	 * Returns all modules taught by a specified member of staff.
	 *
	 * @param staff specific member of staff
	 * @return ArrayList with appropriate modules.
	 *
	 * @throws SQLException if there is connection or any other database access error
	 */
	public ArrayList<Map<String, Object>> getModulesTaughtBy(String staff) throws SQLException
	{
		Connection connection = SimpleDataSource.getConnection();

		String query =
			"SELECT `module`.* "
			+ "FROM `teaches` "
			+ "JOIN `module` ON `module`.`module_id` = `teaches`.`module_id`"
			+ "JOIN `staff` ON `staff`.`staff_id` = `teaches`.`staff_id`"
			+ "WHERE `staff`.`staff_id` = ?"
			+ "GROUP BY `module`.`module_id`";

		PreparedStatement preparedStatement = connection.prepareStatement(query);

		preparedStatement.setString(1, staff);
		ArrayList<Map<String, Object>> list = resultSetToArrayList(preparedStatement.executeQuery());

		preparedStatement.close();
		connection.close();

		return list;
	}


	/**
	 * Returns all students registered on a specific module.
	 *
	 * @param module specific module
	 * @return ArrayList with appropriate students
	 *
	 * @throws SQLException if there is connection or any other database access error
	 */
	public ArrayList<Map<String, Object>> getStudentsRegisteredOn(String module) throws SQLException
	{
		Connection connection = SimpleDataSource.getConnection();

		String query =
			"SELECT `student`.* "
			+ "FROM `registered` "
			+ "JOIN `student` ON `student`.`student_id` = `registered`.`student_id`"
			+ "JOIN `module` ON `module`.`module_id` = `registered`.`module_id`"
			+ "WHERE `module`.`module_id` = ?"
			+ "GROUP BY `student`.`student_id`";

		PreparedStatement preparedStatement = connection.prepareStatement(query);

		preparedStatement.setString(1, module);
		ArrayList<Map<String, Object>> list = resultSetToArrayList(preparedStatement.executeQuery());

		preparedStatement.close();
		connection.close();

		return list;
	}


	/**
	 * Returns all staff who teach modules on which a specific student is registered.
	 *
	 * @param student specific student
	 * @return ArrayList with appropriate staff and theire modules
	 *
	 * @throws SQLException if there is connection or any other database access error
	 */
	public ArrayList<Map<String, Object>> getStaffOfStudentsModules(String student) throws SQLException
	{
		Connection connection = SimpleDataSource.getConnection();

		String query =
			"SELECT `staff`.*, `module`.* "
			+ "FROM `teaches` "
			+ "JOIN `staff` ON `staff`.`staff_id` = `teaches`.`staff_id`"
			+ "JOIN `module` ON `module`.`module_id` = `teaches`.`module_id`"
			+ "JOIN `registered` ON `registered`.`module_id` = `module`.`module_id`"
			+ "JOIN `student` ON `student`.`student_id` = `registered`.`student_id`"
			+ "WHERE `student`.`student_id` = ?"
			+ "GROUP BY `staff`.`staff_id`";

		PreparedStatement preparedStatement = connection.prepareStatement(query);

		preparedStatement.setString(1, student);
		ArrayList<Map<String, Object>> list = resultSetToArrayList(preparedStatement.executeQuery());

		preparedStatement.close();
		connection.close();

		return list;
	}


	/**
	 * Returns all staff who teach on more than one module.
	 *
	 * @param moreThan more than condition value
	 * @return ArrayList with appropriate members of staff
	 *
	 * @throws SQLException if there is connection or any other database access error
	 */
	public ArrayList<Map<String, Object>> getStaffWhoTeachMoreThan(int moreThan) throws SQLException
	{
		Connection connection = SimpleDataSource.getConnection();

		String query =
			"SELECT `staff`.*, COUNT(*) AS teach_count "
			+ "FROM `teaches` "
			+ "JOIN `staff` ON `staff`.`staff_id` = `teaches`.`staff_id`"
			+ "GROUP BY `staff`.`staff_id`"
			+ "HAVING teach_count > ?";

		PreparedStatement preparedStatement = connection.prepareStatement(query);

		preparedStatement.setInt(1, moreThan);
		ArrayList<Map<String, Object>> list = resultSetToArrayList(preparedStatement.executeQuery());

		preparedStatement.close();
		connection.close();

		return list;
	}


	/**
	 * Coverts ResultSet to appropriate ArrayList.
	 *
	 * @param resultSet ResultSet to be converted
	 * @return converted ArrayList
	 *
	 * @throws SQLException if ther is ResultSet access error
	 */
	private ArrayList<Map<String, Object>> resultSetToArrayList(ResultSet resultSet) throws SQLException
	{
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columnsCount = metaData.getColumnCount();
		ArrayList<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		while (resultSet.next()) {
			HashMap<String, Object> row = new HashMap<String, Object>(columnsCount);

			for (int i = 1; i <= columnsCount; i++) {
				row.put(metaData.getColumnName(i), resultSet.getObject(i));
			}
			list.add(row);
		}

		return list;
	}
}
