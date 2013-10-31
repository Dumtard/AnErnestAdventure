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

  //TODO Remove these textures and sprites
  private Texture texture;
  private Sprite sprite;

  public Player() {
    position = new Vector2(0, 500);
    velocity = new Vector2(0, 0);

    /*texture = new Texture(Gdx.files.internal("data/Ernest.png"));
    texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

    TextureRegion region = new TextureRegion(texture, 0, 0, 32, 64);

    sprite = new Sprite(region);
    sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    sprite.setPosition(position.x, position.y);*/
  }
  
  public Player(String n) {
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
    //texture.dispose();
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

  //TODO Remove these functions to appropriate classes
  public void draw(SpriteBatch batch) {
    sprite.setPosition(position.x, position.y);
    sprite.draw(batch);
  }
}
