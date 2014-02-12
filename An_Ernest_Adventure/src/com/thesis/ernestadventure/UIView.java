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
  
  TextureRegion UI_Jump_Up;
  TextureRegion UI_Jump_Down;
  
  TextureRegion UI_Heart;
  
  private Player player;
  
  private OrthographicCamera camera;
  
  public UIView (UI ui, SpriteBatch batch, OrthographicCamera camera, Player player) {
    this.batch = batch;
    this.camera = camera;
    this.ui = ui;
    this.player = player;
    
    UI_Atlas = new TextureAtlas(Gdx.files.internal("UI/packed/UI.pack"));
    UI_Background = UI_Atlas.createSprite("UI_background");
    UI_Slider = UI_Atlas.createSprite("Slide_Bar");
    UI_Slider_Selector = UI_Atlas.createSprite("Slide_Selector");
    UI_Jump_Up = UI_Atlas.createSprite("Jump_Up");
    UI_Jump_Down = UI_Atlas.createSprite("Jump_Down");
    UI_Heart = UI_Atlas.createSprite("Heart");
  }
  
  public void render(float delta) {
    // Background
    batch.draw(UI_Background, camera.position.x - (camera.viewportWidth/2.0f),
                              camera.position.y - (camera.viewportHeight/2.0f),
                              camera.viewportWidth,
                              camera.viewportHeight*(3.0f/15.0f));
    
    // Jump Button
    if (ui.jumpButton.getPressed()) {
      batch.draw(UI_Jump_Down, camera.position.x-50,
                              (camera.position.y - (camera.viewportHeight/2.0f))+30,
                              100, 33);
    } else {
      batch.draw(UI_Jump_Up, camera.position.x-50,
                            (camera.position.y - (camera.viewportHeight/2.0f))+30,
                            100, 33);
    }
    
    // Slider
    batch.draw(UI_Slider, (camera.position.x - (camera.viewportWidth/2.0f))+5,
                          (camera.position.y - (camera.viewportHeight/2.0f))+30,
                          200, 33);
    batch.draw(UI_Slider_Selector, (camera.position.x - (camera.viewportWidth/2.0f))+5 + ((ui.slider.getPosition()+1)*100)-4,
                                   (camera.position.y - (camera.viewportHeight/2.0f))+30,
                                   8, 33);
    
    // Health
    for (int i = 0; i < player.health; i++) {
      batch.draw(UI_Heart, camera.position.x + (camera.viewportWidth/2.0f) - 275 + 33*i,
          (camera.position.y - (camera.viewportHeight/2.0f))+30,
          32, 32);
    }
  }
}
