import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Iterator;
import java.util.Map;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;


/**
 * XML processor class which allows to create XML document with root element and append elements to root element.
 *
 * @author Dominik Harmim harmim6@gmail.com
 */
public class XmlProcessor
{
	/**
	 * File which is processing.
	 */
	private File file;

	/**
	 * XML output factory instance.
	 */
	private XMLOutputFactory xmlOutputFactory;

	/**
	 * XML parser instance.
	 */
	private XmlParser xmlParser;

	/**
	 * XML transformer instance.
	 */
	private Transformer transformer;


	/**
	 * Creates and initializes XML processor.
	 *
	 * @param file file which will be processing
	 *
	 * @throws ParserConfigurationException if a DocumentBuilder cannot be created
	 * @throws TransformerConfigurationException if a Transformer cannot be created
	 */
	XmlProcessor(File file) throws ParserConfigurationException, TransformerConfigurationException
	{
		this.file = file;
		xmlOutputFactory = XMLOutputFactory.newInstance();
		xmlParser = new XmlParser();
		transformer = TransformerFactory.newInstance().newTransformer();
	}


	/**
	 * Creates document with given root element.
	 *
	 * @param rootElementName root element name
	 *
	 * @throws XMLStreamException if XmlStreamWriter reports error
	 * @throws IOException in case there is problem with output file
	 */
	public void createXmlDocument(String rootElementName) throws XMLStreamException, IOException
	{
		PrintWriter printWriter = new PrintWriter(file);
		XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(printWriter);

		xmlStreamWriter.writeStartDocument("UTF-8", "1.0");
		xmlStreamWriter.writeStartElement(rootElementName);
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeEndDocument();

		printWriter.close();
	}


	/**
	 * Appends element to root element.
	 *
	 * @param name name of element to be appended
	 * @param attributes attributes of element to be appended
	 * @param subElements sub-elements of element to be appended
	 *
	 * @throws IOException in case there is problem with I/O file
	 * @throws SAXException if any parse errors occur
	 * @throws TransformerException if modified XML file couldn't be saved
	 */
	public void appendElementToRoot(String name, Map<String, String> attributes, Map<String, String> subElements)
		throws IOException, SAXException, TransformerException
	{
		Document document = xmlParser.parse(file);
		Element rootElement = document.getDocumentElement();
		Element element = (Element) rootElement.appendChild(document.createElement(name));

		Iterator attributesIterator = attributes.entrySet().iterator();
		while (attributesIterator.hasNext()) {
			Map.Entry<String, String> pair = (Map.Entry<String, String>) attributesIterator.next();
			element.setAttribute(pair.getKey(), pair.getValue());
			attributesIterator.remove();
		}

		Iterator subElementsIterator = subElements.entrySet().iterator();
		while (subElementsIterator.hasNext()) {
			Map.Entry<String, String> pair = (Map.Entry<String, String>) subElementsIterator.next();
			Element subElement = (Element) element.appendChild(document.createElement(pair.getKey()));
			subElement.setTextContent(pair.getValue());
			subElementsIterator.remove();
		}

		transformer.transform(new DOMSource(document), new StreamResult(file));
	}
}
