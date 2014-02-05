package com.thesis.ernestadventure;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class ErnestGame extends Game {
  
  public static final String loginName = "kokiri";
  public static float GAMETIME = 0;

  @Override
  public void create() {
    setScreen(new StartScreen(this));
  }
}
