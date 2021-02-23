package EDU.UCSD.Extension;

import org.w3c.dom.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;

class UserHandler extends DefaultHandler {

    boolean isJobResult = false;
    boolean isSerial = false;
    boolean isVisibleString = false;
    boolean isUnsigned = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase("serial")) {
            isSerial = true;
        } else if (qName.equalsIgnoreCase("visible-string")) {
            isVisibleString = true;
        } else if (qName.equalsIgnoreCase("unsigned")) {
            isUnsigned = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("jobresult")) {
            return;
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (isSerial) {
            System.out.println("serial: " + new String(ch, start, length));
            isSerial = false;
        } else if (isVisibleString) {
            System.out.println("visible-string: " + new String(ch, start, length));
            isVisibleString = false;
        } else if (isUnsigned) {
            System.out.println("unsigned: " + new String(ch, start, length));
            isUnsigned = false;
        }
    }
}

/**
 * The  Lesson4XML four XML program is invoked without arguments to parse a file
 * named JobResult_UCSDExt.xml, which contains the following XML structure:
 * <pre>
 * <jobresult>
 *     <serial>0000012345</serial>
 *     <data>
 *         <visible-string length="16">000000007b020000</visible-string>
 *     </data>
 *     <data>
 *         <structure count="2">
 *             <unsigned>255</unsigned>
 *             <double-long-unsigned>0</double-long-unsigned>
 *         </structure>
 *     </data>
 *     <data>
 *         <double-long-unsigned>1538752262</double-long-unsigned>
 *     </data>
 * </jobresult>
 * </pre>
 * <p>
 * Lesson4XML uses three methods to retrieve the text component of the "serial",
 * "visible-string"  and "unsigned"  elements of  the document.  The methods  of
 * retrieval  are  DOM, SAX  and XPATH.  A successful run  of the program should
 * generate something resembling the following:
 * <p>
 * Results of XML parsing using DOM parser:
 * serial: 0000012345
 * visible-string: 000000007b020000
 * unsigned: 255
 * <p>
 * Results of XML parsing using SAX parser:
 * serial: 0000012345
 * visible-string: 000000007b020000
 * unsigned: 255
 * <p>
 * Results of XML parsing using XPATH:
 * serial: 0000012345
 * visible-string: 000000007b020000
 * unsigned: 255
 * <p>
 * This  is my first exposure to the use of Java for manipulating XML documents.
 * Of  the  three methodologies  I  implemented,  I found  SAX  to  be the  most
 * challenging. XPATH seems to be the most versatile. Using XPATH, the following
 * two lines…
 * <pre>
 *         String expression = "//serial|//visible-string|//unsigned";
 *         NodeList nodeList =
 *         (NodeList)xpath.compile(expression).evaluate(document, XPathConstants.NODESET);
 * </pre>
 * … are all  that were required to generate a list of the targeted  nodes.
 */
public class Lesson4XML {
    private String filename;

    public Lesson4XML(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public void useDOMtoParseXMLFile() throws ParserConfigurationException, IOException, SAXException {
        // Create document builder.
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        // Create document object from XML file.
        Document document = builder.parse(new File(this.filename));
        //Normalize the XML Structure;
        document.getDocumentElement().normalize();
        // Extract the root element.
        Element root = document.getDocumentElement();
        NodeList nodeList = document.getElementsByTagName("jobresult");
        for (int index = 0; index < nodeList.getLength(); index++) {
            Node node = nodeList.item(index);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                System.out.println("serial: " + element.getElementsByTagName("serial").item(0).getTextContent());
                System.out.println("visible-string: " + element.getElementsByTagName("visible-string").item(0).getTextContent());
                System.out.println("unsigned: " + element.getElementsByTagName("unsigned").item(0).getTextContent());
            }
        }
    }

    /**
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public void useSAXtoParseXMLFile() throws ParserConfigurationException, SAXException {
        try {
            File inputFile = new File(this.filename);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserHandler userhandler = new UserHandler();
            saxParser.parse(inputFile, userhandler);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    /**
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @throws XPathExpressionException
     */
    public void useXPATHtoParseXMLFile() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        File inputFile = new File(this.filename);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // never forget this!
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputFile);

        //Create XPath
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();

        String expression = "//serial|//visible-string|//unsigned";
        NodeList nodeList = (NodeList) xpath.compile(expression).evaluate(document, XPathConstants.NODESET);

        for (int index = 0; index < nodeList.getLength(); index++) {
            System.out.printf("%s: %s%n",
                    nodeList.item(index).getNodeName(),
                    nodeList.item(index).getTextContent());
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "filename='" + filename + '\'' +
                '}';
    }

    public static void main(String[] arguments) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        Lesson4XML lesson4XML = new Lesson4XML("JobResult_UCSDExt.xml");
        System.out.println("\nResults of XML parsing using DOM parser:");
        lesson4XML.useDOMtoParseXMLFile();
        System.out.println();
        System.out.println("Results of XML parsing using SAX parser:");
        lesson4XML.useSAXtoParseXMLFile();
        System.out.println();
        System.out.println("Results of XML parsing using XPATH:");
        lesson4XML.useXPATHtoParseXMLFile();
        System.out.println("\nAll done!");
    }
}
