/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.util.combos;

import com.intiro.itr.db.*;

import com.intiro.itr.util.*;

import com.intiro.itr.util.personalization.*;

import com.intiro.itr.util.xml.*;

import com.intiro.toolbox.log.*;


public class ProjectCodesCombo extends XMLCombo {
  //~ Instance/static variables ........................................................................................

  int projectId = -1;

  //~ Constructors .....................................................................................................

  /**
   * Constructor I for ProjectCodesCombo.
   * Creates a combo with a value = "null" and text = "Select activity".
   *
   *
   * @param    profile                the UserProfile of the user.
   */
  public ProjectCodesCombo( UserProfile profile )
                    throws XMLBuilderException {
    this( profile, "Select activity" );
  }
  /**
   * Constructor II for ProjectCodesCombo.
   * Creates a combo with a value = "null" and text = "---------",
   * if makeNullEntry is true.
   *
   * @param    profile                the UserProfile of the user.
   * @param makeNullEntry        a boolean specifying if empty entry should be added.
   */
  public ProjectCodesCombo( UserProfile profile, boolean makeNullEntry )
                    throws XMLBuilderException {
    super( profile, makeNullEntry );
  }
  /**
   * Constructor III for ProjectCodesCombo.
   * Creates a combo with a value = "null" and text = nameOnNullEntry.
   *
   * @param    profile                the UserProfile of the user.
   * @param nameOnNullEntry        a String specifying the name on the entry with value "null".
   */
  public ProjectCodesCombo( UserProfile profile, String nameOnNullEntry )
                    throws XMLBuilderException {
    super( profile, nameOnNullEntry );
  }
  //~ Methods ..........................................................................................................

  public void setProjectId( int projectId ) {
    this.projectId = projectId;
  }


  public void load( String valueToBeSelected )
            throws XMLBuilderException {
    try {
      StringRecordset rs = null;

      if ( projectId == -1 ) {
        rs = dbQuery.getAllProjectCodes();
      } else {
        rs = dbQuery.getProjectCodesForProject( projectId );
      }

      while ( !rs.getEOF() ) {
        addEntry( rs.getField( DBConstants.PROJECTCODE_ID_PK ), 
                  rs.getField( DBConstants.PROJECTCODE_CODE ) + ", " + 
                  rs.getField( DBConstants.PROJECTCODE_DESCRIPTION ) );
        rs.moveNext();
      }

      setSelectedValue( valueToBeSelected );
      rs.close();
    } catch ( Exception e ) {
      if ( IntiroLog.i() ) {
        IntiroLog.info( getClass(), "ERROR FROM DATABASE, exception = " + e.getMessage() );
      }

      throw new XMLBuilderException( e.getMessage() );
    }
  }
}