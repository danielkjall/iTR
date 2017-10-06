package com.intiro.itr.util.combos;

import com.intiro.itr.db.*;
import com.intiro.itr.util.*;
import com.intiro.itr.util.personalization.*;
import com.intiro.itr.util.xml.*;
import com.intiro.itr.util.log.IntiroLog;

public class TimeTypeCombo extends XMLCombo {

  /**
   * Constructor I for TimeTypeCombo. Creates a combo with a value = "null" and text = "Select".
   *
   *
   * @param profile the UserProfile of the user.
   */
  public TimeTypeCombo(UserProfile profile) throws XMLBuilderException {
    this(profile, "Select");
  }

  /**
   * Constructor II for TimeTypeCombo. Creates a combo with a value = "null" and text = "---------", if makeNullEntry is true.
   *
   * @param profile the UserProfile of the user.
   * @param makeNullEntry a boolean specifying if empty entry should be added.
   */
  public TimeTypeCombo(UserProfile profile, boolean makeNullEntry) throws XMLBuilderException {
    super(profile, makeNullEntry);
  }

  /**
   * Constructor III for TimeTypeCombo. Creates a combo with a value = "null" and text = nameOnNullEntry.
   *
   * @param profile the UserProfile of the user.
   * @param nameOnNullEntry a String specifying the name on the entry with value "null".
   */
  public TimeTypeCombo(UserProfile profile, String nameOnNullEntry) throws XMLBuilderException {
    super(profile, nameOnNullEntry);
  }

  @Override
  public void load(String valueToBeSelected) throws XMLBuilderException {
    try {
      StringRecordset rs = DBQueries.getProxy().getTimeTypes();

      while (!rs.getEOF()) {
        addEntry(rs.getField(DBConstants.TIMETYPE_ID_PK), rs.getField(DBConstants.TIMETYPE_TYPE));
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
