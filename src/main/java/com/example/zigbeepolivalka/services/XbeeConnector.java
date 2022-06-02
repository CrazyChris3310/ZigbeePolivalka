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

  private static final Logger log = LoggerFactory.getLogger(XbeeConnector.class);

  private XBeeDevice localDevice;

  public XbeeConnector(@Value("${local.xbee.device.port}") String port,
                       @Value("${local.xbee.device.baudrate}") int baudRate) {
    localDevice = new XBeeDevice(port, baudRate);
  }

  public void setFlowers(List<Flower> flowers) {
    try {
      localDevice.open();
      localDevice.addDataListener(new DataReceiverListener(flowers));
    } catch (XBeeException e) {
      System.out.println("Error opening connection. " + e.getMessage());
    }
  }

  public List<RemoteXBeeDevice> discoverNetwork() throws XBeeException {
    LoggerFactory.getLogger(XbeeConnector.class);
    List<RemoteXBeeDevice> network;
    XBeeNetwork net = localDevice.getNetwork();
    net.setDiscoveryTimeout(3000);
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

  public void sendData(RemoteXBeeDevice remote, byte modeId, short modeParameter) throws XBeeException {
    byte[] data = ByteBuffer.allocate(3).put(modeId).putShort(modeParameter).array();
    localDevice.sendData(remote, data);
  }
}
