package com.thesis.ernestadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class View {
  private int width;
  private int height;
  
  private OrthographicCamera camera;
  private SpriteBatch batch;
  
  private Player player;
  
  //TODO Remove these textures and sprites
  private Texture texture;
  private Sprite sprite;
  
  public View(Player player) {
    width = Gdx.graphics.getWidth();
    height = Gdx.graphics.getHeight();
    
    batch = new SpriteBatch();
    
    this.player = player;
    
    camera = new OrthographicCamera(width, height);
    camera.translate(width / 2, height / 2);
    
    texture = new Texture(Gdx.files.internal("data/libgdx.png"));
    texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

    TextureRegion region = new TextureRegion(texture, 0, 0, 800, 96);

    sprite = new Sprite(region);
    sprite.setPosition(0, 0);
  }
  
  public void dispose() {
    batch.dispose();
  }
  
  public void render() {
    camera.update();
    
    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    player.draw(batch);
    sprite.draw(batch);
    batch.end();
  }
}
