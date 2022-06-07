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

@Service
public class ZigBeeService {

  private final XbeeConnector connector;

  private List<Flower> flowers = new ArrayList<>();

  public ZigBeeService(XbeeConnector connector) {
    this.connector = connector;
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

  public void updateFlower(String id, Flower newFlower) throws NoSuchFlowerException {
    Flower oldFlower = flowers.stream()
                                .filter(flower -> flower.getId().equals(id))
                                .findAny()
                                .orElseThrow(NoSuchFlowerException::new);

    oldFlower.setName(newFlower.getName());
    oldFlower.setWateringMode(newFlower.getWateringMode());
  }

  public void removeFlower(String id) {
    flowers.removeIf(flower -> flower.getId().equals(id));
  }

  // TODO: Exception handling must happen here, return status should be discussed
  public List<Flower> getAvailableFlowers() throws XBeeException {
    List<RemoteXBeeDevice> devices = connector.discoverNetwork();
    List<Flower> available = devices.stream()
            .map(Flower::new)
            .collect(Collectors.toList());
    available.retainAll(this.flowers);
    flowers.addAll(available);
    return available;
  }

  public void selectFlowers(Collection<String> ids){
    for (Flower flower: flowers) {
      if (ids.contains(String.valueOf(flower.getId()))) {
        flower.setSelected(true);
      }
    }
  }
}
