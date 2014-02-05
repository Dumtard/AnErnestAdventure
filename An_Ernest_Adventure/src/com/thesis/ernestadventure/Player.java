package com.thesis.ernestadventure;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class Player {
  public static int MAXBULLETS = 100;
  
  private Vector2 position;
  private Vector2 velocity;
  private int width;
  private int height;
  
  private boolean isGrounded;
  private boolean isFacingRight;
  
  public ArrayList<Bullet> bullets;
  
  public int health = 32;
  public long lastDamage = 0;

  public Player() {
    position = new Vector2(100, 300);
    velocity = new Vector2(0, 0);
    width = 32;
    height = 64;
    isGrounded = false;
    isFacingRight = true;
    
    bullets = new ArrayList<Bullet>();
  }
  
  public Player(Vector2 position) {
    this.position = new Vector2(position);
    velocity = new Vector2(0, 0);
    width = 32;
    height = 64;
    isGrounded = false;
    isFacingRight = true;
    
    bullets = new ArrayList<Bullet>();
  }

  public void shoot(int screenX, int screenY) {
    bullets.add(new Bullet(screenX, screenY));
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
