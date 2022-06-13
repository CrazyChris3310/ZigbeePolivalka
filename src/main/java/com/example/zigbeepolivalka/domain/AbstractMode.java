package com.example.zigbeepolivalka.domain;

/**
 * Very basic implementation of {@link WateringMode}
 * where duration of valve opening time is handled.
 */
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
