package com.thesis.ernestadventure;

import java.util.HashMap;

import com.esotericsoftware.kryonet.Client;

public class UIController {
  private UI ui;
  private Client client;
  private HashMap<String, Player> players;
  
  public UIController(UI ui, Client client, HashMap<String, Player> players) {
    this.ui = ui;
    this.client = client;
    this.players = players;
  }
  
  public void update(float delta) {
    ui.toString();
    client.toString();
    players.toString();
  }
}
