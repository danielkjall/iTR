/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intiro.itr.util.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LockHelper {

  private static final Map<String, Object> parallelLockMap = new ConcurrentHashMap<String, Object>();

  public static Object getLockingObject(String key) {
    Object lock = new Object();
    if (parallelLockMap != null) {

      if (parallelLockMap.containsKey(key) == false) {
        parallelLockMap.put(key, new Object());
      }

      lock = parallelLockMap.get(key);

    }
    return lock;
  }
}