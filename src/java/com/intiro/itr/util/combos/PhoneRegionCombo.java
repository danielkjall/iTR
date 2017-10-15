package com.intiro.itr.util.combos;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBQueriesConfig;
import com.intiro.itr.db.InvocationHandlerSetting;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.xml.XMLCombo;
import com.intiro.itr.util.log.IntiroLog;

public class PhoneRegionCombo extends XMLCombo {

  /**
   * Constructor I for PhoneRegionCombo. Creates a combo with a value = "null" and text = "Select regioncode".
   *
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public PhoneRegionCombo() throws XMLBuilderException {
    this("Select regioncode");
  }

  /**
   * Constructor II for PhoneRegionCombo. Creates a combo with a value = "null" and text = "---------", if makeNullEntry is true.
   *
   * @param makeNullEntry a boolean specifying if empty entry should be added.
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public PhoneRegionCombo(boolean makeNullEntry) throws XMLBuilderException {
    super(makeNullEntry);
  }

  /**
   * Constructor III for PhoneRegionCombo. Creates a combo with a value = "null" and text = nameOnNullEntry.
   *
   * @param nameOnNullEntry a String specifying the name on the entry with value "null".
   * @throws com.intiro.itr.util.xml.XMLBuilderException
   */
  public PhoneRegionCombo(String nameOnNullEntry) throws XMLBuilderException {
    super(nameOnNullEntry);
  }

  @Override
  public void load(String valueToBeSelected) throws XMLBuilderException {
    try {
      String cacheKey = getClass().getName() + ".getAllPhoneRegions";
      String statisticKey = getClass().getName() + ".load";
      int cacheTime = 3600 * 10;
      InvocationHandlerSetting s = InvocationHandlerSetting.create(cacheKey, cacheTime, statisticKey);
      StringRecordset rs = DBQueriesConfig.getProxy(s).getAllPhoneRegions();

      while (!rs.getEOF()) {
        addEntry(rs.getField(DBConstants.PHONEREGIONCODE_ID_PK), rs.getField(DBConstants.PHONEREGIONCODE_REGIONCODE) + " (" + rs.getField(DBConstants.PHONEREGIONCODE_REGIONNAME) + ")");
        rs.moveNext();
      }

      setSelectedValue(valueToBeSelected);
      rs.close();
    } catch (Exception e) {
      if (IntiroLog.i()) {
        IntiroLog.info(getClass(), "ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(e.getMessage());
    }
  }
}
