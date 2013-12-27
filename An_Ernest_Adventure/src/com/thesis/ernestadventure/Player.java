package com.thesis.ernestadventure;

import com.badlogic.gdx.math.Vector2;

public class Player {
  private Vector2 position;
  private Vector2 velocity;
  private int width;
  private int height;
  
  private boolean isGrounded;
  private boolean isFacingRight;

  public Player() {
    position = new Vector2(100, 300);
    velocity = new Vector2(0, 0);
    width = 32;
    height = 64;
    isGrounded = false;
    isFacingRight = true;
  }
  
  public Player(Vector2 position) {
    this.position = new Vector2(position);
    velocity = new Vector2(0, 0);
    width = 32;
    height = 64;
    isGrounded = false;
    isFacingRight = true;
  }

  public int getWidth() {
    return width;
  }
  
  public int getHeight() {
    return height;
  }
  
  public Vector2 getPosition() {
    return position;
  }

  public Vector2 getVelocity() {
    return velocity;
  }

  public void setPosition(float x, float y) {
    this.position.set(x, y);
  }

  public void setPosition(Vector2 position) {
    this.position.set(position);
  }

  public void setVelocity(float x, float y) {
    this.velocity.set(x, y);
  }

  public void setVelocity(Vector2 velocity) {
    this.velocity.set(velocity);
  }
  
  public void setIsGrounded(boolean isGrounded) {
    this.isGrounded = isGrounded;
  }
  
  public boolean getIsGrounded() {
    return this.isGrounded;
  }
  
  public void setIsFacingRight(boolean isFacingRight) {
    this.isFacingRight = isFacingRight;
  }
  
  public boolean getIsFacingRight() {
    return this.isFacingRight;
  }
}
