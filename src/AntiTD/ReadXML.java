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
    private Tile[][] map;


    public ReadXML(File xmlMap) {
        this.xmlMap = xmlMap;
        levels=new ArrayList<Level>();
    }
    public ArrayList<Level> getLevels(){
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
        //gör stuff med varje element
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
        //när end-tag hittas
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

