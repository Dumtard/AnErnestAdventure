package com.thesis.ernestadventure;

import java.util.HashMap;

import com.badlogic.gdx.math.Rectangle;
import com.esotericsoftware.kryonet.Client;
import com.thesis.ernestadventure.Network.EnemyUpdate;

public class EnemyController {
  private final float GRAVITY = 0.5f;

  private HashMap<String, Player> players;

  private Client client;

  private Area area;
  
  public EnemyController (Client client, HashMap<String, Player> players, 
      Area area) {
    this.client = client;
    this.players = players;
    this.area = area;
  }
  
  public void update (Enemy enemy, float delta) {
    enemy.setPosition(enemy.getPosition().x + (enemy.getVelocity().x * delta),
                      enemy.getPosition().y + (enemy.getVelocity().y * delta));

    int tilePositionX = (int) (enemy.getPosition().x / Tile.SIZE);
    int tilePositionY = (int) (enemy.getPosition().y / Tile.SIZE);
    
    if (enemy instanceof BomberEnemy) {
      
      if (area.tiles[tilePositionX+2][tilePositionY].collidable ||
          area.tiles[tilePositionX][tilePositionY].collidable) {
        if (enemy.getVelocity().x > 0) {
          enemy.setPosition((tilePositionX) * Tile.SIZE, enemy.getPosition().y);
        } else if ( enemy.getVelocity().x < 0) {
          enemy.setPosition((tilePositionX+1) * Tile.SIZE, enemy.getPosition().y);
        }
        enemy.getVelocity().scl(-1);
        enemy.setIsFacingRight(!enemy.getIsFacingRight());
      }
      
      if (((BomberEnemy)enemy).attacking) {
        ((BomberEnemy)enemy).bullet.y  += ((BomberEnemy)enemy).bulletVelocity*delta*Tile.SIZE;
      } else {
        ((BomberEnemy)enemy).bullet.x = enemy.getPosition().x;
        ((BomberEnemy)enemy).bullet.y = enemy.getPosition().y;
        if (((BomberEnemy)enemy).getIsFacingRight()) {
          ((BomberEnemy)enemy).bullet.x += Tile.SIZE;
        } else {
          ((BomberEnemy)enemy).bullet.x += Tile.SIZE/2;
        }
      }
     
      //bullet collisions   
      Rectangle bulletRect = new Rectangle(((BomberEnemy)enemy).bullet.x+2, ((BomberEnemy)enemy).bullet.y,
          12, 16);

      int bulletTilePositionX = (int)(((BomberEnemy)enemy).bullet.x / Tile.SIZE);
      int bulletTilePositionY = (int)(((BomberEnemy)enemy).bullet.y / Tile.SIZE);
      if (bulletTilePositionY < 3) {
        ((BomberEnemy)enemy).attacking = false;
        bulletTilePositionY = 2;
      }
      
      if (area.tiles[bulletTilePositionX][bulletTilePositionY].collidable) {
        Rectangle tileRect = new Rectangle(area.tiles[bulletTilePositionX][bulletTilePositionY].x,
                                           area.tiles[bulletTilePositionX][bulletTilePositionY].y,
                                           32, 32);

        if (bulletRect.overlaps(tileRect)) {
          ((BomberEnemy)enemy).attacking = false;
          ((BomberEnemy)enemy).bullet.x = enemy.getPosition().x;
          ((BomberEnemy)enemy).bullet.y = enemy.getPosition().y;
        }
      }
      if (area.tiles[bulletTilePositionX+1][bulletTilePositionY].collidable) {
        Rectangle tileRect2 = new Rectangle(area.tiles[bulletTilePositionX+1][bulletTilePositionY].x,
                                            area.tiles[bulletTilePositionX+1][bulletTilePositionY].y,
                                            32, 32);

        if (bulletRect.overlaps(tileRect2)) {
          ((BomberEnemy)enemy).attacking = false;
          ((BomberEnemy)enemy).bullet.x = enemy.getPosition().x;
          ((BomberEnemy)enemy).bullet.y = enemy.getPosition().y;
        }
      }
    } else if (enemy instanceof Enemy) {
      if (tilePositionX+1 < area.width && area.tiles[tilePositionX+1][tilePositionY].collidable    ||
          area.tiles[tilePositionX][tilePositionY].collidable      ||
          tilePositionX+1 < area.width && !area.tiles[tilePositionX+1][tilePositionY-1].collidable ||
          !area.tiles[tilePositionX][tilePositionY-1].collidable) {
        if (enemy.getVelocity().x > 0) {
          enemy.setPosition((tilePositionX) * Tile.SIZE, enemy.getPosition().y);
        } else if ( enemy.getVelocity().x < 0) {
          enemy.setPosition((tilePositionX+1) * Tile.SIZE, enemy.getPosition().y);
        }
        
        enemy.getVelocity().scl(-1);
      }
    }
    
  }
}
