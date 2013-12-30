package com.thesis.ernestadventure;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
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

  public void update(float delta) {
    for (Map.Entry<String, Player> player : players.entrySet()) {
      // Apply Gravity
      player.getValue().setVelocity(player.getValue().getVelocity().x, player.getValue().getVelocity().y - GRAVITY);

      
      Rectangle playerRect = new Rectangle(player.getValue().getPosition().x, player.getValue().getPosition().y,
                                           player.getValue().getWidth()-2, player.getValue().getHeight()-2);
      
      // Collision
      playerRect.x += player.getValue().getVelocity().x;
      
      int tilePositionX = (int) (playerRect.x / Tile.SIZE);
      int tilePositionY = (int) (playerRect.y / Tile.SIZE);
      
      // Right
      if (playerRect.x > player.getValue().getPosition().x) {
        
        if (area.tiles[tilePositionX+1][tilePositionY].collidable) {
          Rectangle blockRect = new Rectangle(area.tiles[tilePositionX+1][tilePositionY].x,
                                              area.tiles[tilePositionX+1][tilePositionY].y,
                                              Tile.SIZE,
                                              Tile.SIZE);
          
          if (playerRect.overlaps(blockRect)) {
            player.getValue().setPosition(blockRect.x-Tile.SIZE, playerRect.y);
            if (player.getValue().getVelocity().y < 0) {
              player.getValue().setVelocity(0, player.getValue().getVelocity().y);
            }
          }
        } else if (area.tiles[tilePositionX+1][tilePositionY+1].collidable) {
          Rectangle blockRect = new Rectangle(area.tiles[tilePositionX+1][tilePositionY+1].x,
                                              area.tiles[tilePositionX+1][tilePositionY+1].y,
                                              Tile.SIZE,
                                              Tile.SIZE);

          if (playerRect.overlaps(blockRect)) {
            player.getValue().setPosition(blockRect.x-Tile.SIZE, playerRect.y);
            if (player.getValue().getVelocity().y < 0) {
              player.getValue().setVelocity(0, player.getValue().getVelocity().y);
            }
          }
        } else if (area.tiles[tilePositionX+1][tilePositionY+2].collidable) {
          Rectangle blockRect = new Rectangle(area.tiles[tilePositionX+1][tilePositionY+2].x,
                                              area.tiles[tilePositionX+1][tilePositionY+2].y,
                                              Tile.SIZE,
                                              Tile.SIZE);

          if (playerRect.overlaps(blockRect)) {
            player.getValue().setPosition(blockRect.x-Tile.SIZE, playerRect.y);
            if (player.getValue().getVelocity().y < 0) {
              player.getValue().setVelocity(0, player.getValue().getVelocity().y);
            }
          }
        
        // New Level
        } if (area.tiles[tilePositionX+1][tilePositionY].exit) {
          try {
            area.loadArea(2);
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          player.getValue().setPosition(area.getStart());
          break;
        }

      // Left
        
      } else if (playerRect.x < player.getValue().getPosition().x) {
        if (area.tiles[tilePositionX][tilePositionY].collidable) {
          Rectangle blockRect = new Rectangle(area.tiles[tilePositionX][tilePositionY].x,
                                              area.tiles[tilePositionX][tilePositionY].y,
                                              Tile.SIZE,
                                              Tile.SIZE);
          
          if (playerRect.overlaps(blockRect)) {
            player.getValue().setPosition(blockRect.x+Tile.SIZE, playerRect.y);
            if (player.getValue().getVelocity().y < 0) {
              player.getValue().setVelocity(0, player.getValue().getVelocity().y);
            }
          }
        } else if (area.tiles[tilePositionX][tilePositionY+1].collidable) {
          Rectangle blockRect = new Rectangle(area.tiles[tilePositionX][tilePositionY+1].x,
                                              area.tiles[tilePositionX][tilePositionY+1].y,
                                              Tile.SIZE,
                                              Tile.SIZE);

          if (playerRect.overlaps(blockRect)) {
            player.getValue().setPosition(blockRect.x+Tile.SIZE, playerRect.y);
            if (player.getValue().getVelocity().y < 0) {
              player.getValue().setVelocity(0, player.getValue().getVelocity().y);
            }
          }
        } else if (area.tiles[tilePositionX][tilePositionY+2].collidable) {
          Rectangle blockRect = new Rectangle(area.tiles[tilePositionX][tilePositionY+2].x,
                                              area.tiles[tilePositionX][tilePositionY+2].y,
                                              Tile.SIZE,
                                              Tile.SIZE);

          if (playerRect.overlaps(blockRect)) {
            player.getValue().setPosition(blockRect.x+Tile.SIZE, playerRect.y);
            if (player.getValue().getVelocity().y < 0) {
              player.getValue().setVelocity(0, player.getValue().getVelocity().y);
            }
          }
        }
      }

      playerRect.x = player.getValue().getPosition().x;
      playerRect.y += player.getValue().getVelocity().y;
      
      tilePositionX = (int) (playerRect.x / Tile.SIZE);
      tilePositionY = (int) (playerRect.y / Tile.SIZE);
      
      // Bottom
      if (playerRect.y < player.getValue().getPosition().y) {
        if (area.tiles[tilePositionX][tilePositionY].collidable) {
          Rectangle blockRect = new Rectangle(area.tiles[tilePositionX][tilePositionY].x,
                                              area.tiles[tilePositionX][tilePositionY].y,
                                              Tile.SIZE,
                                              Tile.SIZE);
          
          if (playerRect.overlaps(blockRect)) {
            player.getValue().setPosition(playerRect.x, blockRect.y+Tile.SIZE);
            player.getValue().setVelocity(player.getValue().getVelocity().x, 0);
            player.getValue().setIsGrounded(true);
          }
        } else if (area.tiles[tilePositionX+1][tilePositionY].collidable) {
          Rectangle blockRect = new Rectangle(area.tiles[tilePositionX+1][tilePositionY].x,
                                              area.tiles[tilePositionX+1][tilePositionY].y,
                                              Tile.SIZE,
                                              Tile.SIZE);

          if (playerRect.overlaps(blockRect)) {
            player.getValue().setPosition(playerRect.x, blockRect.y+Tile.SIZE);
            player.getValue().setVelocity(player.getValue().getVelocity().x, 0);
            player.getValue().setIsGrounded(true);
          }
        }

      // Top
      } else if (playerRect.y > player.getValue().getPosition().y) {
        if (area.tiles[tilePositionX][tilePositionY+2].collidable) {
          Rectangle blockRect = new Rectangle(area.tiles[tilePositionX][tilePositionY+2].x,
                                              area.tiles[tilePositionX][tilePositionY+2].y,
                                              Tile.SIZE,
                                              Tile.SIZE);
          
          if (playerRect.overlaps(blockRect)) {
            player.getValue().setPosition(playerRect.x, blockRect.y-playerRect.height);
            player.getValue().setVelocity(player.getValue().getVelocity().x, 0);
          }
        } else if (area.tiles[tilePositionX+1][tilePositionY+2].collidable) {
          Rectangle blockRect = new Rectangle(area.tiles[tilePositionX+1][tilePositionY+2].x,
                                              area.tiles[tilePositionX+1][tilePositionY+2].y,
                                              Tile.SIZE,
                                              Tile.SIZE);

          if (playerRect.overlaps(blockRect)) {
            player.getValue().setPosition(playerRect.x, blockRect.y-playerRect.height);
            player.getValue().setVelocity(player.getValue().getVelocity().x, 0);
           }
        } 
      }
      playerRect.y = player.getValue().getPosition().y;

      // Update Player location
      player.getValue().setPosition(player.getValue().getPosition().x + (player.getValue().getVelocity().x * delta * Tile.SIZE),
                                    player.getValue().getPosition().y + (player.getValue().getVelocity().y * delta * Tile.SIZE));
      
      
      // Bullets
      Iterator<Bullet> i = player.getValue().bullets.iterator();
      while (i.hasNext()) {
        Bullet bullet = i.next();
        bullet.position.add(bullet.velocity);
        
        if (bullet.position.x > (area.width*32) ||
            bullet.position.y > (area.height*32) ||
            bullet.position.x < 0 ||
            bullet.position.y < 0) {
          i.remove();
          Gdx.app.log("Remove", "Removed");
        }
      }
      for (Bullet bullet : player.getValue().bullets) {
        bullet.position.add(bullet.velocity);
        
      }
      if (player.getValue().bullets.size() > 50) {
        player.getValue().bullets.remove(0);
      }
    }
  }

  public void keyDown(int keycode) {
//    Gdx.app.log("Key", "" + Gdx.input.isKeyPressed(21));
    
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
//     Gdx.app.log("Key", "" + Gdx.input.isKeyPressed(21));

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
  
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    players.get(ErnestGame.loginName).shoot(screenX, screenY);
    return false;
  }
  
  
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//    Gdx.app.log("TouchUp", "Game Controller");
    return false;
  }
  
  public boolean touchDragged(int screenX, int screenY, int pointer) {
//    Gdx.app.log("TouchDragged", "Game Controller");
    return false;
  }
}
