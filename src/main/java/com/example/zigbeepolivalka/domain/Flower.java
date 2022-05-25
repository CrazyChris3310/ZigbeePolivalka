package com.example.zigbeepolivalka.domain;

public class Flower {

  private int id;
  private String name;
  private WateringMode wateringMode;
  private int currentMoistureLevel;

  public Flower(String name, WateringMode wateringMode) {
    this.name = name;
    this.wateringMode = wateringMode;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public WateringMode getWateringMode() {
    return wateringMode;
  }

  public void setWateringMode(WateringMode wateringMode) {
    this.wateringMode = wateringMode;
  }

  public int getCurrentMoistureLevel() {
    return currentMoistureLevel;
  }

  public void setCurrentMoistureLevel(int currentMoistureLevel) {
    this.currentMoistureLevel = currentMoistureLevel;
  }
}
