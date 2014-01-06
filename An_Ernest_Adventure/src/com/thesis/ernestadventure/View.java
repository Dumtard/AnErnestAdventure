package com.thesis.ernestadventure;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class View {
  private UIView uiView;
  private GameView gameView;
  
  private OrthographicCamera camera;
  private SpriteBatch batch;
  
  public View(UI ui, HashMap<String, Player> players, Area area, ArrayList<Enemy> enemies) {
    int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();
    
    float aspectRatio = GameScreen.GAMEHEIGHT/height;
    
    camera = new OrthographicCamera(GameScreen.GAMEHEIGHT / aspectRatio, 480);
    camera.translate((GameScreen.GAMEHEIGHT / aspectRatio) / 2, 480 / 2);

    batch = new SpriteBatch();
    
    uiView = new UIView(ui, batch, camera);
    gameView = new GameView(batch, camera, players, area, enemies);
  }
  
  public void dispose() {
    batch.dispose();
  }

  public void render(float delta) {
    gameView.updateCamera();
    camera.update();

    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    
    gameView.render(delta);
    uiView.render(delta);

    batch.end();
  }
}
