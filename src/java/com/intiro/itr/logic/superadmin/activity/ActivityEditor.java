/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.logic.superadmin.activity;

import com.intiro.itr.logic.activity.Activity;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.xml.DynamicXMLCarrier;
import com.intiro.itr.util.xml.XMLBuilder;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;

public class ActivityEditor extends DynamicXMLCarrier {

  //~ Instance/static variables ........................................................................................

  static final String XML_ACTIVITY_END = "</activity>";
  static final String XML_ACTIVITY_START = "<activity>";
  static final String XML_MODE_END = "</mode>";
  static final String XML_MODE_START = "<mode>";
  static final String XML_TITLE_END = "</title>";
  static final String XML_TITLE_START = "<title>";
  protected String activityId = "";

  //modes
  protected boolean inAddMode = false;
  protected boolean inDeleteMode = false;
  protected boolean inEditMode = false;
  protected boolean inViewMode = false;
  protected String mode = "not set";
  protected Activity oneActivity = null;
  protected String title = "";

  //~ Constructors .....................................................................................................

  public ActivityEditor(UserProfile profile, String mode, String activityId) throws XMLBuilderException {
    super(profile);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".Constructor(): Entered");
    }
    if (mode != null) {
      if (mode.indexOf("Add") != -1) {
        inAddMode = true;
        mode = "Add";
        title = "Add";
      }
      else if (mode.indexOf("Edit") != -1) {
        inEditMode = true;
        mode = "Edit";
        title = "Edit";
      }
      else if (mode.indexOf("View") != -1) { //view mode
        inViewMode = true;
        mode = "View";
        title = "View";
      }
      else { //delete mode
        inDeleteMode = true;
        mode = "Delete";
        title = "Delete";
      }
    }

    //IntiroLog.detail(getClass().getName()+".doGet(): mode = ."+mode+".");
    //IntiroLog.detail(getClass().getName()+".doGet(): mode.trim() = ."+mode.trim()+".");
    this.mode = mode;
    this.activityId = activityId;

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".Constructor(): activityId = " + activityId);
    }

    //Load the activity to edit, view
    Activity oneActivity = new Activity();

    if (inEditMode || inViewMode || inDeleteMode) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".Constructor(): loading project with projid = " + activityId + ", inAddMode =  " + inAddMode);
      }

      oneActivity.load(activityId);
    }

    this.oneActivity = oneActivity;

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".Constructor(): Leaving");
    }
  }

  //~ Methods ..........................................................................................................

  public Activity getModifiedActivity() {
    return oneActivity;
  }

  /**
   * This is the method that will produce the XML.
   * It will fill the xmlDoc with XML.
   * @param    xmlDoc a StringBuffer to be filled with xml.
   */
  public void toXML(StringBuffer xmlDoc) throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): entered");
    }

    XMLBuilder builder = new XMLBuilder();

    //Get start of document
    builder.getStartOfDocument(xmlDoc);

    //Title
    xmlDoc.append(XML_TITLE_START);
    xmlDoc.append(title);
    xmlDoc.append(XML_TITLE_END);

    //Mode
    xmlDoc.append(XML_MODE_START);
    xmlDoc.append(mode);
    xmlDoc.append(XML_MODE_END);

    //Project
    xmlDoc.append(XML_ACTIVITY_START);

    if (getModifiedActivity() != null) {
      getModifiedActivity().toXML(xmlDoc);
    }

    xmlDoc.append(XML_ACTIVITY_END);

    //Get end of document
    builder.getEndOfDocument(xmlDoc);

    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer): xmlDoc = " + xmlDoc);
    }
  }
  /**
   * @return Returns the activityId.
   */
  public String getActivityId() {
    return activityId;
  }
  /**
   * @return Returns the inAddMode.
   */
  public boolean isInAddMode() {
    return inAddMode;
  }
  /**
   * @return Returns the inDeleteMode.
   */
  public boolean isInDeleteMode() {
    return inDeleteMode;
  }
  /**
   * @return Returns the inEditMode.
   */
  public boolean isInEditMode() {
    return inEditMode;
  }
  /**
   * @return Returns the inViewMode.
   */
  public boolean isInViewMode() {
    return inViewMode;
  }
  /**
   * @return Returns the mode.
   */
  public String getMode() {
    return mode;
  }
  /**
   * @return Returns the oneActivity.
   */
  public Activity getOneActivity() {
    return oneActivity;
  }
  /**
   * @return Returns the title.
   */
  public String getTitle() {
    return title;
  }
}