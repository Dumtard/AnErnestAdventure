package com.thesis.ernestadventure;

import java.io.BufferedReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Area {
  String tileset;
  Tile[][] tiles;
  
  public Area() throws IOException {
    FileHandle handle;
    handle = Gdx.files.internal("areas/area1");    
    BufferedReader reader = handle.reader(65535);

    String line;
    int width = 0, height = 0;
    while ((line = reader.readLine()) != null) {
      
      String[] lineSplit;
      if ((lineSplit = line.split("\\s+")).length > 1) {
        if (lineSplit[0].equals("Tileset:")) {
          tileset = lineSplit[1];
        } else if (lineSplit[0].equals("Width:")) {
          width = Integer.parseInt(lineSplit[1]);
        } else if (lineSplit[0].equals("Height:")) {
          height = Integer.parseInt(lineSplit[1]);
        } 
        
      } else {
        tiles = new Tile[width][height];
        
        
        
        if (line.equals("Back:")) {
          line = reader.readLine();
          
          for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
//              tiles[j][i] = new Tile(line.charAt(j), j*Tile.SIZE, (GameScreen.GAMEHEIGHT-Tile.SIZE)-(i*Tile.SIZE));
            }
            
            line = reader.readLine();
          }
        }
        if (line.equals("Middle:")) {
          line = reader.readLine();
          
          for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
              tiles[j][i] = new Tile(line.charAt(j), j*Tile.SIZE, (GameScreen.GAMEHEIGHT-Tile.SIZE)-(i*Tile.SIZE));
            }
            line = reader.readLine();
          }
        }
        if (line.equals("Front:")) {
          line = reader.readLine();
          
          for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
//              tiles[j][i] = new Tile(line.charAt(j), j*Tile.SIZE, (GameScreen.GAMEHEIGHT-Tile.SIZE)-(i*Tile.SIZE));
            }
            line = reader.readLine();
          }
        }
        if (line.equals("Collision:")) {
          line = reader.readLine();
          
          for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
              if (line.charAt(j) == '#') {
                tiles[j][i].collidable = true;
              } else {
                tiles[j][i].collidable = false;
              }
            }
            line = reader.readLine();
          }
        }
      }
    }
  }
  
}
