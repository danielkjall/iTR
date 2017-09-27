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

public class UserProfileChangerActivator extends ResultDisplayer {

  //~ Instance/static variables ........................................................................................

  //~ Constructors .....................................................................................................

  public UserProfileChangerActivator(UserProfile profile) throws XMLBuilderException {
    super(profile);
  }

  //~ Methods ..........................................................................................................

  /**
   * This the method that retrives the message to use for failure.
   */
  public String getFailureMessage() {
    return "Your user profile could NOT be changed! Contact your administrator";
  }

  /**
   * This the method that retrives the message to use for success.
   */
  public String getSuccessMessage() {
    return "Your user profile was successfully changed!";
  }

  public void changeUserProfile(String firstName, String lastName, String languageId, String skinId) throws Exception {

    /*If we manage to change the password*/
    if (firstName != null) {
      getUserProfile().setFirstName(firstName);
    }
    if (lastName != null) {
      getUserProfile().setLastName(lastName);
    }
    if (languageId != null) {
      getUserProfile().setLanguageId(languageId);
    }
    if (skinId != null) {
      getUserProfile().setSkinId(skinId);
    }

    //Execute update
    dbExecute.updateUserProfile(getUserProfile());

    //Reload userProfile, is importent because it load a lot of good stuff.
    userProfile.reload();
  }
}