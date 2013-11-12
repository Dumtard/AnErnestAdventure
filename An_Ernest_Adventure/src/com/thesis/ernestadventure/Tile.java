package com.thesis.ernestadventure;

public class Tile {

  public static final int SIZE = 32;
  
  int id;
  int x, y;
  boolean collidable;
  
  public Tile(int id, int x, int y) {
    this.id = id;
    this.x = x;
    this.y = y;
    this.collidable = true;
  }
}