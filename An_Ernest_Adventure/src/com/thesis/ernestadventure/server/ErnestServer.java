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
        Connection[] connections = new Connection[10];
        connections = server.getConnections();
        
        if (object instanceof Login) {
          System.out.println(((Login) object).name + " has logged on.");
          
          System.out.println(connections.length + " users currently online.");
          
          for (int i = 0; i < connections.length; i++) {
            if (c.getRemoteAddressUDP().getAddress() != connections[i].getRemoteAddressUDP().getAddress()) {
              connections[i].sendUDP(object);
            }
          }
          
          return;
        } else if (object instanceof Move) {
          for (int i = 0; i < connections.length; i++) {
            if (c.getRemoteAddressUDP().getAddress() != connections[i].getRemoteAddressUDP().getAddress()) {
              connections[i].sendUDP(object);
            }
          }
          
          return;
        } else if (object instanceof Stop) {
          for (int i = 0; i < connections.length; i++) {
            if (c.getRemoteAddressUDP().getAddress() != connections[i].getRemoteAddressUDP().getAddress()) {
              connections[i].sendUDP(object);
            }
          }
          
          return;
        }
      }
    });

    server.bind(54555, 54555);
    server.start();
  }

  public static void main(String[] args) throws IOException {
    Log.set(Log.LEVEL_DEBUG);
    new ErnestServer();
  }
}
