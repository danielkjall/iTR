/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic.admin;

import com.intiro.itr.db.*;

import com.intiro.itr.logic.weekreport.WeekReport;

import com.intiro.itr.util.*;

import com.intiro.itr.util.personalization.*;

import com.intiro.itr.util.xml.*;

import com.intiro.toolbox.log.*;

import java.util.*;


public class ApproveWeeks extends DynamicXMLCarrier {
  //~ Instance/static variables ........................................................................................

  static final String XML_MODE_END = "</mode>";
  static final String XML_MODE_START = "<mode>";
  String mode = "";

  //week reports
  Vector <WeekReport> weekReports = new Vector <WeekReport> ();

  //~ Constructors .....................................................................................................

  /**
   * Constructor I for Weeks.
   *
   * @param       profile the UserProfile for the current user.
   * @exception   XMLBuilderException if something goes wrong.
   */
  public ApproveWeeks( UserProfile profile, String mode )
               throws XMLBuilderException {
    super( profile );
    weekReports = new Vector <WeekReport> ();
    this.mode = mode;
  }
  //~ Methods ..........................................................................................................

  /**
   * Load the weekreports for users.
   */
  public Vector load()
              throws XMLBuilderException {
    if ( IntiroLog.d() ) {
      IntiroLog.detail( getClass(), getClass().getName() + ".load(): Submitted" );
    }

    Vector <String> usersThatNeedApprovel = new Vector <String> ();
    String previousUserId = "-1";

    try {
      StringRecordset rs = dbQuery.getUsersThatNeedApprovel();
      String userId = null;

      while ( !rs.getEOF() ) {
        userId = rs.getField( DBConstants.ENTRYROW_USERID_FK );

        //Only add new users
        if ( !previousUserId.equalsIgnoreCase( userId ) ) {
          usersThatNeedApprovel.add( userId ); //Add userid to usersThatNeedApprovel
        }

        previousUserId = userId;
        rs.moveNext();
      }

      rs.close();
    } catch ( Exception e ) {
      if ( IntiroLog.d() ) {
        IntiroLog.detail( getClass(), 
                          getClass().getName() + ".load(): ERROR FROM DATABASE, exception = " + e.getMessage() );
      }

      throw new XMLBuilderException( e.getMessage() );
    }
    for ( int i = 0; i < usersThatNeedApprovel.size(); i++ ) {
      if ( IntiroLog.d() ) {
        IntiroLog.detail( getClass(), 
                          getClass().getName() + ".load(): usersThatNeedApprovel.get(i) = " + 
                          usersThatNeedApprovel.get( i ) );
      }
    }

    UserProfile profile = new UserProfile();

    for ( int i = 0; i < usersThatNeedApprovel.size(); i++ ) {
      String userId = usersThatNeedApprovel.get( i );
      profile = new UserProfile();
      profile.load( userId );
      profile.setClientInfo( getUserProfile().getClientInfo() );

      try {
        StringRecordset rs = dbQuery.getSubmittedWeeks( userId, false, true );
        int lastCalendarWeekId = -1;

        while ( !rs.getEOF() ) {
          int calendarWeekId = Integer.parseInt( rs.getField( DBConstants.CALENDARWEEK_ID_PK ) );

          //Only make a new WeekReport if we have a new week.
          if ( calendarWeekId != lastCalendarWeekId ) {
            lastCalendarWeekId = calendarWeekId;

            ITRCalendar fromDate = new ITRCalendar( rs.getField( DBConstants.CALENDARWEEK_FROM_DATE ) );
            ITRCalendar toDate = new ITRCalendar( rs.getField( DBConstants.CALENDARWEEK_TO_DATE ) );
            WeekReport oneWeekReport = new WeekReport( profile, fromDate, toDate, "Approve" );
            oneWeekReport.load();

            //Add weekreport to week reports
            weekReports.add( oneWeekReport );
          }

          rs.moveNext();
        }

        rs.close();
      } catch ( Exception e ) {
        if ( IntiroLog.d() ) {
          IntiroLog.detail( getClass(), 
                            getClass().getName() + ".load(): ERROR FROM DATABASE, exception = " + e.getMessage() );
        }

        throw new XMLBuilderException( e.getMessage() );
      }
    }

    return weekReports;
  }

  /**
  * Make xml of weeks.
  */
  public void toXML( StringBuffer xmlDoc )
             throws Exception {
    if ( IntiroLog.d() ) {
      IntiroLog.detail( getClass(), getClass().getName() + ".toXML(): Entering" );
    }

    XMLBuilder builder = new XMLBuilder();

    //Get start of document
    builder.getStartOfDocument( xmlDoc );

    //mode
    xmlDoc.append( XML_MODE_START );
    xmlDoc.append( mode );
    xmlDoc.append( XML_MODE_END );

    //week reports
    WeekReport oneWeekReport = null;

    //Loop through all weekReports in combobox
    for ( int i = 0; i < weekReports.size(); i++ ) {
      oneWeekReport = weekReports.get( i );

      if ( oneWeekReport != null ) {
        oneWeekReport.toXMLSummary( xmlDoc, i );
      }
    }

    //Get end of document
    builder.getEndOfDocument( xmlDoc );

    if ( IntiroLog.d() ) {
      IntiroLog.detail( getClass(), getClass().getName() + ".toXML(): xmlDoc = " + xmlDoc.toString() );
    }
  }
}