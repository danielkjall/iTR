/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.util.xml;

import java.util.ArrayList;

import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.toolbox.log.IntiroLog;

/**
 * This class represents a combo.
 * It is abstract so it need to be subclasses to be used by a specific combobox.
 *
 * @see com.intiro.itr.logic.DateCombo
 * @see com.intiro.itr.logic.LocationCombo
 *
 */
public abstract class XMLCombo extends DynamicXMLCarrier {

  //~ Instance/static variables ........................................................................................

  static final String XML_FALSE = "false";
  static final String XML_ITEM_END = "</item>";
  static final String XML_ITEM_SELECTED_END = "</selected>";
  static final String XML_ITEM_SELECTED_START = "<selected>";

  /*Item, name, value, selected*/
  static final String XML_ITEM_START = "<item>";
  static final String XML_ITEM_TEXT_END = "</text>";
  static final String XML_ITEM_TEXT_START = "<text>";
  static final String XML_ITEM_VALUE_END = "</value>";
  static final String XML_ITEM_VALUE_START = "<value>";
  static final String XML_TRUE = "true";
  protected ArrayList <Entry> entries = new ArrayList <Entry> ();
  protected String nameEnd = null;
  protected String nameStart = null;

  //~ Constructors .....................................................................................................

  /**
   * Constructor I for XMLCombo.
   * Creates a combo with a value = "null" and text = "---------",
   * if makeNullEntry is true.
   *
   * @param    profile                the UserProfile of the user.
   * @param makeNullEntry        a boolean specifying if empty entry should be added.
   */
  public XMLCombo(UserProfile profile, boolean makeNullEntry) throws XMLBuilderException {
    super(profile);

    if (makeNullEntry) {
      addEntry("null", "------");
    }
  }

  /**
   * Constructor II for XMLCombo.
   * Creates a combo with a value = "null" and text = nameOnNullEntry.
   *
   * @param    profile                the UserProfile of the user.
   * @param nameOnNullEntry        a String specifying the name on the entry with value "null".
   */
  public XMLCombo(UserProfile profile, String nameOnNullEntry) throws XMLBuilderException {
    super(profile);
    addEntry("null", nameOnNullEntry);
  }

  /**
   * Constructor III for XMLCombo.
   * Creates a combo with a value = "null" and text = "---------",
   * if makeNullEntry is true.
   *
   * @param makeNullEntry        a boolean specifying if empty entry should be added.
   */
  public XMLCombo(boolean makeNullEntry) throws XMLBuilderException {
    super();

    if (makeNullEntry) {
      addEntry("null", "------");
    }
  }

  /**
   * Constructor IV for XMLCombo.
   * Creates a combo with a value = "null" and text = nameOnNullEntry.
   *
   * @param nameOnNullEntry        a String specifying the name on the entry with value "null".
   */
  public XMLCombo(String nameOnNullEntry) throws XMLBuilderException {
    super();
    addEntry("null", nameOnNullEntry);
  }

  //~ Methods ..........................................................................................................

  /**
   * Set selected entry by value.
   */
  public void setSelectedValue(String valueToMatch) {
    Entry oneEntry = null;

    /*Loop through all enties in combobox*/
    for (int i = 0; i < entries.size(); i++) {
      oneEntry = entries.get(i);

      /*If found match, select else unselect all other*/
      if (oneEntry.value.equalsIgnoreCase(valueToMatch)) {
        oneEntry.setSelected(XML_TRUE);
      }
      else {
        oneEntry.setSelected(XML_FALSE);
      }
    }
  }

  /**
   * Create the head of the document.
   */
  public void setStartEndTags(String nameStart, String nameEnd) throws XMLBuilderException {
    this.nameStart = nameStart;
    this.nameEnd = nameEnd;
  }

  /**
   * Add an entry to combobox
   */
  public void addEntry(String value, String text) {
    Entry oneEntry = new Entry(value, text, XML_FALSE);
    entries.add(oneEntry);
  }

  /**
   * Add an selected entry to combobox
   */
  public void addSelectedEntry(String value, String text) {
    Entry oneEntry = new Entry(value, text, XML_TRUE);
    entries.add(oneEntry);
  }

  public abstract void load(String valueToBeSelected) throws XMLBuilderException;

  public void load() throws XMLBuilderException {
    load("null");
  }

  /**
   * Make xml of the combobox.
   */
  public void toXML(StringBuffer xmlDoc) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): Entering");
    }
    if (nameStart != null) {
      xmlDoc.append(nameStart);
    }

    Entry oneEntry = null;

    /*Loop through all entries in combobox*/
    for (int i = 0; i < entries.size(); i++) {
      oneEntry = entries.get(i);
      oneEntry.toXML(xmlDoc);
    }
    if (nameEnd != null) {
      xmlDoc.append(nameEnd);
    }
  }

  //~ Inner classes ....................................................................................................

  /**
   * This inner class represents an entry in an combobox.
   */
  public class Entry {

    private String selected = null;
    private String text = null;
    private String value = null;

    /**
     * Constructor for Entry.
     */
    Entry(String value, String text, String selected) {
      this.text = text;
      this.value = value;
      this.selected = selected;
    }

    void setSelected(String selected) {
      this.selected = selected;
    }

    void toXML(StringBuffer xmlDoc) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".toXML(): Entering");
      }

      //IntiroLog.info(getClass().getName()+".toXML(): value = " + value + ", text = " + text+", selected = " + selected);

      /*start*/
      xmlDoc.append(XML_ITEM_START);

      /*value*/
      xmlDoc.append(XML_ITEM_VALUE_START);
      xmlDoc.append(value);
      xmlDoc.append(XML_ITEM_VALUE_END);

      /*text*/
      xmlDoc.append(XML_ITEM_TEXT_START);
      xmlDoc.append(text);
      xmlDoc.append(XML_ITEM_TEXT_END);

      /*selected*/
      if (selected.equalsIgnoreCase(XML_TRUE)) {
        xmlDoc.append(XML_ITEM_SELECTED_START);
        xmlDoc.append(selected);
        xmlDoc.append(XML_ITEM_SELECTED_END);
      }

      /*end*/
      xmlDoc.append(XML_ITEM_END);
    }
    /**
     * @return Returns the selected.
     */
    public boolean isSelected() {
      if(selected != null && selected.equals("true")) return true;
      else return false;
    }
    
    /**
     * @return Returns the text.
     */
    public String getText() {
      return text;
    }
    /**
     * @return Returns the value.
     */
    public String getValue() {
      return value;
    }
  }

  /**
   * @return Returns the entries.
   */
  public ArrayList<Entry> getEntries() {
    return entries;
  }
}