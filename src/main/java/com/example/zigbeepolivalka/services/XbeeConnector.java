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
import java.util.concurrent.locks.Lock;

/**
 * Class is used to work with XbeeDevices, send data to them and scan zigbee network.
 */
@Component
public class XbeeConnector {

  /**
   * Property for changing mode.
   */
  public static final byte MODE_ID = 1;
  /**
   * Property for changing time interval between watering.
   */
  public static final byte WATERING_TIME_PARAM = 2;
  /**
   * Property for changing moisture threshold.
   */
  public static final byte MOISTURE_THRESHOLD_PARAM = 3;
  /**
   * Property for changing time interval when valve will be open.
   */
  public static final byte VALVE_OPEN_TIME = 4;

  private static final Logger log = LoggerFactory.getLogger(XbeeConnector.class);

  private XBeeDevice localDevice;
  private Lock lock;

  /**
   * Creates {@code XbeeConnector} with the local device connected to this PC.
   * @param port USB port name to which local XBee device is connected
   * @param baudRate baud rate of local XBee device
   */
  public XbeeConnector(@Value("${local.xbee.device.port}") String port,
                       @Value("${local.xbee.device.baudrate}") int baudRate) {
    localDevice = new XBeeDevice(port, baudRate);
    try {
      localDevice.open();
    } catch (XBeeException e) {
      log.error("Error opening connection. " + e.getMessage());
    }
  }

  public void setFlowers(List<Flower> flowers) {
    localDevice.addDataListener(new DataReceiverListener(flowers));
    Thread th = new Thread(new XbeePinger(flowers, localDevice, lock));
    th.setDaemon(true);
    th.start();
  }

  /**
   * Method scans the network and searches for all {@link RemoteXBeeDevice}'s in it.
   * @return list of all remote devices in the network
   */
  public List<RemoteXBeeDevice> discoverNetwork() {
    LoggerFactory.getLogger(XbeeConnector.class);
    List<RemoteXBeeDevice> network;
    XBeeNetwork net = localDevice.getNetwork();
    try {
      net.startDiscoveryProcess();
      log.info("Started discovering network");
    } catch (IllegalStateException ignored) {}
    while (net.isDiscoveryRunning()) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        break;
      }
    }
    network = net.getDevices();

    return network;
  }

  /**
   * Method updates given property with a new value on the given remote device.
   * @param remote {@link RemoteXBeeDevice} where to update parameter value
   * @param command property that should be updated
   * @param parameter new value of a property
   * @throws XBeeException if it's impossible to send data
   */
  public void sendData(RemoteXBeeDevice remote, byte command, short parameter) throws XBeeException {
    byte[] data = ByteBuffer.allocate(3).put(command).putShort(parameter).array();
    localDevice.sendData(remote, data);
    log.info("Parameter " + command + " with value " + parameter + " is set on device "
             + remote.get64BitAddress());
  }

  public void setLock(Lock lock) {
    this.lock = lock;
  }
}
