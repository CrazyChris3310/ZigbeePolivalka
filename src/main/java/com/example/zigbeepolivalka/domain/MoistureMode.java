package com.example.zigbeepolivalka.domain;

/**
 * Class represents watering mode where a flower is watered
 * only when a moisture level on the flower reaches specified threshold.
 */
public class MoistureMode extends AbstractMode {

  private Integer moistureLevel;

  /**
   * Creates a mode with a given moisture threshold.
   * @param moistureLevel moisture threshold (between 0 and 100)
   */
  public MoistureMode(Integer moistureLevel) {
    this.moistureLevel = moistureLevel;
  }

  public MoistureMode() {
  }

  @Override
  public byte getModeId() {
    return 1;
  }

  /**
   * Method returns a moisture level after which flower should be watered.
   * @return moisture threshold
   */
  @Override
  public Integer getModeParameter() {
    return moistureLevel;
  }

  /**
   * Method sets a moisture level after which flower should be watered.
   * @param param moisture threshold (between 0 and 100)
   */
  @Override
  public void setModeParameter(Integer param) {
    this.moistureLevel = param;
  }

}
