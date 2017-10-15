package com.intiro.itr.logic.superadmin.users;

import com.intiro.itr.db.DBExecute;
import com.intiro.itr.db.InvocationHandlerSetting;
import com.intiro.itr.util.ResultDisplayer;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.XMLBuilderException;

public class UserEditActivator extends ResultDisplayer {

  String XML_FAILURE_MESSAGE = "Your user profile could NOT be changed! Contact your administrator";
  String XML_SUCCESS_MESSAGE = "Your user profile was successfully changed!";

  public UserEditActivator(UserProfile profile) throws XMLBuilderException {
    super(profile);
  }

  public String getFailureMessage() {
    return XML_FAILURE_MESSAGE;
  }

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
    String statisticKey = getClass().getName() + ".changeUseProfile";
    InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
    DBExecute.getProxy(s).updateUserProfile(getUserProfile());

    //Reload userProfile, is importent because it load a lot of good stuff.
    userProfile.reload();
  }
}
