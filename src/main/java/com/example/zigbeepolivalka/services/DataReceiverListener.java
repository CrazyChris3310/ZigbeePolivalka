package com.example.zigbeepolivalka.services;

import com.digi.xbee.api.listeners.IDataReceiveListener;
import com.digi.xbee.api.models.XBeeMessage;
import com.digi.xbee.api.utils.HexUtils;

public class DataReceiverListener implements IDataReceiveListener {
  @Override
  public void dataReceived(XBeeMessage xBeeMessage) {
    System.out.format("From %s >> %s | %s%n", xBeeMessage.getDevice().get64BitAddress(),
                      HexUtils.prettyHexString(HexUtils.byteArrayToHexString(xBeeMessage.getData())),
                      new String(xBeeMessage.getData()));
  }
}
