package com.example.zigbeepolivalka.services;

import com.digi.xbee.api.listeners.IDataReceiveListener;
import com.digi.xbee.api.models.XBeeMessage;
import com.digi.xbee.api.utils.HexUtils;
import com.example.zigbeepolivalka.domain.Flower;

import java.nio.ByteBuffer;
import java.util.List;

public class DataReceiverListener implements IDataReceiveListener {

  private List<Flower> flowers;

  public DataReceiverListener(List<Flower> flowers) {
    this.flowers = flowers;
  }

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
    if (mode == 101 || mode == 102) {
      short data = buf.getShort();
      fl.setCurrentMoistureLevel(data);
      System.out.format("From %s >> %s | %s%n", xBeeMessage.getDevice().get64BitAddress(),
                        data, new String(xBeeMessage.getData()));
    }
  }
}
