package com.example.zigbeepolivalka.domain;

import com.digi.xbee.api.RemoteXBeeDevice;

import java.util.Objects;

public class Flower {

  private String id;
  private String name;
  private AbstractMode wateringMode;
  private int currentMoistureLevel;
  private RemoteXBeeDevice remoteXBeeDevice;
  private boolean isSelected;

  public Flower(String name, AbstractMode wateringMode) {
    this.name = name;
    this.wateringMode = wateringMode;
  }

  public Flower(String id, String name, AbstractMode wateringMode) {
    this.id = id;
    this.name = name;
    this.wateringMode = wateringMode;
  }

  public Flower(RemoteXBeeDevice device) {
    this.remoteXBeeDevice = device;
    this.id = remoteXBeeDevice.get64BitAddress().toString();
    this.isSelected = false;
  }

  public String getId() {
    return id;
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

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean selected) {
    isSelected = selected;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    Flower flower = (Flower) o;
    return Objects.equals(id, flower.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
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
