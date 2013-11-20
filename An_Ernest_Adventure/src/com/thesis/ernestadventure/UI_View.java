package com.thesis.ernestadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class UI_View {
  SpriteBatch batch;

  TextureAtlas UI_Atlas;
  TextureRegion UI_Background; 
  
  public UI_View (SpriteBatch batch) {
    this.batch = batch;
    
    UI_Atlas = new TextureAtlas(Gdx.files.internal("UI/UI.pack"));
    UI_Background = UI_Atlas.createSprite("UI_background");
  }
  
  public void render(float x, float y, float w, float h) {
    batch.draw(UI_Background, x, y, w, h);
  }
}
