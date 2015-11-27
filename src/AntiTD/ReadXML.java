package AntiTD;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by dv13tes on 2015-11-27.
 */
public class ReadXML {
    public ReadXML() throws IOException {
        NodeList nodeList;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inStream);
            Element data=doc.getDocumentElement();
            //Fixa så att tex inte flera textnoder finns efter varandra
            data.normalize();
            nodeList = doc.getElementsByTagName(TEA);
        } catch (ParserConfigurationException e) {
            throw new IOException("Unable to configure parser");
        } catch (SAXException e) {
            throw new IOException("Not correct format");
        }
    }
}
