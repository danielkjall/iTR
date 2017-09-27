/** 
* Title:         
* Description:   
* Copyright:     Copyright (c) 2001
* Company:       Intiro Development AB
* @author        Daniel Kjall
* @version       1.0
*/
package com.intiro.itr.logic.project;

import com.intiro.itr.db.*;

import com.intiro.itr.util.StringRecordset;

import com.intiro.itr.util.xml.XMLBuilderException;

import com.intiro.toolbox.log.IntiroLog;

import java.util.Vector;


public class ProjectMember {
  //~ Instance/static variables ........................................................................................

  public static final String XML_ENDTAG_ACTIVE = "</active>";
  public static final String XML_ENDTAG_FIRSTNAME = "</firstname>";
  public static final String XML_ENDTAG_ID = "</id>";
  public static final String XML_ENDTAG_ITR_PROJECTID = "</itr_projectid>";
  public static final String XML_ENDTAG_ITR_USERID = "</itr_userid>";
  public static final String XML_ENDTAG_LASTNAME = "</lastname>";
  public static final String XML_ENDTAG_LOGINID = "</loginid>";
  public static final String XML_ENDTAG_PROJECTADMIN = "</projectadmin>";
  public static final String XML_ENDTAG_RATE = "</rate>";
  public static final String XML_STARTTAG_ACTIVE = "<active>";
  public static final String XML_STARTTAG_FIRSTNAME = "<firstname>";
  public static final String XML_STARTTAG_ID = "<id>";
  public static final String XML_STARTTAG_ITR_PROJECTID = "<itr_projectid>";
  public static final String XML_STARTTAG_ITR_USERID = "<itr_userid>";
  public static final String XML_STARTTAG_LASTNAME = "<lastname>";
  public static final String XML_STARTTAG_LOGINID = "<loginid>";
  public static final String XML_STARTTAG_PROJECTADMIN = "<projectadmin>";
  public static final String XML_STARTTAG_RATE = "<rate>";
  protected boolean active;
  protected String firstName = "";
  protected int iTR_ProjectId;
  protected int iTR_UserId;
  protected int id;
  protected String lastName = "";
  protected String loginId = "";
  protected boolean projectAdmin;
  protected int rate;
  boolean stillAssigned = true;

  //~ Methods ..........................................................................................................

  /**
    * Setter function
    * 
    * return void
    */
  public void setActive( boolean active ) {
    this.active = active;
  }

  /**
    * Getter function
    * 
    * return a boolean representing the property.
    */
  public boolean getActive() {
    return active;
  }

  /**
    * Setter function
    * return void
    */
  public void setFirstName( String firstName ) {
    this.firstName = firstName;
  }

  /**
    * Getter function
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
  public void setITR_ProjectId( int iTR_ProjectId ) {
    this.iTR_ProjectId = iTR_ProjectId;
  }

  /**
    * Getter function
    * 
    * return a int representing the property.
    */
  public int getITR_ProjectId() {
    return iTR_ProjectId;
  }

  /**
    * Setter function
    * 
    * return void
    */
  public void setITR_UserId( int iTR_UserId ) {
    this.iTR_UserId = iTR_UserId;
  }

  /**
    * Getter function
    * 
    * return a int representing the property.
    */
  public int getITR_UserId() {
    return iTR_UserId;
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
    * return void
    */
  public void setLastName( String lastName ) {
    this.lastName = lastName;
  }

  /**
    * Getter function
    * return a String representing the property.
    */
  public String getLastName() {
    return lastName;
  }

  /**
    * Setter function
    * return void
    */
  public void setLoginId( String loginId ) {
    this.loginId = loginId;
  }

  /**
    * Getter function
    * return a String representing the property.
    */
  public String getLoginId() {
    return loginId;
  }

  /**
    * Setter function
    * return void
    */
  public void setProjectAdmin( boolean projectAdmin ) {
    this.projectAdmin = projectAdmin;
  }

  /**
    * Getter function
    * return a boolean representing the property.
    */
  public boolean getProjectAdmin() {
    return projectAdmin;
  }

  /**
    * Setter function
    * 
    * return void
    */
  public void setRate( int rate ) {
    this.rate = rate;
  }

  /**
    * Getter function
    * 
    * return a int representing the property.
    */
  public int getRate() {
    return rate;
  }

  /**
    * Setter function
    * return void
    */
  public void setStillAssigned( boolean stillAssigned ) {
    this.stillAssigned = stillAssigned;
  }

  /**
    * Getter function
    * return an int representing the property.
    */
  public boolean getStillAssigned() {
    return stillAssigned;
  }

  /**
     * Delete the ProjectMember.
     * @return boolean.  false if nothing was deleted from db
     */
  public boolean delete()
                 throws Exception {
    if ( IntiroLog.d() ) {
      IntiroLog.detail( getClass(), getClass().getName() + ".delete(): Entering" );
    }

    boolean retVal = false;
    DBExecute dbE = new DBExecute();

    try {
      //retVal = dbE.deactivateProjectMember( this.getId() );

      retVal = dbE.deleteProjectMember(this.getId());
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
      IntiroLog.detail( getClass(), getClass().getName() + ".load(): Entering" );
    }

    DBQueries query = new DBQueries();
    boolean success = false;

    try {
      StringRecordset rs = query.loadProjectMember( Integer.parseInt( id ) );

      if ( !rs.getEOF() ) {
        setId( rs.getInt( "Id" ) );
        setITR_ProjectId( rs.getInt( "ITR_ProjectId" ) );
        setITR_UserId( rs.getInt( "ITR_UserId" ) );
        setRate( rs.getInt( "Rate" ) );

        String tmp = rs.getField( "Active" );

        if ( ( tmp != null && tmp.trim().equals( "1" ) ) || ( tmp != null && tmp.trim().equalsIgnoreCase( "true" ) ) ) {
          this.setActive( true );
        } else {
          this.setActive( false );
        }

        tmp = rs.getField( "ProjectAdmin" );

        if ( ( tmp != null && tmp.trim().equals( "1" ) ) || ( tmp != null && tmp.trim().equalsIgnoreCase( "true" ) ) ) {
          this.setProjectAdmin( true );
        } else {
          this.setProjectAdmin( false );
        }

        success = true;
      }

      rs.close();
    } catch ( Exception e ) {
      if ( IntiroLog.ce() ) {
        IntiroLog.criticalError( getClass(), 
                                 getClass().getName() + ".load(): ERROR FROM DATABASE, exception = " + e.getMessage() );
      }

      throw new XMLBuilderException( getClass().getName() + ".load(): " + e.getMessage() );
    }
    if ( IntiroLog.d() ) {
      IntiroLog.detail( getClass(), getClass().getName() + ".load(): Leaving" );
    }

    return success;
  }


  public static Vector<ProjectMember> loadAssignedProjectMembers( int projectId )
                                           throws XMLBuilderException {
    if ( IntiroLog.d() ) {
      IntiroLog.detail( ProjectMember.class, 
                        ProjectMember.class.getName() + ".loadAssignedProjectMembers(int projectId): Entering" );
    }

    DBQueries query = new DBQueries();
    Vector<ProjectMember> retval = new Vector<ProjectMember>();

    try {
      StringRecordset rs = query.loadAssignedProjectMembers( projectId );

      while ( !rs.getEOF() ) {
        ProjectMember pm = new ProjectMember();
        pm.setId( rs.getInt( "Id" ) );
        pm.setITR_ProjectId( rs.getInt( "ITR_ProjectId" ) );
        pm.setITR_UserId( rs.getInt( "ITR_UserId" ) );
        pm.setRate( rs.getInt( "Rate" ) );

        String tmp = rs.getField( "Active" );

        if ( ( tmp != null && tmp.trim().equals( "1" ) ) || ( tmp != null && tmp.trim().equalsIgnoreCase( "true" ) ) ) {
          pm.setActive( true );
        } else {
          pm.setActive( false );
        }

        tmp = rs.getField( "ProjectAdmin" );

        if ( ( tmp != null && tmp.trim().equals( "1" ) ) || ( tmp != null && tmp.trim().equalsIgnoreCase( "true" ) ) ) {
          pm.setProjectAdmin( true );
        } else {
          pm.setProjectAdmin( false );
        }

        pm.setFirstName( rs.getField( "FirstName" ) );
        pm.setLastName( rs.getField( "LastName" ) );
        pm.setLoginId( rs.getField( "LoginId" ) );
        retval.addElement( pm );
        rs.moveNext();
      }

      rs.close();
    } catch ( Exception e ) {
      if ( IntiroLog.ce() ) {
        IntiroLog.criticalError( ProjectMember.class, 
                                 ProjectMember.class.getName() + 
                                 ".loadAssignedProjectMembers(): ERROR FROM DATABASE, exception = " + e.getMessage() );
      }

      throw new XMLBuilderException( ProjectMember.class.getName() + ".loadAssignedProjectMembers(): " + 
                                     e.getMessage() );
    }
    if ( IntiroLog.d() ) {
      IntiroLog.detail( ProjectMember.class, ProjectMember.class.getName() + ".loadAssignedProjectMembers(): Leaving" );
    }

    return retval;
  }


  public static Vector<ProjectMember> loadAvailableProjectMembers( int projectId )
                                            throws XMLBuilderException {
    if ( IntiroLog.d() ) {
      IntiroLog.detail( ProjectMember.class, 
                        ProjectMember.class.getName() + ".loadAvailableProjectMembers(int projectId): Entering" );
    }

    DBQueries query = new DBQueries();
    Vector<ProjectMember> retval = new Vector<ProjectMember>();

    try {
      StringRecordset rs = query.loadAvailableProjectMembers( projectId );

      while ( !rs.getEOF() ) {
        ProjectMember pm = new ProjectMember();
        pm.setITR_ProjectId( projectId );
        pm.setITR_UserId( rs.getInt( "Id" ) );
        pm.setRate( 0 );
        pm.setActive( true );
        pm.setProjectAdmin( false );
        pm.setFirstName( rs.getField( "FirstName" ) );
        pm.setLastName( rs.getField( "LastName" ) );
        pm.setLoginId( rs.getField( "LoginId" ) );
        retval.addElement( pm );
        rs.moveNext();
      }

      rs.close();
    } catch ( Exception e ) {
      if ( IntiroLog.ce() ) {
        IntiroLog.criticalError( ProjectMember.class, 
                                 ProjectMember.class.getName() + 
                                 ".loadAvailableProjectMembers(): ERROR FROM DATABASE, exception = " + e.getMessage() );
      }

      throw new XMLBuilderException( ProjectMember.class.getName() + ".loadAvailableProjectMembers(): " + 
                                     e.getMessage() );
    }
    if ( IntiroLog.d() ) {
      IntiroLog.detail( ProjectMember.class, ProjectMember.class.getName() + ".loadAvailableProjectMembers(): Leaving" );
    }

    return retval;
  }


  public boolean save()
               throws XMLBuilderException {
    if ( IntiroLog.d() ) {
      IntiroLog.detail( getClass(), getClass().getName() + ".save(): Entering" );
    }
    if ( getId() != 0 ) {
      if ( IntiroLog.d() ) {
        IntiroLog.detail( getClass(), getClass().getName() + ".save(): updating" );
      }
      try {
        DBExecute dbExecute = new DBExecute();
        dbExecute.updateProjectMember( this );
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
        StringRecordset rs = dbQuery.makeNewProjectMemberAndFetchId( this );

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

    return true;
  }


  public String toString() {
    StringBuffer retval = new StringBuffer();
    retval.append( "id = " + getId() + ", " );
    retval.append( "iTR_ProjectId = " + getITR_ProjectId() + ", " );
    retval.append( "iTR_UserId = " + getITR_UserId() + ", " );
    retval.append( "rate = " + getRate() + ", " );
    retval.append( "active = " + getActive() + ", " );
    retval.append( "projectAdmin = " + getProjectAdmin() + ", " );
    retval.append( "firstName = " + getFirstName() + ", " );
    retval.append( "lastName = " + getLastName() + ", " );
    retval.append( "loginId = " + getLoginId() + " " );

    return retval.toString();
  }


  public boolean toXML( StringBuffer xmlDoc ) {
    if ( IntiroLog.d() ) {
      IntiroLog.detail( getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): Entering" );
    }

    xmlDoc.append( XML_STARTTAG_ID );
    xmlDoc.append( getId() );
    xmlDoc.append( XML_ENDTAG_ID );
    xmlDoc.append( XML_STARTTAG_ITR_PROJECTID );
    xmlDoc.append( getITR_ProjectId() );
    xmlDoc.append( XML_ENDTAG_ITR_PROJECTID );
    xmlDoc.append( XML_STARTTAG_ITR_USERID );
    xmlDoc.append( getITR_UserId() );
    xmlDoc.append( XML_ENDTAG_ITR_USERID );
    xmlDoc.append( XML_STARTTAG_RATE );
    xmlDoc.append( getRate() );
    xmlDoc.append( XML_ENDTAG_RATE );
    xmlDoc.append( XML_STARTTAG_ACTIVE );
    xmlDoc.append( getActive() );
    xmlDoc.append( XML_ENDTAG_ACTIVE );
    xmlDoc.append( XML_STARTTAG_PROJECTADMIN );
    xmlDoc.append( getProjectAdmin() );
    xmlDoc.append( XML_ENDTAG_PROJECTADMIN );
    xmlDoc.append( XML_STARTTAG_FIRSTNAME );
    xmlDoc.append( getFirstName() );
    xmlDoc.append( XML_ENDTAG_FIRSTNAME );
    xmlDoc.append( XML_STARTTAG_LASTNAME );
    xmlDoc.append( getLastName() );
    xmlDoc.append( XML_ENDTAG_LASTNAME );
    xmlDoc.append( XML_STARTTAG_LOGINID );
    xmlDoc.append( getLoginId() );
    xmlDoc.append( XML_ENDTAG_LOGINID );

    return true;
  }
}