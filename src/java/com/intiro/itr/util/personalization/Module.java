/**
 * Title:         ITR
 * Description:
 * Copyright:     Copyright (c) 2001
 * Company:       Intiro Development AB
 *
 * @author Daniel Kjall
 * @version 1.0
 */
package com.intiro.itr.util.personalization;

import java.util.ArrayList;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBQueriesConfig;
import com.intiro.itr.db.InvocationHandlerSetting;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.cache.ItrCache;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;
import java.util.HashMap;
import java.util.Map;

public class Module {

  public static final String CACHE_ALL_MODULES = "CACHE_ALL_MODULES";
  private int moduleId = -1;
  private String name = "";
  private int rolesId = -1;

  public void setModuleId(int moduleId) {
    this.moduleId = moduleId;
  }

  public int getModuleId() {
    return moduleId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setRoleId(int roleId) {
    this.rolesId = roleId;
  }

  public int getRoleId() {
    return rolesId;
  }

  public static Map<Integer, ArrayList<Module>> loadAllModules() throws XMLBuilderException {
    Map<Integer, ArrayList<Module>> retval = new HashMap<>();
    try {

      String cacheKey = Module.class.getName() + ".loadAllModules";
      String statisticKey = Module.class.getName() + ".loadAllModules";
      int cacheTime = 3600 * 10;
      InvocationHandlerSetting s = InvocationHandlerSetting.create(cacheKey, cacheTime, statisticKey);
      StringRecordset rs = DBQueriesConfig.getProxy(s).getModulesForRole(-1);
      while (!rs.getEOF()) {
        Module oneModule = new Module();
        oneModule.setModuleId(Integer.parseInt(rs.getField(DBConstants.MODULE_ID_PK)));
        oneModule.setRoleId(Integer.parseInt(rs.getField(DBConstants.MODULE_ROLESID_FK)));
        oneModule.setName(rs.getField(DBConstants.MODULE_MODULE));

        Integer key = oneModule.getRoleId();
        if (retval.containsKey(key) == false) {
          retval.put(key, new ArrayList<>());
        }
        retval.get(key).add(oneModule);

        rs.moveNext();
      }
    } catch (Exception e) {
      if (IntiroLog.d()) {
        IntiroLog.detail(Module.class, ".loadAllModules(): ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(e.getMessage());
    }

    return retval;
  }

  public static ArrayList<Module> load(int roleId) throws XMLBuilderException {
    ArrayList<Module> retval = new ArrayList<>();

    try {
      String cacheKey = Module.class.getName() + ".load_" + roleId;
      String statisticKey = Module.class.getName() + ".load";
      int cacheTime = 3600 * 10;
      InvocationHandlerSetting s = InvocationHandlerSetting.create(cacheKey, cacheTime, statisticKey);
      StringRecordset rs = DBQueriesConfig.getProxy(s).getModulesForRole(roleId);

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
        IntiroLog.detail(Module.class, ".load(int roleId): ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(e.getMessage());
    }

    return retval;
  }
}
