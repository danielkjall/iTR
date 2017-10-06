package com.intiro.itr.util.cache;

import com.intiro.itr.util.log.IntiroLog;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ItrCache {

  private static int purgeIntervalInSeconds = 15;

  /**
   * Only used for unit testing
   *
   * @param seconds
   */
  public static void setPurgeInterval(int seconds) {
    purgeIntervalInSeconds = seconds;
  }

  private static boolean _purgingInitialized = false;

  public static void initilizeCachePurge() {
    if (_purgingInitialized == false) {
      _purgingInitialized = true;
      Thread t = new Thread(new Runnable() {
        @Override
        public void run() {
          while (true) {
            try {
              Thread.sleep(purgeIntervalInSeconds * 1000);
            } catch (InterruptedException ex) {
            }
            purgeExpiredItems();
          }
        }
      });

      t.setDaemon(true);
      t.start();
    }
  }

  private static void purgeExpiredItems() {
    for (String key : _cache.keySet()) {
      CachedObject entry = _cache.get(key);

      if (entry != null && entry.hasExpired()) {
        _cache.remove(key);
      }
    }

  }

  private static Map<String, CachedObject> _cache = new ConcurrentHashMap<String, CachedObject>();

  public static <T> void put(String key, T value) {
    initilizeCachePurge();
    CachedObject co = new CachedObject(value);
    _cache.put(key, co);
  }

  public static <T> void put(String key, T value, int cacheTimeInSeconds) {
    initilizeCachePurge();
    CachedObject co = new CachedObject(value, cacheTimeInSeconds);
    _cache.put(key, co);
  }

  public static <T> void remove(String key) {
    _cache.remove(key);
  }

  @SuppressWarnings("unchecked")
  public static <T> T get(String key) {
    if (_cache.containsKey(key) == false) {
      return null;
    }
    CachedObject co = _cache.get(key);
    try {
      T obj = (T) co.getValue();
      return obj;
    } catch (Exception ex) {
      IntiroLog.warning(ItrCache.class, ex.getMessage());
    }
    return null;
  }

  public static void clear() {
    try {
      _cache.clear();
    } catch (Exception e) {
      // Swallow exception when clearing cache
      IntiroLog.warning(ItrCache.class, "Failed during clean of cache, e: " + e.getMessage());
    }
  }

  public static <T> T getAndExtend(String key, int extendSeconds) {
    if (_cache.containsKey(key) == false) {
      return null;
    }
    CachedObject co = _cache.get(key);
    try {
      @SuppressWarnings("unchecked")
      T obj = (T) co.getValue();
      co.addExpire(extendSeconds);
      return obj;
    } catch (Exception ex) {
      IntiroLog.warning(ItrCache.class, ex.getMessage());
    }
    return null;
  }
}
