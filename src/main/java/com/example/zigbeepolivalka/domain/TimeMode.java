package com.example.zigbeepolivalka.domain;

public class TimeMode extends AbstractMode {

  private Integer wateringTime;

  public TimeMode(Integer wateringTime) {
    this.wateringTime = wateringTime;
  }

  public TimeMode() {
  }

  @Override
  public byte getModeId() {
    return 2;
  }

  @Override
  public Integer getModeParameter() {
    return wateringTime;
  }

  @Override
  public void setModeParameter(Integer param) {
    this.wateringTime = param;
  }
}
