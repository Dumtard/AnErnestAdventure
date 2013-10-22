package com.thesis.ernestadventure.server;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.thesis.ernestadventure.Network;
import com.thesis.ernestadventure.Network.Login;
import com.thesis.ernestadventure.Network.Move;
import com.thesis.ernestadventure.Network.Stop;

public class ErnestServer {
  Server server;

  public ErnestServer() throws IOException {
    server = new Server();

    Network.register(server);

    server.addListener(new Listener() {
      public void received(Connection c, Object object) {
        if (object instanceof Login) {
          System.out.println(((Login) object).name);

          return;
        } else if (object instanceof Move) {
          System.out.println("Moving speed: " + ((Move) object).velocity.x + ", "
              + ((Move) object).velocity.y);

          return;
        } else if (object instanceof Stop) {
          System.out.println("Stopped at: " + ((Stop) object).position.x + ", "
              + ((Stop) object).position.y);
          
          return;
        }
      }
    });

    server.bind(54555);
    server.start();
  }

  public static void main(String[] args) throws IOException {
    Log.set(Log.LEVEL_DEBUG);
    new ErnestServer();
  }
}
