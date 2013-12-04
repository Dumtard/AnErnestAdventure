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

  public GameController(Client client, HashMap<String, Player> players,
      Area area) {
    this.client = client;
    this.players = players;
    this.area = area;
  }

  // public void update(float delta) {
  // for (Map.Entry<String, Player> player: players.entrySet()) {
  //
  // //Apply Gravity
  // player.getValue().setVelocity(player.getValue().getVelocity().x,
  // player.getValue().getVelocity().y - GRAVITY);
  //
  // int tilePositionX = (int)(player.getValue().getPosition().x / Tile.SIZE);
  // int tilePositionY = (int)(player.getValue().getPosition().y / Tile.SIZE);
  //
  // //Collision
  // if (tilePositionX >= 0 && tilePositionX < area.width &&
  // tilePositionY >= 0 && tilePositionY < area.height) {
  //
  // player.getValue().setPosition(player.getValue().getPosition().x +
  // (player.getValue().getVelocity().x * delta * Tile.SIZE),
  // player.getValue().getPosition().y + (player.getValue().getVelocity().y *
  // delta * Tile.SIZE));
  //
  //
  // // player.getValue().setPosition(player.getValue().getPosition().x,
  // // player.getValue().getPosition().y + (player.getValue().getVelocity().y *
  // delta * Tile.SIZE));
  //
  // //Bottom
  // if (area.tiles[tilePositionX][tilePositionY].collidable) {
  // player.getValue().setPosition(player.getValue().getPosition().x,
  // ((tilePositionY+1)*Tile.SIZE));
  // player.getValue().setVelocity(player.getValue().getVelocity().x, 0);
  // player.getValue().setIsGrounded(true);
  //
  // //Top
  // } else if (area.tiles[tilePositionX][tilePositionY+2].collidable) {
  // player.getValue().setVelocity(player.getValue().getVelocity().x, 0);
  // player.getValue().setPosition(player.getValue().getPosition().x,
  // (tilePositionY * Tile.SIZE)-1);
  // }
  //
  // player.getValue().setPosition(player.getValue().getPosition().x +
  // (player.getValue().getVelocity().x * delta * Tile.SIZE),
  // player.getValue().getPosition().y);
  //
  // //Right
  // // if (area.tiles[tilePositionX+1][tilePositionY].collidable ||
  // // area.tiles[tilePositionX+1][tilePositionY+1].collidable) {
  // // player.getValue().setVelocity(0, player.getValue().getVelocity().y);
  // // player.getValue().setPosition(tilePositionX * Tile.SIZE,
  // player.getValue().getPosition().y);
  // //
  // // //Left
  // // } else if (area.tiles[tilePositionX][tilePositionY].collidable ||
  // // area.tiles[tilePositionX][tilePositionY+1].collidable) {
  // // player.getValue().setVelocity(0, player.getValue().getVelocity().y);
  // // player.getValue().setPosition(((tilePositionX + 1) * Tile.SIZE)+1,
  // player.getValue().getPosition().y);
  // // }
  //
  // //Update Player location
  // // player.getValue().setPosition(player.getValue().getPosition().x +
  // (player.getValue().getVelocity().x * delta * Tile.SIZE),
  // // player.getValue().getPosition().y + (player.getValue().getVelocity().y *
  // delta * Tile.SIZE));
  //
  // }
  // }
  // }

  public void update(float delta) {

    for (Map.Entry<String, Player> player : players.entrySet()) {
      // Apply Gravity
      player.getValue().setVelocity(player.getValue().getVelocity().x, player.getValue().getVelocity().y - GRAVITY);

      // Collision

      int tilePositionX = 0;
      int tilePositionY = 0;

      if (tilePositionX >= 0 && tilePositionX < area.width && tilePositionY >= 0 && tilePositionY < area.height) {

        tilePositionX = (int) ((player.getValue().getPosition().x + (player.getValue().getVelocity().x * delta * Tile.SIZE)) / Tile.SIZE);
        tilePositionY = (int) ((player.getValue().getPosition().y) / Tile.SIZE);
        
        // Update Player location
        player.getValue().setPosition(player.getValue().getPosition().x + (player.getValue().getVelocity().x * delta * Tile.SIZE),
                                      player.getValue().getPosition().y);

        // Right
        if (area.tiles[tilePositionX+1][tilePositionY].collidable ||
            area.tiles[tilePositionX+1][tilePositionY+1].collidable) {
          player.getValue().setPosition((tilePositionX * Tile.SIZE)-1, player.getValue().getPosition().y);
          player.getValue().setVelocity(0, player.getValue().getVelocity().y);
  
        // Left
        } else if (area.tiles[tilePositionX][tilePositionY].collidable ||
                   area.tiles[tilePositionX][tilePositionY+1].collidable) {
          player.getValue().setPosition(((tilePositionX+1) * Tile.SIZE)+1, player.getValue().getPosition().y);
          player.getValue().setVelocity(0, player.getValue().getVelocity().y);
        }

        tilePositionX = (int) ((player.getValue().getPosition().x) / Tile.SIZE);
        tilePositionY = (int) ((player.getValue().getPosition().y + (player.getValue().getVelocity().y * delta * Tile.SIZE)) / Tile.SIZE);
        
        
        // Update Player location
        player.getValue().setPosition(player.getValue().getPosition().x,
                                    player.getValue().getPosition().y + (player.getValue().getVelocity().y * delta * Tile.SIZE));

        // Bottom
        if (area.tiles[tilePositionX][tilePositionY].collidable ||
            area.tiles[tilePositionX+1][tilePositionY].collidable) {
          player.getValue().setPosition(player.getValue().getPosition().x, (tilePositionY+1) * Tile.SIZE);
          player.getValue().setVelocity(player.getValue().getVelocity().x, 0);
          player.getValue().setIsGrounded(true);
  
        // Top
        } else if (area.tiles[tilePositionX][tilePositionY+2].collidable ||
                   area.tiles[tilePositionX+1][tilePositionY+2].collidable) {
          player.getValue().setPosition(player.getValue().getPosition().x, tilePositionY * Tile.SIZE);
          player.getValue().setVelocity(player.getValue().getVelocity().x, 0);
        }
  

      }
    }
  }

  public void keyDown(int keycode) {
    // Left Arrow && Right Arrow
    if (Gdx.input.isKeyPressed(21) && Gdx.input.isKeyPressed(22)) {
      players.get(ErnestGame.loginName).setVelocity(
          new Vector2(0, players.get(ErnestGame.loginName).getVelocity().y));
      Stop stop = new Stop();
      stop.name = ErnestGame.loginName;
      stop.position = players.get(ErnestGame.loginName).getPosition();
      client.sendUDP(stop);

      // Left Arrow
    } else if (Gdx.input.isKeyPressed(21)) {
      players.get(ErnestGame.loginName)
          .setVelocity(
              new Vector2(-3.0f, players.get(ErnestGame.loginName)
                  .getVelocity().y));
      players.get(ErnestGame.loginName).setIsFacingRight(false);
      Move move = new Move();
      move.name = ErnestGame.loginName;
      move.velocity = players.get(ErnestGame.loginName).getVelocity();
      client.sendUDP(move);

      // Right Arrow
    } else if (Gdx.input.isKeyPressed(22)) {
      players.get(ErnestGame.loginName).setVelocity(
          new Vector2(3.0f, players.get(ErnestGame.loginName).getVelocity().y));
      players.get(ErnestGame.loginName).setIsFacingRight(true);
      Move move = new Move();
      move.name = ErnestGame.loginName;
      move.velocity = players.get(ErnestGame.loginName).getVelocity();
      client.sendUDP(move);
    }

    // Space Bar
    if (keycode == 62) {
      if (players.get(ErnestGame.loginName).getIsGrounded()) {
        players.get(ErnestGame.loginName)
            .setVelocity(
                new Vector2(players.get(ErnestGame.loginName).getVelocity().x,
                    8.5f));
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

    // Left Arrow && Right Arrow
    if (Gdx.input.isKeyPressed(21) && Gdx.input.isKeyPressed(22)) {
      players.get(ErnestGame.loginName).setVelocity(
          new Vector2(players.get(ErnestGame.loginName).getVelocity().x,
              players.get(ErnestGame.loginName).getVelocity().y));
      // Move move = new Move();
      // move.name = ErnestGame.loginName;
      // move.velocity = players.get(ErnestGame.loginName).getVelocity();
      // client.sendUDP(move);

      // Left Arrow
    } else if (Gdx.input.isKeyPressed(21)) {
      players.get(ErnestGame.loginName)
          .setVelocity(
              new Vector2(-3.0f, players.get(ErnestGame.loginName)
                  .getVelocity().y));
      players.get(ErnestGame.loginName).setIsFacingRight(false);
      Move move = new Move();
      move.name = ErnestGame.loginName;
      move.velocity = players.get(ErnestGame.loginName).getVelocity();
      client.sendUDP(move);

      // Right Arrow
    } else if (Gdx.input.isKeyPressed(22)) {
      players.get(ErnestGame.loginName).setVelocity(
          new Vector2(3.0f, players.get(ErnestGame.loginName).getVelocity().y));
      players.get(ErnestGame.loginName).setIsFacingRight(true);
      Move move = new Move();
      move.name = ErnestGame.loginName;
      move.velocity = players.get(ErnestGame.loginName).getVelocity();
      client.sendUDP(move);

    } else {
      players.get(ErnestGame.loginName).setVelocity(
          new Vector2(0, players.get(ErnestGame.loginName).getVelocity().y));
      Stop stop = new Stop();
      stop.name = ErnestGame.loginName;
      stop.position = players.get(ErnestGame.loginName).getPosition();
      client.sendUDP(stop);
    }
  }
}
