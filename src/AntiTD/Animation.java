package AntiTD;

import AntiTD.tiles.Tile;
import AntiTD.tiles.TowerTile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Rallmo on 2015-12-04.
 */
public class Animation {
  Tile[][] currentMap;
  int[] pixels;
  private int w;
    private int h;


  public Animation(Tile[][] map){

    this.currentMap = map;

  }

  public int[] pixelColor(int w, int h){
    this.w = w;
      this.h = h;
    pixels = new int[w*h];
    return pixels;
  }

  public Tile[][] readImageTower(String string, int w, int h, int sx, int sy){
    try{
      BufferedImage image = ImageIO.read(new File(string));
      int xTiles = (image.getWidth() - sx) / w;
      int yTiles = (image.getHeight()-sy)/h;

      Tile[][] result = new Tile[xTiles][yTiles];
      for(int x = 0; x< xTiles; x++){
        for(int y = 0; y< yTiles; y++){
          result[x][y] = new TowerTile(new Position(w,h));
          image.getRGB(sx+x*w,sy+y*h,w,h,pixelColor(result[x][y].getPosition().getX(),result[x][y].getPosition().getY()),0,w);
        }

      }
      return result;
    }catch (IOException e){
      e.printStackTrace();
    }
    return null;

  }
}
