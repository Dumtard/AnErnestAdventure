package com.thesis.ernestadventure;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class View {
  private UIView uiView;
  private GameView gameView;
  
  private OrthographicCamera camera;
  private SpriteBatch batch;
  
  public View(UI ui, HashMap<String, Player> players, Area area) {
    int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();
    
    float aspectRatio = 480f/height;
    
    camera = new OrthographicCamera(width * aspectRatio, 480);
    camera.translate((width * aspectRatio) / 2, 480 / 2);

    batch = new SpriteBatch();
    
    uiView = new UIView(ui, batch, camera);
    gameView = new GameView(batch, camera, players, area);
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
