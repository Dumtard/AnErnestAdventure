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

    if (enemy instanceof BomberEnemy) {
      if (((BomberEnemy)enemy).attacking) {
        ((BomberEnemy)enemy).bullet.y  += ((BomberEnemy)enemy).bulletVelocity;
      } else {
        ((BomberEnemy)enemy).bullet.x = enemy.getPosition().x;
        ((BomberEnemy)enemy).bullet.y = enemy.getPosition().y;
      }
    }
  }
}
