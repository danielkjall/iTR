package com.intiro.itr.util.logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItrLogger {

  private static final ItrLogger instance = new ItrLogger();
  private static boolean use = false;
  private static final ObjectMapper mapper = new ObjectMapper();

  public static ItrLogger getInstance() {
    return instance;
  }

  public boolean isUse() {
    return use;
  }

  public void setUse(boolean useLog) {
    use = useLog;
  }

  public static ObjectMapper getMapper() {
    return mapper;
  }

  private final int poolSize = 10;

  private final Map<Integer, List<ItrLogEntry>> lists = new HashMap<>();

  public void log(ItrLogEntry entry) {
    if (!use) {
      return;
    }
    int listId = (int) Math.floor(Math.random() * poolSize);
    List<ItrLogEntry> list = getList(listId);
    list.add(entry);
  }

  public List<ItrLogEntry> fetchAndClearEntries() {

    List<List<ItrLogEntry>> _lists = new ArrayList<>();

    synchronized (lists) {
      for (List<ItrLogEntry> list : lists.values()) {
        _lists.add(list);
      }
      lists.clear();
    }

    List<ItrLogEntry> ret = new ArrayList<>();

    for (List<ItrLogEntry> list : _lists) {
      ret.addAll(list);
    }

    return ret;
  }

  private List<ItrLogEntry> getList(int id) {

    List<ItrLogEntry> list;

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

}
