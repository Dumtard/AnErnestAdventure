package com.thesis.ernestadventure;

import com.badlogic.gdx.math.Vector2;

public class Player {
  private Vector2 position;
  private Vector2 velocity;

  public Player() {
    position = new Vector2(100, 300);
    velocity = new Vector2(0, 0);
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
}
