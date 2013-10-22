package com.thesis.ernestadventure;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.thesis.ernestadventure.Network.Login;
import com.thesis.ernestadventure.Network.Move;

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
          System.out.println(((Move) object).velocity.x + ", "
              + ((Move) object).velocity.y);

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
