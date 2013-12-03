package com.thesis.ernestadventure;

import com.badlogic.gdx.math.Vector2;

public class Player {
  private Vector2 position;
  private Vector2 velocity;
  
  private boolean isGrounded;
  private boolean isFacingRight;

  public Player() {
    position = new Vector2(100, 300);
    velocity = new Vector2(0, 0);
    isGrounded = false;
    isFacingRight = true;
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
