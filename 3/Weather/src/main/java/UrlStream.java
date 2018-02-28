import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.URL;


/**
 * URL stream which performs connection to given URL and returns input stream and response information.
 *
 * @author Dominik Harmim harmim6@gmail.com
 */
public class UrlStream
{
	/**
	 * Connection to given URL.
	 */
	private HttpURLConnection connection;


	/**
	 * Creates connection to given URL.
	 *
	 * @param url URL to be connected
	 *
	 * @throws IOException in case of invalid URL or failed connection
	 */
	UrlStream(String url) throws IOException
	{
		connection = (HttpURLConnection) (new URL(url)).openConnection();
	}


	/**
	 * Returns HTTP response code.
	 *
	 * @return HTTP response code
	 *
	 * @throws IOException if an error occurred connecting to the server
	 */
	public int getResponseCode() throws IOException
	{
		return connection.getResponseCode();
	}


	/**
	 * Returns HTTP response message.
	 *
	 * @return HTTP response message
	 *
	 * @throws IOException if an error occurred connecting to the server
	 */
	public String getResponseMessage() throws IOException
	{
		return connection.getResponseMessage();
	}


	/**
	 * Returns connection input stream.
	 *
	 * @return connection input stream
	 *
	 * @throws IOException if an I/O error occurs while creating the input stream
	 */
	public InputStream getInputStream() throws IOException
	{
		return connection.getInputStream();
	}
}
