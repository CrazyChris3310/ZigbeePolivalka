package com.example.zigbeepolivalka.domain;

public abstract class AbstractMode implements WateringMode {

  private String duration;

  @Override
  public String getDuration() {
    return duration;
  }

  @Override
  public void setDuration(String duration) {
    this.duration = duration;
  }

}
