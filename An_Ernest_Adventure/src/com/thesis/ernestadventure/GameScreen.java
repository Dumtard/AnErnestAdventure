package com.thesis.ernestadventure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.thesis.ernestadventure.Network.BomberEnemyUpdate;
import com.thesis.ernestadventure.Network.Connect;
import com.thesis.ernestadventure.Network.Disconnect;
import com.thesis.ernestadventure.Network.EnemyUpdate;
import com.thesis.ernestadventure.Network.Initialize;
import com.thesis.ernestadventure.Network.Move;
import com.thesis.ernestadventure.Network.Shoot;
import com.thesis.ernestadventure.Network.Stop;

public class GameScreen implements Screen {
  public static final float GAMEHEIGHT = 480f;
  
  private Controller controller;
  private View view;
  
  private Client client;
  
  private UI ui;
  private HashMap<String, Player> players;
  private ArrayList<Enemy> enemies;

  private Area area;

  public GameScreen() {
    
  }
  
  @Override
  public void render(float delta) {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    
    controller.update(delta);
    view.render(delta);
    
    ErnestGame.GAMETIME += delta;
    
  }

  @Override
  public void resize(int width, int height) {
    // TODO Auto-generated method stub
  }

  @Override
  public void show() {
    ui = new UI();
    client = new Client(65536, 2048);
    
    players = new HashMap<String, Player>();
    enemies = new ArrayList<Enemy>();
    area = new Area();
    
    //connect
    client.start();
    Network.register(client);
    try {
//      client.connect(5000, "localhost", 54555, 54555);  // Local Server
      client.connect(5000, "dumtard.com", 54555, 54555); // Charles Server
//      client.connect(5000, "192.168.0.184", 54555, 54555);  // laptop Server
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    
    client.addListener(new Listener() {
      public void received(Connection c, Object object) {
//        System.out.println(object.getClass().getName());
        
        if (object instanceof Connect) {
          System.out.println(((Connect) object).name + " has joined");
          players.put(((Connect) object).name, new Player(area.getStart()));

          return;
        } else if (object instanceof Disconnect) {
          System.out.println(((Disconnect) object).name + " has left");
          players.remove(((Disconnect) object).name);
          
          return;
        } else if (object instanceof Move) {
          players.get(((Move) object).name).setVelocity(((Move) object).velocity);
          if (((Move) object).velocity.x < 0) {
            players.get(((Move) object).name).setIsFacingRight(false);
          } else {
            players.get(((Move) object).name).setIsFacingRight(true);
          }
          
          return;
        } else if (object instanceof Stop) {
          players.get(((Stop) object).name).setPosition(((Stop) object).position);
          players.get(((Stop) object).name).setVelocity(0, 0);
          
          return;
        } else if (object instanceof Shoot) {
          players.get(((Shoot) object).name).
              shoot((int)((Shoot) object).position.x, (int)((Shoot) object).position.y);
          
        } else if (object instanceof Initialize) {
          if (area.height == 0) {
            try {
              area.loadArea(((Initialize)object).area.intValue());
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          
          enemies.clear();
          synchronized (enemies) {
            for (Enemy enemy : ((Initialize)object).enemies) {
              enemies.add(enemy);
            }
          }
          
          players.clear();
          synchronized (players) {
            for (Map.Entry<String, Player> nameandplayer : ((Initialize)object).players.entrySet()) {
              if ((nameandplayer.getKey().charAt(0) >= 48 &&
                  nameandplayer.getKey().charAt(0) <= 57) ||
                  (nameandplayer.getKey().charAt(0) >= 65 &&
                  nameandplayer.getKey().charAt(0) <= 90) ||
                  (nameandplayer.getKey().charAt(0) >= 97 &&
                  nameandplayer.getKey().charAt(0) <= 122)) {
                players.put(nameandplayer.getKey(), nameandplayer.getValue());
              } else {
                players.put(nameandplayer.getKey().substring(1), nameandplayer.getValue());
              }
            }
          }
          
        } else if (object instanceof ArrayList) {
          enemies.clear();
          synchronized (enemies) {
            for (Enemy enemy : (ArrayList<Enemy>)object) {
              enemies.add(enemy);
            }
          }
        } else if (object instanceof EnemyUpdate) {
          EnemyUpdate update = ((EnemyUpdate) object);
          
          try {
            enemies.get(update.index).setIsFacingRight(update.isFacingRight);
            enemies.get(update.index).setPosition(update.position);
            enemies.get(update.index).setVelocity(update.velocity);
          } catch (IndexOutOfBoundsException e) {
            
          }
        
        } else if (object instanceof BomberEnemyUpdate) {
          BomberEnemyUpdate update = ((BomberEnemyUpdate) object);
        
        try {
          enemies.get(update.index).setIsFacingRight(update.isFacingRight);
          enemies.get(update.index).setPosition(update.position);
          enemies.get(update.index).setVelocity(update.velocity);
          ((BomberEnemy)enemies.get(update.index)).attacking = update.attacking;
        } catch (IndexOutOfBoundsException e) {
          
        }
          
        } else if (object instanceof Area) {
          if (area.index != ((Area) object).index) {
            try {
              area.loadArea(((Area)object).index);
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
        }
      }
    });
    
    Connect login = new Connect();
    login.name = ErnestGame.loginName;

    client.sendTCP(login);

    
    //wait for replay
    while (true) {
      if (players.get(ErnestGame.loginName) != null) {
        break;
      }
      Thread.yield();
    }
    
    
    if (players.size() == 1) {
//      client.setTimeout(20);
      Area a = new Area();
      a.width = area.width;
      a.height = area.height;
      client.sendTCP(a);
      for (int i = 0; i < area.width; ++i) {
        client.sendTCP(area.tiles[i]);
      }
//      client.sendTCP(new Move());

//      for (Vector2 position : area.enemyPositions) {
      for (int i = 0; i < area.enemyPositions.size(); ++i) {
        Vector2 position = area.enemyPositions.get(i);
        
        if (area.enemyType.get(i) == '#') {
          Enemy e = new Enemy(position);
          enemies.add(e);
        } else if (area.enemyType.get(i) == '@') {
          BomberEnemy e = new BomberEnemy(position);
          enemies.add(e);
        }
        //TODO enemy type
      }
      client.sendTCP(enemies);
    }
    
    players.get(ErnestGame.loginName).setPosition(area.getStart());
    
    Stop startPosition = new Stop();
    startPosition.name = ErnestGame.loginName;
    startPosition.position = players.get(ErnestGame.loginName).getPosition(); 
    client.sendTCP(startPosition);
    
    view = new View(ui, players, area, enemies);
    controller = new Controller(client, ui, players, area, enemies);
  }

  @Override
  public void hide() {
    // TODO Auto-generated method stub
  }

  @Override
  public void pause() {
    // TODO Auto-generated method stub
  }

  @Override
  public void resume() {
    // TODO Auto-generated method stub
  }

  @Override
  public void dispose() {
    view.dispose();
  }
}
