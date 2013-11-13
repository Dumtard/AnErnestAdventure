package com.thesis.ernestadventure;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.thesis.ernestadventure.Network.Move;
import com.thesis.ernestadventure.Network.Stop;

public class Controller implements InputProcessor {
  private final float GRAVITY = 0.5f;
  
  private HashMap<String, Player>  players;
  private Client client;

  private Area area;
  
  public Controller(Client client, HashMap<String, Player> players, Area area) {
    Gdx.input.setInputProcessor(this);
    this.players = players;
    this.client = client;
    this.area = area;
  }

  public void update(float delta) {
    
    //TODO Remove magic number '32'
    for (Map.Entry<String, Player> player: players.entrySet()) {
      //Apply Gravity
      player.getValue().setVelocity(player.getValue().getVelocity().x, player.getValue().getVelocity().y - GRAVITY);
      
      //Update Player location
      player.getValue().setPosition(player.getValue().getPosition().x + (player.getValue().getVelocity().x * delta * Tile.SIZE),
          player.getValue().getPosition().y + (player.getValue().getVelocity().y * delta * Tile.SIZE));
      
      //Collision
      int tilePositionX = (int)((player.getValue().getPosition().x + Tile.SIZE/2) / Tile.SIZE);
      int tilePositionY = (int)(player.getValue().getPosition().y / Tile.SIZE);
      if (tilePositionX >= 0 && tilePositionX < area.width &&
          tilePositionY >= 0 && tilePositionY < area.height) {
        if (area.tiles[tilePositionX][tilePositionY].collidable) {
          player.getValue().setPosition(player.getValue().getPosition().x, ((tilePositionY+1)*Tile.SIZE));
          player.getValue().setVelocity(player.getValue().getVelocity().x, 0);
        }
        if (area.tiles[(int)((player.getValue().getPosition().x + Tile.SIZE)/Tile.SIZE)][tilePositionY+1].collidable) {
          player.getValue().setPosition(tilePositionX*Tile.SIZE, player.getValue().getPosition().y);
          player.getValue().setVelocity(0, player.getValue().getVelocity().y);
        }
        if (area.tiles[(int)((player.getValue().getPosition().x)/Tile.SIZE)][tilePositionY+1].collidable) {
          player.getValue().setPosition((tilePositionX)*Tile.SIZE, player.getValue().getPosition().y);
          player.getValue().setVelocity(0, player.getValue().getVelocity().y);
        }
        if (area.tiles[tilePositionX][tilePositionY+2].collidable) {
          player.getValue().setPosition(player.getValue().getPosition().x, ((tilePositionY)*Tile.SIZE));
          player.getValue().setVelocity(player.getValue().getVelocity().x, 0);
        }
      }
      
//      if (player.getValue().getPosition().y <= 96) {
//        player.getValue().setPosition(player.getValue().getPosition().x, Tile.SIZE*3);
//        player.getValue().setVelocity(player.getValue().getVelocity().x, 0);
//      }
    }
  }
  
  @Override
  public boolean keyDown(int keycode) {
    // Gdx.app.log("Key", "" + keycode);

    //Left arrow
    if (keycode == 21) {
      players.get(ErnestGame.loginName).setVelocity(new Vector2(-3.0f, players.get(ErnestGame.loginName).getVelocity().y));
      Move move = new Move();
      move.name = ErnestGame.loginName;
      move.velocity = players.get(ErnestGame.loginName).getVelocity();
      client.sendUDP(move);
      
    //Right arrow
    } else if (keycode == 22) {
      players.get(ErnestGame.loginName).setVelocity(new Vector2(3.0f, players.get(ErnestGame.loginName).getVelocity().y));
      Move move = new Move();
      move.name = ErnestGame.loginName;
      move.velocity = players.get(ErnestGame.loginName).getVelocity();
      client.sendUDP(move);
      
    //Space Bar
    } else if (keycode == 62) {
      players.get(ErnestGame.loginName).setVelocity(new Vector2(players.get(ErnestGame.loginName).getVelocity().x, 8.5f));
      Move move = new Move();
      move.name = ErnestGame.loginName;
      move.velocity = players.get(ErnestGame.loginName).getVelocity();
      client.sendUDP(move);
    }

    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    // Gdx.app.log("Key", "" + keycode);

    //Left arrow
    if (keycode == 21) {
      players.get(ErnestGame.loginName).setVelocity(new Vector2(0, players.get(ErnestGame.loginName).getVelocity().y));
      Stop stop = new Stop();
      stop.name = ErnestGame.loginName;
      stop.position = players.get(ErnestGame.loginName).getPosition();
      client.sendUDP(stop);
      
    //Right arrow
    } else if (keycode == 22) {
      players.get(ErnestGame.loginName).setVelocity(new Vector2(0, players.get(ErnestGame.loginName).getVelocity().y));
      Stop stop = new Stop();
      stop.name = ErnestGame.loginName;
      stop.position = players.get(ErnestGame.loginName).getPosition();
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
      players.get(ErnestGame.loginName).setVelocity(new Vector2(-3.0f, players.get(ErnestGame.loginName).getVelocity().y));
      Move move = new Move();
      move.name = ErnestGame.loginName;
      move.velocity = players.get(ErnestGame.loginName).getVelocity();
      client.sendUDP(move);
    } else if (screenX >= 400 && screenY > 240) {
      players.get(ErnestGame.loginName).setVelocity(new Vector2(3.0f, players.get(ErnestGame.loginName).getVelocity().y));
      Move move = new Move();
      move.name = ErnestGame.loginName;
      move.velocity = players.get(ErnestGame.loginName).getVelocity();
      client.sendUDP(move);
    } else if (screenY < 240) {
      players.get(ErnestGame.loginName).setVelocity(new Vector2(players.get(ErnestGame.loginName).getVelocity().x, 8.5f));
      Move move = new Move();
      move.name = ErnestGame.loginName;
      move.velocity = players.get(ErnestGame.loginName).getVelocity();
      client.sendUDP(move);
    }

    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    if (screenX <= 400 && screenY > 240) {
      players.get(ErnestGame.loginName).setVelocity(new Vector2(0, players.get(ErnestGame.loginName).getVelocity().y));
      Stop stop = new Stop();
      stop.name = ErnestGame.loginName;
      stop.position = players.get(ErnestGame.loginName).getPosition();
      client.sendUDP(stop);
    } else if (screenX >= 400 && screenY > 240) {
      players.get(ErnestGame.loginName).setVelocity(new Vector2(0, players.get(ErnestGame.loginName).getVelocity().y));
      Stop stop = new Stop();
      stop.name = ErnestGame.loginName;
      stop.position = players.get(ErnestGame.loginName).getPosition();
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
