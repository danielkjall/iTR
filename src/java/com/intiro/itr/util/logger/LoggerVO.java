package com.intiro.itr.util.logger;

import java.util.Date;

public class LoggerVO {

  private int userId;
  private int anropstidInMs;
  private String felmeddelande;
  private String inParameter;
  private String utParameter;
  private String metodnamn;
  private String sessionId;
  private Date timestamp;

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getAnropstidInMs() {
    return anropstidInMs;
  }

  public void setAnropstidInMs(int anropstidInMs) {
    this.anropstidInMs = anropstidInMs;
  }

  public String getFelmeddelande() {
    if (felmeddelande != null && felmeddelande.length() > 5999) {
      felmeddelande = felmeddelande.substring(0, 5999);
    }
    return felmeddelande;
  }

  public void setFelmeddelande(String felmeddelande) {
    this.felmeddelande = felmeddelande;
  }

  public String getInParameter() {
    if (inParameter != null && inParameter.length() > 5999) {
      inParameter = inParameter.substring(0, 5999);
    }
    return inParameter;
  }

  public void setInParameter(String inParameter) {
    this.inParameter = inParameter;
  }

  public String getUtParameter() {
    if (utParameter != null && utParameter.length() > 5999) {
      utParameter = utParameter.substring(0, 5999);
    }
    return utParameter;
  }

  public void setUtParameter(String utParameter) {
    this.utParameter = utParameter;
  }

  public String getMetodnamn() {
    return metodnamn;
  }

  public void setMetodnamn(String metodnamn) {
    this.metodnamn = metodnamn;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

}
