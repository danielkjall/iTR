package com.intiro.itr.util.statistics;

import java.util.Date;

public class StatisticVO {

  private String kategori;
  private String action;
  private String status;
  private int count;
  private Date timestamp;

  public String getKategori() {
    return kategori;
  }

  public void setKategori(String kategori) {
    this.kategori = kategori;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

}
