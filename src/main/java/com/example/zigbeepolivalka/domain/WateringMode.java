package com.example.zigbeepolivalka.domain;

public interface WateringMode {

  int getModeId();

  String getModeParameter();

  void setModeParameter(String param);

  String getDuration();

  void setDuration(String duration);

}
