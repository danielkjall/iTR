/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjäll
 * @version       1.0
 */
package com.intiro.itr.common;



//import com.intiro.itr.db.*;
import com.intiro.itr.util.*;
import com.intiro.itr.util.personalization.*;
import com.intiro.itr.util.xml.*;
import com.intiro.toolbox.log.IntiroLog;
import java.util.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;

public class Common extends DynamicXMLCarrier {

  //~ Instance/static variables ........................................................................................

  static final String XML_LOGINID_END = "</loginid>";
  static final String XML_LOGINID_START = "<loginid>";

  //~ Constructors .....................................................................................................

  public Common(UserProfile profile) throws XMLBuilderException {
    super(profile);
  }


  // Method to retreive years from published years
  public String[] getSubmitedYears(Boolean currentUser) throws XMLBuilderException {
        Vector<String> vector = new Vector<String>();
        String userId = "";
        if (currentUser)
        {
            userId =getUserProfile().getUserId();
        }
        try {
            StringRecordset rs = dbQuery.getSubmittedYears(userId);
            
            while (!rs.getEOF()) {
                vector.add(rs.getField("theYear") + ";" + rs.getField("quantity"));
                rs.moveNext();
            }
            
            } 
        catch (Exception e) {
            if (IntiroLog.e()) {
                IntiroLog.error(getClass(), getClass().getName() + ".loadSubmitted(): ERROR FROM DATABASE, exception = " + e.getMessage());
            }

            throw new XMLBuilderException(e.getMessage());
        }
        
    /* Work around för att konvertera vector till array */    
    String[] arrRes = new String[vector.size()];
    vector.copyInto(arrRes);
    
    return arrRes;
    }
  
  public Vector<String> getUsersList(String year)
  {
      Vector<String> usersList = new Vector<String>();
  try {
        StringRecordset rs = dbQuery.getUsersReportedYear(year);
        while (!rs.getEOF()) {
             usersList.add(rs.getField("Id") + ";" + rs.getField("FullName"));
            rs.moveNext();
        }   
    }
    catch (Exception e) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".load(): ERROR FROM DATABASE, exception = " + e.getMessage());
        }
        //throw new XMLBuilderException(e.getMessage());
      }
      
      return usersList;
  }
  
}
