package com.example.zigbeepolivalka.services;

import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;
import com.example.zigbeepolivalka.domain.Flower;
import com.example.zigbeepolivalka.exceptions.NoSuchFlowerException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.zigbeepolivalka.services.XbeeConnector.MOISTURE_THRESHOLD_PARAM;
import static com.example.zigbeepolivalka.services.XbeeConnector.WATERING_TIME_PARAM;
import static com.example.zigbeepolivalka.services.XbeeConnector.WATER_AMOUNT;

@Service
public class ZigBeeService {

  // TODO: if the device is powered off, it should be removed from flowers list
  private final XbeeConnector connector;

  private List<Flower> flowers = new ArrayList<>();

  public ZigBeeService(XbeeConnector connector) {
    this.connector = connector;
    this.connector.setFlowers(flowers);
  }

  public List<Flower> getFlowers() {
    return flowers.stream()
            .filter(Flower::isSelected)
            .collect(Collectors.toList());
  }

  public Flower getFlowerById(String id) throws NoSuchFlowerException {
    return flowers.stream()
            .filter(flower -> flower.getId().equals(id))
            .findAny()
            .orElseThrow(NoSuchFlowerException::new);
  }

  public void updateFlower(String id, Flower newFlower) throws NoSuchFlowerException,
          XBeeException {
    Flower oldFlower = getFlowerById(id);

    oldFlower.setName(newFlower.getName());
    oldFlower.setWateringMode(newFlower.getWateringMode());

    connector.sendData(oldFlower.getRemoteXBeeDevice(),
                       XbeeConnector.MODE_ID,
                       oldFlower.getWateringMode().getModeId());

    if (oldFlower.getWateringMode().getModeId() == 1) {
      connector.sendData(oldFlower.getRemoteXBeeDevice(),
                         MOISTURE_THRESHOLD_PARAM,
                         (short)(oldFlower.getWateringMode().getModeParameter().shortValue()
                                 * 1024 / 100));
    } else {
      connector.sendData(oldFlower.getRemoteXBeeDevice(),
                         WATERING_TIME_PARAM,
                         oldFlower.getWateringMode().getModeParameter().shortValue());
    }
    connector.sendData(oldFlower.getRemoteXBeeDevice(),
                       WATER_AMOUNT,
                       oldFlower.getValveOpenTime());
  }

  public void removeFlower(String id) throws NoSuchFlowerException, XBeeException {
    Flower toRemove = getFlowerById(id);
    flowers.remove(toRemove);
    connector.sendData(toRemove.getRemoteXBeeDevice(), (byte)3, (short)0);
  }

  // TODO: Exception handling must happen here, return status should be discussed
  public List<Flower> getAvailableFlowers() throws XBeeException {
    List<RemoteXBeeDevice> devices = connector.discoverNetwork();
    List<Flower> available = devices.stream()
            .map(Flower::new)
            .collect(Collectors.toList());
    available.removeAll(this.flowers);
    flowers.addAll(available);
    return flowers.stream()
            .filter(dev -> !dev.isSelected())
            .collect(Collectors.toList());
  }

  public void selectFlowers(Collection<String> ids){
    for (Flower flower: flowers) {
      if (ids.contains(String.valueOf(flower.getId()))) {
        flower.setSelected(true);
      }
    }
  }
}
