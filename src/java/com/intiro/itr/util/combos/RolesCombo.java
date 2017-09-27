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


public class RolesCombo extends XMLCombo {
  //~ Constructors .....................................................................................................

  /**
   * Constructor I for RolesCombo.
   * Creates a combo with a value = "null" and text = "Select role".
   */
  public RolesCombo( UserProfile inUserProfile )
             throws XMLBuilderException {
    this( "Select role" );
  }
  /**
   * Constructor II for RolesCombo.
   * Creates a combo with a value = "null" and text = "---------",
   * if makeNullEntry is true.
   *
   * @param makeNullEntry        a boolean specifying if empty entry should be added.
   */
  public RolesCombo( boolean makeNullEntry )
             throws XMLBuilderException {
    super( makeNullEntry );
  }
  /**
   * Constructor III for RolesCombo.
   * Creates a combo with a value = "null" and text = nameOnNullEntry.
   *
   * @param nameOnNullEntry        a String specifying the name on the entry with value "null".
   */
  public RolesCombo( String nameOnNullEntry )
             throws XMLBuilderException {
    super( nameOnNullEntry );
  }
  //~ Methods ..........................................................................................................

  public void load( String valueToBeSelected )
            throws XMLBuilderException {
    try {
      StringRecordset rs = new DBQueries().getRoles();

      while ( !rs.getEOF() ) {
        addEntry( rs.getField( DBConstants.ROLES_ID_PK ), rs.getField( DBConstants.ROLES_NAME ) );
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