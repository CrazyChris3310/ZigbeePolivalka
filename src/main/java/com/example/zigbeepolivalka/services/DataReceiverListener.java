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
    byte data = buf.get();
    fl.setCurrentMoistureLevel(data);
    System.out.format("From %s >> %s | %s%n", xBeeMessage.getDevice().get64BitAddress(),
                      HexUtils.prettyHexString(HexUtils.byteArrayToHexString(xBeeMessage.getData())),
                      new String(xBeeMessage.getData()));
  }
}
