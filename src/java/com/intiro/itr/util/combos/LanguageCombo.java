package com.intiro.itr.util.combos;

import com.intiro.itr.db.*;
import com.intiro.itr.util.*;
import com.intiro.itr.util.personalization.*;
import com.intiro.itr.util.xml.*;
import com.intiro.itr.util.log.IntiroLog;

public class LanguageCombo extends XMLCombo {

  /**
   * Constructor I for LanguageCombo. Creates a combo with a value = "null" and text = "Select language".
   *
   *
   * @param profile the UserProfile of the user.
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public LanguageCombo(UserProfile profile) throws XMLBuilderException {
    this(profile, "Select language");
  }

  /**
   * Constructor II for LanguageCombo. Creates a combo with a value = "null" and text = "---------", if makeNullEntry is true.
   *
   * @param profile the UserProfile of the user.
   * @param makeNullEntry a boolean specifying if empty entry should be added.
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public LanguageCombo(UserProfile profile, boolean makeNullEntry) throws XMLBuilderException {
    super(profile, makeNullEntry);
  }

  /**
   * Constructor III for LanguageCombo. Creates a combo with a value = "null" and text = nameOnNullEntry.
   *
   * @param profile the UserProfile of the user.
   * @param nameOnNullEntry a String specifying the name on the entry with value "null".
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public LanguageCombo(UserProfile profile, String nameOnNullEntry) throws XMLBuilderException {
    super(profile, nameOnNullEntry);
  }

  @Override
  public void load(String valueToBeSelected) throws XMLBuilderException {
    try {
      String cacheKey = getClass().getName() + ".getLanguages";
      String statisticKey = getClass().getName() + ".load";
      int cacheTime = 3600 * 10;
      InvocationHandlerSetting s = InvocationHandlerSetting.create(cacheKey, cacheTime, statisticKey);
      StringRecordset rs = DBQueriesConfig.getProxy(s).getLanguages();

      while (!rs.getEOF()) {
        addEntry(rs.getField(DBConstants.LANGUAGE_ID_PK), rs.getField(DBConstants.LANGUAGE_NAME));
        rs.moveNext();
      }

      setSelectedValue(valueToBeSelected);
      rs.close();
    } catch (Exception e) {
      if (IntiroLog.i()) {
        IntiroLog.info(getClass(), "ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(e.getMessage());
    }
  }
}
