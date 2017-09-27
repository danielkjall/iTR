/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.ui;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * Base class for all of the UIElements on a page or Deck.
 */
public abstract class UIElement {

  //~ Instance/static variables ........................................................................................

  protected String cSSClass = "Title5";
  protected String cSSID = null;
  protected Vector <UIElement> contents = new Vector <UIElement> (5);
  private boolean hasBeenDisplayed = false;
  private String myObjName = new String("No Name");
  private UIElement myParent = null;
  private String thisClass = new String(this.getClass().getName() + ".");

  //~ Constructors .....................................................................................................

  /**
   * Constructor
   *
   * @throws    UIException If the superclass constructor fails
   */
  public UIElement() throws UIException {
    super();
  }

  /**
   * Constructor
   * Create the UIElement with the given UIElement as it's contents
   *
   * @param    newUIElement UIElement to use as contents of this new UIElement
   * @throws    UIException If the UIElement cannot be used or the paramter is invalid
   */
  public UIElement(UIElement newUIElement) throws UIException {
    super();
    add(newUIElement);
  }

  /**
   * Constructor
   * Create an UIElement with a name
   *
   * @param    UIElementName  The name of the new UIElement
   * @throws    UIException If the UIElement cannot be created
   */
  public UIElement(String UIElementName) throws UIException {
    super();

    if (UIElementName != null) {
      setName(UIElementName);
    }
    else {
      setName("null");
    }
  }

  //~ Methods ..........................................................................................................

  public void setCSSClass(String className) {
    cSSClass = className;
  }

  public void setCSSID(String id) {
    cSSID = id;
  }

  /**
   * Return the count of contents in this item
   */
  public int getContentCount() {
    return contents.size();
  }

  /**
   * Set the name of this UIElement
   *
   * @param    newName The new name
   * @throws    UIException If the name cannot be set
   */
  public void setName(String newName) throws UIException {
    String myName = new String(thisClass + "setName(String)");

    if (newName == null) { throw new UIException(myName + ":Cannot set name to null"); }

    myObjName = newName;
  }

  /**
   * Return the name of this UIElement
   *
   * @returns    String The name
   * @throws    UIException If the name cannot be accessed or the object has itself as a parent
   */
  public String getName() throws UIException {
    String myName = new String(thisClass + "getName()");

    if (myObjName == null) {
      myObjName = new String("Null Name");
    }
    if (myParent == this) { throw new UIException(myName + ":Object " + myObjName + " cannot have itself as a parent"); }

    return new String("<strong>" + myObjName + "</strong>");
  }

  /**
   * Add a new UIElement to this UIElement's contents
   *
   * @param    newUIElement UIElement to be added
   * @throws    UIException If the UIElement cannot be added
   */
  public synchronized void add(UIElement newUIElement) throws UIException {
    String myName = new String(thisClass + "add(UIElement)");

    if (newUIElement == null) { throw new UIException(myName + ":Cannot add null UIElement to '" + getName() + "'"); }

    newUIElement.setParent(this);
    contents.addElement(newUIElement);
  }

  /**
   * Alternate display method using an output stream
   *
   * @param    out            Outputstream to use to display to the client
   * @param    clientInfo        ClientInfo information about the client.
   * @param    eServiceName    the eServiceName that is to be accessed.
   * @throws   UIException If the UIElement (or it's contents) cannot be displayed
   */
  public void display(OutputStream out) throws UIException {
    display(new PrintWriter(out));
  }

  /**
   * Display the UIElement to the client
   * Each UIElement must implement this method
   *
   * @param    out            Outputstream to use to display to the client
   * @param    clientInfo        ClientInfo information about the client.
   * @throws   UIException If the UIElement (or it's contents) cannot be displayed
   */
  public void display(PrintWriter out) throws UIException {
    display(out);
  }

  /**
   * Mark this object as having been displayed
   *
   * @throws    UIException If the object has already been displayed
   */
  protected void setDisplayed() throws UIException {
    String myName = new String(thisClass + "setDisplayed()");

    if (hasBeenDisplayed) { throw new UIException(myName + ":UIElement " + getName() + " has already been displayed - cannot display twice"); }
  }

  /**
   * Set the parent of this UIElement to the named UIElement
   *
   * @param    newParent The new parent UIElement of this UIElement
   * @throws    UIException If the given UIElement cannot be used as a parent or the parameter is invalid
   */
  protected void setParent(UIElement newParent) throws UIException {
    String myName = new String(thisClass + "setParent(UIElement)");

    if (newParent == null) { throw new UIException(myName + ":Can't set parent of " + getName() + " to a null UIElement"); }
    if (myParent != null) {
      if (newParent == myParent) {
        throw new UIException(myName + ":This UIElement, " + getName() + ", has already been added to the UIElement you are adding it to: " + newParent.getName() + " - cannot add again");
      }
      else {
        throw new UIException(myName + ":This UIElement, '" + getName() + "', has already been added to another UIElement:" + myParent.getName() + " - cannot add it to another UIElements");
      }
    }

    myParent = newParent;
  }

  /**
   * See if the object has been displayed
   *
   * @throws    UIException if the object has never been displayed
   */
  protected void finalize() throws UIException {
    String myName = new String(thisClass + "finalize()");

    if (!hasBeenDisplayed) { throw new UIException(myName + ":WARNING: Object '" + getName() + "' was never displayed!"); }
  }
}