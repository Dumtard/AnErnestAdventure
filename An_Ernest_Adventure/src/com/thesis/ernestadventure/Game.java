package com.thesis.ernestadventure;

import java.io.IOException;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.esotericsoftware.kryonet.Client;
import com.thesis.ernestadventure.Network.Login;

public class Game implements ApplicationListener {
  private Player player;

  private Client client;

  private Controller controller;
  private View view;

  @Override
  public void create() {
    
    player = new Player();
    
    client = new Client();
    client.start();
    Network.register(client);

    try {
       client.connect(5000, "localhost", 54555);
//      client.connect(5000, "10.101.106.77", 54555);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    
    controller = new Controller(client, player);
    view = new View(player);
    
    Login login = new Login();
    login.name = "Charles";

    client.sendTCP(login);
  }

  @Override
  public void dispose() {
    view.dispose();
    player.dispose();
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
