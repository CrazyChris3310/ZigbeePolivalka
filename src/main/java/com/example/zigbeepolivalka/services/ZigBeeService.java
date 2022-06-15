package com.example.zigbeepolivalka.services;

import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.exceptions.XBeeException;
import com.example.zigbeepolivalka.domain.Flower;
import com.example.zigbeepolivalka.exceptions.NoSuchFlowerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static com.example.zigbeepolivalka.services.XbeeConnector.MOISTURE_THRESHOLD_PARAM;
import static com.example.zigbeepolivalka.services.XbeeConnector.WATERING_TIME_PARAM;
import static com.example.zigbeepolivalka.services.XbeeConnector.VALVE_OPEN_TIME;

/**
 * {@code ZigBeeService} provides methods for CRUD operations with flowers.
 */
@Service
public class ZigBeeService {

  // TODO: if the device is powered off, it should be removed from flowers list
  private final XbeeConnector connector;

  private List<Flower> flowers = new ArrayList<>();
  private Lock lock;


  /**
   * Creates {@code ZigBeeService} with a given {@link XbeeConnector}.
   */
  public ZigBeeService(XbeeConnector connector) throws IOException, ClassNotFoundException {
    this.connector = connector;
    readFromFile();
    this.connector.setFlowers(flowers);
    this.lock = new ReentrantLock();
  }

  private void readFromFile() throws IOException, ClassNotFoundException {
    FileInputStream fis = new FileInputStream("flower_data");
    ObjectInputStream ois = new ObjectInputStream(fis);
    this.flowers = (List<Flower>) ois.readObject();
  }

  @PreDestroy
  public void saveDataOfFlowers() throws IOException {
    FileOutputStream fos = new FileOutputStream("flower_data");
    ObjectOutputStream oos = new ObjectOutputStream(fos);
    oos.writeObject(flowers);
    oos.flush();
  }

  /**
   * Method returns all tracked flowers, i.e. they are selected to be tracked.
   * @return list of tracked flowers
   */
  public List<Flower> getFlowers() {
    return flowers.stream()
            .filter(Flower::isSelected)
            .collect(Collectors.toList());
  }

  /**
   * Method returns flower with given id
   * @throws NoSuchFlowerException if flower with such id does not exist
   */
  public Flower getFlowerById(String id) throws NoSuchFlowerException {
    return flowers.stream()
            .filter(flower -> flower.getId().equals(id))
            .findAny()
            .orElseThrow(NoSuchFlowerException::new);
  }

  /**
   * Method updates parameters like watering mode or mode on existing flower.
   * @param id identifier of a flower to be updated
   * @param newFlower flower with new data that should be written to updating flower
   * @throws NoSuchFlowerException if flower with such id does not exist
   * @throws XBeeException if problems with sending data to remote device happened.
   */
  public void updateFlower(String id, Flower newFlower) throws NoSuchFlowerException,
          XBeeException {
    Flower oldFlower = getFlowerById(id);

    oldFlower.setName(newFlower.getName());
    oldFlower.setWateringMode(newFlower.getWateringMode());
    oldFlower.setValveOpenTime(newFlower.getValveOpenTime());

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
                       VALVE_OPEN_TIME,
                       oldFlower.getValveOpenTime());
  }

  /**
   * Method removes flower with given id from tracked state.
   * @param id identifier of a removing flower
   * @throws NoSuchFlowerException if flower with such id does not exist
   * @throws XBeeException if connection problems happened
   */
  public void removeFlower(String id) throws NoSuchFlowerException, XBeeException {
    Flower toRemove = getFlowerById(id);
    flowers.remove(toRemove);
    connector.sendData(toRemove.getRemoteXBeeDevice(), (byte)3, (short)0);
  }

  // TODO: Exception handling must happen here, return status should be discussed

  /**
   * Method return all untracked flowers connected to the same zigbee network.
   * If new flowers were added to the network they will be included to the returned list.
   * @return flowers which aren't tracked yet
   * @throws XBeeException if network scan fails
   */
  public List<Flower> getAvailableFlowers() throws XBeeException {
    List<RemoteXBeeDevice> devices = connector.discoverNetwork();
    List<Flower> available = devices.stream()
            .map(Flower::new)
            .collect(Collectors.toList());
    available.removeAll(this.flowers);
    lock.lock();
    try {
      flowers.addAll(available);
      return flowers.stream()
              .filter(dev -> !dev.isSelected())
              .collect(Collectors.toList());
    } finally {
      lock.unlock();
    }
  }

  /**
   * Method selects flowers with given ids to be tracked.
   * @param ids identifiers of flowers to be tracked
   */
  public void selectFlowers(Collection<String> ids){
    for (Flower flower: flowers) {
      if (ids.contains(String.valueOf(flower.getId()))) {
        flower.setSelected(true);
      }
    }
  }
}
