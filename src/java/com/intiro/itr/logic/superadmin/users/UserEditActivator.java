/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic.superadmin.users;

import com.intiro.itr.util.ResultDisplayer;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.XMLBuilderException;

public class UserEditActivator extends ResultDisplayer {

  //~ Instance/static variables ........................................................................................

  String XML_FAILURE_MESSAGE = "Your user profile could NOT be changed! Contact your administrator";
  String XML_SUCCESS_MESSAGE = "Your user profile was successfully changed!";

  //~ Constructors .....................................................................................................

  public UserEditActivator(UserProfile profile) throws XMLBuilderException {
    super(profile);
  }

  //~ Methods ..........................................................................................................

  /**
   * This the method that retrives the message to use for failure.
   */
  public String getFailureMessage() {
    return XML_FAILURE_MESSAGE;
  }

  /**
   * This the method that retrives the message to use for success.
   */
  public String getSuccessMessage() {
    return XML_SUCCESS_MESSAGE;
  }

  public void changeUserProfile(String firstName, String lastName, String languageId, String skinId) throws Exception {
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