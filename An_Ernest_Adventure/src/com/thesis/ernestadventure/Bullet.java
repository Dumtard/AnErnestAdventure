package com.thesis.ernestadventure;

import com.badlogic.gdx.math.Vector2;

public class Bullet {
  
  public Vector2 velocity;
  public Vector2 position;

  private int screenX;
  private int screenY;
  
  public Bullet() {
    velocity = new Vector2(0, 0);
    position = new Vector2(0, 0);
  }
  
  public Bullet(int x, int y) {
    this.screenX = x;
    this.screenY = y;
    velocity = new Vector2(0, 0);
    position = new Vector2(0, 0);
  }
  
  public boolean hasVelocity() {
    return ((velocity.x != 0) && (velocity.y != 0));
  }
  
  public int getScreenX() {
    return this.screenX;
  }
  
  public int getScreenY() {
    return this.screenY;
  }
  
  public void setScreenX(int x) {
    this.screenX = x;
  }
  
  public void setScreenY(int y) {
    this.screenY = y;
  }
  
  public void setX(float x) {
    position.x = x;
  }
  
  public void setY(float y) {
    position.y = y;
  }
  
}
