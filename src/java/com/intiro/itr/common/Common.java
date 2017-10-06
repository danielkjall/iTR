package com.intiro.itr.common;

import com.intiro.itr.db.DBQueries;
import com.intiro.itr.util.*;
import com.intiro.itr.util.personalization.*;
import com.intiro.itr.util.xml.*;
import com.intiro.itr.util.log.IntiroLog;
import java.util.*;

public class Common extends DynamicXMLCarrier {

  static final String XML_LOGINID_END = "</loginid>";
  static final String XML_LOGINID_START = "<loginid>";

  public Common(UserProfile profile) throws XMLBuilderException {
    super(profile);
  }

  // Method to retreive years from published years
  public List<String> getSubmitedYears(Boolean currentUser) throws XMLBuilderException {
    List<String> retval = new ArrayList<>();
    String userId = "";
    if (currentUser) {
      userId = getUserProfile().getUserId();
    }
    try {
      StringRecordset rs = DBQueries.getProxy().getSubmittedYears(userId);

      while (!rs.getEOF()) {
        retval.add(rs.getField("theYear") + ";" + rs.getField("quantity"));
        rs.moveNext();
      }

    } catch (Exception e) {
      IntiroLog.error(getClass(), getClass().getName() + ".loadSubmitted(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }

    return retval;
  }

  public ArrayList<String> getUsersList(String year) {
    ArrayList<String> usersList = new ArrayList<>();
    try {
      StringRecordset rs = DBQueries.getProxy().getUsersReportedYear(year);
      while (!rs.getEOF()) {
        usersList.add(rs.getField("Id") + ";" + rs.getField("FullName"));
        rs.moveNext();
      }
    } catch (Exception e) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".load(): ERROR FROM DATABASE, exception = " + e.getMessage());
      }
    }

    return usersList;
  }
}
