package com.intiro.itr.logic.setting;

import com.intiro.itr.db.DBExecute;
import com.intiro.itr.db.InvocationHandlerSetting;
import com.intiro.itr.util.ResultDisplayer;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.XMLBuilderException;

public class PasswordSaverActivator extends ResultDisplayer {

  public PasswordSaverActivator(UserProfile profile) throws XMLBuilderException {
    super(profile);
  }

  public String getFailureMessage() {
    return "Your password could NOT be changed! Contact your administrator";
  }

  public String getSuccessMessage() {
    return "Your password was successfully changed!";
  }

  public void changePassword(String newLoginId, String newPassword) throws Exception {

    /*If we manage to change the password*/
    String statisticKey = getClass().getName() + ".changePassword";
    InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
    success = DBExecute.getProxy(s).changePassword(getUserProfile().getUserId(), newLoginId, newPassword);

    if (success) {
      userProfile.setLoginId(newLoginId);
      userProfile.setPassword(newPassword);
    }
  }
}
