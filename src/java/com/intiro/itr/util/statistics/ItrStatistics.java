package com.intiro.itr.util.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItrStatistics {

  private static final ItrStatistics instance = new ItrStatistics();
  public final static String STATUS_SUCCESS = "SUCCESS";
  public final static String STATUS_CACHED_VALUE = "CACHED_VALUE";
  public final static String STATUS_FAILED = "FAILED";

  public static ItrStatistics getInstance() {
    return instance;
  }

  private final int listSize = 10;
  private final Map<Integer, List<ItrStatistic>> lists = new HashMap<>();

  private boolean use;

  public boolean isUse() {
    return use;
  }

  public void setUse(boolean use) {
    this.use = use;
  }

  public void addGeneric(String action, String methodCalled, String status) {
    int listId = (int) Math.floor(Math.random() * listSize);

    List<ItrStatistic> list = getList(listId);

    list.add(new ItrStatistic(action, methodCalled, status));
  }

  private List<ItrStatistic> getList(int id) {
    List<ItrStatistic> list;

    list = lists.get(id);
    if (list != null) {
      return list;
    }
    synchronized (lists) {
      list = lists.get(id);
      if (list != null) {
        return list;
      }

      list = Collections.synchronizedList(new ArrayList<>());
      lists.put(id, list);
    }

    return list;

  }

  public Map<ItrStatistic, Integer> getResult() {

    Map<ItrStatistic, Integer> map = new HashMap<>();

    List<List<ItrStatistic>> _lists = new ArrayList<>();

    synchronized (lists) {
      for (List<ItrStatistic> list : lists.values()) {
        _lists.add(list);
      }
      lists.clear();
    }

    for (List<ItrStatistic> list : _lists) {
      for (ItrStatistic stat : list) {
        int count = map.containsKey(stat) ? map.get(stat) : 0;
        map.put(stat, ++count);
      }
    }

    return map;
  }
}
