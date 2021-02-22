package EDU.UCSD.Extension;
// https://howtodoinjava.com/java/xml/read-xml-dom-parser-example/
// Import DOM parser packages.

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.validation.Schema;
import java.io.*;

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

    public void useSAXtoParseXMLFile() {
        System.out.println("SAX is not yet implemented.");
    }

    public void useXPATHtoParseXMLFile() {
        System.out.println("XPATH is not yet implemented.");
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "filename='" + filename + '\'' +
                '}';
    }

    public static void main(String[] arguments) throws IOException, SAXException, ParserConfigurationException {
        Lesson4XML lesson4XML = new Lesson4XML("JobResult_UCSDExt.xml");
        System.out.println("\nResults of XML parsing using DOM parser:");
        lesson4XML.useDOMtoParseXMLFile();
        System.out.println();
        System.out.println("Results of XML parsing using SAX parser:");
        lesson4XML.useSAXtoParseXMLFile();
        System.out.println();
        System.out.println("Results of XML parsing using XPATH:");
        lesson4XML.useXPATHtoParseXMLFile();
        System.out.println("All done!");
    }
}
