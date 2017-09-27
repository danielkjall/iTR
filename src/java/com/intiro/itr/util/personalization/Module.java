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
import com.intiro.itr.db.DBQueries;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.toolbox.log.IntiroLog;

public class Module {

  //~ Instance/static variables ........................................................................................

  int moduleId = -1;
  String name = "";
  int rolesId = -1;

  //~ Methods ..........................................................................................................

  /**
   * Sets the moduleId of the module.
   *
   * @param   an int containing the moduleId of the module.
   */
  public void setModuleId(int moduleId) {
    this.moduleId = moduleId;
  }

  /**
   * Get the moduleId of the module.
   *
   * @return   an int containing the moduleId of the module.
   */
  public int getModuleId() {
    return moduleId;
  }

  /**
   * Sets the name of the module.
   *
   * @param   a String containing the name of the module.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get the name of the module.
   *
   * @return   a String containing the name of the module.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the roleId of the role.
   *
   * @param   an int containing the roleId of the role.
   */
  public void setRoleId(int roleId) {
    this.rolesId = roleId;
  }

  /**
   * Get the roleId of the role.
   *
   * @return   an int containing the roleId of the role.
   */
  public int getRoleId() {
    return rolesId;
  }

  /**
   * Load the modules for the roleId.
   */
  public static Vector <Module> load(int roleId) throws XMLBuilderException {
    Vector <Module> retval = new Vector <Module> ();

    try {
      StringRecordset rs = new DBQueries().getModulesForRole(roleId);

      while (!rs.getEOF()) {
        Module oneModule = new Module();
        oneModule.setModuleId(Integer.parseInt(rs.getField(DBConstants.MODULE_ID_PK)));
        oneModule.setRoleId(roleId);
        oneModule.setName(rs.getField(DBConstants.MODULE_MODULE));
        retval.add(oneModule);
        rs.moveNext();
      }
    } catch (Exception e) {
      if (IntiroLog.d()) {
        IntiroLog.detail(Module.class, ".load(String roleId): ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(e.getMessage());
    }

    return retval;
  }
}