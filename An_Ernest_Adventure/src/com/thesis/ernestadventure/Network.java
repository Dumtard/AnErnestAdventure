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
		kryo.register(Login.class);
		kryo.register(Move.class);
		kryo.register(Vector2.class);
	}

	static public class Login {
		public String name;
	}
	
	static public class Move {
		public Vector2 velocity;
	}
}
