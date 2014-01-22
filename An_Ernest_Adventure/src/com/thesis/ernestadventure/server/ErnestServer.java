package com.thesis.ernestadventure.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.badlogic.gdx.math.Rectangle;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.thesis.ernestadventure.Area;
import com.thesis.ernestadventure.Enemy;
import com.thesis.ernestadventure.Network;
import com.thesis.ernestadventure.Network.Connect;
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
      
      enemyUpdate(delta);
      try {
        Thread.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      Thread.yield();
    }
  }
  
  private void enemyUpdate(float delta) {
//    System.out.println("numEnemies: " + enemies.size());
    for (int i = 0; i < enemies.size(); ++i) {
      Enemy enemy = enemies.get(i);
      enemy.setPosition(enemy.getPosition().x + (enemy.getVelocity().x * delta),
                        enemy.getPosition().y + (enemy.getVelocity().y * delta));
      
      Rectangle enemyRect = new Rectangle(enemy.getPosition().x+1, enemy.getPosition().y,
          enemy.getWidth()-1, enemy.getHeight()-2);
      
      int tilePositionX = (int) (enemyRect.x / Tile.SIZE);
      int tilePositionY = (int) (enemyRect.y / Tile.SIZE);
      
      // + ", " + tilePositionY);

      if (tilePositionX+1 < area.width && area.tiles[tilePositionX+1][tilePositionY].collidable    ||
          area.tiles[tilePositionX][tilePositionY].collidable      ||
          tilePositionX+1 < area.width && !area.tiles[tilePositionX+1][tilePositionY-1].collidable ||
          !area.tiles[tilePositionX][tilePositionY-1].collidable) {
//        if (enemy.getVelocity().x > 0) {
//          enemy.setPosition((tilePositionX)*Tile.SIZE, enemy.getPosition().y);
//        } else if (enemy.getVelocity().x < 0) {
//          enemy.setPosition((tilePositionX+1)*Tile.SIZE, enemy.getPosition().y);
//        }
        
        enemy.getVelocity().scl(-1);
        EnemyUpdate update = new EnemyUpdate();
        update.index = i;
        update.position = enemy.getPosition();
        update.velocity = enemy.getVelocity();
        server.sendToAllUDP(update);
//        System.out.println("Collision: (" + tilePositionX + ", " + tilePositionY + ")");
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
            players.remove(disconnected);
            connections.remove(i);
            connectionNames.remove(i);
            //TODO Send disconnect
            break;
          }
        }
        
        if (connections.size() == 0) {
          players.clear();
          enemies.clear();
          areaIndex = 1;
        }
//        Disconnect disconnect = new Disconnect();
//        disconnect.name = disconnected;
//        server.sendToAllTCP(disconnect);
      }
      
      public void received(Connection c, Object object) {
//        System.out.println(object.getClass().getName());
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
        
        } else if (object instanceof Move) {
//          players.get(((Move) object).name).setPosition(((Move) object).position);
//          players.get(((Move) object).name).setVelocity(((Move) object).velocity);
          
          for (int i = 0; i < connections.size(); i++) {
            if (c.getRemoteAddressUDP().getAddress() != 
                connections.get(i).getRemoteAddressUDP().getAddress()) {
              connections.get(i).sendUDP(object);
            }
          }
          return;
          
        } else if (object instanceof Stop) {
          players.get(((Stop) object).name).setPosition(((Stop) object).position);
          
          for (int i = 0; i < connections.size(); i++) {
            if (c.getRemoteAddressUDP().getAddress() != 
                connections.get(i).getRemoteAddressUDP().getAddress()) {
              connections.get(i).sendUDP(object);
            }
          }
          return;
        } else if (object instanceof Shoot) {
          for (int i = 0; i < connections.size(); i++) {
            if (c.getRemoteAddressUDP().getAddress() !=
                connections.get(i).getRemoteAddressUDP().getAddress()) {
              connections.get(i).sendUDP(object);
            }
          }
          
        } else if (object instanceof Area) {
          enemies.clear();
          area = new Area();
          area = ((Area)object);
          area.tiles = new Tile[area.width][area.height];
          area.width = 0;
          
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
