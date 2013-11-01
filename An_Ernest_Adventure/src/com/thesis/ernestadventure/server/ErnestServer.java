package com.thesis.ernestadventure.server;

import java.io.IOException;
import java.util.Vector;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.thesis.ernestadventure.Network;
import com.thesis.ernestadventure.Network.Connect;
import com.thesis.ernestadventure.Network.Disconnect;
import com.thesis.ernestadventure.Network.Move;
import com.thesis.ernestadventure.Network.Stop;

public class ErnestServer {
  Server server;
  Vector<String> connectionNames = new Vector<String>();
  Vector<Connection> connections = new Vector<Connection>();

  public ErnestServer() throws IOException {
    server = new Server();

    Network.register(server);
    
    server.addListener(new Listener() {
      public void disconnected(Connection c) {
        String disconnected = "";
        for (int i = 0; i < connections.size(); i++) {
          if (connections.get(i) == c) {
            disconnected = connectionNames.get(i);
            connections.remove(i);
            connectionNames.remove(i);
            break;
          }
        }
        Disconnect disconnect = new Disconnect();
        disconnect.name = disconnected;
        server.sendToAllTCP(disconnect);
      }
      
      public void received(Connection c, Object object) {
        if (object instanceof Connect) {
          System.out.println(((Connect) object).name + " has logged on.");
          connections.add(c);
          System.out.println(connections.get(connections.size()-1).getRemoteAddressTCP().getAddress());
          connectionNames.add(((Connect) object).name);

          for (int i = 0; i < connections.size(); i++) {
            if (c.getRemoteAddressTCP().getAddress() != 
                connections.get(i).getRemoteAddressTCP().getAddress()) {
              connections.get(i).sendTCP(object);
            }
          }
          
          for (int i = 0; i < connectionNames.size(); i++) {
            if (c.getRemoteAddressTCP().getAddress() != 
                connections.get(i).getRemoteAddressTCP().getAddress()) {
              Connect login = new Connect();
              login.name = connectionNames.get(i);
              c.sendTCP(login);
            }
          }
          System.out.println(connections.size() + " users currently online.");
          return;
        
        } else if (object instanceof Move) {
          for (int i = 0; i < connections.size(); i++) {
            if (c.getRemoteAddressUDP().getAddress() != 
                connections.get(i).getRemoteAddressUDP().getAddress()) {
              connections.get(i).sendUDP(object);
            }
          }
          return;
          
        } else if (object instanceof Stop) {
          for (int i = 0; i < connections.size(); i++) {
            if (c.getRemoteAddressUDP().getAddress() != 
                connections.get(i).getRemoteAddressUDP().getAddress()) {
              connections.get(i).sendUDP(object);
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
