package com.thesis.ernestadventure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.thesis.ernestadventure.Network.Move;
import com.thesis.ernestadventure.Network.Shoot;
import com.thesis.ernestadventure.Network.Stop;

public class GameController {
  private final float GRAVITY = 0.5f;

  private HashMap<String, Player> players;
  private ArrayList<Enemy> enemies;
  
  private EnemyController enemyController;

  private Client client;

  private Area area;

  public GameController(Client client, HashMap<String, Player> players,
      Area area, ArrayList<Enemy> enemies) {
    this.client = client;
    this.players = players;
    this.area = area;
    this.enemies = enemies;
    
    enemyController = new EnemyController(client, players, area);
  }

  public void update(float delta) {
    for (Map.Entry<String, Player> nameandplayer : players.entrySet()) {
      Player player = nameandplayer.getValue();
      playerCollision(player);

      // Update Player location
      player.setPosition(player.getPosition().x + (player.getVelocity().x * delta * Tile.SIZE),
                         player.getPosition().y + (player.getVelocity().y * delta * Tile.SIZE));
      
      bulletCollision(player);
    }
    
    for(Enemy enemy : enemies) {
      enemyController.update(enemy, delta);
    }
  }

  //TODO refactor collisions into function(tilepositionX, tilepositionY)
  private void playerCollision(Player player) {
    // Apply Gravity
    player.setVelocity(player.getVelocity().x, player.getVelocity().y - GRAVITY);

    
    Rectangle playerRect = new Rectangle(player.getPosition().x+1, player.getPosition().y,
                                         player.getWidth()-1, player.getHeight()-2);
    
    // Collision
    playerRect.x += player.getVelocity().x;
    
    int tilePositionX = (int) (playerRect.x / Tile.SIZE);
    int tilePositionY = (int) (playerRect.y / Tile.SIZE);
    
    // Right
    if (playerRect.x > player.getPosition().x) {
      
      if (area.tiles[tilePositionX+1][tilePositionY].collidable) {
        Rectangle blockRect = new Rectangle(area.tiles[tilePositionX+1][tilePositionY].x,
                                            area.tiles[tilePositionX+1][tilePositionY].y,
                                            Tile.SIZE,
                                            Tile.SIZE);
        
        if (playerRect.overlaps(blockRect)) {
          player.setPosition(blockRect.x-Tile.SIZE, playerRect.y);
          if (player.getVelocity().y < 0) {
            player.setVelocity(0, player.getVelocity().y);
          }
        }
      } else if (area.tiles[tilePositionX+1][tilePositionY+1].collidable) {
        Rectangle blockRect = new Rectangle(area.tiles[tilePositionX+1][tilePositionY+1].x,
                                            area.tiles[tilePositionX+1][tilePositionY+1].y,
                                            Tile.SIZE,
                                            Tile.SIZE);

        if (playerRect.overlaps(blockRect)) {
          player.setPosition(blockRect.x-Tile.SIZE, playerRect.y);
          if (player.getVelocity().y < 0) {
            player.setVelocity(0, player.getVelocity().y);
          }
        }
      } else if (area.tiles[tilePositionX+1][tilePositionY+2].collidable) {
        Rectangle blockRect = new Rectangle(area.tiles[tilePositionX+1][tilePositionY+2].x,
                                            area.tiles[tilePositionX+1][tilePositionY+2].y,
                                            Tile.SIZE,
                                            Tile.SIZE);

        if (playerRect.overlaps(blockRect)) {
          player.setPosition(blockRect.x-Tile.SIZE, playerRect.y);
          if (player.getVelocity().y < 0) {
            player.setVelocity(0, player.getVelocity().y);
          }
        }
      
      // Next Area
      //TODO Make for ever side --It currently only enters levels where door is entered from the left
      } if (area.tiles[tilePositionX+1][tilePositionY].exit) {
        enemies.clear();
        try {
          area.loadArea(2);
          for (Vector2 position : area.enemyPositions) {
            Enemy e = new Enemy(position);
            enemies.add(e);
            //TODO enemy type
          }
          player.setPosition(area.getStart());
          player.bullets.clear();
        } catch (IOException e) {
          e.printStackTrace();
        }
        return;
      }

    // Left
    } else if (playerRect.x < player.getPosition().x) {
      if (area.tiles[tilePositionX][tilePositionY].collidable) {
        Rectangle blockRect = new Rectangle(area.tiles[tilePositionX][tilePositionY].x,
                                            area.tiles[tilePositionX][tilePositionY].y,
                                            Tile.SIZE,
                                            Tile.SIZE);
        
        if (playerRect.overlaps(blockRect)) {
          player.setPosition(blockRect.x+Tile.SIZE+2, playerRect.y);
          if (player.getVelocity().y < 0) {
            player.setVelocity(0, player.getVelocity().y);
          }
        }
      } else if (area.tiles[tilePositionX][tilePositionY+1].collidable) {
        Rectangle blockRect = new Rectangle(area.tiles[tilePositionX][tilePositionY+1].x,
                                            area.tiles[tilePositionX][tilePositionY+1].y,
                                            Tile.SIZE,
                                            Tile.SIZE);

        if (playerRect.overlaps(blockRect)) {
          player.setPosition(blockRect.x+Tile.SIZE+2, playerRect.y);
          if (player.getVelocity().y < 0) {
            player.setVelocity(0, player.getVelocity().y);
          }
        }
      } else if (area.tiles[tilePositionX][tilePositionY+2].collidable) {
        Rectangle blockRect = new Rectangle(area.tiles[tilePositionX][tilePositionY+2].x,
                                            area.tiles[tilePositionX][tilePositionY+2].y,
                                            Tile.SIZE,
                                            Tile.SIZE);

        if (playerRect.overlaps(blockRect)) {
          player.setPosition(blockRect.x+Tile.SIZE+2, playerRect.y);
          if (player.getVelocity().y < 0) {
            player.setVelocity(0, player.getVelocity().y);
          }
        }
      }
    }

    playerRect.x = player.getPosition().x;
    playerRect.y += player.getVelocity().y;
    
    tilePositionX = (int) (playerRect.x / Tile.SIZE);
    tilePositionY = (int) (playerRect.y / Tile.SIZE);
    
    // Bottom
    if (playerRect.y < player.getPosition().y) {
      if (area.tiles[tilePositionX][tilePositionY].collidable) {
        Rectangle blockRect = new Rectangle(area.tiles[tilePositionX][tilePositionY].x,
                                            area.tiles[tilePositionX][tilePositionY].y,
                                            Tile.SIZE,
                                            Tile.SIZE);
        
        if (playerRect.overlaps(blockRect)) {
          player.setPosition(playerRect.x, blockRect.y+Tile.SIZE);
          player.setVelocity(player.getVelocity().x, 0);
          player.setIsGrounded(true);
        }
      } else if (area.tiles[tilePositionX+1][tilePositionY].collidable) {
        Rectangle blockRect = new Rectangle(area.tiles[tilePositionX+1][tilePositionY].x,
                                            area.tiles[tilePositionX+1][tilePositionY].y,
                                            Tile.SIZE,
                                            Tile.SIZE);

        if (playerRect.overlaps(blockRect)) {
          player.setPosition(playerRect.x, blockRect.y+Tile.SIZE);
          player.setVelocity(player.getVelocity().x, 0);
          player.setIsGrounded(true);
        }
      }

    // Top
    } else if (playerRect.y > player.getPosition().y) {
      if (area.tiles[tilePositionX][tilePositionY+2].collidable) {
        Rectangle blockRect = new Rectangle(area.tiles[tilePositionX][tilePositionY+2].x,
                                            area.tiles[tilePositionX][tilePositionY+2].y,
                                            Tile.SIZE,
                                            Tile.SIZE);
        
        if (playerRect.overlaps(blockRect)) {
          player.setPosition(playerRect.x, blockRect.y-playerRect.height);
          player.setVelocity(player.getVelocity().x, 0);
        }
      } else if (area.tiles[tilePositionX+1][tilePositionY+2].collidable) {
        Rectangle blockRect = new Rectangle(area.tiles[tilePositionX+1][tilePositionY+2].x,
                                            area.tiles[tilePositionX+1][tilePositionY+2].y,
                                            Tile.SIZE,
                                            Tile.SIZE);

        if (playerRect.overlaps(blockRect)) {
          player.setPosition(playerRect.x, blockRect.y-playerRect.height);
          player.setVelocity(player.getVelocity().x, 0);
         }
      } 
    }
    playerRect.y = player.getPosition().y;
    
    
  }
  
  private void bulletCollision(Player player) {
    // Bullets Collision
    synchronized (player.bullets) {
      Iterator<Bullet> i = player.bullets.iterator();
      while (i.hasNext()) {
        Bullet bullet = i.next();
  
        // Outside of area
        if (bullet.position.x > (area.width*32) ||
            bullet.position.y > (area.height*32) ||
            bullet.position.x < 0 ||
            bullet.position.y < 0) {
          i.remove();
        }
        
        Rectangle bulletRect = new Rectangle(bullet.position.x, bullet.position.y, bullet.size, bullet.size);
        bulletRect.x += bullet.velocity.x;
        
        // Get bullets current square
        int bulletPositionX = (int) (bulletRect.x / Tile.SIZE);
        int bulletPositionY = (int) (bulletRect.y / Tile.SIZE);
        
        // Right
        if (bulletRect.x > bullet.position.x) {
          if (area.tiles[bulletPositionX+1][bulletPositionY].collidable) {
            Rectangle blockRect = new Rectangle(area.tiles[bulletPositionX+1][bulletPositionY].x,
                                                area.tiles[bulletPositionX+1][bulletPositionY].y,
                                                Tile.SIZE,
                                                Tile.SIZE);
            if (bulletRect.overlaps(blockRect)) {
              i.remove();
              break;
            }  
          } else if (area.tiles[bulletPositionX+1][bulletPositionY+1].collidable) {
            Rectangle blockRect = new Rectangle(area.tiles[bulletPositionX+1][bulletPositionY+1].x,
                                                area.tiles[bulletPositionX+1][bulletPositionY+1].y,
                                                Tile.SIZE,
                                                Tile.SIZE);
            if (bulletRect.overlaps(blockRect)) {
              i.remove();
              break;
            }  
          }
          
        // Left
        } else if (bulletRect.x < bullet.position.x) {
          if (area.tiles[bulletPositionX][bulletPositionY].collidable) {
            Rectangle blockRect = new Rectangle(area.tiles[bulletPositionX][bulletPositionY].x,
                                                area.tiles[bulletPositionX][bulletPositionY].y,
                                                Tile.SIZE,
                                                Tile.SIZE);
            if (bulletRect.overlaps(blockRect)) {
              i.remove();
              break;
            }
          } else if (area.tiles[bulletPositionX][bulletPositionY+1].collidable) {
            Rectangle blockRect = new Rectangle(area.tiles[bulletPositionX][bulletPositionY+1].x,
                                                area.tiles[bulletPositionX][bulletPositionY+1].y,
                                                Tile.SIZE,
                                                Tile.SIZE);
            if (bulletRect.overlaps(blockRect)) {
              i.remove();
              break;
            }  
          }
        }
          
        bulletRect.x = bullet.position.x;
        bulletRect.y += bullet.velocity.y;
        
        // Get bullets current square
        bulletPositionX = (int) (bulletRect.x / Tile.SIZE);
        bulletPositionY = (int) (bulletRect.y / Tile.SIZE);
        
        // Top
        if (bulletRect.y > bullet.position.y) {
          if (area.tiles[bulletPositionX][bulletPositionY+1].collidable) {
            Rectangle blockRect = new Rectangle(area.tiles[bulletPositionX][bulletPositionY+1].x,
                                                area.tiles[bulletPositionX][bulletPositionY+1].y,
                                                Tile.SIZE,
                                                Tile.SIZE);
            if (bulletRect.overlaps(blockRect)) {
              i.remove();
              break;
            }
          } else if (area.tiles[bulletPositionX+1][bulletPositionY+1].collidable) {
            Rectangle blockRect = new Rectangle(area.tiles[bulletPositionX+1][bulletPositionY+1].x,
                                                area.tiles[bulletPositionX+1][bulletPositionY+1].y,
                                                Tile.SIZE,
                                                Tile.SIZE);
            if (bulletRect.overlaps(blockRect)) {
              i.remove();
              break;
            }
          }
          
        // Bottom
        } else if (bulletRect.y < bullet.position.y) {
          if (area.tiles[bulletPositionX][bulletPositionY].collidable) {
            Rectangle blockRect = new Rectangle(area.tiles[bulletPositionX][bulletPositionY].x,
                                                area.tiles[bulletPositionX][bulletPositionY].y,
                                                Tile.SIZE,
                                                Tile.SIZE);
            if (bulletRect.overlaps(blockRect)) {
              i.remove();
              break;
            }
          } else if (area.tiles[bulletPositionX+1][bulletPositionY].collidable) {
            Rectangle blockRect = new Rectangle(area.tiles[bulletPositionX+1][bulletPositionY].x,
                                                area.tiles[bulletPositionX+1][bulletPositionY].y,
                                                Tile.SIZE,
                                                Tile.SIZE);
            if (bulletRect.overlaps(blockRect)) {
              i.remove();
              break;
            }
          }
        }
        
        Iterator<Enemy> e = enemies.iterator();
        while (e.hasNext()) {
          Enemy enemy = e.next();
          Rectangle enemyRect = new Rectangle(enemy.getPosition().x, enemy.getPosition().y,
                                              32f, 32f);
          
          if (bulletRect.overlaps(enemyRect)) {
            e.remove();
            i.remove();
            client.sendTCP(enemies);
            break;
          }
        }
      }
    }
    
    synchronized(player.bullets) {
      // Move bullets
      for (Bullet bullet : player.bullets) {
        bullet.position.add(bullet.velocity);
      }
      
      // Limit number of bullets
      if (player.bullets.size() > 5) {
        player.bullets.remove(0);
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
    
    Shoot shoot = new Shoot();
    shoot.name = ErnestGame.loginName;
    shoot.position = new Vector2(screenX, screenY);
    client.sendUDP(shoot);
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
