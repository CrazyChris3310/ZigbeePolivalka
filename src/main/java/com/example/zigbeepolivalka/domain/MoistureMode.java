package com.example.zigbeepolivalka.domain;

public class MoistureMode extends AbstractMode {

  private Integer moistureLevel;

  public MoistureMode(String moistureLevel) {
    this.moistureLevel = moistureLevel;
  }

  public MoistureMode() {
  }

  @Override
  public int getModeId() {
    return 1;
  }

  @Override
  public Integer getModeParameter() {
    return moistureLevel;
  }

  @Override
  public void setModeParameter(Integer param) {
    this.moistureLevel = param;
  }

}
