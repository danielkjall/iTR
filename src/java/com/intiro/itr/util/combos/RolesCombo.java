package com.intiro.itr.util.combos;

import com.intiro.itr.db.*;
import com.intiro.itr.util.*;
import com.intiro.itr.util.personalization.*;
import com.intiro.itr.util.xml.*;
import com.intiro.itr.util.log.IntiroLog;

public class RolesCombo extends XMLCombo {

  /**
   * Constructor I for RolesCombo. Creates a combo with a value = "null" and text = "Select role".
   *
   * @param inUserProfile
   */
  public RolesCombo(UserProfile inUserProfile) throws XMLBuilderException {
    this("Select role");
  }

  /**
   * Constructor II for RolesCombo. Creates a combo with a value = "null" and text = "---------", if makeNullEntry is true.
   *
   * @param makeNullEntry a boolean specifying if empty entry should be added.
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public RolesCombo(boolean makeNullEntry) throws XMLBuilderException {
    super(makeNullEntry);
  }

  /**
   * Constructor III for RolesCombo. Creates a combo with a value = "null" and text = nameOnNullEntry.
   *
   * @param nameOnNullEntry a String specifying the name on the entry with value "null".
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public RolesCombo(String nameOnNullEntry) throws XMLBuilderException {
    super(nameOnNullEntry);
  }

  @Override
  public void load(String valueToBeSelected) throws XMLBuilderException {
    try {
      StringRecordset rs = DBQueries.getProxy().getRoles();

      while (!rs.getEOF()) {
        addEntry(rs.getField(DBConstants.ROLES_ID_PK), rs.getField(DBConstants.ROLES_NAME));
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
