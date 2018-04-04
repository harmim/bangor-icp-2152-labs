/**
 * Representation of database column.
 *
 * @author Dominik Harmim harmim6@gmail.com
 */
public class Column
{
	/**
	 * Primary key name.
	 */
	private String key;

	/**
	 * Column name/label.
	 */
	private String name;

	/**
	 * Data type of column in format of printf specifier, eg. d or s.
	 */
	private char type;

	/**
	 * Width of column for printing purpose.
	 */
	private int width;

	/**
	 * Value of column.
	 */
	private Object value;


	/**
	 * Creates column with primary key, name, data type and width.
	 * Typically column to be printed.
	 *
	 * @param key column primary key
	 * @param name column name
	 * @param type column data type
	 * @param width column width
	 */
	public Column(String key, String name, char type, int width)
	{
		this.key = key;
		this.name = name;
		this.type = type;
		this.width = width;
	}


	/**
	 * Creates column with primary key name and it's value.
	 * Typically column retrieved from database or column to be processed in query.
	 *
	 * @param key primary key name
	 * @param value particular value
	 */
	public Column(String key, Object value)
	{
		this.key = key;
		this.value = value;
	}


	/**
	 * Returns column primary key name.
	 *
	 * @return column primary key name
	 */
	public String getKey()
	{
		return key;
	}


	/**
	 * Returns column name.
	 *
	 * @return column name
	 */
	public String getName()
	{
		return name;
	}


	/**
	 * Returns column data type.
	 *
	 * @return column date type
	 */
	public char getType()
	{
		return type;
	}


	/**
	 * Returns column width.
	 *
	 * @return column width
	 */
	public int getWidth()
	{
		return width;
	}


	/**
	 * Returns column value.
	 *
	 * @return column value
	 */
	public Object getValue()
	{
		return value;
	}
}
