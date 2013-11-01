package com.thesis.ernestadventure;

import com.badlogic.gdx.Game;

public class ErnestGame extends Game {

  public static final String loginName = "dumtard";
  
  @Override
  public void create() {
    setScreen(new GameScreen());
  }
}
