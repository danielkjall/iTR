/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic.setting;

import com.intiro.itr.util.ResultDisplayer;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.XMLBuilderException;

public class PasswordSaverActivator extends ResultDisplayer {

  //~ Constructors .....................................................................................................

  public PasswordSaverActivator(UserProfile profile) throws XMLBuilderException {
    super(profile);
  }

  //~ Methods ..........................................................................................................

  /**
   * This the method that retrives the message to use for failure.
   */
  public String getFailureMessage() {
    return "Your password could NOT be changed! Contact your administrator";
  }

  /**
   * This the method that retrives the message to use for success.
   */
  public String getSuccessMessage() {
    return "Your password was successfully changed!";
  }

  public void changePassword(String newLoginId, String newPassword) throws Exception {

    /*If we manage to change the password*/
    success = dbExecute.changePassword(newLoginId, newPassword);

    if (success) {
      userProfile.setLoginId(newLoginId);
      userProfile.setPassword(newPassword);
    }
  }
}