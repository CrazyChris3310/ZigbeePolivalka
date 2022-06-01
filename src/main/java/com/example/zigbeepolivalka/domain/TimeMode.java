package com.example.zigbeepolivalka.domain;

public class TimeMode extends AbstractMode {

  private String wateringTime;

  public TimeMode(String wateringTime) {
    this.wateringTime = wateringTime;
  }

  public TimeMode() {
  }

  @Override
  public int getModeId() {
    return 2;
  }

  @Override
  public String getModeParameter() {
    return wateringTime;
  }

  @Override
  public void setModeParameter(String param) {
    this.wateringTime = param;
  }
}
