package com.thesis.ernestadventure;

import java.io.IOException;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.thesis.ernestadventure.Network.Login;
import com.thesis.ernestadventure.Network.Move;

public class Game implements ApplicationListener {
  private OrthographicCamera camera;
  private SpriteBatch batch;
  private Texture texture;
  private Sprite sprite;

  private Player player;

  private Client client;

  private Controller controller;

  @Override
  public void create() {
    float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();
    
    player = new Player();
    
    client = new Client();
    client.start();
    Network.register(client);

    try {
      // client.connect(5000, "localhost", 54555);
      client.connect(5000, "10.101.106.77", 54555);
    } catch (IOException ex) {
      Gdx.app.log("Exception", "IO");
      ex.printStackTrace();
    }
    
    controller = new Controller(client, player);

    texture = new Texture(Gdx.files.internal("data/libgdx.png"));
    texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

    TextureRegion region = new TextureRegion(texture, 0, 0, 800, 96);

    sprite = new Sprite(region);
    // sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
    sprite.setPosition(0, 0);

    camera = new OrthographicCamera(w, h);
    camera.translate(w / 2, h / 2);
    camera.update();
    batch = new SpriteBatch();

    Login login = new Login();
    login.name = "Dumtard";

    client.sendTCP(login);
  }

  @Override
  public void dispose() {
    batch.dispose();
    player.dispose();
  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    player.update();
    player.draw(batch);
    sprite.draw(batch);
    batch.end();
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
