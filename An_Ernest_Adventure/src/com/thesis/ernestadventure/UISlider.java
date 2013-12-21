package com.thesis.ernestadventure;

public class UISlider {
  float position;
  
  UISlider() {
    position = 0;
  }
  
  void setPosition(float position) {
    if (position < -1.0f) {
      this.position = -1.0f;
    } else if (position > 1.0f) {
      this.position = 1.0f;
    } else {
      this.position = position;
    }
  }
  
  float getPosition() {
    return position;
  }
}
