/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic.frames;

import com.intiro.itr.util.personalization.Role;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;


public class Navigation extends DynamicXMLCarrier {
  //~ Instance/static variables ........................................................................................

  static final String XML_MENUADMIN_END = "</menuadmin>";
  static final String XML_MENUADMIN_START = "<menuadmin>";
  static final String XML_MENUBASIC_END = "</menubasic>";
  static final String XML_MENUBASIC_START = "<menubasic>";
  static final String XML_MENUSUPERADMIN_END = "</menusuperadmin>";
  static final String XML_MENUSUPERADMIN_START = "<menusuperadmin>";
  static final String XML_NOM_END = "</nom>";
  static final String XML_NOM_START = "<nom>";
  protected boolean menuAdmin = false;
  protected boolean menuBasic = false;

  // How many blocks on the navigation the user is authorized for.
  protected int menuLevel = 3;
  protected boolean menuSuperAdmin = false;

  //~ Constructors .....................................................................................................

  public Navigation( UserProfile profile )
             throws XMLBuilderException {
    super( profile );

    Role role = profile.getRole();
    menuSuperAdmin = role.isSuperAdmin();
    menuAdmin = ( role.isAdmin() || menuSuperAdmin );
    menuBasic = ( role.isEmployee() || role.isUnderConsultant() || menuAdmin );

    if ( menuBasic && menuAdmin ) {
      menuLevel = 4;
    }
    if ( menuBasic && menuAdmin && menuSuperAdmin ) {
      menuLevel = 5;
    }
  }
  //~ Methods ..........................................................................................................

  /**
     * This is the method that will produce the XML.
     * It will fill the xmlDoc with XML.
     * @param    xmlDoc a StringBuffer to be filled with xml.
     */
  public void toXML( StringBuffer xmlDoc )
             throws Exception {
    if ( IntiroLog.d() ) {
      IntiroLog.detail( getClass(), getClass().getName() + ".toXML(StringBuffer): entered" );
    }

    XMLBuilder builder = new XMLBuilder();

    /*Get start of document*/
    builder.getStartOfDocument( xmlDoc );

    //Basic Menu
    xmlDoc.append( XML_NOM_START );
    xmlDoc.append( menuLevel );
    xmlDoc.append( XML_NOM_END );

    //Basic Menu
    xmlDoc.append( XML_MENUBASIC_START );
    xmlDoc.append( menuBasic );
    xmlDoc.append( XML_MENUBASIC_END );

    //Admin Menu
    xmlDoc.append( XML_MENUADMIN_START );
    xmlDoc.append( menuAdmin );
    xmlDoc.append( XML_MENUADMIN_END );

    //Super Admin Menu
    xmlDoc.append( XML_MENUSUPERADMIN_START );
    xmlDoc.append( menuSuperAdmin );
    xmlDoc.append( XML_MENUSUPERADMIN_END );

    /*Get end of document*/
    builder.getEndOfDocument( xmlDoc );
  }
}