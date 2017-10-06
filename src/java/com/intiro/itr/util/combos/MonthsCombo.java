package com.intiro.itr.util.combos;

import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.xml.XMLCombo;
import com.intiro.itr.util.log.IntiroLog;

public class MonthsCombo extends XMLCombo {

  String[] monthsENG = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
  String[] monthsSWE = {"Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "September", "Oktober", "November", "December"};

  /**
   * Constructor I for MonthsCombo. Creates a combo with a value = "null" and text = "Select Month".
   *
   * @param profile the UserProfile of the user.
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public MonthsCombo(UserProfile profile) throws XMLBuilderException {
    super(profile, "Select Month");
  }

  /**
   * Constructor II for MonthsCombo. Creates a combo with a value = "null" and text = "---------", if makeNullEntry is true.
   *
   * @param profile the UserProfile of the user.
   * @param makeNullEntry a boolean specifying if empty entry should be added.
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public MonthsCombo(UserProfile profile, boolean makeNullEntry) throws XMLBuilderException {
    super(profile, makeNullEntry);
  }

  /**
   * Constructor III for MonthsCombo. Creates a combo with a value = "null" and text = nameOnNullEntry.
   *
   * @param profile the UserProfile of the user.
   * @param nameOnNullEntry a String specifying the name on the entry with value "null".
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public MonthsCombo(UserProfile profile, String nameOnNullEntry) throws XMLBuilderException {
    super(profile, nameOnNullEntry);
  }

  @Override
  public void load(String valueToBeSelected) throws XMLBuilderException {
    try {

      // Langauge is set to english as default
      String[] months = monthsENG;

      if (this.userProfile.getLanguageCode().equalsIgnoreCase("sv")) {
        months = monthsSWE;
      } else if (this.userProfile.getLanguageCode().equalsIgnoreCase("en")) {
        months = monthsENG;
      }

      String tmp;

      for (int i = 0; i < 12; i++) {
        tmp = (i + 1 < 10) ? ("0" + (i + 1)) : ("" + (i + 1));
        addEntry(tmp, months[i]);
      }

      setSelectedValue(valueToBeSelected);
    } catch (Exception e) {
      if (IntiroLog.i()) {
        IntiroLog.info(getClass(), "Error creating months combo, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(e.getMessage());
    }
  }
}
