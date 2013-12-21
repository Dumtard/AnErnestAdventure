package com.thesis.ernestadventure;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;

public class UIController {
  private UI ui;
  private Client client;
  private HashMap<String, Player> players;
  
  public UIController(UI ui, Client client, HashMap<String, Player> players) {
    this.ui = ui;
    this.client = client;
    this.players = players;
  }
  
  public void update(float delta) {
    ui.toString();
    client.toString();
    players.toString();
  }
  
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    // Set Position of Slider
    if (screenX < (Gdx.graphics.getWidth() / 4)+10 &&
        screenX > 0) {
      ui.slider.setPosition(((screenX/((Gdx.graphics.getWidth() / 4.0f)+10))*2)-1);
    }
    return false;
  }
  
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    // Set Position of Slider
    ui.slider.setPosition(0);
    return false;
  }
  
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    // Set Position of Slider
    if (screenX < (Gdx.graphics.getWidth() / 4)+10 &&
        screenX > 0) {
      ui.slider.setPosition(((screenX/((Gdx.graphics.getWidth() / 4.0f)+10))*2)-1);
    }
    return false;
  }
}
