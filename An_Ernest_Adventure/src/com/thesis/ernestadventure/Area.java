package com.thesis.ernestadventure;

import java.io.BufferedReader;
import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

public class Area {
  String tileset;
  Tile[][] tiles;
  int width = 0;
  int height = 0;
  
  Vector2 playerStart;
  
  public Area() throws IOException {
    loadArea(1);
  }
  
  public Vector2 getStart() {
    return playerStart;
  }

  public void loadArea(int areaNumber) throws IOException {
    FileHandle handle;
    handle = Gdx.files.internal("areas/area" + areaNumber);
    BufferedReader reader = handle.reader(65535);

    String line;
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
          
          for (int i = height-1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
              tiles[j][i] = new Tile(line.charAt(j), j*Tile.SIZE, (i*Tile.SIZE));
            }
            
            line = reader.readLine();
          }
        }
        if (line.equals("Middle:")) {
          line = reader.readLine();
          
          for (int i = height-1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
              if (line.charAt(j) == '#') {
                tiles[j][i].id = line.charAt(j);
              }
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
          
          for (int i = height-1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
              if (line.charAt(j) == '#') {
                tiles[j][i].collidable = true;
              } else if (line.charAt(j) == '@') {
                playerStart = new Vector2(j*32, i*32);
                tiles[j][i].collidable = false;
              } else if (line.charAt(j) == '$') {
                tiles[j][i].collidable = false;
                tiles[j][i].exit = true;
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
