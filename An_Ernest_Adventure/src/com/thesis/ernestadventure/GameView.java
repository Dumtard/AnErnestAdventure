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

public class GameView {
  private SpriteBatch batch;
  private OrthographicCamera camera;
  
  private HashMap<String, Player> players;
  
  private Area area;
  
  //TODO Array?
  private Sprite sprite1;
  private Sprite sprite2;
  private Sprite sprite3;
  
  private Animation walkAnimation;
  private Animation idleAnimation;
  
  public GameView(SpriteBatch batch, OrthographicCamera camera, HashMap<String, Player> players, Area area) {
    this.batch = batch;
    this.camera = camera;
    this.players = players;
    this.area = area;
    
    sprite1 = new Sprite();
    sprite2 = new Sprite();
    sprite3 = new Sprite();
    
    TextureAtlas tileAtlas = new TextureAtlas(Gdx.files.internal("tiles/tiles.pack"));
    
    sprite1 = tileAtlas.createSprite("Tile#");
    sprite2 = tileAtlas.createSprite("TileDot");
    sprite3 = tileAtlas.createSprite("Tile@");
    
    loadTextures();
  }
  
  private void loadTextures() {
    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("data/packed/ernest.pack"));
    
    TextureRegion[] idleFrames = new TextureRegion[2];
    for (int i = 0; i < 2; i++) {
      idleFrames[i] = atlas.findRegion("Idle-" + i); 
    }
    
    TextureRegion[] walkFrames = new TextureRegion[2];
    for (int i = 0; i < 2; i++) {
      walkFrames[i] = atlas.findRegion("Walk-" + 0);
    }
    idleAnimation = new Animation(0.5f, idleFrames);
    walkAnimation = new Animation(0.13f, walkFrames);
  }
  
  private void renderArea(float delta) {
    for (int i = 0; i < area.height; i++) {
      for (int j = 0; j < area.width; j++) {
        if (area.tiles[j][i].id == 35) {
          sprite1.setPosition(area.tiles[j][i].x, area.tiles[j][i].y);
          sprite1.draw(batch);
        } else if (area.tiles[j][i].id == 46) {
          sprite2.setPosition(-100, -100);
          sprite2.draw(batch);
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
    // Loops through all players and renders them
    // Skips player 1 - Player 1 drawn afterwards to always be on top
    for (Map.Entry<String, Player> player: players.entrySet()) {
      if (!player.getKey().equals(ErnestGame.loginName)) {
        Player currentPlayer = player.getValue();
        //Walking animations
        if (currentPlayer.getVelocity().x != 0 &&
            currentPlayer.getIsGrounded()) { 
          if (currentPlayer.getIsFacingRight()) {
            TextureRegion currentFrame = walkAnimation.getKeyFrame(ErnestGame.GAMETIME, true);
            if (currentFrame.isFlipX()) {
              currentFrame.flip(true, false);
            }
            batch.draw(currentFrame, currentPlayer.getPosition().x,
                                     currentPlayer.getPosition().y,
                                     currentPlayer.getWidth(),
                                     currentPlayer.getHeight());
          } else {
            TextureRegion currentFrame = walkAnimation.getKeyFrame(ErnestGame.GAMETIME, true);
            if (!currentFrame.isFlipX()) {
              currentFrame.flip(true, false);
            }
            batch.draw(currentFrame, currentPlayer.getPosition().x,
                                     currentPlayer.getPosition().y,
                                     currentPlayer.getWidth(),
                                     currentPlayer.getHeight());
          }
        
        //Jumping animations
        } else if ((currentPlayer.getVelocity().x != 0 &&
                    !currentPlayer.getIsGrounded()) ||
                   (currentPlayer.getVelocity().x == 0 &&
                    !currentPlayer.getIsGrounded())) {
          if (currentPlayer.getIsFacingRight()) {
            TextureRegion currentFrame = walkAnimation.getKeyFrame(ErnestGame.GAMETIME, true);
            batch.draw(currentFrame, currentPlayer.getPosition().x,
                                     currentPlayer.getPosition().y,
                                     currentPlayer.getWidth(),
                                     currentPlayer.getHeight());
          } else {
            TextureRegion currentFrame = walkAnimation.getKeyFrame(ErnestGame.GAMETIME, true);
            currentFrame.flip(true, false);
            batch.draw(currentFrame, currentPlayer.getPosition().x,
                                     currentPlayer.getPosition().y,
                                     currentPlayer.getWidth(),
                                     currentPlayer.getHeight());
          }
          
        //Idle animations
        } else {
          TextureRegion currentFrame = idleAnimation.getKeyFrame(ErnestGame.GAMETIME, true);
          batch.draw(currentFrame, currentPlayer.getPosition().x,
                                   currentPlayer.getPosition().y,
                                   currentPlayer.getWidth(),
                                   currentPlayer.getHeight());
        }
      }
    }

    // Ensure player 1 is always visible
    
    //Walking animations
    if (players.get(ErnestGame.loginName).getVelocity().x != 0 &&
        players.get(ErnestGame.loginName).getIsGrounded()) { 
      if (players.get(ErnestGame.loginName).getIsFacingRight()) {
        TextureRegion currentFrame = walkAnimation.getKeyFrame(ErnestGame.GAMETIME, true);
        if (currentFrame.isFlipX()) {
          currentFrame.flip(true, false);
        }
        batch.draw(currentFrame, players.get(ErnestGame.loginName).getPosition().x,
                                 players.get(ErnestGame.loginName).getPosition().y,
                                 players.get(ErnestGame.loginName).getWidth(),
                                 players.get(ErnestGame.loginName).getHeight());
      } else {
        TextureRegion currentFrame = walkAnimation.getKeyFrame(ErnestGame.GAMETIME, true);
        if (!currentFrame.isFlipX()) {
          currentFrame.flip(true, false);
        }
        batch.draw(currentFrame, players.get(ErnestGame.loginName).getPosition().x,
                                 players.get(ErnestGame.loginName).getPosition().y,
                                 players.get(ErnestGame.loginName).getWidth(),
                                 players.get(ErnestGame.loginName).getHeight());
      }
    
    //Jumping animations
    } else if ((players.get(ErnestGame.loginName).getVelocity().x != 0 &&
                !players.get(ErnestGame.loginName).getIsGrounded()) ||
               (players.get(ErnestGame.loginName).getVelocity().x == 0 &&
                !players.get(ErnestGame.loginName).getIsGrounded())) {
      if (players.get(ErnestGame.loginName).getIsFacingRight()) {
        TextureRegion currentFrame = walkAnimation.getKeyFrame(ErnestGame.GAMETIME, true);
        if (currentFrame.isFlipX()) {
          currentFrame.flip(true, false);
        }
        batch.draw(currentFrame, players.get(ErnestGame.loginName).getPosition().x,
                                 players.get(ErnestGame.loginName).getPosition().y,
                                 players.get(ErnestGame.loginName).getWidth(),
                                 players.get(ErnestGame.loginName).getHeight());
      } else {
        TextureRegion currentFrame = walkAnimation.getKeyFrame(ErnestGame.GAMETIME, true);
        if (!currentFrame.isFlipX()) {
          currentFrame.flip(true, false);
        }
        batch.draw(currentFrame, players.get(ErnestGame.loginName).getPosition().x,
                                 players.get(ErnestGame.loginName).getPosition().y,
                                 players.get(ErnestGame.loginName).getWidth(),
                                 players.get(ErnestGame.loginName).getHeight());
      }
      
    //Idle animations
    } else {
      TextureRegion currentFrame = idleAnimation.getKeyFrame(ErnestGame.GAMETIME, true);
      batch.draw(currentFrame, players.get(ErnestGame.loginName).getPosition().x,
                               players.get(ErnestGame.loginName).getPosition().y,
                               players.get(ErnestGame.loginName).getWidth(),
                               players.get(ErnestGame.loginName).getHeight());
    }
  }
  
  public void updateCamera() {
    //Move Camera
    camera.position.set(players.get(ErnestGame.loginName).getPosition().x,
                        players.get(ErnestGame.loginName).getPosition().y,
                        camera.position.z);
    
    if (players.get(ErnestGame.loginName).getPosition().x > area.width*Tile.SIZE - (camera.viewportWidth/2)) {
      camera.position.set(area.width*Tile.SIZE - (camera.viewportWidth/2),
          camera.position.y,
          camera.position.z);
    } else if (players.get(ErnestGame.loginName).getPosition().x < (camera.viewportWidth/2)) {
      camera.position.set((camera.viewportWidth/2),
          camera.position.y,
          camera.position.z);
    }
    
    if (players.get(ErnestGame.loginName).getPosition().y > area.height*Tile.SIZE - (camera.viewportHeight/2)) {
      camera.position.set(camera.position.x,
          area.height*Tile.SIZE - (camera.viewportHeight/2),
          camera.position.z);
    } else if (players.get(ErnestGame.loginName).getPosition().y < (camera.viewportHeight/2)) {
      camera.position.set(camera.position.x,
          (camera.viewportHeight/2),
          camera.position.z);
    }
  }
  
  public void render(float delta) {
    renderArea(delta);
    renderPlayers(delta);
  }
  
}
