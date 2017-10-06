package com.intiro.itr.util.logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.intiro.itr.util.ItrUtil;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ItrLogEntry {

  private String sessionId;
  private String userId;
  private String key;
  private Object[] inParams;
  private Object outParams;
  private Throwable error;
  private int anropstidInMs;
  private Date timestamp;

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getUserId() {
    return userId;
  }

  public void setAktor(String aktor) {
    this.userId = aktor;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public Object[] getIn() {
    return inParams;
  }

  public void setIn(Object[] in) {
    this.inParams = in;
  }

  public Object getOut() {
    return outParams;
  }

  public void setOut(Object out) {
    this.outParams = out;
  }

  public Throwable getError() {
    return error;
  }

  public void setError(Throwable error) {
    this.error = error;
  }

  public int getAnropstidInMs() {
    return anropstidInMs;
  }

  public void setAnropstidInMs(int anropstidInMs) {
    this.anropstidInMs = anropstidInMs;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  private String getInParametersAsJson() {
    List<String> in = new ArrayList<>();

    if (getIn() == null) {
      return null;
    }

    for (Object obj : getIn()) {

      try {
        if (obj != null) {
          in.add("[" + ItrLogger.getMapper().writeValueAsString(obj) + "]");
        } else {
          in.add("NULL");
        }
      } catch (Exception e) {
        in.add("[Error: " + e.getMessage() + "]");
      }
    }

    return String.join(",", in.toArray(new String[in.size()]));

  }

  private StringBuilder logObject(Object obj) throws Exception {
    StringBuilder sb = new StringBuilder();

    if (obj != null) {
      sb.append(ItrLogger.getMapper().writeValueAsString(obj));
    } else {
      sb.append("NULL");
    }

    return sb;
  }

  private String getOutAsString() {
    String out;

    try {
      out = logObject(getOut()).toString();
    } catch (Exception e) {
      out = "[Error: " + e.getMessage() + "]";
    }

    return out;
  }

  private String getStacktrace() {
    if (getError() == null) {
      return null;
    }

    StringWriter errors = new StringWriter();
    getError().printStackTrace(new PrintWriter(errors));

    return errors.toString();
  }

  public LoggerVO getVO() {
    LoggerVO vo = new LoggerVO();

    vo.setUserId((ItrUtil.isStrNullOrEmpty(getUserId())) ? -1 : Integer.parseInt(getUserId()));
    vo.setAnropstidInMs(getAnropstidInMs());
    vo.setFelmeddelande(getStacktrace());
    vo.setInParameter(getInParametersAsJson());
    vo.setMetodnamn(getKey());
    vo.setSessionId(getSessionId());
    vo.setTimestamp(getTimestamp());
    vo.setUtParameter(getOutAsString());

    return vo;
  }
}
