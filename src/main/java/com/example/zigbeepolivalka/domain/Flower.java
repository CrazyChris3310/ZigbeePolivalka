package com.example.zigbeepolivalka.domain;

import com.digi.xbee.api.RemoteXBeeDevice;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class is a program representation of a real {@link RemoteXBeeDevice}.
 * Id of each flower is MAC address of corresponding XbeeDevice.
 * Each flower may be tracked by program, which means it will dynamically update it's data
 * and corresponding XbeeDevice can be managed by it.
 */
public class Flower {

  private String id;
  private String name;
  private WateringMode wateringMode;
  private int currentMoistureLevel;
  private RemoteXBeeDevice remoteXBeeDevice;
  private boolean isSelected;
  private byte valveOpenTime;

  /**
   * Creates a flower with a given name and watering mode.
   * This should be used to create a stub flower that will contain new data to update
   * an existing flower in {@link com.example.zigbeepolivalka.services.ZigBeeService}
   * @param name name of a flower
   * @param wateringMode mode of watering, by time or by moisture threshold
   */
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

  /**
   * Creates a flower based on a raw data that can be retrieved
   * from connected {@link RemoteXBeeDevice}. The name in this case is the same as id and
   * default watering mode is {@link MoistureMode} with param 0. Device in this case is not
   * selected to be tracked.
   * @param device XbeeDevice that is represented by creating flower
   */
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

  /**
   * Select a flower to be tracked or not.
   * @param selected new state of being selected
   */
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
