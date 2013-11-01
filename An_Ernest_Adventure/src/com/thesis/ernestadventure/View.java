package com.thesis.ernestadventure;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
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

  private Texture playerTexture;
  private Sprite playerSprite;
  private Sprite player2Sprite;
  
  //TODO Remove these textures and sprites
  private Texture texture;
  private Sprite sprite;
  
  private float time = 0.0f;
  
  /** Textures **/
  private TextureRegion ernestIdle;
  
  /** Animations **/
  private Animation walkAnimation;
  
  public View(HashMap<String, Player> players) {
    width = Gdx.graphics.getWidth();
    height = Gdx.graphics.getHeight();
    
    batch = new SpriteBatch();
    
    this.players = players;
    
    camera = new OrthographicCamera(width, height);
    camera.translate(width / 2, height / 2);
    
    playerTexture = new Texture(Gdx.files.internal("data/ernest.png"));
    playerTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

    TextureRegion playerRegion = new TextureRegion(playerTexture, 0, 0, 32, 64);

    playerSprite = new Sprite(playerRegion);
    playerSprite.setOrigin(playerSprite.getWidth() / 2, playerSprite.getHeight() / 2);
    player2Sprite = new Sprite(playerRegion);
    player2Sprite.setOrigin(player2Sprite.getWidth() / 2, player2Sprite.getHeight() / 2);
    
    //TODO Remove this
    texture = new Texture(Gdx.files.internal("data/libgdx.png"));
    texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

    TextureRegion region = new TextureRegion(texture, 0, 0, 800, 96);

    sprite = new Sprite(region);
    sprite.setPosition(0, 0);
    
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
    playerTexture.dispose();
  }
  
  public void render(float delta) {
    
    camera.update();
    
    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    for (Map.Entry<String, Player> player: players.entrySet()) {
//      player.getValue().draw(batch);
//      player2Sprite.setPosition(player.getValue().getPosition().x,
//                               player.getValue().getPosition().y);
//      player2Sprite.draw(batch);
    }
//    playerSprite.setPosition(players.get("dumtard").getPosition().x,
//        players.get("dumtard").getPosition().y);
//    
//    playerSprite.draw(batch);
    time += delta;
    
    TextureRegion bobFrame = walkAnimation.getKeyFrame(time, true);
    batch.draw(bobFrame, players.get("dumtard").getPosition().x, players.get("dumtard").getPosition().y, 32, 64);
    
    sprite.draw(batch);
    batch.end();
  }
}
