package AntiTD;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;


/**
 * Created by dv13tes on 2015-11-27.
 */
public class ReadXML {
    NodeList nodeList;

    private class Level {
        String row;

        public Level() {

        }
    }


    public ReadXML() throws IOException {

        try {

            File fXmlFile = new File("levels.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("row");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    System.out.println("row number : " + eElement.getAttribute("number"));
                    System.out.println("tiles : " + eElement.getElementsByTagName("tiles").item(0).getTextContent());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}