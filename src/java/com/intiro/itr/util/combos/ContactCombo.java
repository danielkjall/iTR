/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.util.combos;

import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.xml.XMLCombo;
import com.intiro.toolbox.log.IntiroLog;

public class ContactCombo extends XMLCombo {

  //~ Constructors .....................................................................................................

  /**
   * Constructor I for ContactCombo.
   * Creates a combo with a value = "null" and text = "Select contact".
   *
   *
   * @param    profile                the UserProfile of the user.
   */
  public ContactCombo(UserProfile profile) throws XMLBuilderException {
    this(profile, "Select contact");
  }

  /**
   * Constructor II for ContactCombo.
   * Creates a combo with a value = "null" and text = "---------",
   * if makeNullEntry is true.
   *
   * @param    profile                the UserProfile of the user.
   * @param makeNullEntry        a boolean specifying if empty entry should be added.
   */
  public ContactCombo(UserProfile profile, boolean makeNullEntry) throws XMLBuilderException {
    super(profile, makeNullEntry);
  }

  /**
   * Constructor III for ContactCombo.
   * Creates a combo with a value = "null" and text = nameOnNullEntry.
   *
   * @param    profile                the UserProfile of the user.
   * @param nameOnNullEntry        a String specifying the name on the entry with value "null".
   */
  public ContactCombo(UserProfile profile, String nameOnNullEntry) throws XMLBuilderException {
    super(profile, nameOnNullEntry);
  }

  //~ Methods ..........................................................................................................

  public void load(String valueToBeSelected) throws XMLBuilderException {
    try {
      StringRecordset rs = dbQuery.getContacts();

      while (!rs.getEOF()) {
        addEntry(rs.getField("Id"), rs.getField("FirstName") + " " + rs.getField("LastName"));
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