package com.example.zigbeepolivalka.domain;

/**
 * Interface represents a mode of watering.
 * Each mode must manage only one property of a flower.
 */
public interface WateringMode {

  /**
   * Method returns a unique identifier of a mode
   * @return mode identifier
   */
  byte getModeId();

  /**
   * Method returns a value of a parameter managed by this mode.
   * @return mode parameter value
   */
  Integer getModeParameter();

  /**
   * Method sets a value of a parameter managed by this mode.
   * @param param new parameter value
   */
  void setModeParameter(Integer param);

  /**
   * Method returns an interval in which the valve should be open in seconds.
   * @return valve open time
   */
  Integer getDuration();

  /**
   * Method sets an interval in which the valve should be open in seconds.
   * @param duration time of valve being open
   */
  void setDuration(Integer duration);

}
