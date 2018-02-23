import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


/**
 * Generates menu screens, reads input values and displays output.
 *
 * @author Dominik Harmim harmim6@gmail.com
 */
public class DatabaseTextInterface
{
	/**
	 * Application menu types.
	 */
	private enum Menu {
		MAIN,
		STUDENTS,
		STAFF,
		MODULES,
		REPORTS,
	}


	/**
	 * Current active menu.
	 */
	private Menu currentMenu;

	/**
	 * Database manager instance.
	 */
	private DatabaseManager databaseManager;

	/**
	 * Java utils Scanner instance for input.
	 */
	private Scanner input;


	/**
	 * Creates and initialize database text interface.
	 *
	 * @throws IOException if properties file couldn't be opend
	 * @throws ClassNotFoundException if there is problem with mysql driver
	 */
	public DatabaseTextInterface() throws IOException, ClassNotFoundException
	{
		currentMenu = Menu.MAIN;
		databaseManager = new DatabaseManager();
		input = new Scanner(System.in);
	}


	/**
	 * Displays current menu.
	 */
	public void displayMenu()
	{
		displayMenuHeader();
		displayMenuContent();
		System.out.print(":> ");
	}


	/**
	 * Processes user action. (Reads users input and generate appropriate output.)
	 *
	 * @return true if everything is OK, false in case of error or user wants quit
	 */
	public boolean processUserAction()
	{
		int option;
		try {
			option = input.nextInt();
		} catch (InputMismatchException e) {
			System.out.println(e.toString());
			return false;
		}

		System.out.println();

		try {
			int result;
			ArrayList<Column> columns;

			switch (currentMenu) {
				case MAIN:
					switch (option) {
						case 0: // Quit
							return false;

						case 1: // Students
							currentMenu = Menu.STUDENTS;
							return true;

						case 2: // Staff
							currentMenu = Menu.STAFF;
							return true;

						case 3: // Modules
							currentMenu = Menu.MODULES;
							return true;

						case 4: // Reports
							currentMenu = Menu.REPORTS;
							return true;

						default:
							System.out.println("Unknown option.");
					}
					break;

				case STUDENTS:
					switch (option) {
						case 0: // Return to Main Menu
							currentMenu = Menu.MAIN;
							return true;

						case 1: // List students
							displayTable(Arrays.asList(
								new Column("student_id", "Student ID", 's', 10),
								new Column("student_name", "Name", 's', 40),
								new Column("degree_scheme", "Degree Scheme", 's', 40)
							), databaseManager.getAllFromTable("student"));
							break;

						case 2: // Add student
							columns = new ArrayList<Column>(3);
							System.out.print("Enter Student ID: ");
							columns.add(new Column("student_id", input.next() + input.nextLine()));
							System.out.print("Enter Name: ");
							columns.add(new Column("student_name", input.next() + input.nextLine()));
							System.out.print("Enter Degree Scheme: ");
							columns.add(new Column("degree_scheme", input.next() + input.nextLine()));

							try {
								result = databaseManager.insert(columns, "student");
							} catch (MySQLIntegrityConstraintViolationException e) {
								System.out.println("\nStudent with this ID already exists.");
								break;
							}

							if (result == 1) {
								System.out.println("\nStudent have been successfully added.");
							} else {
								System.out.println("\nInsert error.");
							}
							break;

						case 3: // Remove student
							System.out.print("Enter Student ID you want to remove: ");
							result = databaseManager.delete(
								new Column("student_id", input.next() + input.nextLine()),
								"student"
							);

							if (result == 1) {
								System.out.println("\nStudent have been successfully removed.");
							} else {
								System.out.println("\nStudent with entered ID does not exists or some error occurres.");
							}
							break;

						case 4: // Update student
							System.out.print("Enter Student ID you want to update: ");
							Column id = new Column("student_id", input.next() + input.nextLine());

							columns = new ArrayList<Column>(3);
							System.out.print("Enter new Name: ");
							columns.add(new Column("student_name", input.next() + input.nextLine()));
							System.out.print("Enter new Degree Scheme: ");
							columns.add(new Column("degree_scheme", input.next() + input.nextLine()));

							result = databaseManager.update(columns, id, "student");

							if (result == 1) {
								System.out.println("\nStudent have been successfully updated.");
							} else {
								System.out.println("\nStudent with entered ID does not exists or some error occurres.");
							}
							break;

						default:
							System.out.println("Unknown option.");
					}
					break;

				case STAFF:
					switch (option) {
						case 0: // Return to Main Menu
							currentMenu = Menu.MAIN;
							return true;

						case 1: // List staff
							displayTable(Arrays.asList(
								new Column("staff_id", "Staff ID", 's', 10),
								new Column("staff_name", "Name", 's', 40),
								new Column("staff_grade", "Staff Grade", 's', 20)
							), databaseManager.getAllFromTable("staff"));
							break;

						case 2: // Add staff
							columns = new ArrayList<Column>(3);
							System.out.print("Enter Staff ID: ");
							columns.add(new Column("staff_id", input.next() + input.nextLine()));
							System.out.print("Enter Name: ");
							columns.add(new Column("staff_name", input.next() + input.nextLine()));
							System.out.print("Enter Staff Grade: ");
							columns.add(new Column("staff_grade", input.next() + input.nextLine()));

							try {
								result = databaseManager.insert(columns, "staff");
							} catch (MySQLIntegrityConstraintViolationException e) {
								System.out.println("\nStaff with this ID already exists.");
								break;
							}

							if (result == 1) {
								System.out.println("\nStaff have been successfully added.");
							} else {
								System.out.println("\nInsert error.");
							}
							break;

						case 3: // Remove staff
							System.out.print("Enter Staff ID you want to remove: ");
							result = databaseManager.delete(
								new Column("staff_id", input.next() + input.nextLine()),
								"staff"
							);

							if (result == 1) {
								System.out.println("\nStaff have been successfully removed.");
							} else {
								System.out.println("\nStaff with entered ID does not exists or some error occurres.");
							}
							break;

						case 4: // Update staff
							System.out.print("Enter Staff ID you want to update: ");
							Column id = new Column("staff_id", input.next() + input.nextLine());

							columns = new ArrayList<Column>(3);
							System.out.print("Enter new Name: ");
							columns.add(new Column("staff_name", input.next() + input.nextLine()));
							System.out.print("Enter new Staff Grade: ");
							columns.add(new Column("staff_grade", input.next() + input.nextLine()));

							result = databaseManager.update(columns, id, "staff");

							if (result == 1) {
								System.out.println("\nStaff have been successfully updated.");
							} else {
								System.out.println("\nStaff with entered ID does not exists or some error occurres.");
							}
							break;

						case 5: // List modules on which staff teach
							displayTable(Arrays.asList(
								new Column("module_id", "Module ID", 's', 10),
								new Column("staff_id", "Staff ID", 's', 10)
							), databaseManager.getAllFromTable("teaches"));
							break;

						default:
							System.out.println("Unknown option.");
					}
					break;

				case MODULES:
					switch (option) {
						case 0: // Return to Main Menu
							currentMenu = Menu.MAIN;
							return true;

						case 1: // List modules
							displayTable(Arrays.asList(
								new Column("module_id", "Module ID", 's', 10),
								new Column("module_name", "Name", 's', 40),
								new Column("credits", "Credits", 's', 5)
							), databaseManager.getAllFromTable("module"));
							break;

						case 2: // Add module
							columns = new ArrayList<Column>(3);
							System.out.print("Enter Module ID: ");
							columns.add(new Column("module_id", input.next() + input.nextLine()));
							System.out.print("Enter Name: ");
							columns.add(new Column("module_name", input.next() + input.nextLine()));
							System.out.print("Enter Credits: ");
							columns.add(new Column("credits", input.nextInt()));

							try {
								result = databaseManager.insert(columns, "module");
							} catch (MySQLIntegrityConstraintViolationException e) {
								System.out.println("\nModule with this ID already exists.");
								break;
							}

							if (result == 1) {
								System.out.println("\nModule have been successfully added.");
							} else {
								System.out.println("\nInsert error.");
							}
							break;

						case 3: // Remove module
							System.out.print("Enter Module ID you want to remove: ");
							result = databaseManager.delete(
								new Column("module_id", input.next() + input.nextLine()),
								"module"
							);

							if (result == 1) {
								System.out.println("\nModule have been successfully removed.");
							} else {
								System.out.println("\nModule with entered ID does not exists or some error occurres.");
							}
							break;

						case 4: // Update module
							System.out.print("Enter Module ID you want to update: ");
							Column id = new Column("module_id", input.next() + input.nextLine());

							columns = new ArrayList<Column>(3);
							System.out.print("Enter new Name: ");
							columns.add(new Column("module_name", input.next() + input.nextLine()));
							System.out.print("Enter new Credits: ");
							columns.add(new Column("credits", input.nextInt()));

							result = databaseManager.update(columns, id, "module");

							if (result == 1) {
								System.out.println("\nModule have been successfully updated.");
							} else {
								System.out.println("\nModule with entered ID does not exists or some error occurres.");
							}
							break;

						case 5: // List module registrations
							displayTable(Arrays.asList(
								new Column("module_id", "Module ID", 's', 10),
								new Column("student_id", "Student ID", 's', 10)
							), databaseManager.getAllFromTable("registered"));
							break;

						default:
							System.out.println("Unknown option.");
					}
					break;

				case REPORTS:
					switch (option) {
						case 0: // // Return to Main Menu
							currentMenu = Menu.MAIN;
							return true;

						case 1: // Modules taught by
							System.out.print("Enter Staff ID: ");
							displayTable(Arrays.asList(
								new Column("module_id", "Module ID", 's', 10),
								new Column("module_name", "Name", 's', 40)
							), databaseManager.getModulesTaughtBy(input.next() + input.nextLine()));
							break;

						case 2: // Students registered on
							System.out.print("Enter Module ID: ");
							displayTable(Arrays.asList(
								new Column("student_id", "Student ID", 's', 10),
								new Column("student_name", "Name", 's', 40)
							), databaseManager.getStudentsRegisteredOn(input.next() + input.nextLine()));
							break;

						case 3: // Staff who teach student
							System.out.print("Enter Student ID: ");
							displayTable(Arrays.asList(
								new Column("staff_id", "Staff ID", 's', 10),
								new Column("staff_name", "Staff Name", 's', 40),
								new Column("module_id", "Module ID", 's', 10)
							), databaseManager.getStaffOfStudentsModules(input.next() + input.nextLine()));
							break;

						case 4: // Staff who teach more than
							System.out.print("Enter more than number: ");
							displayTable(Arrays.asList(
								new Column("staff_id", "Staff ID", 's', 10),
								new Column("staff_name", "Staff Name", 's', 40)
							), databaseManager.getStaffWhoTeachMoreThan(input.nextInt()));
							break;

						default:
							System.out.println("Unknown option.");
					}
					break;

				default:
					return false;
			}
		} catch (SQLException e) {
			System.out.println(e.toString());
			return false;
		} catch (InputMismatchException e) {
			System.out.println(e.toString());
			return false;
		}

		System.out.println("\nPress Enter key to continue...");
		try {
			System.in.read();
		} catch (Exception ignored) {}

		return true;
	}


	/**
	 * Displays current menu header.
	 */
	private void displayMenuHeader()
	{
		String menuText;
		switch (currentMenu) {
			case MAIN:
				menuText = "Main Menu";
				break;

			case STUDENTS:
				menuText = "Sub-Menu (Students)";
				break;

			case STAFF:
				menuText = "Sub-Menu (Staff)";
				break;

			case MODULES:
				menuText = "Sub-Menu (Modules)";
				break;

			case REPORTS:
				menuText = "Sub-Menu (Reports)";
				break;

			default:
				return;
		}

		System.out.println(menuText);
		System.out.println("********************");
	}


	/**
	 * Displays current menu content.
	 */
	private void displayMenuContent()
	{
		ArrayList<String> options = new ArrayList<String>(4);
		switch (currentMenu) {
			case MAIN:
				options.addAll(Arrays.asList(
					"Quit",
					"Students",
					"Staff",
					"Modules",
					"Reports"
				));
				break;

			case STUDENTS:
				options.addAll(Arrays.asList(
					"Return to Main Menu",
					"List students",
					"Add student",
					"Remove student",
					"Update student"
				));
				break;

			case STAFF:
				options.addAll(Arrays.asList(
					"Return to Main Menu",
					"List staff",
					"Add staff",
					"Remove staff",
					"Update staff",
					"List modules on which staff teach"
				));
				break;

			case MODULES:
				options.addAll(Arrays.asList(
					"Return to Main Menu",
					"List modules",
					"Add module",
					"Remove module",
					"Update module",
					"List module registrations"
				));
				break;

			case REPORTS:
				options.addAll(Arrays.asList(
					"Return to Main Menu",
					"Modules taught by",
					"Students registered on",
					"Staff who teach student",
					"Staff who teach more than"
				));
				break;

			default:
				return;
		}

		for (int i = 1; i < options.size(); i++) {
			System.out.printf("%d. %s\n", i, options.get(i));
		}

		if (options.size() > 0) {
			System.out.printf("0. %s\n", options.get(0));
		}
	}


	/**
	 * Display table of given columns with given rows.
	 *
	 * @param columns table columns
	 * @param rows table rows
	 */
	private void displayTable(List<Column> columns, ArrayList<Map<String, Object>> rows)
	{
		if (rows.size() == 0) {
			System.out.println("Empty...");
			return;
		}

		// table header
		StringBuilder headerNames = new StringBuilder();
		StringBuilder headerAsterisks = new StringBuilder();
		for (Column column : columns) {
			headerNames.append(String.format(
				"%-" + column.getWidth() + "s  ",
				column.getName()
			));
			headerAsterisks.append(
				String.format("%0" + column.getWidth() + "d  ", 0).replace("0", "*")
			);
		}
		System.out.printf("%s\n%s\n", headerNames.toString(), headerAsterisks.toString());


		// table items
		StringBuilder items = new StringBuilder();
		for (Map<String, Object> row : rows) {
			for (Column column : columns) {
				items.append(String.format(
					"%-" + column.getWidth() + column.getType() + "  ", row.get(column.getKey()))
				);
			}
			items.append("\n");
		}
		System.out.print(items.toString());
	}
}
