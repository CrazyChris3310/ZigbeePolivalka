package com.example.zigbeepolivalka.domain;

public class TimeMode extends AbstractMode {

  private Integer wateringTime;

  @Override
  public int getModeId() {
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
