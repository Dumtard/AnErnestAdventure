package com.thesis.ernestadventure;

public class UIJumpButton {
  boolean pressed;
  
  UIJumpButton() {
    pressed = false;
  }
  
  void setPressed(boolean pressed) {
    this.pressed = pressed;
  }
  
  boolean getPressed() {
    return pressed;
  }
}
