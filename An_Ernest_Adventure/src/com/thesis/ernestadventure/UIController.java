package com.thesis.ernestadventure;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;

public class UIController {
  private UI ui;
  private Client client;
  private HashMap<String, Player> players;
  
  int movePointer;
  int jumpPointer;
  
  public UIController(UI ui, Client client, HashMap<String, Player> players) {
    this.ui = ui;
    this.client = client;
    this.players = players;
    
    movePointer = 0;
    jumpPointer = 0;
  }
  
  public void update(float delta) {
    if (ui.slider.getPosition() < 0) {
      players.get(ErnestGame.loginName).setIsFacingRight(false);
    } else if (ui.slider.getPosition() > 0) {
      players.get(ErnestGame.loginName).setIsFacingRight(true);
    }
    
    players.get(ErnestGame.loginName).setVelocity(ui.slider.getPosition() * 3.0f,
                                                  players.get(ErnestGame.loginName).getVelocity().y);
  }
  
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    // Set Position of Slider
    if (screenX < (Gdx.graphics.getWidth() / 4)+10 &&
        screenX > 0) {
      movePointer = pointer;
      
      ui.slider.setPosition(((screenX/((Gdx.graphics.getWidth() / 4.0f)+10))*2)-1);
    }
    
    if (screenX < ((Gdx.graphics.getWidth()/2) + 100) &&
        screenX > ((Gdx.graphics.getWidth()/2) - 100)) {
      jumpPointer = pointer;
      
      ui.jumpButton.setPressed(true);

      if (players.get(ErnestGame.loginName).getIsGrounded()) {
        players.get(ErnestGame.loginName)
            .setVelocity(
                new Vector2(players.get(ErnestGame.loginName).getVelocity().x,
                    8.5f));
        players.get(ErnestGame.loginName).setIsGrounded(false);
//          Move move = new Move();
//          move.name = ErnestGame.loginName;
//          move.velocity = players.get(ErnestGame.loginName).getVelocity();
//          client.sendUDP(move);
      }
    }
    return false;
  }
  
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    // Set Position of Slider
    if (pointer == movePointer) {
      ui.slider.setPosition(0);
    }
    
    // Set Jumped button to released
    if (pointer == jumpPointer) {
      ui.jumpButton.setPressed(false);
    }
    
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
