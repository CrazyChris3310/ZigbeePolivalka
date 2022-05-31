package com.example.zigbeepolivalka.domain;

public abstract class AbstractMode implements WateringMode {

  private Integer duration;

  @Override
  public Integer getDuration() {
    return duration;
  }

  @Override
  public void setDuration(Integer duration) {
    this.duration = duration;
  }

}
