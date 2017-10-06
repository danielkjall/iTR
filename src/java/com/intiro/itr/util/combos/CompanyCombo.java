package com.intiro.itr.util.combos;

import com.intiro.itr.db.*;
import com.intiro.itr.util.*;
import com.intiro.itr.util.log.IntiroLog;
import com.intiro.itr.util.personalization.*;
import com.intiro.itr.util.xml.*;

public class CompanyCombo extends XMLCombo {

  /**
   * Constructor I for CompanyCombo. Creates a combo with a value = "null" and text = "Select project".
   *
   *
   * @param profile the UserProfile of the user.
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public CompanyCombo(UserProfile profile) throws XMLBuilderException {
    this(profile, "Select company");
  }

  /**
   * Constructor II for CompanyCombo. Creates a combo with a value = "null" and text = "---------", if makeNullEntry is true.
   *
   * @param profile the UserProfile of the user.
   * @param makeNullEntry a boolean specifying if empty entry should be added.
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public CompanyCombo(UserProfile profile, boolean makeNullEntry) throws XMLBuilderException {
    super(profile, makeNullEntry);
  }

  /**
   * Constructor III for CompanyCombo. Creates a combo with a value = "null" and text = nameOnNullEntry.
   *
   * @param profile the UserProfile of the user.
   * @param nameOnNullEntry a String specifying the name on the entry with value "null".
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public CompanyCombo(UserProfile profile, String nameOnNullEntry) throws XMLBuilderException {
    super(profile, nameOnNullEntry);
  }

  @Override
  public void load(String valueToBeSelected) throws XMLBuilderException {
    try {
      StringRecordset rs = null;
      rs = DBQueries.getProxy().getAllCompanies();

      while (!rs.getEOF()) {
        addEntry(rs.getField(DBConstants.COMPANY_ID_PK), rs.getField(DBConstants.COMPANY_NAME));
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
