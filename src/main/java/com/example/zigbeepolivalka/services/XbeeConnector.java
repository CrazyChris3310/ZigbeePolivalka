package com.example.zigbeepolivalka.services;

import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.XBeeNetwork;
import com.digi.xbee.api.exceptions.XBeeException;
import com.example.zigbeepolivalka.domain.Flower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.List;

@Component
public class XbeeConnector {

  public static final byte MODE_ID = 1;
  public static final byte WATERING_TIME_PARAM = 2;
  public static final byte MOISTURE_THRESHOLD_PARAM = 3;
  public static final byte WATER_AMOUNT = 4;

  private static final Logger log = LoggerFactory.getLogger(XbeeConnector.class);

  private XBeeDevice localDevice;

  public XbeeConnector(@Value("${local.xbee.device.port}") String port,
                       @Value("${local.xbee.device.baudrate}") int baudRate) {
    localDevice = new XBeeDevice(port, baudRate);
    try {
      localDevice.open();
    } catch (XBeeException e) {
      System.out.println("Error opening connection. " + e.getMessage());
    }
  }

  public void setFlowers(List<Flower> flowers) {
    localDevice.addDataListener(new DataReceiverListener(flowers));
  }

  public List<RemoteXBeeDevice> discoverNetwork() {
    LoggerFactory.getLogger(XbeeConnector.class);
    List<RemoteXBeeDevice> network;
    XBeeNetwork net = localDevice.getNetwork();
    net.startDiscoveryProcess();
    while (net.isDiscoveryRunning()) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        break;
      }
    }
    log.info("Started discovering network");
    network = net.getDevices();

    return network;
  }

  public void sendData(RemoteXBeeDevice remote, byte command, short parameter) throws XBeeException {
    byte[] data = ByteBuffer.allocate(3).put(command).putShort(parameter).array();
    localDevice.sendData(remote, data);
  }
}
