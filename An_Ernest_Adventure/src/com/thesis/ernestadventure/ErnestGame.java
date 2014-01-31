package com.thesis.ernestadventure;

import com.badlogic.gdx.Game;

public class ErnestGame extends Game {
  
  public static final String loginName = "Tablet";
  public static float GAMETIME = 0;

  @Override
  public void create() {
    setScreen(new GameScreen());
  }
}
