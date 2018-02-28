import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;


/**
 * DOM XML parser which allows parse given input stream and returns DOM document object.
 * Performs evaluate XPath on parsed document.
 *
 * @author Dominik Harmim harmim6@gmail.com
 */
public class XmlParser
{
	/**
	 * DOM document builder.
	 */
	private DocumentBuilder documentBuilder;

	/**
	 * Parsed DOM document object.
	 */
	private Document document;

	/**
	 * XPath provides access to the XPath evaluation environment and expressions.
	 */
	private XPath xPath;


	/**
	 * Creates and initializes XML parser.
	 *
	 * @throws ParserConfigurationException if a DocumentBuilder cannot be created
	 */
	XmlParser() throws ParserConfigurationException
	{
		documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		xPath = XPathFactory.newInstance().newXPath();
	}


	/**
	 * Parses given input stream.
	 *
	 * @param inputStream input stream to be parsed
	 * @return parsed document
	 *
	 * @throws IOException if any IO errors occur
	 * @throws SAXException if any parse errors occur
	 */
	public Document parse(InputStream inputStream) throws IOException, SAXException
	{
		document = documentBuilder.parse(inputStream);

		return document;
	}


	/**
	 * Parses given file.
	 *
	 * @param file file to be parsed
	 * @return parsed document
	 *
	 * @throws IOException if any IO errors occur
	 * @throws SAXException if any parse errors occur
	 */
	public Document parse(File file) throws IOException, SAXException
	{
		document = documentBuilder.parse(file);

		return document;
	}


	/**
	 * Evaluate XPath to parsed document.
	 *
	 * @param path XPath to be evaluated to parsed document
	 * @return value from evaluated path
	 *
	 * @throws XPathExpressionException if expression cannot be evaluated
	 */
	public String evaluatePath(String path) throws XPathExpressionException
	{
		return xPath.evaluate(path, document);
	}
}
