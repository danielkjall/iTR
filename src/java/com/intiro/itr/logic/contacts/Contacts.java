/**
* Title:
* Description:
* Copyright:     Copyright (c) 2001
* Company:       Intiro Development AB
* @author        Daniel Kjall
* @version       1.0
*/
package com.intiro.itr.logic.contacts;

import com.intiro.itr.db.*;

import com.intiro.itr.logic.email.Email;

import com.intiro.itr.logic.phone.PhoneNumber;

import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.StringRecordset;

import com.intiro.itr.util.xml.XMLBuilderException;

import com.intiro.toolbox.log.IntiroLog;

import java.util.Vector;


public class Contacts {
  //~ Instance/static variables ........................................................................................

  public static final String XML_ENDTAG_CONTACT = "</contact>";
  public static final String XML_ENDTAG_DESCRIPTION = "</description>";
  public static final String XML_ENDTAG_FIRSTCONTACT = "</firstcontact>";
  public static final String XML_ENDTAG_FIRSTNAME = "</firstname>";
  public static final String XML_ENDTAG_FRIENDLEVEL = "</friendlevel>";
  public static final String XML_ENDTAG_ID = "</id>";
  public static final String XML_ENDTAG_ITR_COMPANYID = "</itr_companyid>";
  public static final String XML_ENDTAG_KNOWNBYUSER_ID = "</knownbyuser_id>";
  public static final String XML_ENDTAG_LASTNAME = "</lastname>";
  public static final String XML_ENDTAG_POSITION = "</position>";
  public static final String XML_STARTTAG_CONTACT = "<contact>";
  public static final String XML_STARTTAG_DESCRIPTION = "<description>";
  public static final String XML_STARTTAG_FIRSTCONTACT = "<firstcontact>";
  public static final String XML_STARTTAG_FIRSTNAME = "<firstname>";
  public static final String XML_STARTTAG_FRIENDLEVEL = "<friendlevel>";
  public static final String XML_STARTTAG_ID = "<id>";
  public static final String XML_STARTTAG_ITR_COMPANYID = "<itr_companyid>";
  public static final String XML_STARTTAG_KNOWNBYUSER_ID = "<knownbyuser_id>";
  public static final String XML_STARTTAG_LASTNAME = "<lastname>";
  public static final String XML_STARTTAG_POSITION = "<position>";
  protected String description = "";
  protected Vector emails = new Vector();
  protected ITRCalendar firstContact = new ITRCalendar();
  protected String firstName = "";
  protected String friendLevel = "";
  protected int iTR_CompanyId = -1;
  protected int id = -1;
  protected int knownByUser_Id = -1;
  protected String lastName = "";
  protected Vector phones = new Vector();
  protected String position = "";

  //~ Methods ..........................................................................................................

  /**
    * Setter function
    *
    * return void
    */
  public void setDescription( String description ) {
    this.description = description;
  }

  /**
    * Getter function
    *
    * return a String representing the property.
    */
  public String getDescription() {
    return description;
  }

  /**
      *    Gets a specific email for this Contact.
      *    @return        an Email.
      */
  public Email getEmail( int index ) {
    Email oneEmail = null;

    if ( emails != null ) {
      oneEmail = ( Email )emails.get( index );
    }

    return oneEmail;
  }

  /**
      *    Gets the emails for this Contact.
      *    @return        a String, specifying the emailId.
      */
  public Vector getEmails() {
    return emails;
  }

  /**
    * Setter function
    *
    * return void
    */
  public void setFirstContact( ITRCalendar firstContact ) {
    this.firstContact = firstContact;
  }

  /**
    * Getter function
    *
    * return a ITRCalendar representing the property.
    */
  public ITRCalendar getFirstContact() {
    return firstContact;
  }

  /**
    * Setter function
    *
    * return void
    */
  public void setFirstName( String firstName ) {
    this.firstName = firstName;
  }

  /**
    * Getter function
    *
    * return a String representing the property.
    */
  public String getFirstName() {
    return firstName;
  }

  /**
    * Setter function
    *
    * return void
    */
  public void setFriendLevel( String friendLevel ) {
    this.friendLevel = friendLevel;
  }

  /**
    * Getter function
    *
    * return a String representing the property.
    */
  public String getFriendLevel() {
    return friendLevel;
  }

  /**
    * Setter function
    *
    * return void
    */
  public void setITR_CompanyId( int iTR_CompanyId ) {
    this.iTR_CompanyId = iTR_CompanyId;
  }

  /**
    * Getter function
    *
    * return a int representing the property.
    */
  public int getITR_CompanyId() {
    return iTR_CompanyId;
  }

  /**
    * Setter function
    *
    * return void
    */
  public void setId( int id ) {
    this.id = id;
  }

  /**
    * Getter function
    *
    * return a int representing the property.
    */
  public int getId() {
    return id;
  }

  /**
    * Setter function
    *
    * return void
    */
  public void setKnownByUser_Id( int knownByUser_Id ) {
    this.knownByUser_Id = knownByUser_Id;
  }

  /**
    * Getter function
    *
    * return a int representing the property.
    */
  public int getKnownByUser_Id() {
    return knownByUser_Id;
  }

  /**
    * Setter function
    *
    * return void
    */
  public void setLastName( String lastName ) {
    this.lastName = lastName;
  }

  /**
    * Getter function
    *
    * return a String representing the property.
    */
  public String getLastName() {
    return lastName;
  }

  /**
      *    Gets a specific phoneNumber for this Contact.
      *    @return        a PhoneNumber.
      */
  public PhoneNumber getPhoneNumber( int index ) {
    PhoneNumber onePhone = null;

    if ( phones != null ) {
      onePhone = ( PhoneNumber )phones.get( index );
    }

    return onePhone;
  }

  /**
      *    Gets the phones for this Contact.
      *    @return        a Vector, containing the contacts phoneNumbers.
      */
  public Vector getPhoneNumbers() {
    return phones;
  }

  /**
    * Setter function
    *
    * return void
    */
  public void setPosition( String position ) {
    this.position = position;
  }

  /**
    * Getter function
    *
    * return a String representing the property.
    */
  public String getPosition() {
    return position;
  }

  /**
    * Delete the Contacts.
    *
    * @return boolean.  false if nothing was deleted from db
    */
  public boolean delete()
                 throws Exception {
    if ( IntiroLog.d() ) {
      IntiroLog.detail( getClass(), getClass().getName() + ".delete(): Entering" );
    }

    boolean retVal = false;

    try {
      retVal = new DBExecute().deleteContact( this );
    } catch ( Exception e ) {
      IntiroLog.info( getClass(), 
                      getClass().getName() + ".delete(): ERROR FROM DATABASE, exception = " + e.getMessage() );
      throw new XMLBuilderException( e.getMessage() );
    }

    return retVal;
  }


  public boolean load( String id )
               throws XMLBuilderException {
    if ( IntiroLog.d() ) {
      IntiroLog.detail( getClass(), getClass().getName() + ".load(String id): Entering" );
    }

    DBQueries query = new DBQueries();
    boolean success = false;

    try {
      StringRecordset rs = query.loadContact( Integer.parseInt( id ) );

      if ( IntiroLog.t() ) {
        IntiroLog.trace( getClass(), getClass().getName() + ".load(String id): rs=" + rs );
      }
      if ( !rs.getEOF() ) {
        setId( rs.getInt( "Id" ) );
        setITR_CompanyId( rs.getInt( "ITR_CompanyId" ) );
        setPosition( rs.getField( "Position" ) );
        setFriendLevel( rs.getField( "FriendLevel" ) );
        setDescription( rs.getField( "Description" ) );
        setFirstContact( rs.getITRDate( "FirstContact" ) );
        setFirstName( rs.getField( "FirstName" ) );
        setLastName( rs.getField( "LastName" ) );
        setKnownByUser_Id( rs.getInt( "KnownByUser_Id" ) );
        success = true;
      }

      rs.close();
    } catch ( Exception e ) {
      if ( IntiroLog.ce() ) {
        IntiroLog.criticalError( getClass(), 
                                 getClass().getName() + ".load(String id): ERROR FROM DATABASE, exception = " + 
                                 e.getMessage() );
      }

      throw new XMLBuilderException( getClass().getName() + ".load(String id): " + e.getMessage() );
    }
    if ( IntiroLog.d() ) {
      IntiroLog.detail( getClass(), getClass().getName() + ".load(String id): Leaving" );
    }

    return success;
  }

  /**
      *    Loads the emails for this Contact.
      */
  public void loadEmails()
                  throws XMLBuilderException {
    if ( getId() != -1 ) {
      this.emails = Email.load( -1, getId(), -1 );
    }
  }

  /**
      *    Loads the phones for this Contact.
      */
  public void loadPhoneNumbers()
                        throws XMLBuilderException {
    if ( getId() != -1 ) {
      this.phones = PhoneNumber.load( -1, getId(), -1 );
    }
  }


  public void save()
            throws XMLBuilderException {
    if ( IntiroLog.d() ) {
      IntiroLog.detail( getClass(), getClass().getName() + ".save(): Entering" );
    }
    if ( getId() != -1 ) {
      if ( IntiroLog.d() ) {
        IntiroLog.detail( getClass(), getClass().getName() + ".save(): updating" );
      }
      try {
        DBExecute dbExecute = new DBExecute();
        dbExecute.updateContacts( this );
      } catch ( Exception e ) {
        if ( IntiroLog.ce() ) {
          IntiroLog.criticalError( getClass(), 
                                   getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + 
                                   e.getMessage() );
        }

        throw new XMLBuilderException( e.getMessage() );
      }
    } else {
      if ( IntiroLog.d() ) {
        IntiroLog.detail( getClass(), getClass().getName() + ".save(): creating new" );
      }
      try {
        DBQueries dbQuery = new DBQueries();
        StringRecordset rs = dbQuery.makeNewContactAndFetchId( this );

        if ( !rs.getEOF() ) {
          setId( rs.getInt( "maxId" ) );
        }
      } catch ( Exception e ) {
        if ( IntiroLog.ce() ) {
          IntiroLog.criticalError( getClass(), 
                                   getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + 
                                   e.getMessage() );
        }

        throw new XMLBuilderException( e.getMessage() );
      }
    }
  }


  public String toString() {
    StringBuffer retval = new StringBuffer();
    retval.append( "id = " + getId() );
    retval.append( ", iTR_CompanyId = " + getITR_CompanyId() );
    retval.append( ", position = " + getPosition() );
    retval.append( ", friendLevel = " + getFriendLevel() );
    retval.append( ", description = " + getDescription() );
    retval.append( ", firstContact = " );

    if ( getFirstContact() != null ) {
      retval.append( getFirstContact().getCalendarInStoreFormat() );
    } else {
      retval.append( "null" );
    }

    retval.append( ", firstContact = " + getFirstContact() );
    retval.append( ", firstName = " + getFirstName() );
    retval.append( ", lastName = " + getLastName() );
    retval.append( ", knownByUser_Id = " + getKnownByUser_Id() );

    return retval.toString();
  }


  public void toXML( StringBuffer xmlDoc ) {
    if ( IntiroLog.d() ) {
      IntiroLog.detail( getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): Entering" );
    }

    xmlDoc.append( XML_STARTTAG_CONTACT );
    xmlDoc.append( XML_STARTTAG_ITR_COMPANYID );
    xmlDoc.append( getITR_CompanyId() );
    xmlDoc.append( XML_ENDTAG_ITR_COMPANYID );
    xmlDoc.append( XML_STARTTAG_POSITION );
    xmlDoc.append( getPosition() );
    xmlDoc.append( XML_ENDTAG_POSITION );
    xmlDoc.append( XML_STARTTAG_FRIENDLEVEL );
    xmlDoc.append( getFriendLevel() );
    xmlDoc.append( XML_ENDTAG_FRIENDLEVEL );
    xmlDoc.append( XML_STARTTAG_DESCRIPTION );
    xmlDoc.append( getDescription() );
    xmlDoc.append( XML_ENDTAG_DESCRIPTION );
    xmlDoc.append( XML_STARTTAG_FIRSTCONTACT );

    if ( getFirstContact() != null ) {
      xmlDoc.append( getFirstContact().getCalendarInStoreFormat() );
    }

    xmlDoc.append( XML_ENDTAG_FIRSTCONTACT );
    xmlDoc.append( XML_STARTTAG_FIRSTNAME );
    xmlDoc.append( getFirstName() );
    xmlDoc.append( XML_ENDTAG_FIRSTNAME );
    xmlDoc.append( XML_STARTTAG_LASTNAME );
    xmlDoc.append( getLastName() );
    xmlDoc.append( XML_ENDTAG_LASTNAME );
    xmlDoc.append( XML_STARTTAG_KNOWNBYUSER_ID );
    xmlDoc.append( getKnownByUser_Id() );
    xmlDoc.append( XML_ENDTAG_KNOWNBYUSER_ID );

    if ( emails != null ) {

      //Emails
      Email oneEmail = null;

      //Loop through all the Emails
      if ( IntiroLog.d() ) {
        IntiroLog.detail( getClass(), getClass().getName() + ".toXML(StringBuffer): emails.size() = " + emails.size() );
      }

      int j = 0;

      for ( int i = 0; i < emails.size(); i++ ) {
        if ( IntiroLog.d() ) {
          IntiroLog.detail( getClass(), getClass().getName() + ".toXML(StringBuffer): Emails, Loop[" + i + "]" );
        }

        oneEmail = ( Email )emails.get( i );

        if ( oneEmail != null ) {
          oneEmail.toXML( xmlDoc, i, j++ );
        }
      }
    }
    if ( phones != null ) {

      //PhoneNumbers
      PhoneNumber onePhone = null;

      //Loop through all the phone numbers
      int j = 0;

      if ( IntiroLog.d() ) {
        IntiroLog.detail( getClass(), getClass().getName() + ".toXML(StringBuffer): phones.size() = " + phones.size() );
      }
      for ( int i = 0; i < phones.size(); i++ ) {
        if ( IntiroLog.d() ) {
          IntiroLog.detail( getClass(), getClass().getName() + ".toXML(StringBuffer): Phones, Loop[" + i + "]" );
        }

        onePhone = ( PhoneNumber )phones.get( i );

        if ( onePhone != null ) {
          onePhone.toXML( xmlDoc, i, j++ );
        }
      }
    }

    xmlDoc.append( XML_ENDTAG_CONTACT );
  }
}