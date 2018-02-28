import java.io.IOException;
import java.io.InputStream;


/**
 * Command-line client to retrieve and display weather in Bangor.
 *
 * @author Dominik Harmim harmim6@gmail.com
 */
public class CommandLineWeather
{
	public static void main(String[] args)
	{
		// Obtains input stream and prints response information.
		InputStream inputStream;
		try {
			UrlStream urlStream = new UrlStream("http://open.live.bbc.co.uk/weather/feeds/en/2656397/observations.rss");
			int responseCode = urlStream.getResponseCode();
			System.out.printf("%d %s\n", responseCode, urlStream.getResponseMessage());

			if (responseCode != 200) {
				return;
			}

			inputStream = urlStream.getInputStream();
		} catch (IOException e) {
			System.out.println(e.toString());
			return;
		}

		// Parses input stream document and prints weather information.
		try {
			XmlParser xmlParser = new XmlParser();
			xmlParser.parse(inputStream);
			System.out.println(xmlParser.evaluatePath("/rss/channel/item/title"));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
