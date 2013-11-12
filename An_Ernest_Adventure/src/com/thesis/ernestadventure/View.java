package com.thesis.ernestadventure;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
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

//  private Texture playerTexture;
//  private Sprite playerSprite;
//  private Sprite player2Sprite;
  
  private TextureAtlas tileAtlas;
  
  //TODO Remove these textures and sprites
  private Texture texture;
  private Sprite sprite1;
  private Sprite sprite2;
  private Sprite sprite3;
  
  Area area;
  
  private float time = 0.0f;
  
  /** Textures **/
  private TextureRegion ernestIdle;
  
  /** Animations **/
  private Animation walkAnimation;
  
  public View(HashMap<String, Player> players) {
    //TODO Refactor
    try {
      area = new Area();
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    width = Gdx.graphics.getWidth();
    height = Gdx.graphics.getHeight();
    
    float aspectRatio = 480f/height;
    
    batch = new SpriteBatch();
    
    this.players = players;
    
    camera = new OrthographicCamera(width*aspectRatio, 480);
    camera.translate((width*aspectRatio) / 2, 480 / 2);
    
    tileAtlas = new TextureAtlas(Gdx.files.internal("tiles/tiles.pack"));
    
    sprite1 = new Sprite();
    sprite2 = new Sprite();
    sprite3 = new Sprite();
    
    sprite1 = tileAtlas.createSprite("Tile#");
    sprite2 = tileAtlas.createSprite("TileDot");
    sprite3 = tileAtlas.createSprite("Tile@");
    
    //TODO Remove this
    texture = new Texture(Gdx.files.internal("tiles/tiles.png"));
    texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    
    loadTextures();
  }
  
  private void loadTextures() {
    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("data/ernest.pack"));
    ernestIdle = atlas.findRegion("Frame-0");
    TextureRegion[] walkFrames = new TextureRegion[4];
    for (int i = 0; i < 4; i++) {
      walkFrames[i] = atlas.findRegion("Frame-" + i);
    }
    walkAnimation = new Animation(0.13f, walkFrames);
  }
  
  public void dispose() {
    batch.dispose();
    texture.dispose();
//    playerTexture.dispose();
  }
  
  private void renderArea(float delta) {
    for (int i = 0; i < area.tiles.length; i++) {
      for (int j = 0; j < area.tiles[i].length; j++) {
        if (area.tiles[j][i].id == 35) {
          sprite1.setPosition(area.tiles[j][i].x, area.tiles[j][i].y);
          sprite1.draw(batch);
        } else if (area.tiles[j][i].id == 46) {
          sprite2.setPosition(area.tiles[j][i].x, area.tiles[j][i].y);
          sprite2.draw(batch);
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
  
  public void render(float delta) {
    
    camera.update();
    
    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    
    renderArea(delta);
    renderPlayers(delta);
    
    time += delta;
    
    batch.end();
  }
}
