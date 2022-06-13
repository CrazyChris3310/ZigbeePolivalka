package com.example.zigbeepolivalka.domain;

/**
 * Class represents watering mode where a flower is watered
 * only when certain amount of time will expire.
 */
public class TimeMode extends AbstractMode {

  private Integer wateringTime;

  /**
   * Creates a mode with a given time interval to wait until watering in minutes.
   * @param wateringTime interval of time to wait
   */
  public TimeMode(Integer wateringTime) {
    this.wateringTime = wateringTime;
  }

  public TimeMode() {
  }

  @Override
  public byte getModeId() {
    return 2;
  }

  /**
   * Method returns time interval that should be sustained before watering in minutes.
   * @return time interval
   */
  @Override
  public Integer getModeParameter() {
    return wateringTime;
  }

  /**
   * Method sets time interval that should be sustained before watering in minutes.
   * @param param interval of time to wait
   */
  @Override
  public void setModeParameter(Integer param) {
    this.wateringTime = param;
  }
}
