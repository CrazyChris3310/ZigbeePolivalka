package com.example.zigbeepolivalka.services;

import com.example.zigbeepolivalka.domain.Flower;
import com.example.zigbeepolivalka.exceptions.NoSuchFlowerException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ZigBeeService {

  List<Flower> flowers = new ArrayList<>();

  public List<Flower> getConnectedDevices() {
    return flowers;
  }

  public Flower getFlowerByName(String name) throws NoSuchFlowerException {
    return flowers.stream()
            .filter(flower -> flower.getName().equals(name))
            .findAny()
            .orElseThrow(NoSuchFlowerException::new);
  }

  public Flower getFlowerById(int id) throws NoSuchFlowerException {
    return flowers.stream()
            .filter(flower -> flower.getId() == id)
            .findAny()
            .orElseThrow(NoSuchFlowerException::new);
  }

  public Flower addFlower(Flower flower) {
    flowers.add(flower);
    return flower;
  }

  public void updateFlower(int id, Flower newFlower) throws NoSuchFlowerException {
    Flower oldFlower = flowers.stream()
                                .filter(flower -> flower.getId() == id)
                                .findAny()
                                .orElseThrow(NoSuchFlowerException::new);

    oldFlower.setName(newFlower.getName());
    oldFlower.setWateringMode(newFlower.getWateringMode());
  }

  public void removeFlower(int id) {
    flowers.removeIf(flower -> flower.getId() == id);
  }

}
