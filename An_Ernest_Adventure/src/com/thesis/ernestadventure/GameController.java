package com.thesis.ernestadventure;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.thesis.ernestadventure.Network.Move;
import com.thesis.ernestadventure.Network.Stop;

public class GameController {
  private final float GRAVITY = 0.5f;
  
  private HashMap<String, Player> players;
  
  private Client client;
  
  private Area area;
  
  public GameController(Client client, HashMap<String, Player> players, Area area) {
    this.client = client;
    this.players = players;
    this.area = area;
  }
  
  public void update(float delta) {
    for (Map.Entry<String, Player> player: players.entrySet()) {

      //Apply Gravity
      player.getValue().setVelocity(player.getValue().getVelocity().x, player.getValue().getVelocity().y - GRAVITY);
      
      //Update Player location
      player.getValue().setPosition(player.getValue().getPosition().x + (player.getValue().getVelocity().x * delta * Tile.SIZE),
                                    player.getValue().getPosition().y + (player.getValue().getVelocity().y * delta * Tile.SIZE));
      
      int tilePositionTLX = (int)(player.getValue().getPosition().x / Tile.SIZE);
      int tilePositionTLY = (int)((player.getValue().getPosition().y + ((Tile.SIZE * 2) - 1)) / Tile.SIZE);
      
      int tilePositionTRX = (int)((player.getValue().getPosition().x + (Tile.SIZE - 1)) / Tile.SIZE);
      int tilePositionTRY = (int)((player.getValue().getPosition().y + ((Tile.SIZE*2) - 1)) / Tile.SIZE);
      
      int tilePositionMLX = (int)(player.getValue().getPosition().x / Tile.SIZE);
      int tilePositionMLY = (int)((player.getValue().getPosition().y + (Tile.SIZE - 1)) / Tile.SIZE);
      
      int tilePositionMRX = (int)((player.getValue().getPosition().x + (Tile.SIZE - 1)) / Tile.SIZE);
      int tilePositionMRY = (int)((player.getValue().getPosition().y + (Tile.SIZE - 1)) / Tile.SIZE);
      
      int tilePositionBLX = (int)(player.getValue().getPosition().x / Tile.SIZE);
      int tilePositionBLY = (int)(player.getValue().getPosition().y / Tile.SIZE);
      
      int tilePositionBRX = (int)((player.getValue().getPosition().x + (Tile.SIZE - 1)) / Tile.SIZE);
      int tilePositionBRY = (int)(player.getValue().getPosition().y / Tile.SIZE);


      
      //Collision
      if (tilePositionTLX >= 0 && tilePositionTRX < area.width &&
          tilePositionBLY >= 0 && tilePositionTLY < area.height) {
        
        //Bottom
        if (area.tiles[tilePositionBLX][tilePositionBLY].collidable ||
            area.tiles[tilePositionBRX][tilePositionBRY].collidable) {
          player.getValue().setPosition(player.getValue().getPosition().x, (tilePositionMLY * Tile.SIZE));
          player.getValue().setVelocity(player.getValue().getVelocity().x, 0);
          player.getValue().setIsGrounded(true);
  
        //Top
        } else if (area.tiles[tilePositionTLX][tilePositionTLY].collidable ||
                   area.tiles[tilePositionTRX][tilePositionTRY].collidable) {
          player.getValue().setPosition(player.getValue().getPosition().x, (tilePositionBLY * Tile.SIZE));
          player.getValue().setVelocity(player.getValue().getVelocity().x, 0);
        }
        
        tilePositionTLX = (int)(player.getValue().getPosition().x / Tile.SIZE);
        tilePositionTLY = (int)((player.getValue().getPosition().y + ((Tile.SIZE * 2) - 1)) / Tile.SIZE);
        
        tilePositionTRX = (int)((player.getValue().getPosition().x + (Tile.SIZE - 1)) / Tile.SIZE);
        tilePositionTRY = (int)((player.getValue().getPosition().y + ((Tile.SIZE*2) - 1)) / Tile.SIZE);
        
        tilePositionMLX = (int)(player.getValue().getPosition().x / Tile.SIZE);
        tilePositionMLY = (int)((player.getValue().getPosition().y + (Tile.SIZE - 1)) / Tile.SIZE);
        
        tilePositionMRX = (int)((player.getValue().getPosition().x + (Tile.SIZE - 1)) / Tile.SIZE);
        tilePositionMRY = (int)((player.getValue().getPosition().y + (Tile.SIZE - 1)) / Tile.SIZE);
        
        tilePositionBLX = (int)(player.getValue().getPosition().x / Tile.SIZE);
        tilePositionBLY = (int)(player.getValue().getPosition().y / Tile.SIZE);
        
        tilePositionBRX = (int)((player.getValue().getPosition().x + (Tile.SIZE - 1)) / Tile.SIZE);
        tilePositionBRY = (int)(player.getValue().getPosition().y / Tile.SIZE);
        
        //Right   
        if ((area.tiles[tilePositionTRX][tilePositionTRY].collidable) ||
            (area.tiles[tilePositionMRX][tilePositionMRY].collidable) ||
            (area.tiles[tilePositionBRX][tilePositionBRY].collidable)) {
          player.getValue().setPosition(tilePositionBLX * Tile.SIZE, player.getValue().getPosition().y);
          player.getValue().setVelocity(0, player.getValue().getVelocity().y);
        
        //Left
        } else if ((area.tiles[tilePositionTLX][tilePositionTLY].collidable) ||
                   (area.tiles[tilePositionMLX][tilePositionMLY].collidable) ||
                   (area.tiles[tilePositionBLX][tilePositionBLY].collidable)) {
          player.getValue().setPosition(tilePositionBRX * Tile.SIZE, player.getValue().getPosition().y);
          player.getValue().setVelocity(0, player.getValue().getVelocity().y);
        }
        

      }
    }
  }
  
  public void keyDown(int keycode) {
    //Left Arrow && Right Arrow
    if (Gdx.input.isKeyPressed(21) && Gdx.input.isKeyPressed(22)) {
      players.get(ErnestGame.loginName).setVelocity(new Vector2(0, players.get(ErnestGame.loginName).getVelocity().y));
      Stop stop = new Stop();
      stop.name = ErnestGame.loginName;
      stop.position = players.get(ErnestGame.loginName).getPosition();
      client.sendUDP(stop);
      
    //Left Arrow
    } else if (Gdx.input.isKeyPressed(21)) {
      players.get(ErnestGame.loginName).setVelocity(new Vector2(-3.0f, players.get(ErnestGame.loginName).getVelocity().y));
      players.get(ErnestGame.loginName).setIsFacingRight(false);
      Move move = new Move();
      move.name = ErnestGame.loginName;
      move.velocity = players.get(ErnestGame.loginName).getVelocity();
      client.sendUDP(move);
      
    //Right Arrow
    } else if (Gdx.input.isKeyPressed(22)) {
      players.get(ErnestGame.loginName).setVelocity(new Vector2(3.0f, players.get(ErnestGame.loginName).getVelocity().y));
      players.get(ErnestGame.loginName).setIsFacingRight(true);
      Move move = new Move();
      move.name = ErnestGame.loginName;
      move.velocity = players.get(ErnestGame.loginName).getVelocity();
      client.sendUDP(move);
    }  
      
    //Space Bar
    if (keycode == 62) {
      if (players.get(ErnestGame.loginName).getIsGrounded()) {
        players.get(ErnestGame.loginName).setVelocity(new Vector2(players.get(ErnestGame.loginName).getVelocity().x, 8.5f));
        players.get(ErnestGame.loginName).setIsGrounded(false);
        Move move = new Move();
        move.name = ErnestGame.loginName;
        move.velocity = players.get(ErnestGame.loginName).getVelocity();
        client.sendUDP(move);
      }
    }
  }
  
  public void keyUp(int keycode) {
    // Gdx.app.log("Key", "" + keycode);

    //Left Arrow && Right Arrow
    if (Gdx.input.isKeyPressed(21) && Gdx.input.isKeyPressed(22)) {
      players.get(ErnestGame.loginName).setVelocity(new Vector2(players.get(ErnestGame.loginName).getVelocity().x, players.get(ErnestGame.loginName).getVelocity().y));
//      Move move = new Move();
//      move.name = ErnestGame.loginName;
//      move.velocity = players.get(ErnestGame.loginName).getVelocity();
//      client.sendUDP(move);
      
    //Left Arrow
    } else if (Gdx.input.isKeyPressed(21)) {
      players.get(ErnestGame.loginName).setVelocity(new Vector2(-3.0f, players.get(ErnestGame.loginName).getVelocity().y));
      players.get(ErnestGame.loginName).setIsFacingRight(false);
      Move move = new Move();
      move.name = ErnestGame.loginName;
      move.velocity = players.get(ErnestGame.loginName).getVelocity();
      client.sendUDP(move);
      
    //Right Arrow
    } else if (Gdx.input.isKeyPressed(22)) {
      players.get(ErnestGame.loginName).setVelocity(new Vector2(3.0f, players.get(ErnestGame.loginName).getVelocity().y));
      players.get(ErnestGame.loginName).setIsFacingRight(true);
      Move move = new Move();
      move.name = ErnestGame.loginName;
      move.velocity = players.get(ErnestGame.loginName).getVelocity();
      client.sendUDP(move);
    
    } else {
      players.get(ErnestGame.loginName).setVelocity(new Vector2(0, players.get(ErnestGame.loginName).getVelocity().y));
      Stop stop = new Stop();
      stop.name = ErnestGame.loginName;
      stop.position = players.get(ErnestGame.loginName).getPosition();
      client.sendUDP(stop);
    }
  }
}
