package com.example.zigbeepolivalka.domain;

import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.XBeeNetwork;
import com.digi.xbee.api.exceptions.XBeeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class XbeeConnector {

  private static final Logger log = LoggerFactory.getLogger(XbeeConnector.class);

  @Value("${local.xbee.device.port}")
  private static String PORT;
  @Value("${local.xbee.device.baudrate}")
  private static int BAUD_RATE;

  private XBeeDevice localDevice;

  public XbeeConnector() {
    localDevice = new XBeeDevice(PORT, BAUD_RATE);
  }

  public List<RemoteXBeeDevice> discoverNetwork() throws XBeeException {
    LoggerFactory.getLogger(XbeeConnector.class);
    List<RemoteXBeeDevice> network;
    localDevice.open();
    XBeeNetwork net = localDevice.getNetwork();
    net.setDiscoveryTimeout(3000);
    net.startDiscoveryProcess();
    log.info("Started discovering network");
    network = net.getDevices();

    return network;
  }

  public Object receiveData() {
    return new Object();
  }

  public void sendData(String id) {

  }
}
