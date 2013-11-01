package utils;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

public class ErnestPacker {
  public static void main (String[] args) throws Exception {
    TexturePacker2.process("../An_Ernest_Adventure-android/assets/data", "../An_Ernest_Adventure-android/assets/data", "ernest.pack");
  }
}