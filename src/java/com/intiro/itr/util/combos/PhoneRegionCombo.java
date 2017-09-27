/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.util.combos;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.xml.XMLCombo;
import com.intiro.toolbox.log.IntiroLog;

public class PhoneRegionCombo extends XMLCombo {

  //~ Constructors .....................................................................................................

  /**
   * Constructor I for PhoneRegionCombo.
   * Creates a combo with a value = "null" and text = "Select regioncode".
   *
   *
   * @param    profile                the UserProfile of the user.
   */
  public PhoneRegionCombo() throws XMLBuilderException {
    this("Select regioncode");
  }

  /**
   * Constructor II for PhoneRegionCombo.
   * Creates a combo with a value = "null" and text = "---------",
   * if makeNullEntry is true.
   *
   * @param    profile                the UserProfile of the user.
   * @param makeNullEntry        a boolean specifying if empty entry should be added.
   */
  public PhoneRegionCombo(boolean makeNullEntry) throws XMLBuilderException {
    super(makeNullEntry);
  }

  /**
   * Constructor III for PhoneRegionCombo.
   * Creates a combo with a value = "null" and text = nameOnNullEntry.
   *
   * @param    profile                the UserProfile of the user.
   * @param nameOnNullEntry        a String specifying the name on the entry with value "null".
   */
  public PhoneRegionCombo(String nameOnNullEntry) throws XMLBuilderException {
    super(nameOnNullEntry);
  }

  //~ Methods ..........................................................................................................

  public void load(String valueToBeSelected) throws XMLBuilderException {
    try {
      StringRecordset rs = dbQuery.getAllPhoneRegions();

      while (!rs.getEOF()) {
        addEntry(rs.getField(DBConstants.PHONEREGIONCODE_ID_PK), rs.getField(DBConstants.PHONEREGIONCODE_REGIONCODE) + " (" + rs.getField(DBConstants.PHONEREGIONCODE_REGIONNAME) + ")");
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