/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Fredrik Bjork
 * @version       1.0
 */
package com.intiro.itr.logic.company;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBExecute;
import com.intiro.itr.db.DBQueries;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.toolbox.log.IntiroLog;

public class Company {

  //~ Instance/static variables ........................................................................................

  static final String XML_COMPANYID_END = "</compid>";
  static final String XML_COMPANYID_START = "<compid>";
  static final String XML_COMPANY_END = "</company>";
  static final String XML_COMPANY_START = "<company>";
  static final String XML_INVOICEADDRESSROW1_END = "</invoiceaddressrow1>";
  static final String XML_INVOICEADDRESSROW1_START = "<invoiceaddressrow1>";
  static final String XML_INVOICEADDRESSROW2_END = "</invoiceaddressrow2>";
  static final String XML_INVOICEADDRESSROW2_START = "<invoiceaddressrow2>";
  static final String XML_INVOICEADDRESSROW3_END = "</invoiceaddressrow3>";
  static final String XML_INVOICEADDRESSROW3_START = "<invoiceaddressrow3>";
  static final String XML_NAME_END = "</name>";
  static final String XML_NAME_START = "<name>";
  static final String XML_VISITADDRESSROW1_END = "</visitaddressrow1>";
  static final String XML_VISITADDRESSROW1_START = "<visitaddressrow1>";
  static final String XML_VISITADDRESSROW2_END = "</visitaddressrow2>";
  static final String XML_VISITADDRESSROW2_START = "<visitaddressrow2>";
  static final String XML_VISITADDRESSROW3_END = "</visitaddressrow3>";
  static final String XML_VISITADDRESSROW3_START = "<visitaddressrow3>";
  int companyId = -1;
  int companyParentId = -1;
  String invoiceAddressRow1 = "";
  String invoiceAddressRow2 = "";
  String invoiceAddressRow3 = "";
  String name = "";
  String visitAddressRow1 = "";
  String visitAddressRow2 = "";
  String visitAddressRow3 = "";

  //~ Constructors .....................................................................................................

  /**
   * Constructor I for Company.
   */
  public Company() {
    //empty
  }

  //~ Methods ..........................................................................................................

  /**
   * Gets the CompanyId of the logged on user.
   *
   * @return      an int, specifying the PhoneId.
   */
  public int getCompanyId() {
    return companyId;
  }

  /**
   * Gets the CompanyParentId of the logged on user.
   *
   * @return      an int, specifying the PhoneId.
   */
  public int getCompanyParentId() {
    return companyParentId;
  }

  /**
   * Sets the invoiceAddressRow1 of the company.
   *
   * @param      invoiceAddressRow1 of the company.
   */
  public void setInvoiceAddressRow1(String invoiceAddressRow1) {
    this.invoiceAddressRow1 = invoiceAddressRow1;
  }

  /**
   * Gets the invoiceAddressRow1 of the company.
   *
   * @return      invoiceAddressRow1 of the company.
   */
  public String getInvoiceAddressRow1() {
    return invoiceAddressRow1;
  }

  /**
   * Sets the invoiceAddressRow2 of the company.
   *
   * @param      invoiceAddressRow2 of the company.
   */
  public void setInvoiceAddressRow2(String invoiceAddressRow2) {
    this.invoiceAddressRow2 = invoiceAddressRow2;
  }

  /**
   * Gets the invoiceAddressRow1 of the company.
   *
   * @return      invoiceAddressRow2 of the company.
   */
  public String getInvoiceAddressRow2() {
    return invoiceAddressRow2;
  }

  /**
   * Sets the invoiceAddressRow3 of the company.
   *
   * @param      invoiceAddressRow3 of the company.
   */
  public void setInvoiceAddressRow3(String invoiceAddressRow3) {
    this.invoiceAddressRow3 = invoiceAddressRow3;
  }

  /**
   * Gets the invoiceAddressRow3 of the company.
   *
   * @return      invoiceAddressRow3 of the company.
   */
  public String getInvoiceAddressRow3() {
    return invoiceAddressRow3;
  }

  /**
   * Sets the name of the company.
   *
   * @param      the name of tha company.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the name of the company.
   *
   * @return     The name of tha company .
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the visitAddressRow1 of the company.
   *
   * @param      visitAddressRow1 of the company.
   */
  public void setVisitAddressRow1(String visitAddressRow1) {
    this.visitAddressRow1 = visitAddressRow1;
  }

  /**
   * Gets the visitAddressRow1 of the company.
   *
   * @return      visitAddressRow1 of the company.
   */
  public String getVisitAddressRow1() {
    return visitAddressRow1;
  }

  /**
   * Sets the visitAddressRow2 of the company.
   *
   * @param      visitAddressRow2 of the company.
   */
  public void setVisitAddressRow2(String visitAddressRow2) {
    this.visitAddressRow2 = visitAddressRow2;
  }

  /**
   * Gets the visitAddressRow2 of the company.
   *
   * @return      visitAddressRow2 of the company.
   */
  public String getVisitAddressRow2() {
    return visitAddressRow2;
  }

  /**
   * Sets the visitAddressRow3 of the company.
   *
   * @param      visitAddressRow3 of the company.
   */
  public void setVisitAddressRow3(String visitAddressRow3) {
    this.visitAddressRow3 = visitAddressRow3;
  }

  /**
   * Gets the visitAddressRow3 of the company.
   *
   * @return      visitAddressRow3 of the company.
   */
  public String getVisitAddressRow3() {
    return visitAddressRow3;
  }

  /**
   * Delete the Company.
   *
   * @return boolean.  false if nothing was deleted from db
   */
  public boolean delete() throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(Company.class, "com.intiro.itr.logic.company.Company.load(): Entering");
    }
    try {
      boolean retVal = new DBExecute().deleteCompany(companyId);

      return retVal;
    } catch (Exception e) {
      IntiroLog.info(getClass(), getClass().getName() + ".delete(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }
  }

  /**
   * Load the Company for the specified companyId.
   *
   * @return Company
   */
  public void load(int companyId) throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(Company.class, "com.intiro.itr.logic.company.Company.load(): Entering");
    }
    try {
      if (companyId == -1 || companyId == 0) { throw new Exception("Company.load(String companyId): Input has no meaningfull value."); }

      StringRecordset rs = new DBQueries().loadCompany(Integer.toString(companyId));
      setCompanyId(Integer.parseInt(rs.getField(DBConstants.COMPANY_ID_PK)));

      //        setCompanyParentId(Integer.parseInt(rs.getField(DBConstants.COMPANYPARENT_ID_PK)));
      setName(rs.getField(DBConstants.COMPANY_NAME));
      setInvoiceAddressRow1(rs.getField(DBConstants.COMPANY_INVOICEADDRESS_ROW1));
      setInvoiceAddressRow2(rs.getField(DBConstants.COMPANY_INVOICEADDRESS_ROW2));
      setInvoiceAddressRow3(rs.getField(DBConstants.COMPANY_INVOICEADDRESS_ROW3));
      setVisitAddressRow1(rs.getField(DBConstants.COMPANY_VISITADDRESS_ROW1));
      setVisitAddressRow2(rs.getField(DBConstants.COMPANY_VISITADDRESS_ROW2));
      setVisitAddressRow3(rs.getField(DBConstants.COMPANY_VISITADDRESS_ROW3));
    } catch (Exception e) {
      IntiroLog.info(Company.class, ".load(String companyId): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }
  }

  /**
   * Save the company to db.
   */
  public void save() throws Exception {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".save(): Entering");
    }
    // if a new company is created
    if (getCompanyId() == -1) {
      try {
        DBQueries dbQueries = new DBQueries();
        StringRecordset rs = dbQueries.addCompanyAndGetId(this);

        if (!rs.getEOF()) {
          setCompanyId(Integer.parseInt(rs.getField("maxId")));
        }
        else {
          throw new XMLBuilderException(getClass().getName() + ".save(): Could not make and find a new company.");
        }
      } catch (Exception e) {
        if (IntiroLog.d()) {
          IntiroLog.detail(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
        }

        throw new Exception(e.getMessage());
      }
    }

    // if an existing company is updated
    else {
      try {
        DBExecute dbExecute = new DBExecute();
        dbExecute.updateCompany(this);
      } catch (Exception e) {
        if (IntiroLog.e()) {
          IntiroLog.error(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception = " + e.getMessage());
        }

        throw new Exception(e.getMessage());
      }
    }
  }

  public String toString() {
    StringBuffer retval = new StringBuffer();
    this.toXML(retval);

    return retval.toString();
  }

  public void toXML(StringBuffer xmlDoc) {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".toXML(StringBuffer xmlDoc): Entering");
    }

    xmlDoc.append(XML_COMPANY_START);
    xmlDoc.append(XML_NAME_START);
    xmlDoc.append(getName());
    xmlDoc.append(XML_NAME_END);
    xmlDoc.append(XML_INVOICEADDRESSROW1_START);
    xmlDoc.append(getInvoiceAddressRow1());
    xmlDoc.append(XML_INVOICEADDRESSROW1_END);
    xmlDoc.append(XML_INVOICEADDRESSROW2_START);
    xmlDoc.append(getInvoiceAddressRow2());
    xmlDoc.append(XML_INVOICEADDRESSROW2_END);
    xmlDoc.append(XML_INVOICEADDRESSROW3_START);
    xmlDoc.append(getInvoiceAddressRow3());
    xmlDoc.append(XML_INVOICEADDRESSROW3_END);
    xmlDoc.append(XML_VISITADDRESSROW1_START);
    xmlDoc.append(getVisitAddressRow1());
    xmlDoc.append(XML_VISITADDRESSROW1_END);
    xmlDoc.append(XML_VISITADDRESSROW2_START);
    xmlDoc.append(getVisitAddressRow2());
    xmlDoc.append(XML_VISITADDRESSROW2_END);
    xmlDoc.append(XML_VISITADDRESSROW3_START);
    xmlDoc.append(getVisitAddressRow3());
    xmlDoc.append(XML_VISITADDRESSROW3_END);
    xmlDoc.append(XML_COMPANY_END);
  }

  /**
   * Sets the companyId.
   *
   * @param      companyId, an int specifying the companyId.
   */
  protected void setCompanyId(int companyId) {
    this.companyId = companyId;
  }

  /**
   * Sets the companyParentId.
   *
   * @param      companyId, an int specifying the companyId.
   */
  protected void setCompanyParentId(int companyParentId) {
    this.companyParentId = companyParentId;
  }
}