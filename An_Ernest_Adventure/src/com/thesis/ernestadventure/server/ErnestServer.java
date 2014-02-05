package com.thesis.ernestadventure.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.thesis.ernestadventure.Area;
import com.thesis.ernestadventure.BomberEnemy;
import com.thesis.ernestadventure.Enemy;
import com.thesis.ernestadventure.Network;
import com.thesis.ernestadventure.Network.BomberEnemyUpdate;
import com.thesis.ernestadventure.Network.Connect;
import com.thesis.ernestadventure.Network.Disconnect;
import com.thesis.ernestadventure.Network.EnemyUpdate;
import com.thesis.ernestadventure.Network.Initialize;
import com.thesis.ernestadventure.Network.Move;
import com.thesis.ernestadventure.Network.Shoot;
import com.thesis.ernestadventure.Network.Stop;
import com.thesis.ernestadventure.Player;
import com.thesis.ernestadventure.Tile;

public class ErnestServer {
  Server server;
  Vector<String> connectionNames = new Vector<String>();
  Vector<Connection> connections = new Vector<Connection>();
  HashMap<String, Player> players = new HashMap<String, Player>();
  ArrayList<Enemy> enemies = new ArrayList<Enemy>();
  Area area = new Area();
  int areaIndex = 1;
  
  public void run() {
    long current;
    long previous = System.currentTimeMillis();
    while (true) {
      current = System.currentTimeMillis();
      float delta = (current - previous)/1000.0f;
      previous = current;
      
      playerUpdate(delta);
      enemyUpdate(delta);
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      Thread.yield();
    }
  }
  
  private void playerUpdate(float delta) {
    for (Map.Entry<String, Player> nameandplayer : players.entrySet()) {
      Player player = nameandplayer.getValue();
      
      player.setPosition(player.getPosition().x + (player.getVelocity().x * delta * Tile.SIZE),
                         player.getPosition().y + (player.getVelocity().y * delta * Tile.SIZE));
      
      Rectangle playerRect = new Rectangle(player.getPosition().x + 1, player.getPosition().y,
                                           player.getWidth() - 1, player.getHeight() - 2);
      
      boolean takeDamage = false;
      
      //collision with enemies
      for (int i = 0; i < enemies.size(); ++i) {
        Enemy enemy = enemies.get(i);
        
        Rectangle enemyRect = new Rectangle(enemy.getPosition().x, enemy.getPosition().y,
                                            enemy.getWidth(), enemy.getHeight());
        
        if (playerRect.overlaps(enemyRect)) {
          //damage player
          if (System.currentTimeMillis()-player.lastDamage < 1000) {
            takeDamage = true;
          }
          
        }
        if (enemy instanceof BomberEnemy) {
          //check enemy's bullets
          Rectangle bulletRect = new Rectangle(((BomberEnemy) enemy).bullet.x, 
              ((BomberEnemy) enemy).bullet.y,
              16, 16);
          if (playerRect.overlaps(bulletRect)) {
            if ((System.currentTimeMillis() - player.lastDamage) > 1000) {
              takeDamage = true;
            }
            
            ((BomberEnemy) enemy).attacking = false;
          }
        }
      }
      
      if (takeDamage) {
        player.health--;
        player.lastDamage = System.currentTimeMillis();

        player.setVelocity(player.getVelocity().x * -1, 7);
      }
    }  
  }
  
  private void enemyUpdate(float delta) {
    for (int i = 0; i < enemies.size(); ++i) {
      Enemy enemy = enemies.get(i);
      enemy.setPosition(enemy.getPosition().x + (enemy.getVelocity().x * delta),
                        enemy.getPosition().y + (enemy.getVelocity().y * delta));
      
    Rectangle enemyRect = new Rectangle(enemy.getPosition().x, enemy.getPosition().y,
        enemy.getWidth(), enemy.getHeight()-2);
    
    int tilePositionX = (int) (enemyRect.x / Tile.SIZE);
    int tilePositionY = (int) (enemyRect.y / Tile.SIZE);
    
    if (enemy instanceof BomberEnemy) {
      boolean sendUpdate = false;
      if (area.tiles[tilePositionX+2][tilePositionY].collidable ||
          area.tiles[tilePositionX][tilePositionY].collidable) {
        if (enemy.getVelocity().x > 0) {
          enemy.setPosition((tilePositionX) * Tile.SIZE, enemy.getPosition().y);
        } else if ( enemy.getVelocity().x < 0) {
          enemy.setPosition((tilePositionX+1) * Tile.SIZE, enemy.getPosition().y);
        }
        enemy.getVelocity().scl(-1);
        enemy.setIsFacingRight(!enemy.getIsFacingRight());
        sendUpdate = true;
      }
      
      for (Map.Entry<String, Player> nameandplayer : players.entrySet()) {
        Player player = nameandplayer.getValue();
        if ((player.getPosition().x - ((BomberEnemy)enemy).bullet.x) > -16 &&
            (player.getPosition().x - ((BomberEnemy)enemy).bullet.x) < 0) {
          if (!((BomberEnemy)enemy).attacking) {
            enemy.attack();
            sendUpdate = true;
          }
        }
      }
      
      //dropping bullets
      if (((BomberEnemy)enemy).attacking) {
        ((BomberEnemy)enemy).bullet.y += ((BomberEnemy)enemy).bulletVelocity*delta*Tile.SIZE;
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
        sendUpdate = true;
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
          sendUpdate = true;
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
          sendUpdate = true;
        }
      }
      
      if (sendUpdate) {
        BomberEnemyUpdate update = new BomberEnemyUpdate();
        update.index = i;
        update.position = enemy.getPosition();
        update.velocity = enemy.getVelocity();
        update.attacking = ((BomberEnemy)enemy).attacking;
        update.isFacingRight = ((BomberEnemy)enemy).getIsFacingRight();
        server.sendToAllTCP(update);
        
      }
      
    }
    else if (enemy instanceof Enemy) {
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
          EnemyUpdate update = new EnemyUpdate();
          update.index = i;
          update.position = enemy.getPosition();
          update.velocity = enemy.getVelocity();
          update.isFacingRight = enemy.getIsFacingRight();
          server.sendToAllTCP(update);
        }
        
      }
    }
  }
  

  public ErnestServer() throws IOException {
    server = new Server();

    Network.register(server);
    
    server.addListener(new Listener() {
      public void disconnected(Connection c) {
        String disconnected = "";
        for (int i = 0; i < connections.size(); i++) {
          if (connections.get(i) == c) {
            disconnected = connectionNames.get(i);
            System.out.println("Player " + disconnected + " disconnected");
            
            players.remove(disconnected);
            connections.remove(i);
            connectionNames.remove(i);
            
            Disconnect discoSignal = new Disconnect();
            discoSignal.name = disconnected;
            server.sendToAllTCP(discoSignal);
            break;
          }
        }
        
        if (connections.size() <= 0) {
          players.clear();
          enemies.clear();
          areaIndex = 1;
        }
      }
      
      public void received(Connection c, Object object) {
        if (object instanceof Connect) {
          System.out.println(((Connect) object).name + " has logged on.");
          connections.add(c);
          System.out.println(connections.get(connections.size()-1).getRemoteAddressTCP().getAddress());
          
          Initialize init = new Initialize();
          players.put(((Connect) object).name, new Player());
          init.players = players;
          init.area = areaIndex;
          init.enemies = enemies;
          
          for (int i = 0; i < connections.size(); i++) {
            connections.get(i).sendTCP(init);
          }
          
        connectionNames.add(((Connect) object).name);

        for (int i = 0; i < connections.size(); i++) {
          if (c.getRemoteAddressTCP().getAddress() != 
              connections.get(i).getRemoteAddressTCP().getAddress()) {
            Connect connection = new Connect();
            connection.name = ((Connect) object).name;
            connections.get(i).sendTCP(connection);
          }
        }
//        
//        for (int i = 0; i < connectionNames.size(); i++) {
//          if (c.getRemoteAddressTCP().getAddress() != 
//              connections.get(i).getRemoteAddressTCP().getAddress()) {
//            Connect login = new Connect();
//            login.name = connectionNames.get(i);
//            c.sendTCP(login);
//          }
//        }
          
          System.out.println(connections.size() + " users currently online.");
          return;
        
        } else if (object instanceof ArrayList) {
          enemies = (ArrayList<Enemy>) object;
          for (int i = 0; i < connections.size(); i++) {
            if (c.getRemoteAddressTCP().getAddress() != 
                connections.get(i).getRemoteAddressTCP().getAddress()) {
              connections.get(i).sendTCP(enemies);
            }
          }
        
        } else if (object instanceof Move) {
          players.get(((Move) object).name).setPosition(((Move) object).position);
          players.get(((Move) object).name).setVelocity(((Move) object).velocity);
          
          for (int i = 0; i < connections.size(); i++) {
            if (c.getRemoteAddressTCP().getAddress() != 
                connections.get(i).getRemoteAddressTCP().getAddress()) {
              connections.get(i).sendTCP(object);
            }
          }
          return;
          
        } else if (object instanceof Stop) {
          players.get(((Stop) object).name).setPosition(((Stop) object).position);
          players.get(((Stop) object).name).setVelocity(new Vector2(0, 0));
          
          for (int i = 0; i < connections.size(); i++) {
            if (c.getRemoteAddressTCP().getAddress() != 
                connections.get(i).getRemoteAddressTCP().getAddress()) {
              connections.get(i).sendTCP(object);
            }
          }
          return;
        } else if (object instanceof Shoot) {
          for (int i = 0; i < connections.size(); i++) {
            if (c.getRemoteAddressTCP().getAddress() !=
                connections.get(i).getRemoteAddressTCP().getAddress()) {
              connections.get(i).sendTCP(object);
            }
          }
          
        } else if (object instanceof Area) {
          enemies.clear();

          area = new Area();
          area = ((Area)object);
          area.tiles = new Tile[area.width][area.height];
          area.width = 0;
          areaIndex = ((Area)object).index;
          
          for (int i = 0; i < connections.size(); i++) {
            if (c.getRemoteAddressTCP().getAddress() !=
                connections.get(i).getRemoteAddressTCP().getAddress()) {
              connections.get(i).sendTCP(area);
            }
          }
          
          
        } else if (object instanceof Tile[]) {
          area.tiles[area.width] = ((Tile[])object);
          area.width++;
        }
        
        
      }
    });

    server.bind(54555, 54555);
    server.start();
  }

  public static void main(String[] args) throws IOException {
    Log.set(Log.LEVEL_DEBUG);
    ErnestServer server = new ErnestServer();
    server.run();
  }
}
