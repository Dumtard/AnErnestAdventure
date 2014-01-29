package com.thesis.ernestadventure;

import com.badlogic.gdx.math.Vector2;

public class BomberEnemy extends Enemy {
  public Vector2 bullet = new Vector2(-1, -1);
  public boolean attacking = false;
  public float bulletVelocity = -4;
  
  public BomberEnemy() {
    position = new Vector2(100, 300);
    velocity = new Vector2(40, 0);
    width = 64;
    height = 32;
    isGrounded = false;
    isFacingRight = true;
  }
  
  public BomberEnemy(Vector2 position) {
    position = new Vector2(position);
    velocity = new Vector2(40, 0);
    width = 64;
    height = 32;
    isGrounded = false;
    isFacingRight = true;
  }
  
  @Override
  public void attack() {
    attacking = true;
  }
}
