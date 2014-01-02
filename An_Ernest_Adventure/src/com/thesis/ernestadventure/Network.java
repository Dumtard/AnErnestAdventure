package com.thesis.ernestadventure;

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
}
