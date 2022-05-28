package com.example.zigbeepolivalka.domain;

public class MoistureMode extends AbstractMode {

  private String moistureLevel;

  @Override
  public int getModeId() {
    return 1;
  }

  @Override
  public String getModeParameter() {
    return moistureLevel;
  }

  @Override
  public void setModeParameter(String param) {
    this.moistureLevel = param;
  }

}
