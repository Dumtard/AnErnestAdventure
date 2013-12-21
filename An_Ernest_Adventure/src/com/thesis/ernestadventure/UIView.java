package com.thesis.ernestadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class UIView {
  SpriteBatch batch;
  
  UI ui;

  TextureAtlas UI_Atlas;
  TextureRegion UI_Background; 
  TextureRegion UI_Slider;
  TextureRegion UI_Slider_Selector;
  
  private OrthographicCamera camera;
  
  public UIView (UI ui, SpriteBatch batch, OrthographicCamera camera) {
    this.batch = batch;
    this.camera = camera;
    this.ui = ui;
    
    UI_Atlas = new TextureAtlas(Gdx.files.internal("UI/packed/UI.pack"));
    UI_Background = UI_Atlas.createSprite("UI_background");
    UI_Slider = UI_Atlas.createSprite("Slide_Bar");
    UI_Slider_Selector = UI_Atlas.createSprite("Slide_Selector");
  }
  
  public void render(float delta) {
    batch.draw(UI_Background, camera.position.x - (camera.viewportWidth/2.0f),
                              camera.position.y - (camera.viewportHeight/2.0f),
                              camera.viewportWidth,
                              camera.viewportHeight*(3.0f/15.0f));
    batch.draw(UI_Slider, 5, 30, 200, 33);
    batch.draw(UI_Slider_Selector, 5 + ((ui.slider.getPosition()+1)*100)-4, 30, 8, 33);
  }
}
