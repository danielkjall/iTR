package com.intiro.itr.util.combos;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBQueries;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.xml.XMLCombo;
import com.intiro.itr.util.log.IntiroLog;

public class PhoneCountryCombo extends XMLCombo {

  /**
   * Constructor I for PhoneCountryCombo. Creates a combo with a value = "null" and text = "Select countrycode".
   *
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public PhoneCountryCombo() throws XMLBuilderException {
    this("Select countrycode");
  }

  /**
   * Constructor II for PhoneCountryCombo. Creates a combo with a value = "null" and text = "---------", if makeNullEntry is true.
   *
   * @param makeNullEntry a boolean specifying if empty entry should be added.
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public PhoneCountryCombo(boolean makeNullEntry) throws XMLBuilderException {
    super(makeNullEntry);
  }

  /**
   * Constructor III for PhoneCountryCombo. Creates a combo with a value = "null" and text = nameOnNullEntry.
   *
   * @param nameOnNullEntry a String specifying the name on the entry with value "null".
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public PhoneCountryCombo(String nameOnNullEntry) throws XMLBuilderException {
    super(nameOnNullEntry);
  }

  @Override
  public void load(String valueToBeSelected) throws XMLBuilderException {
    try {
      StringRecordset rs = DBQueries.getProxy().getAllPhoneCountries();

      while (!rs.getEOF()) {
        addEntry(rs.getField(DBConstants.PHONECOUNTRYCODE_ID_PK), rs.getField(DBConstants.PHONECOUNTRYCODE_COUNTRYCODE) + " (" + rs.getField(DBConstants.PHONECOUNTRYCODE_COUNTRYNAME) + ")");
        rs.moveNext();
      }

      setSelectedValue(valueToBeSelected);
      rs.close();
    } catch (Exception e) {
      if (IntiroLog.i()) {
        IntiroLog.info(getClass(), getClass().getName() + ".load(): ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(e.getMessage());
    }
  }
}
