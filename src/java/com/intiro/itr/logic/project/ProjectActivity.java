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


public class ProjectActivity {
  //~ Instance/static variables ........................................................................................

  public static final String XML_ENDTAG_CODE = "</code>";
  public static final String XML_ENDTAG_DESC = "</description>";
  public static final String XML_ENDTAG_ID = "</id>";
  public static final String XML_ENDTAG_ITR_PROJECTCODEID = "</projectcodeid>";
  public static final String XML_ENDTAG_ITR_PROJECTID = "</projectid>";
  public static final String XML_STARTTAG_CODE = "<code>";
  public static final String XML_STARTTAG_DESC = "<description>";
  public static final String XML_STARTTAG_ID = "<id>";
  public static final String XML_STARTTAG_ITR_PROJECTCODEID = "<projectcodeid>";
  public static final String XML_STARTTAG_ITR_PROJECTID = "<projectid>";
  String code = "";
  String description;
  int id;
  int projectCodeId;
  int projectId;
  boolean stillAssigned = true;

  //~ Methods ..........................................................................................................

  /**
    * Setter function
    *
    * return void
    */
  public void setCode( String code ) {
    this.code = code;
  }

  /**
    * Getter function
    *
    * return a String representing the property.
    */
  public String getCode() {
    return code;
  }

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
    * return an String representing the property.
    */
  public String getDescription() {
    return description;
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
    * return an int representing the property.
    */
  public int getId() {
    return id;
  }

  /**
    * Setter function
    *
    * return void
    */
  public void setProjectCodeId( int projectCodeId ) {
    this.projectCodeId = projectCodeId;
  }

  /**
    * Getter function
    *
    * return a int representing the property.
    */
  public int getProjectCodeId() {
    return projectCodeId;
  }

  /**
    * Setter function
    *
    * return void
    */
  public void setProjectId( int projectId ) {
    this.projectId = projectId;
  }

  /**
    * Getter function
    *
    * return an int representing the property.
    */
  public int getProjectId() {
    return projectId;
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


  public static Vector <ProjectActivity> loadProjectActivities( int projectId )
                                      throws XMLBuilderException {
    if ( IntiroLog.d() ) {
      IntiroLog.detail( ProjectActivity.class, 
                        ProjectActivity.class.getName() + ".getProjectActivities(int projectId): Entering" );
    }

    DBQueries query = new DBQueries();
    Vector <ProjectActivity> retval = new Vector<ProjectActivity>();

    try {
      StringRecordset rs = query.loadProjectActivities( projectId );

      while ( !rs.getEOF() ) {
        ProjectActivity pa = new ProjectActivity();
        pa.setId( rs.getInt( "Id" ) );
        pa.setProjectId( rs.getInt( "ITR_ProjectId" ) );
        pa.setProjectCodeId( rs.getInt( "ITR_ProjectCodeId" ) );
        pa.setDescription( rs.getField( DBConstants.PROJECTCODE_DESCRIPTION ) );
        pa.setCode( rs.getField( DBConstants.PROJECTCODE_CODE ) );
        retval.addElement( pa );
        rs.moveNext();
      }

      rs.close();
    } catch ( Exception e ) {
      if ( IntiroLog.ce() ) {
        IntiroLog.criticalError( ProjectActivity.class, 
                                 ProjectActivity.class.getName() + ".load(): ERROR FROM DATABASE, exception = " + 
                                 e.getMessage() );
      }

      throw new XMLBuilderException( ProjectActivity.class.getName() + ".load(): " + e.getMessage() );
    }
    if ( IntiroLog.d() ) {
      IntiroLog.detail( ProjectActivity.class, ProjectActivity.class.getName() + ".load(): Leaving" );
    }

    return retval;
  }

  /**
     * Delete the ProjectActivity.
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
      retVal = dbE.deleteProjectActivity( getId() );
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
      StringRecordset rs = query.loadProjectActivity( Integer.parseInt( id ) );

      if ( !rs.getEOF() ) {
        setId( rs.getInt( "Id" ) );
        setProjectId( rs.getInt( "ITR_ProjectId" ) );
        setProjectCodeId( rs.getInt( "ITR_ProjectCodeId" ) );
        setDescription( rs.getField( DBConstants.PROJECTCODE_DESCRIPTION ) );
        setCode( rs.getField( DBConstants.PROJECTCODE_CODE ) );
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


  public void save()
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
        dbExecute.updateProjectActivity( this );
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
        StringRecordset rs = dbQuery.makeNewProjectActivityAndFetchId( this );

        if ( !rs.getEOF() ) {
          int tmp = rs.getInt( "maxId" );
          setId( tmp );
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
    retval.append( ", id = " + getId() );
    retval.append( ", projectId = " + getProjectId() );
    retval.append( ", projectCodeId = " + getProjectCodeId() );

    return retval.toString();
  }


  public void toXML( StringBuffer xmlDoc ) {
    if ( IntiroLog.d() ) {
      IntiroLog.detail( getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): Entering" );
    }

    xmlDoc.append( XML_STARTTAG_ID );
    xmlDoc.append( getId() );
    xmlDoc.append( XML_ENDTAG_ID );
    xmlDoc.append( XML_STARTTAG_DESC );
    xmlDoc.append( getDescription() );
    xmlDoc.append( XML_ENDTAG_DESC );
    xmlDoc.append( XML_STARTTAG_CODE );
    xmlDoc.append( getCode() );
    xmlDoc.append( XML_ENDTAG_CODE );
    xmlDoc.append( XML_STARTTAG_ITR_PROJECTID );
    xmlDoc.append( getProjectId() );
    xmlDoc.append( XML_ENDTAG_ITR_PROJECTID );
    xmlDoc.append( XML_STARTTAG_ITR_PROJECTCODEID );
    xmlDoc.append( getProjectCodeId() );
    xmlDoc.append( XML_ENDTAG_ITR_PROJECTCODEID );
  }
}