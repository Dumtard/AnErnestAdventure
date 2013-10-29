package com.thesis.ernestadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.thesis.ernestadventure.Network.Move;
import com.thesis.ernestadventure.Network.Stop;

public class Controller implements InputProcessor {
  private final float GRAVITY = 0.5f;
  
  private Player player;
  private Client client;

  public Controller(Client client, Player player) {
    Gdx.input.setInputProcessor(this);
    this.player = player;
    this.client = client;
  }

  public void update() {
    //Apply Gravity
    player.setVelocity(player.getVelocity().x, player.getVelocity().y - GRAVITY);
    
    //Update Player location
    player.setPosition(player.getPosition().x + player.getVelocity().x,
                       player.getPosition().y + player.getVelocity().y);

    //Collision
    if (player.getPosition().y <= 96) {
      player.setPosition(player.getPosition().x, 96);
      player.setVelocity(player.getVelocity().x, 0);
    }
  }
  
  @Override
  public boolean keyDown(int keycode) {
    // Gdx.app.log("Key", "" + keycode);

    //Left arrow
    if (keycode == 21) {
      player.setVelocity(new Vector2(-3.0f, player.getVelocity().y));
      Move move = new Move();
      move.name = Game.loginName;
      move.velocity = player.getVelocity();
      client.sendUDP(move);
      
    //Right arrow
    } else if (keycode == 22) {
      player.setVelocity(new Vector2(3.0f, player.getVelocity().y));
      Move move = new Move();
      move.name = Game.loginName;
      move.velocity = player.getVelocity();
      client.sendUDP(move);
      
    //Space Bar
    } else if (keycode == 62) {
      player.setVelocity(new Vector2(player.getVelocity().x, 8.5f));
      Move move = new Move();
      move.name = Game.loginName;
      move.velocity = player.getVelocity();
      client.sendUDP(move);
    }

    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    // Gdx.app.log("Key", "" + keycode);

    //Left arrow
    if (keycode == 21) {
      player.setVelocity(new Vector2(0, player.getVelocity().y));
      Stop stop = new Stop();
      stop.name = Game.loginName;
      stop.position = player.getPosition();
      client.sendUDP(stop);
      
    //Right arrow
    } else if (keycode == 22) {
      player.setVelocity(new Vector2(0, player.getVelocity().y));
      Stop stop = new Stop();
      stop.name = Game.loginName;
      stop.position = player.getPosition();
      client.sendUDP(stop);
    }

    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    // Gdx.app.log("Cursor" + pointer, screenX + ", " + screenY);
    if (screenX <= 400 && screenY > 240) {
      player.setVelocity(new Vector2(-3.0f, player.getVelocity().y));
      Move move = new Move();
      move.name = Game.loginName;
      move.velocity = player.getVelocity();
      client.sendUDP(move);
    } else if (screenX >= 400 && screenY > 240) {
      player.setVelocity(new Vector2(3.0f, player.getVelocity().y));
      Move move = new Move();
      move.name = Game.loginName;
      move.velocity = player.getVelocity();
      client.sendUDP(move);
    } else if (screenY < 240) {
      player.setVelocity(new Vector2(player.getVelocity().x, 8.5f));
      Move move = new Move();
      move.name = Game.loginName;
      move.velocity = player.getVelocity();
      client.sendUDP(move);
    }

    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    if (screenX <= 400 && screenY > 240) {
      player.setVelocity(new Vector2(0, player.getVelocity().y));
      Stop stop = new Stop();
      stop.name = Game.loginName;
      stop.position = player.getPosition();
      client.sendUDP(stop);
    } else if (screenX >= 400 && screenY > 240) {
      player.setVelocity(new Vector2(0, player.getVelocity().y));
      Stop stop = new Stop();
      stop.name = Game.loginName;
      stop.position = player.getPosition();
      client.sendUDP(stop);
    }
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
