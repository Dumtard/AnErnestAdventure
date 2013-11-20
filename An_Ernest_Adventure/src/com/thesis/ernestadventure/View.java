package com.thesis.ernestadventure;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class View {
  private int width;
  private int height;
  
  private OrthographicCamera camera;
  private SpriteBatch batch;
  
  private HashMap<String, Player> players;
  
  UI_View UIView;
  
  
  private TextureAtlas tileAtlas;
  
  //TODO Change naming
  TextureAtlas atlas;


  //TODO Think about moving
  private Sprite sprite1;
  private Sprite sprite2;
  private Sprite sprite3;
  
  private Area area;
  
  private float time = 0.0f;
  
  /** Textures **/
  private TextureRegion ernestIdle;
  
  /** Animations **/
  private Animation walkAnimation;
  
  public View(HashMap<String, Player> players, Area area) {
    this.area = area;
    
    width = Gdx.graphics.getWidth();
    height = Gdx.graphics.getHeight();
    
    float aspectRatio = 480f/height;
    
    batch = new SpriteBatch();
    
    this.players = players;
    
    camera = new OrthographicCamera(width*aspectRatio, 480);
    camera.translate((width*aspectRatio) / 2, 480 / 2);
    
    UIView = new UI_View(batch);
    
    tileAtlas = new TextureAtlas(Gdx.files.internal("tiles/tiles.pack"));
    
    sprite1 = new Sprite();
    sprite2 = new Sprite();
    sprite3 = new Sprite();
    
    sprite1 = tileAtlas.createSprite("Tile#");
    sprite2 = tileAtlas.createSprite("TileDot");
    sprite3 = tileAtlas.createSprite("Tile@");
    
    loadTextures();
  }
  
  public OrthographicCamera getCamera() {
    return camera;
  }
  
  private void loadTextures() {
    atlas = new TextureAtlas(Gdx.files.internal("data/ernest.pack"));
    ernestIdle = atlas.findRegion("Frame-0");
    TextureRegion[] walkFrames = new TextureRegion[4];
    for (int i = 0; i < 4; i++) {
      walkFrames[i] = atlas.findRegion("Frame-" + i);
    }
    walkAnimation = new Animation(0.13f, walkFrames);
  }
  
  public void dispose() {
    batch.dispose();
//    playerTexture.dispose();
  }
  
  private void renderArea(float delta) {
    for (int i = 0; i < area.height; i++) {
      for (int j = 0; j < area.width; j++) {
        if (area.tiles[j][i].id == 35) {
          sprite1.setPosition(area.tiles[j][i].x, area.tiles[j][i].y);
          sprite1.draw(batch);
        } else if (area.tiles[j][i].id == 46) {
//          sprite2.setPosition(area.tiles[j][i].x, area.tiles[j][i].y);
//          sprite2.draw(batch);
        } else {
          sprite3.setPosition(area.tiles[j][i].x, area.tiles[j][i].y);
          sprite3.draw(batch);
        }
      }
    }
  }
  
  private void renderPlayers(float delta) {
    for (Map.Entry<String, Player> player: players.entrySet()) {
      TextureRegion bobFrame = walkAnimation.getKeyFrame(time, true);
      batch.draw(bobFrame, player.getValue().getPosition().x, player.getValue().getPosition().y, 32, 64);
    }
    // Ensure player 1 is always visible
    TextureRegion bobFrame = walkAnimation.getKeyFrame(time, true);
    batch.draw(bobFrame, players.get(ErnestGame.loginName).getPosition().x, 
                         players.get(ErnestGame.loginName).getPosition().y, 32, 64);
  }
  
  private void renderUI() {
    UIView.render(camera.position.x - (camera.viewportWidth/2.0f),
                  camera.position.y - (camera.viewportHeight/2.0f),
                  camera.viewportWidth,
                  camera.viewportHeight*(3.0f/15.0f));
  }
  
  public void render(float delta) {
    
    camera.update();
    
    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    
    renderArea(delta);
    renderPlayers(delta);
    renderUI();
    
    time += delta;
    
    batch.end();
  }
}
