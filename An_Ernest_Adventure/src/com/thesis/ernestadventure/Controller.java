package com.thesis.ernestadventure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.thesis.ernestadventure.Network.Move;

public class Controller implements InputProcessor {
  Player player;
  Client client;

  Controller(Client client, Player player) {
    Gdx.input.setInputProcessor(this);
    this.player = player;
    this.client = client;
  }

  @Override
  public boolean keyDown(int keycode) {
    // Gdx.app.log("Key", "" + keycode);

    if (keycode == 21) {
      player.setVelocity(new Vector2(-2, player.getVelocity().y));
      Move move = new Move();
      move.velocity = player.getVelocity();
      client.sendTCP(move);
    } else if (keycode == 22) {
      player.setVelocity(new Vector2(2, player.getVelocity().y));
      Move move = new Move();
      move.velocity = player.getVelocity();
      client.sendTCP(move);
    } else if (keycode == 62) {
      player.setVelocity(new Vector2(player.getVelocity().x, 4));
      Move move = new Move();
      move.velocity = player.getVelocity();
      client.sendTCP(move);
    }

    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    // Gdx.app.log("Key", "" + keycode);

    if (keycode == 21) {
      player.setVelocity(new Vector2(0, player.getVelocity().y));
    } else if (keycode == 22) {
      player.setVelocity(new Vector2(0, player.getVelocity().y));
    }

    return false;
  }

  @Override
  public boolean keyTyped(char character) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    // Gdx.app.log("Cursor" + pointer, screenX + ", " + screenY);
    if (screenX <= 400 && screenY > 240) {
      player.setVelocity(new Vector2(-2, player.getVelocity().y));
      Move move = new Move();
      move.velocity = player.getVelocity();
      client.sendTCP(move);
    } else if (screenX >= 400 && screenY > 240) {
      player.setVelocity(new Vector2(2, player.getVelocity().y));
      Move move = new Move();
      move.velocity = player.getVelocity();
      client.sendTCP(move);
    } else if (screenY < 240) {
      player.setVelocity(new Vector2(player.getVelocity().x, 4));
      Move move = new Move();
      move.velocity = player.getVelocity();
      client.sendTCP(move);
    }

    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    if (screenX <= 400 && screenY > 240) {
      player.setVelocity(new Vector2(0, player.getVelocity().y));
    } else if (screenX >= 400 && screenY > 240) {
      player.setVelocity(new Vector2(0, player.getVelocity().y));
    }
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean mouseMoved(int screenX, int screenY) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean scrolled(int amount) {
    // TODO Auto-generated method stub
    return false;
  }
}
