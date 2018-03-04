package com.intiro.itr.util.personalization;

public class GamificationSetting {

  private boolean wantReminderMail = false;
  private boolean wantAchievementMail = false;
  private boolean wantLateMail = false;
  private boolean wantPointMail = false;
  private String email;

  public boolean isWantReminderMail() {
    return wantReminderMail;
  }

  public void setWantReminderMail(String status) {
    if (statusIsTrue(status)) {
      this.wantReminderMail = true;
    } else {
      this.wantReminderMail = false;
    }
  }

  public boolean isWantAchievementMail() {
    return wantAchievementMail;
  }

  public void setWantAchievementMail(String status) {
    if (statusIsTrue(status)) {
      this.wantAchievementMail = true;
    } else {
      this.wantAchievementMail = false;
    }
  }

  public boolean isWantLateMail() {
    return wantLateMail;
  }

  public void setWantLateMail(String status) {
    if (statusIsTrue(status)) {
      this.wantLateMail = true;
    } else {
      this.wantLateMail = false;
    }
  }

  public boolean isWantPointMail() {
    return wantPointMail;
  }

  public void setWantPointMail(String status) {
    if (statusIsTrue(status)) {
      this.wantPointMail = true;
    } else {
      this.wantPointMail = false;
    }
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  private boolean statusIsTrue(String status) {
    return (status != null && status.trim().equals("1")) || (status != null && status.trim().equalsIgnoreCase("True")) || (status != null && status.trim().equalsIgnoreCase("on"));
  }

}
