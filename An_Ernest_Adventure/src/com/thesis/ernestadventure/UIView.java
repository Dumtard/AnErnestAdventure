package com.thesis.ernestadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class UIView {
  SpriteBatch batch;

  TextureAtlas UI_Atlas;
  TextureRegion UI_Background; 
  
  private OrthographicCamera camera;
  
  public UIView (UI ui, SpriteBatch batch, OrthographicCamera camera) {
    this.batch = batch;
    this.camera = camera;
    
    UI_Atlas = new TextureAtlas(Gdx.files.internal("UI/UI.pack"));
    UI_Background = UI_Atlas.createSprite("UI_background");
  }
  
  public void render(float delta) {
    batch.draw(UI_Background, camera.position.x - (camera.viewportWidth/2.0f),
                              camera.position.y - (camera.viewportHeight/2.0f),
                              camera.viewportWidth,
                              camera.viewportHeight*(3.0f/15.0f));
  }
}
