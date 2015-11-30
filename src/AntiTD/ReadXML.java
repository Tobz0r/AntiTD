package AntiTD;

/**
 * Created by dv13tes on 2015-11-30.
 */
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import AntiTD.tiles.*;

import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class ReadXML {

    private File xmlMap;

    private int row;
    private int column;

    private ArrayList<Level> levels;
    private Level level;


    public ReadXML(File xmlMap) {
        this.xmlMap = xmlMap;
    }
    public ArrayList<> getLevels(){
        parseXML();
        return levels;
    }

    private void parseXML() {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = factory.newSAXParser();
            xmlHandler handler = new xmlHandler();
            saxParser.parse(xmlMap, handler);
        } catch (ParserConfigurationException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (SAXException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private class xmlHandler extends DefaultHandler {

        private boolean isTile;

        //gör stuff för varje element
        @Override
        public void startElement(String uri, String localName,
                                 String qName, Attributes attributes) throws SAXException {
            if (qName.equals("row")) {
                //count rows?
                column = -1;
                row++;
                isTile = false;
            } else if (qName.equals("tile")) {
                //count col?
                column++;
                isTile = true;
            } else if (qName.equals("mapData")) {
                level=null;
                String mapName = attributes.getValue("name");
                int sizeX = Integer.parseInt(attributes.getValue("sizeX"));
                int sizeY = Integer.parseInt(attributes.getValue("sizeY"));
                int victoryPoints = Integer.parseInt(attributes.getValue("victory"));
                isTile = false;
                row = -1;
                column = -1;

            }
        }
        //gör stuff med varje element
        @Override
        public void characters(char ch[], int start, int length)
                throws SAXException {
            String element = new String(ch, start, length);
            // för testning
            if (isTile) {
                System.out.print(element.toString());
            }
        }
        //när end-tag hittas
        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            if (qName.equals("mapData")) {
                //Add level to arraylist?
            }
        }
    }
}

