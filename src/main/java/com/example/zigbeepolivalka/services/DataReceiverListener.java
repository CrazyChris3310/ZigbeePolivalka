package com.example.zigbeepolivalka.services;

import com.digi.xbee.api.listeners.IDataReceiveListener;
import com.digi.xbee.api.models.XBeeMessage;
import com.example.zigbeepolivalka.domain.Flower;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Class is used for receiving messages from {@link com.digi.xbee.api.RemoteXBeeDevice}
 * and update corresponding flower.
 */
public class DataReceiverListener implements IDataReceiveListener {

  private List<Flower> flowers;

  /**
   * Creates a listener with a collection of flowers in the network.
   * @param flowers collection of flowers in the network.
   */
  public DataReceiverListener(List<Flower> flowers) {
    this.flowers = flowers;
  }

  /**
   * Method is called when new message is received
   * from some {@link com.digi.xbee.api.RemoteXBeeDevice}. It parses the message
   * and updates data if flower that corresponds to device that sent message.
   * @param xBeeMessage message received from {@code RemoteXBeeDevice}
   */
  @Override
  public void dataReceived(XBeeMessage xBeeMessage) {
    Flower fl = flowers.stream()
            .filter(device -> device.getId().equals(
                    xBeeMessage.getDevice().get64BitAddress().toString()))
            .findAny().orElse(null);
    if (fl == null || !fl.isSelected()) {
      return;
    }
    ByteBuffer buf = ByteBuffer.wrap(xBeeMessage.getData());
    byte mode = buf.get();
    if (mode == 101) {
      short data = buf.getShort();
      fl.setCurrentMoistureLevel((int)((data / 1024.0) * 100));
      System.out.format("From %s >> %s | %s%n", xBeeMessage.getDevice().get64BitAddress(),
                        data, new String(xBeeMessage.getData()));
    }
  }
}
