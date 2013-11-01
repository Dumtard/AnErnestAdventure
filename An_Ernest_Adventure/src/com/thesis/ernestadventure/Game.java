package com.thesis.ernestadventure;

import java.io.IOException;
import java.util.HashMap;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.thesis.ernestadventure.Network.Connect;
import com.thesis.ernestadventure.Network.Disconnect;
import com.thesis.ernestadventure.Network.Move;
import com.thesis.ernestadventure.Network.Stop;

public class Game implements ApplicationListener {
  private HashMap<String, Player> players = new HashMap<String, Player>();

  private Client client;

  private Controller controller;
  private View view;

  public static final String loginName = "dumtard";
  
  @Override
  public void create() {
    players.put(Game.loginName, new Player());
    
    client = new Client();
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
          System.out.println(((Move) object).name + ": " + ((Move) object).velocity.x + ", "
              + ((Move) object).velocity.y);
          
          players.get(((Move) object).name).setVelocity(((Move) object).velocity);
          
          return;
        } else if (object instanceof Stop) {
          System.out.println(((Stop) object).name + ": " + ((Stop) object).position.x + ", "
              + ((Stop) object).position.y);

          players.get(((Stop) object).name).setPosition(((Stop) object).position);
          players.get(((Stop) object).name).setVelocity(0, 0);
          
          return;
        }
      }
    });
    
    try {
      client.connect(5000, "localhost", 54555, 54555);  //Use this for desktop
//    client.connect(5000, "10.121.109.105", 54555, 54555); // Use this for android
//    client.connect(5000, client.discoverHost(54555, 54555), 54555, 54555); // This doesn't work currently
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    
    controller = new Controller(client, players);
    view = new View(players);
    
    Connect login = new Connect();
    login.name = Game.loginName;

    client.sendTCP(login);
  }

  @Override
  public void dispose() {
    view.dispose();
  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    
    controller.update();
    view.render();
  }

  @Override
  public void resize(int width, int height) {
  }

  @Override
  public void pause() {
  }

  @Override
  public void resume() {
  }
}
