package AntiTD;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import AntiTD.tiles.*;

import javax.swing.JOptionPane;
import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author Tobias Estefors
 * Parses an xml-file containing all the levels and adds them to an arraylist
 */
public class ReadXML {

    private InputStream xmlMap;
    private InputStream path;

    private int row;
    private int column;

    private ArrayList<Level> levels;
    private Level level;
    private Tile[][] map;


    public ReadXML(File xmlMap) {
        this.path=getClass().getResourceAsStream(xmlMap.getPath());
        this.xmlMap = getClass().getResourceAsStream(xmlMap.getPath());
        levels=new ArrayList<Level>();
    }

    /**
     * Returns an arraylist containing all levels
     * @return arraylist of levels
     */
    public ArrayList<Level> getLevels(){
        try {
            validateXML();
        } catch (IOException e) {
            e.printStackTrace();
        }
        parseXML();
        return levels;
    }

    /**
     * Parses an XML-document with SAX parser
     */
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

        /**
     * Validates XML document against a XML schema
     * @throws IOException
     */
    private void validateXML() throws IOException{
        Source xmlFile=null;
        InputStream stream;
        try {
            URL schemaFile=new URL("http://www8.cs.umu.se/~dv13tes/mapFile.xsd");
            stream=path;
            xmlFile = new StreamSource(stream);
            SchemaFactory schemaFactory = SchemaFactory
                    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(xmlFile);
        } catch (SAXException e) {
            JOptionPane.showMessageDialog(null, xmlFile.getSystemId() + " is NOT valid");
            JOptionPane.showMessageDialog(null, "Reason: " + e.getLocalizedMessage());
            System.exit(-1);
        }
    }

    private class xmlHandler extends DefaultHandler {

        private boolean isTile;

        /**
         * Checks called every time a new element is found in xML
         * Checks the attrubyted and adds them to a new level.
         * @param uri Never used
         * @param localName Never used
         * @param qName the name of the element
         * @param attributes attributes if any in the element
         * @throws SAXException
         */
        @Override
        public void startElement(String uri, String localName,
                                 String qName, Attributes attributes) throws SAXException {
            if (qName.equals("row")) {
                column = -1;
                row++;
                isTile = false;
            } else if (qName.equals("tile")) {
                column++;
                isTile = true;
            } else if (qName.equals("mapData")) {
                level=null;
                map=null;
                String mapName = attributes.getValue("name");
                int sizeX = Integer.parseInt(attributes.getValue("sizeX"));
                int sizeY = Integer.parseInt(attributes.getValue("sizeY"));
                int victoryPoints = Integer.parseInt(attributes.getValue("victory"));
                int startingCredits = Integer.parseInt(attributes.getValue("startingCredits"));
                isTile = false;
                map=new Tile[sizeX][sizeY];
                level=new Level(mapName);
                level.setVictoryPoints(victoryPoints);
                level.setStartingCredits(startingCredits);
                row = -1;
                column = -1;

            }
        }

        /**
         *  Adds the tile from every node to a matrix
         * @param ch, start, length compinds to the name of the node
         * @throws SAXException
         */
        @Override
        public void characters(char ch[], int start, int length)
                throws SAXException {
            String element = new String(ch, start, length);
            if (isTile) {
                try {
                    if(element.equals("StartTile")){

                        level.setStartPosition(new Position(row,column));
                    }
                    element="AntiTD.tiles."+element;

                    Class<?> classFile = Class.forName(element);
                    Object tile = classFile.newInstance();
                    map[row][column]=(Tile) tile;
                    map[row][column].setPosition(new Position(row,column));
                } catch (InstantiationException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                } catch (ClassNotFoundException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                } catch (IllegalAccessException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());

                }

            }
        }

        /**
         * Gets called when a end tag"</>" is found
         * Only does something with mapData and adds the level to an arraylist
         * @param uri,qName,localName string that contain info of the node.
         *                            is never used in this method
         * @throws SAXException
         */
        @Override
        public void endElement(String uri, String localName, String qName)
                throws SAXException {
            if (qName.equals("mapData")) {
                level.addMap(map);
                levels.add(level);
            }
            isTile=false;
        }
    }
}

