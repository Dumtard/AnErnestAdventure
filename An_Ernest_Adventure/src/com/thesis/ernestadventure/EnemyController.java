package com.thesis.ernestadventure;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;

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
//    
//    Rectangle enemyRect = new Rectangle(enemy.getPosition().x, enemy.getPosition().y,
//        enemy.getWidth()-2, enemy.getHeight()-2);
//    
//    int tilePositionX = (int) (enemyRect.x / Tile.SIZE);
//    int tilePositionY = (int) (enemyRect.y / Tile.SIZE);
//    
//    if (area.tiles[tilePositionX+1][tilePositionY].collidable ||
//        area.tiles[tilePositionX][tilePositionY].collidable   ||
//        !area.tiles[tilePositionX+1][tilePositionY-1].collidable ||
//        !area.tiles[tilePositionX][tilePositionY-1].collidable) {
//      enemy.getVelocity().scl(-1);
//    }
  }
}
