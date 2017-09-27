/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 * @author        Daniel Kjall
 * @version       1.0
 */
package com.intiro.itr.util.personalization;

import java.util.Vector;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBExecute;
import com.intiro.itr.db.DBQueries;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.toolbox.log.IntiroLog;

public class Role {

  //~ Instance/static variables ........................................................................................

  boolean isAdmin = false;
  boolean isEmployee = false;
  boolean isSuperAdmin = false;
  boolean isUnderConsultant = false;
  Vector modules = new Vector();
  String name = "";
  int roleId = -1;
  int userId = -1;
  int userRoleId = -1;

  //~ Methods ..........................................................................................................

  //ADMIN
  public boolean isAdmin() {
    return isAdmin;
  }

  //EMPLOYEE
  public boolean isEmployee() {
    return isEmployee;
  }

  /**
   * Sets the Id.
   *
   * @param   an int containing the Id.
   */
  public void setId(int Id) {
    this.userRoleId = Id;
  }

  /**
   * Get the Id.
   *
   * @return   an int containing the id.
   */
  public int getId() {
    return userRoleId;
  }

  /**
   * Get the modules that the role is authorized for.
   *
   * @return   a Vector containing the names of the modules.
   */
  public Vector getModules() {
    return modules;
  }

  /**
   * Sets the name of the role.
   *
   * @param   a String containing the name of the role.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get the name of the role.
   *
   * @return   a String containing the name of the role.
   */
  public String getName() {
    return name;
  }

  /**
   * Check if this Role is authorized to use the Module with supplied moduleId.
   *
   * @param        moduleId, an int specifying the module to check for.
   */
  public boolean isRoleAuthorizedForModule(int moduleId) {
    boolean retval = false;

    for (int i = 0; i < getModules().size(); i++) {
      Module oneModule = (Module) getModules().get(i);

      if (oneModule != null && oneModule.getModuleId() == moduleId) {
        retval = true;
      }
    }

    return retval;
  }

  /**
   * Sets the roleId of the role.
   *
   * @param   an int containing the roleId of the role.
   */
  public void setRoleId(int roleId) {
    this.roleId = roleId;
  }

  /**
   * Get the roleId of the role.
   *
   * @return   an int containing the roleId of the role.
   */
  public int getRoleId() {
    return roleId;
  }

  //SUPER ADMIN
  public boolean isSuperAdmin() {
    return isSuperAdmin;
  }

  //UNDER CONSULTANT
  public boolean isUnderConsultant() {
    return isUnderConsultant;
  }

  /**
   * Sets the userId of the role.
   *
   * @param   an int containing the userId of the role.
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }

  /**
   * Get the userId of the role.
   *
   * @return   an int containing the userId of the role.
   */
  public int getUserId() {
    return userId;
  }

  /**
   * Load the role for the userid.
   */
  public void load(String userId) throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".load(): Entering");
    }

    //clear the roles before loading new roles.
    clear();

    try {
      StringRecordset rs = new DBQueries().getRoleForUser(userId);

      if (!rs.getEOF()) {
        setName(rs.getField(DBConstants.ROLES_NAME));
        setRoleId(Integer.parseInt(rs.getField(DBConstants.USERROLES_ROLESID_FK)));
        setUserId(Integer.parseInt(userId));
        setId(Integer.parseInt(rs.getField(DBConstants.USERROLES_ID_PK)));

        if (getRoleId() == 1) { // Super Admin
          setSuperAdmin();
        }
        else if (getRoleId() == 2) { //Admin
          setAdmin();
        }
        else if (getRoleId() == 3) { //Employee
          setEmployee();
        }
        else if (getRoleId() == 4) { //Under Consultant
          setUnderConsultant();
        }
      }

      modules = Module.load(getRoleId());
    } catch (Exception e) {
      if (IntiroLog.ce()) {
        IntiroLog.criticalError(getClass(), getClass().getName() + ".load(String userId): ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(e.getMessage());
    }
  }

  /**
   * Save the role.
   */
  public void save() throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".save(): Entering");
    }
    if (getId() != -1) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".save(): updating");
      }
      try {
        DBExecute dbExecute = new DBExecute();
        dbExecute.updateUserRoleConnection(this);
      } catch (Exception e) {
        if (IntiroLog.ce()) {
          IntiroLog.criticalError(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception	= " + e.getMessage());
        }

        throw new XMLBuilderException(e.getMessage());
      }
    }
    else {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".save(): creating new");
      }
      try {
        DBQueries dbQuery = new DBQueries();
        StringRecordset rs = dbQuery.makeNewUserRoleConnectionAndFetchId(this);

        if (!rs.getEOF()) {
          setId(Integer.parseInt(rs.getField("maxId")));
        }
      } catch (Exception e) {
        if (IntiroLog.ce()) {
          IntiroLog.criticalError(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception	= " + e.getMessage());
        }

        throw new XMLBuilderException(e.getMessage());
      }
    }

    //We have to load the Role to get the right status on the object.
    load(String.valueOf(getUserId()));
  }

  void setAdmin() {
    isAdmin = true;
  }

  void setEmployee() {
    isEmployee = true;
  }

  void setSuperAdmin() {
    isSuperAdmin = true;
  }

  void setUnderConsultant() {
    isUnderConsultant = true;
  }

  /**
   * Clear the roles.
   */
  void clear() {
    modules = new Vector();
    isUnderConsultant = false;
    isEmployee = false;
    isAdmin = false;
    isSuperAdmin = false;
  }
}