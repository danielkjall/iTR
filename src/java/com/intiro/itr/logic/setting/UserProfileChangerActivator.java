package com.intiro.itr.logic.setting;

import com.intiro.itr.db.DBExecute;
import com.intiro.itr.db.InvocationHandlerSetting;
import com.intiro.itr.util.ResultDisplayer;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.XMLBuilderException;

public class UserProfileChangerActivator extends ResultDisplayer {

  public UserProfileChangerActivator(UserProfile profile) throws XMLBuilderException {
    super(profile);
  }

  public String getFailureMessage() {
    return "Your user profile could NOT be changed! Contact your administrator";
  }

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
    String statisticKey = getClass().getName() + ".changeUserProfile";
    InvocationHandlerSetting s = InvocationHandlerSetting.create(statisticKey);
    DBExecute.getProxy(s).updateUserProfile(getUserProfile());

    //Reload userProfile, is importent because it load a lot of good stuff.
    userProfile.reload();
  }
}
