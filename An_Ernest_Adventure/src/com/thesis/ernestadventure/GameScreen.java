package com.thesis.ernestadventure;

import java.io.IOException;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.thesis.ernestadventure.Network.Connect;
import com.thesis.ernestadventure.Network.Disconnect;
import com.thesis.ernestadventure.Network.Move;
import com.thesis.ernestadventure.Network.Shoot;
import com.thesis.ernestadventure.Network.Stop;

public class GameScreen implements Screen {
  public static final int GAMEHEIGHT = 480;
  
  private Controller controller;
  private View view;
  
  private Client client;
  
  private UI ui;
  private HashMap<String, Player> players;
//  private ArrayList<Enemy> enemies;

  private Area area;

  public GameScreen() {
    ui = new UI();
    client = new Client();
    try {
      area = new Area();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    players = new HashMap<String, Player>();
    players.put(ErnestGame.loginName, new Player(area.getStart()));
    
    view = new View(ui, players, area);
    controller = new Controller(client, ui, players, area);
    
    client.start();
    Network.register(client);
   
    client.addListener(new Listener() {
      public void received(Connection c, Object object) {
        if (object instanceof Connect) {
          System.out.println(((Connect) object).name + " has joined");
          players.put(((Connect) object).name, new Player());

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
        }
      }
    });
    
    try {
//      client.connect(5000, "localhost", 54555, 54555);  // Local Server
      client.connect(5000, "dumtard.com", 54555, 54555); // Charles Server
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    
    Connect login = new Connect();
    login.name = ErnestGame.loginName;

    client.sendTCP(login);
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
    // TODO Auto-generated method stub
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
