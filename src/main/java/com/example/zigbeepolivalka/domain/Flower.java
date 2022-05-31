package com.example.zigbeepolivalka.domain;

public class Flower {

  private int id;
  private String name;
  private AbstractMode wateringMode;
  private int currentMoistureLevel;

  public Flower(String name, AbstractMode wateringMode) {
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

  public AbstractMode getWateringMode() {
    return wateringMode;
  }

  public void setWateringMode(AbstractMode wateringMode) {
    this.wateringMode = wateringMode;
  }

  public int getCurrentMoistureLevel() {
    return currentMoistureLevel;
  }

  public void setCurrentMoistureLevel(int currentMoistureLevel) {
    this.currentMoistureLevel = currentMoistureLevel;
  }


  @Override
  public String toString() {
    return "Flower{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", wateringMode=" + wateringMode +
            ", currentMoistureLevel=" + currentMoistureLevel +
            '}';
  }
}
