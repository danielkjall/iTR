package com.intiro.itr.util.combos;

import com.intiro.itr.db.DBQueriesConfig;
import com.intiro.itr.db.InvocationHandlerSetting;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.xml.XMLCombo;
import com.intiro.itr.util.log.IntiroLog;

public class YearsCombo extends XMLCombo {

  /**
   * Constructor I for YearsCombo. Creates a combo with a value = "null" and text = "Select Year".
   *
   * @param profile the UserProfile of the user.
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public YearsCombo(UserProfile profile) throws XMLBuilderException {
    this(profile, "Select Year");
  }

  /**
   * Constructor II for YearsCombo. Creates a combo with a value = "null" and text = "---------", if makeNullEntry is true.
   *
   * @param profile the UserProfile of the user.
   * @param makeNullEntry a boolean specifying if empty entry should be added.
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public YearsCombo(UserProfile profile, boolean makeNullEntry) throws XMLBuilderException {
    super(profile, makeNullEntry);
  }

  /**
   * Constructor III for YearsCombo. Creates a combo with a value = "null" and text = nameOnNullEntry.
   *
   * @param profile the UserProfile of the user.
   * @param nameOnNullEntry a String specifying the name on the entry with value "null".
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public YearsCombo(UserProfile profile, String nameOnNullEntry) throws XMLBuilderException {
    super(profile, nameOnNullEntry);
  }

  @Override
  public void load(String valueToBeSelected) throws XMLBuilderException {
    try {
      String cacheKey = getClass().getName() + ".getReportYears";
      String statisticKey = getClass().getName() + ".load";
      int cacheTime = 3600 * 10;
      InvocationHandlerSetting s = InvocationHandlerSetting.create(cacheKey, cacheTime, statisticKey);
      StringRecordset rs = DBQueriesConfig.getProxy(s).getReportYears();

      while (!rs.getEOF()) {
        addEntry(rs.getField("year"), rs.getField("year"));
        rs.moveNext();
      }

      setSelectedValue(valueToBeSelected);
      rs.close();
    } catch (Exception e) {
      if (IntiroLog.i()) {
        IntiroLog.info(getClass(), getClass().getName() + ".load(): ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(e.getMessage());
    }
  }
}
