package com.thesis.ernestadventure;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

// This class is a convenient place to keep things common to both the client and server.
public class Network {
	static public final int port = 54555;

	// This registers objects that are going to be sent over the network.
	static public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(Connect.class);
		kryo.register(Disconnect.class);
		kryo.register(Vector2.class);
		kryo.register(Move.class);
		kryo.register(Stop.class);
		kryo.register(Shoot.class);
    kryo.register(HashMap.class);
    kryo.register(ArrayList.class);
    kryo.register(String.class);
    kryo.register(Integer.class);
    kryo.register(Float.class);
    kryo.register(Boolean.class);    
    kryo.register(Enemy.class);
    kryo.register(BomberEnemy.class);
    kryo.register(Player.class);
    kryo.register(Tile[][].class);
    kryo.register(Tile[].class);
    kryo.register(Tile.class);
    kryo.register(Area.class);
//    kryo.register(int.class);
//    kryo.register(float.class);
//    kryo.register(boolean.class);
    kryo.register(Initialize.class);
    kryo.register(EnemyUpdate.class);
    kryo.register(BomberEnemyUpdate.class);
	}

	static public class Connect {
		public String name;
	}
	
	static public class Disconnect {
	  public String name;
	}
	
	static public class Move {
	  public String name;
	  public Vector2 position;
		public Vector2 velocity;
	}
	
	static public class Stop {
	  public String name;
	  public Vector2 position;
	}
	
	static public class Shoot {
	  public String name;
    public Vector2 position;
	}
	
	static public class Initialize {
	  public Integer area;
	  public ArrayList<Enemy> enemies;
	  public HashMap<String, Player> players;
	}
	
	static public class EnemyUpdate {
	  public Integer index;
	  public Boolean isFacingRight;
	  public Vector2 position;
	  public Vector2 velocity;
	}
	
	static public class BomberEnemyUpdate {
    public Integer index;
    public Boolean isFacingRight;
    public Vector2 position;
    public Vector2 velocity;
    public Boolean attacking;
  }
	
//	static public class AreaSize() {
//	  public 
//	}
}
