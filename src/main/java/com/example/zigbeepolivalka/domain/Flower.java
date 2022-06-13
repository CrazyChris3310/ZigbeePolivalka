package com.example.zigbeepolivalka.domain;

import com.digi.xbee.api.RemoteXBeeDevice;

import java.util.Objects;

public class Flower {

  private String id;
  private String name;
  private WateringMode wateringMode;
  private int currentMoistureLevel;
  private RemoteXBeeDevice remoteXBeeDevice;
  private boolean isSelected;
  private byte valveOpenTime;

  public Flower(String name, WateringMode wateringMode) {
    this.name = name;
    this.wateringMode = wateringMode;
    this.valveOpenTime = 5;
  }

  public Flower(String id, String name, WateringMode wateringMode) {
    this.id = id;
    this.name = name;
    this.wateringMode = wateringMode;
    this.valveOpenTime = 5;
  }

  public Flower(RemoteXBeeDevice device) {
    this.remoteXBeeDevice = device;
    this.id = remoteXBeeDevice.get64BitAddress().toString();
    this.name = this.id;
    this.isSelected = false;
    this.wateringMode = new MoistureMode(0);
    this.valveOpenTime = 5;
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

  public boolean isSelected() {
    return isSelected;
  }

  public void setSelected(boolean selected) {
    isSelected = selected;
  }

  public RemoteXBeeDevice getRemoteXBeeDevice() {
    return remoteXBeeDevice;
  }

  public void setRemoteXBeeDevice(RemoteXBeeDevice remoteXBeeDevice) {
    this.remoteXBeeDevice = remoteXBeeDevice;
  }

  public byte getValveOpenTime() {
    return valveOpenTime;
  }

  public void setValveOpenTime(byte valveOpenTime) {
    this.valveOpenTime = valveOpenTime;
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
