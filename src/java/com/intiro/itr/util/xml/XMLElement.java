/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.util.xml;

import java.util.Vector;

import org.w3c.dom.Document;

import com.intiro.itr.util.personalization.UserProfile;

/**
 * Base class for all of the XMLCarriers.
 */
public abstract class XMLElement implements XMLCarrier {

  //~ Instance/static variables ........................................................................................

  protected Vector <XMLElement> contents = new Vector <XMLElement> (5);
  protected UserProfile userProfile = null;
  private boolean hasBeenDisplayed = false;
  private String myObjName = new String("No Name");
  private XMLElement myParent = null;
  private String thisClass = new String(this.getClass().getName() + ".");

  //~ Constructors .....................................................................................................

  /**
   * Constructor I
   */
  public XMLElement() {
    super();
  }

  /**
   * Constructor II
   *
   * @throws    XMLBuilderException If the superclass constructor fails
   */
  public XMLElement(UserProfile userProfile) throws XMLBuilderException {
    super();
    this.userProfile = userProfile;
  }

  /**
   * Constructor III
   * Create the XMLElement with the given XMLElement as it's contents
   *
   * @param    newXMLElement XMLElement to use as contents of this new XMLElement
   * @throws    XMLBuilderException If the XMLElement cannot be used or the paramter is invalid
   */
  public XMLElement(XMLElement newXMLElement) throws XMLBuilderException {
    super();
    add(newXMLElement);
  }

  //~ Methods ..........................................................................................................

  /**
   * This method return the Document held by this class.
   * It uses toXML(xmlDoc) to make a Document.
   *
   * @exception    Exception, if something goes wrong.
   */
  public abstract Document getDocument() throws Exception;

  /**
   * Set the name of this XMLElement
   *
   * @param    newName The new name
   * @throws    XMLBuilderException If the name cannot be set
   */
  public void setName(String newName) throws XMLBuilderException {
    String myName = new String(thisClass + "setName(String)");

    if (newName == null) { throw new XMLBuilderException(myName + ":Cannot set name to null"); }

    myObjName = newName;
  }

  /**
   * This method returns the user profile.
   *
   */
  public UserProfile getUserProfile() {
    return userProfile;
  }

  /**
   * Add a new XMLElement to this XMLElement's contents
   *
   * @param    newXMLElement XMLElement to be added
   * @throws    XMLBuilderException If the XMLElement cannot be added
   */
  public synchronized void add(XMLElement newXMLElement) throws XMLBuilderException {
    String myName = new String(thisClass + "add(XMLElement)");

    if (newXMLElement == null) { throw new XMLBuilderException(myName + ":Cannot add null XMLElement to '" + getName() + "'"); }

    newXMLElement.setParent(this);
    contents.addElement(newXMLElement);
  }

  /**
   * This is the method that will produce the XML.
   * It will fill the xmlDoc with XML.
   * @param    xmlDoc a StringBuffer to be filled with xml.
   *
   */
  public abstract void toXML(StringBuffer xmlDoc) throws Exception;

  /**
   * Return the count of contents in this item
   */
  public int getContentCount() {
    return contents.size();
  }

  /**
   * Return the name of this XMLElement
   *
   * @returns    String The name
   * @throws    XMLBuilderException If the name cannot be accessed or the object has itself as a parent
   */
  public String getName() throws XMLBuilderException {
    String myName = new String(thisClass + "getName()");

    if (myObjName == null) {
      myObjName = new String("Null Name");
    }
    if (myParent == this) { throw new XMLBuilderException(myName + ":Object " + myObjName + " cannot have itself as a parent"); }

    return new String("<strong>" + myObjName + "</strong>");
  }

  /**
   * Mark this object as having been displayed
   *
   * @throws    XMLBuilderException If the object has already been displayed
   */
  protected void setDisplayed() throws XMLBuilderException {
    String myName = new String(thisClass + "setDisplayed()");

    if (hasBeenDisplayed) { throw new XMLBuilderException(myName + ":XMLElement " + getName() + " has already been displayed - cannot display twice"); }
  }

  /**
   * Set the parent of this XMLElement to the named XMLElement
   *
   * @param    newParent The new parent XMLElement of this XMLElement
   * @throws    XMLBuilderException If the given XMLElement cannot be used as a parent or the parameter is invalid
   */
  protected void setParent(XMLElement newParent) throws XMLBuilderException {
    String myName = new String(thisClass + "setParent(XMLElement)");

    if (newParent == null) { throw new XMLBuilderException(myName + ":Can't set parent of " + getName() + " to a null XMLElement"); }
    if (myParent != null) {
      if (newParent == myParent) {
        throw new XMLBuilderException(myName + ":This XMLElement, " + getName() + ", has already been added to the XMLElement you are adding it to: " + newParent.getName() + " - cannot add again");
      }
      else {
        throw new XMLBuilderException(myName + ":This XMLElement, '" + getName() + "', has already been added to another XMLElement:" + myParent.getName() + " - cannot add it to another XMLElements");
      }
    }

    myParent = newParent;
  }

  /**
   * See if the object has been displayed
   *
   * @throws    XMLBuilderException if the object has never been displayed
   */
  protected void finalize() throws XMLBuilderException {
    String myName = new String(thisClass + "finalize()");

    if (!hasBeenDisplayed) { throw new XMLBuilderException(myName + ":WARNING: Object '" + getName() + "' was never displayed!"); }
  }
}