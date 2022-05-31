package com.example.zigbeepolivalka.domain;

public interface WateringMode {

  int getModeId();

  Integer getModeParameter();

  void setModeParameter(Integer param);

  Integer getDuration();

  void setDuration(Integer duration);

}
