package com.example.zigbeepolivalka.domain;

import java.io.Serializable;

/**
 * Very basic implementation of {@link WateringMode}
 * where duration of valve opening time is handled.
 */
public abstract class AbstractMode implements WateringMode, Serializable {

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
