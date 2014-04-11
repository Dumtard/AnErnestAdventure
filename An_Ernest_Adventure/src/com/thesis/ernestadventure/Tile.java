package com.thesis.ernestadventure;

public class Tile {

  public static final int SIZE = 32;
  
  public Integer id;
  public Integer foregroundid;
  public Integer x, y;
  public Integer exit;
  public Boolean collidable;
  
  public Tile() {}
  
  public Tile(int id, int x, int y) {
    this.id = id;
    this.foregroundid = (int)('.');
    this.x = x;
    this.y = y;
    this.collidable = true;
    this.exit = -1;
  }
}