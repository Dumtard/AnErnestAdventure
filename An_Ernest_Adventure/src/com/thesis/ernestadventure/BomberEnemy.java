package com.thesis.ernestadventure;

import com.badlogic.gdx.math.Vector2;

public class BomberEnemy extends Enemy {
  public Vector2 bullet;
  public boolean attacking = false;
  public float bulletVelocity = -1;
  
  public BomberEnemy() {
    position = new Vector2(100, 300);
    velocity = new Vector2(40, 0);
    width = 32;
    height = 64;
    isGrounded = false;
    isFacingRight = true;
  }
  
  public BomberEnemy(Vector2 position) {
    position = new Vector2(position);
    velocity = new Vector2(40, 0);
    width = 32;
    height = 64;
    isGrounded = false;
    isFacingRight = true;
  }
  
  @Override
  public void attack() {
    attacking = true;
  }
}
