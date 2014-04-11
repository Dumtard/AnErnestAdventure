package com.thesis.ernestadventure;

import com.badlogic.gdx.math.Vector2;

public class BomberEnemy extends Enemy {
  public Vector2 bullet = new Vector2(-1, -1);
  public boolean attacking = false;
  public float bulletVelocity = -4;
  
  public BomberEnemy() {
    this.position = new Vector2(100, 300);
    this.velocity = new Vector2(40, 0);
    this.width = 64;
    this.height = 32;
    this.isGrounded = false;
    this.isFacingRight = true;
  }
  
  public BomberEnemy(Vector2 position) {
    this.position = new Vector2(position);
    this.velocity = new Vector2(40, 0);
    this.width = 64;
    this.height = 32;
    this.isGrounded = false;
    this.isFacingRight = true;
  }
  
  @Override
  public void attack() {
    attacking = true;
  }
}
