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
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.xml.XMLCombo;
import com.intiro.toolbox.log.IntiroLog;

public class UsersCombo extends XMLCombo {
  // ~ Instance/static variables ........................................................................................

  boolean activated = false;

  // ~ Constructors .....................................................................................................

  /**
   * Constructor I for UsersCombo. Creates a combo with a value = "null" and text = "Select user".
   * 
   * 
   * @param profile
   *          the UserProfile of the user.
   */
  public UsersCombo(UserProfile profile, boolean activatedUsers) throws XMLBuilderException {
    this(profile, "Select user", activatedUsers);
  }

  /**
   * Constructor II for UsersCombo. Creates a combo with a value = "null" and text = "---------", if makeNullEntry is true.
   * 
   * @param profile
   *          the UserProfile of the user.
   * @param makeNullEntry
   *          a boolean specifying if empty entry should be added.
   */
  public UsersCombo(UserProfile profile, boolean makeNullEntry, boolean activatedUsers) throws XMLBuilderException {
    super(profile, makeNullEntry);
    this.activated = activatedUsers;
  }

  /**
   * Constructor III for UsersCombo. Creates a combo with a value = "null" and text = nameOnNullEntry.
   * 
   * @param profile
   *          the UserProfile of the user.
   * @param nameOnNullEntry
   *          a String specifying the name on the entry with value "null".
   */
  public UsersCombo(UserProfile profile, String nameOnNullEntry, boolean activatedUsers) throws XMLBuilderException {
    super(profile, nameOnNullEntry);
    this.activated = activatedUsers;
  }

  // ~ Methods ..........................................................................................................

  public void load(String valueToBeSelected, boolean forceShowAll) throws XMLBuilderException {
    try {
      StringRecordset rs = dbQuery.getUsers(activated);

      while (!rs.getEOF()) {
        if (forceShowAll || getUserProfile().getUserId().equals(rs.getField(DBConstants.USER_ID_PK)) || getUserProfile().getRole().isAdmin() || getUserProfile().getRole().isSuperAdmin()) {
          addEntry(rs.getField(DBConstants.USER_ID_PK), rs.getField(DBConstants.USER_FIRSTNAME) + " " + rs.getField(DBConstants.USER_LASTNAME) + " ("
              + rs.getField(DBConstants.USER_LOGINID) + ")");
        }
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

  public void load(String inValueToBeSelected) throws XMLBuilderException {
    load(inValueToBeSelected, true);
  }
}