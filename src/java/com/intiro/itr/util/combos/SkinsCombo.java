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


public class SkinsCombo extends XMLCombo {
  //~ Constructors .....................................................................................................

  /**
   * Constructor I for SkinsCombo.
   * Creates a combo with a value = "null" and text = "Select skin".
   *
   *
   * @param    profile                the UserProfile of the user.
   */
  public SkinsCombo( UserProfile profile )
             throws XMLBuilderException {
    this( profile, "Select skin" );
  }
  /**
   * Constructor II for SkinsCombo.
   * Creates a combo with a value = "null" and text = "---------",
   * if makeNullEntry is true.
   *
   * @param    profile                the UserProfile of the user.
   * @param makeNullEntry        a boolean specifying if empty entry should be added.
   */
  public SkinsCombo( UserProfile profile, boolean makeNullEntry )
             throws XMLBuilderException {
    super( profile, makeNullEntry );
  }
  /**
   * Constructor III for SkinsCombo.
   * Creates a combo with a value = "null" and text = nameOnNullEntry.
   *
   * @param    profile                the UserProfile of the user.
   * @param nameOnNullEntry        a String specifying the name on the entry with value "null".
   */
  public SkinsCombo( UserProfile profile, String nameOnNullEntry )
             throws XMLBuilderException {
    super( profile, nameOnNullEntry );
  }
  //~ Methods ..........................................................................................................

  public void load( String valueToBeSelected )
            throws XMLBuilderException {
    try {
      StringRecordset rs = dbQuery.getSkins();

      while ( !rs.getEOF() ) {
        addEntry( rs.getField( DBConstants.SKIN_ID_PK ), rs.getField( DBConstants.SKIN_NAME ) );
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