package com.thesis.ernestadventure;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.esotericsoftware.kryonet.Client;

public class Controller implements InputProcessor {
  private UIController uiController;
  private GameController gameController;
  
  public Controller(Client client, UI ui, HashMap<String, Player> players, Area area) {
    Gdx.input.setInputProcessor(this);
    
    uiController = new UIController(ui, client, players);
    gameController = new GameController(client, players, area);
  }

  public void update(float delta) {
    uiController.update(delta);
    gameController.update(delta);
  }
  
  @Override
  public boolean keyDown(int keycode) {
    gameController.keyDown(keycode);

    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    gameController.keyUp(keycode);

    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    if (screenY < (Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()*(3.0f/15.0f)))) {
      gameController.touchDown(screenX, screenY, pointer, button);
    } else {
      uiController.touchDown(screenX, screenY, pointer, button);
    }
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    gameController.touchUp(screenX, screenY, pointer, button);
    uiController.touchUp(screenX, screenY, pointer, button);
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    if (screenY < (Gdx.graphics.getHeight()-(Gdx.graphics.getHeight()*(3.0f/15.0f)))) {
      gameController.touchDragged(screenX, screenY, pointer);
    } else {
      uiController.touchDragged(screenX, screenY, pointer);
    }
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
