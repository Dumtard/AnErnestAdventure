package com.thesis.ernestadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StartScreen implements Screen, InputProcessor {
  ErnestGame game;
  
  OrthographicCamera camera;
  private SpriteBatch batch;
  
  private TextureRegion startImage;
  
  int width = Gdx.graphics.getWidth();
  int height = Gdx.graphics.getHeight();
  
  public StartScreen(ErnestGame game) {
    this.game = game;
    
    width = Gdx.graphics.getWidth();
    height = Gdx.graphics.getHeight();
    
    camera = new OrthographicCamera(1200, 720);
    camera.translate(1200 / 2, 720 / 2);

    batch = new SpriteBatch();
    
    Gdx.input.setInputProcessor(this);
    
    startImage = new TextureRegion(
        new Texture(Gdx.files.internal("UI/menu.png")), 
        0, 0, 640, 400);
  }
  
  @Override
  public void render(float delta) {
    camera.update();
    
    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    
    //draw start screen
    batch.draw(startImage, 0, 0, 1200, 720);

    batch.end();
    
  }

  @Override
  public void resize(int width, int height) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void show() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void hide() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void pause() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void resume() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void dispose() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public boolean keyDown(int keycode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//    System.out.println("(" + screenX + ", " + screenY + ")");
    if (screenX > 481 && screenX < 637 &&
        screenY > 472 && screenY < 551) {
      game.setScreen(new GameScreen(game));
    }
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean scrolled(int amount) {
    // TODO Auto-generated method stub
    return false;
  }

}
