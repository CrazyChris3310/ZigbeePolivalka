package com.example.zigbeepolivalka.services;

import com.digi.xbee.api.XBeeDevice;
import com.digi.xbee.api.exceptions.TimeoutException;
import com.digi.xbee.api.exceptions.XBeeException;
import com.example.zigbeepolivalka.domain.Flower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class XbeePinger implements Runnable {

  private Logger log = LoggerFactory.getLogger(XbeePinger.class);
  private List<Flower> flowers;
  private XBeeDevice localDevice;
  private Lock lock;

  public XbeePinger(List<Flower> flowers, XBeeDevice localDevice, Lock lock) {
    this.flowers = flowers;
    this.localDevice = localDevice;
    this.lock = lock;
  }

  @Override
  public void run() {
    while(true) {
      lock.lock();
      try {
        for (Iterator<Flower> it = flowers.iterator(); it.hasNext(); ) {
          Flower fl = it.next();
          try {
            fl.getRemoteXBeeDevice().readDeviceInfo();
          } catch (XBeeException e) {
            localDevice.getNetwork().removeRemoteDevice(fl.getRemoteXBeeDevice());
            it.remove();
            log.warn("Remote Xbee device " + fl.getRemoteXBeeDevice().get64BitAddress()
                     + " was removed. " + e.getMessage());
          }
        }
      } finally {
        lock.unlock();
      }
      try {
        Thread.sleep(10 * 1000);
      } catch (InterruptedException e) {
        log.error("Thread interrupted" + e.getMessage());
      }
    }
  }
}
