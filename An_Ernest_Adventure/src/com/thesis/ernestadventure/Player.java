package com.thesis.ernestadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player {
  private Vector2 position;
  private Vector2 velocity;

  /* Temp */
  private Texture texture;
  private Sprite sprite;

  Player() {
    position = new Vector2(0, 500);
    velocity = new Vector2(0, 0);

    texture = new Texture(Gdx.files.internal("data/Ernest.png"));
    texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

    TextureRegion region = new TextureRegion(texture, 0, 0, 32, 64);

    sprite = new Sprite(region);
    sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    sprite.setPosition(position.x, position.y);
  }

  public void dispose() {
    texture.dispose();
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

  /* Temp */
  public void update() {
    setVelocity(getVelocity().x, getVelocity().y - (float) 0.1);
    position.x += velocity.x;
    position.y += velocity.y;
    sprite.setPosition(position.x, position.y);

    if (position.y <= 96) {
      position.y = 96;
      velocity.y = 0;
    }
  }

  public void draw(SpriteBatch batch) {
    sprite.draw(batch);
  }
}
