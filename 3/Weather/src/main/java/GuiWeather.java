import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import java.net.URLEncoder;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.awt.*;


/**
 * Weather GUI Application.
 *
 * @author Dominik Harmim harmim6@gmail.com
 */
public class GuiWeather
{
	/**
	 * BBC weather URL.
	 */
	private static final String BBC_URL = "http://open.live.bbc.co.uk/weather/feeds/en/id/observations.rss";

	/**
	 * Geo API URL.
	 */
	private static final String GEO_URL = "http://api.geonames.org/search?q=query&maxRows=1&lang=en&username=harmim";


	/**
	 * XML parser instance.
	 */
	private XmlParser xmlParser;

	/**
	 * XML processor instance.
	 */
	private XmlProcessor xmlProcessor;

	// Form elements.
	private JPanel panelMain;
	private JTextField textFieldSearch;
	private JButton buttonForecast;
	private JTextArea textAreaResult;
	private JLabel labelImage;
	private JTable tableDescription;
	private JTextField textFieldSearchDataLocation;


	public static void main(String[] args)
	{
		// Creates and initialize main frame.
		GuiWeather app = new GuiWeather();
		JFrame frame = new JFrame("Get BCC Weather");
		frame.setContentPane(app.$$$getRootComponent$$$());
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}


	/**
	 * Initializes instance variables and sets action handlers.
	 */
	private GuiWeather()
	{
		// Creates XML parser.
		try {
			xmlParser = new XmlParser();
		} catch (ParserConfigurationException e) {
			textAreaResult.setText("Internal error.");
			return;
		}

		// Creates search data output file.
		try {
			createSearchDataFile();
		} catch (IOException e) {
			textAreaResult.setText("I/O error.");
			return;
		} catch (Exception e) {
			textAreaResult.setText("Internal error.");
			return;
		}

		// Initialize description table header.
		DefaultTableModel tableModel = (DefaultTableModel) tableDescription.getModel();
		tableModel.addColumn("Temperature", new String[]{""});
		tableModel.addColumn("Wind Direction", new String[]{""});
		tableModel.addColumn("Wind Speed", new String[]{""});
		tableModel.addColumn("Humidity", new String[]{""});
		tableModel.addColumn("Pressure", new String[]{""});
		tableModel.addColumn("Visibility", new String[]{""});

		// Handles forecast button click.
		buttonForecast.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				forecastAction();
			}
		});
	}


	/**
	 * Forecast button click action.
	 */
	private void forecastAction()
	{
		removeDescription();
		removeWeatherIcon();

		if (textFieldSearch.getText().equals("")) {
			return;
		}

		try {
			Map<String, String> weather = getWeatherById(searchLocationId(textFieldSearch.getText()));
			String title = weather.get("title");
			textAreaResult.setText(title);
			setDescription(weather);
			setWeatherIcon(title);
		} catch (Exception e) {
			textAreaResult.setText(e.getMessage());
		}
	}


	/**
	 * Returns location Geo ID by query string.
	 *
	 * @param query query string for searching
	 * @return Geo ID of location
	 *
	 * @throws Exception with user message in case of any error
	 */
	private String searchLocationId(String query) throws Exception
	{
		// Creates new input stream from appropriate URL.
		InputStream inputStream;
		try {
			UrlStream urlStream = new UrlStream(GEO_URL.replace("query", URLEncoder.encode(query, "UTF-8")));
			int responseCode = urlStream.getResponseCode();
			if (responseCode != 200) {
				throw new Exception(responseCode + " " + urlStream.getResponseMessage());
			}

			inputStream = urlStream.getInputStream();
		} catch (IOException e) {
			throw new Exception("I/O error.");
		}

		// parses XML
		try {
			xmlParser.parse(inputStream);
		} catch (Exception e) {
			throw new Exception("Parse error.");
		}

		String geoId, resultCount;
		try {
			geoId = xmlParser.evaluatePath("/geonames/geoname/geonameId");
			resultCount = xmlParser.evaluatePath("/geonames/totalResultsCount");
		} catch (XPathExpressionException e) {
			throw new Exception("Parse error.");
		}

		// writes to search output file
		String searchElementName = "search";
		HashMap<String, String> attributes = new HashMap<String, String>(1);
		attributes.put("date", (new Date()).toString());
		HashMap<String, String> subElements = new HashMap<String, String>(3);
		subElements.put("term", query);

		if (Integer.parseInt(resultCount) == 0) {
			subElements.put("found", "false");
			subElements.put("geoNameID", "");
			xmlProcessor.appendElementToRoot(searchElementName, attributes, subElements);

			throw new Exception("Not found.");
		}

		subElements.put("found", "true");
		subElements.put("geoNameID", geoId);
		xmlProcessor.appendElementToRoot(searchElementName, attributes, subElements);

		return geoId;
	}


	/**
	 * Returns BBC weather by its Geo ID.
	 *
	 * @param id Geo ID of location
	 * @return BBC weather information
	 *
	 * @throws Exception with user message in case of any error
	 */
	private Map<String, String> getWeatherById(String id) throws Exception
	{
		// Creates new input stream from appropriate URL.
		InputStream inputStream;
		try {
			UrlStream urlStream = new UrlStream(BBC_URL.replace("id", URLEncoder.encode(id, "UTF-8")));
			int responseCode = urlStream.getResponseCode();
			if (responseCode != 200) {
				throw new Exception(responseCode + " " + urlStream.getResponseMessage());
			}

			inputStream = urlStream.getInputStream();
		} catch (IOException e) {
			throw new Exception("I/O error.");
		}

		HashMap<String, String> result = new HashMap<String, String>(7);
		// Parses XML file and return weather information.
		try {
			xmlParser.parse(inputStream);
			result.put("title", xmlParser.evaluatePath("/rss/channel/item/title"));

			String description = xmlParser.evaluatePath("/rss/channel/item/description");
			String[] descriptionParts = description.split(",?[A-Za-z\\s]*: ");
			result.put("temperature", descriptionParts[1]);
			result.put("windDirection", descriptionParts[2]);
			result.put("windSpeed", descriptionParts[3]);
			result.put("humidity", descriptionParts[4]);
			result.put("pressure", descriptionParts[5]);
			result.put("visibility", descriptionParts[6]);
		} catch (Exception e) {
			throw new Exception("Parse error.");
		}

		return result;
	}


	/**
	 * Sets weather icon by its type.
	 *
	 * @param title title of weather from BBC
	 */
	private void setWeatherIcon(String title)
	{
		// title parsing
		String weatherType = title.substring(title.indexOf(':') + 1);
		weatherType = weatherType.substring(weatherType.indexOf(':') + 2, weatherType.indexOf(','));

		// creates file url
		URL fileUrl = getClass().getResource(
			"/images/" + weatherType.toLowerCase().replace(' ', '_') + ".png"
		);
		if (fileUrl != null) {
			try {
				// resizes and sets image
				Image image = ImageIO.read(fileUrl).getScaledInstance(100, 100, Image.SCALE_SMOOTH);
				labelImage.setIcon(new ImageIcon(image));
			} catch (IOException ignored) {
			}
		}
	}


	/**
	 * Removes weather icon.
	 */
	private void removeWeatherIcon()
	{
		labelImage.setIcon(null);
	}


	/**
	 * Sets description of weather.
	 *
	 * @param values description values.
	 */
	private void setDescription(Map<String, String> values)
	{
		DefaultTableModel tableModel = (DefaultTableModel) tableDescription.getModel();

		tableModel.setValueAt(values.get("temperature"), 0, 0);
		tableModel.setValueAt(values.get("windDirection"), 0, 1);
		tableModel.setValueAt(values.get("windSpeed"), 0, 2);
		tableModel.setValueAt(values.get("humidity"), 0, 3);
		tableModel.setValueAt(values.get("pressure"), 0, 4);
		tableModel.setValueAt(values.get("visibility"), 0, 5);
	}


	/**
	 * Removes description of weather.
	 */
	private void removeDescription()
	{
		DefaultTableModel tableModel = (DefaultTableModel) tableDescription.getModel();

		for (int i = 0; i <= 5; i++) {
			tableModel.setValueAt("", 0, i);
		}
	}


	/**
	 * Creates search data output file.
	 *
	 * @throws IOException if output file cannot be created
	 * @throws XMLStreamException if XmlStreamWriter reports error
	 * @throws ParserConfigurationException if a DocumentBuilder cannot be created
	 * @throws TransformerConfigurationException if a Transformer cannot be created
	 */
	private void createSearchDataFile()
		throws IOException, XMLStreamException, ParserConfigurationException, TransformerConfigurationException
	{
		File tmpFile = File.createTempFile("weatherAppSearchData", ".tmp.xml");
		tmpFile.deleteOnExit();

		xmlProcessor = new XmlProcessor(tmpFile);
		xmlProcessor.createXmlDocument("weatherSearches");

		textFieldSearchDataLocation.setText(textFieldSearchDataLocation.getText() + tmpFile.getAbsolutePath());
	}


	{
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
		$$$setupUI$$$();
	}

	/**
	 * Method generated by IntelliJ IDEA GUI Designer >>> IMPORTANT!! <<< DO NOT edit this method OR call it in your code!
	 *
	 * @noinspection ALL
	 */
	private void $$$setupUI$$$()
	{
		panelMain = new JPanel();
		panelMain.setLayout(new GridLayoutManager(5, 4, new Insets(10, 10, 10, 10), -1, -1));
		panelMain.setAutoscrolls(false);
		panelMain.setPreferredSize(new Dimension(800, 350));
		textFieldSearch = new JTextField();
		panelMain.add(textFieldSearch, new GridConstraints(0, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
		final JLabel label1 = new JLabel();
		label1.setText("Search");
		panelMain.add(label1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		textAreaResult = new JTextArea();
		textAreaResult.setEditable(false);
		textAreaResult.setLineWrap(true);
		textAreaResult.setText("");
		textAreaResult.setWrapStyleWord(true);
		panelMain.add(textAreaResult, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 30), new Dimension(-1, 30), new Dimension(-1, 30), 1, false));
		buttonForecast = new JButton();
		buttonForecast.setText("Forecast");
		panelMain.add(buttonForecast, new GridConstraints(1, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
		labelImage = new JLabel();
		labelImage.setText("");
		panelMain.add(labelImage, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, 100), new Dimension(100, 100), new Dimension(100, 100), 0, false));
		final JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setEnabled(true);
		scrollPane1.setFocusCycleRoot(false);
		panelMain.add(scrollPane1, new GridConstraints(3, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 50), null, 0, false));
		tableDescription = new JTable();
		tableDescription.setEnabled(false);
		tableDescription.setFocusable(false);
		tableDescription.setPreferredSize(new Dimension(150, 20));
		tableDescription.setRequestFocusEnabled(false);
		tableDescription.setRowSelectionAllowed(false);
		tableDescription.setShowHorizontalLines(true);
		tableDescription.setShowVerticalLines(true);
		tableDescription.setSurrendersFocusOnKeystroke(false);
		scrollPane1.setViewportView(tableDescription);
		final JScrollPane scrollPane2 = new JScrollPane();
		panelMain.add(scrollPane2, new GridConstraints(4, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
		textFieldSearchDataLocation = new JTextField();
		textFieldSearchDataLocation.setText("Search data location: ");
		scrollPane2.setViewportView(textFieldSearchDataLocation);
	}


	/**
	 * @noinspection ALL
	 */
	public JComponent $$$getRootComponent$$$()
	{
		return panelMain;
	}
}
