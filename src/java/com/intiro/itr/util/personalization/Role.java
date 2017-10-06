package com.intiro.itr.util.personalization;

import java.util.ArrayList;

import com.intiro.itr.db.DBConstants;
import com.intiro.itr.db.DBExecute;
import com.intiro.itr.db.DBQueries;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.cache.ItrCache;
import com.intiro.itr.util.xml.XMLBuilderException;
import com.intiro.itr.util.log.IntiroLog;
import java.util.HashMap;
import java.util.Map;

public class Role {

  public static final String CACHE_ALL_ROLE = "CACHE_ALL_ROLE";
  private boolean isAdmin = false;
  private boolean isEmployee = false;
  private boolean isSuperAdmin = false;
  private boolean isUnderConsultant = false;
  private ArrayList<Module> modules = new ArrayList<>();
  private String name = "";
  private int roleId = -1;
  private int userId = -1;
  private int userRoleId = -1;

  //ADMIN
  public boolean isAdmin() {
    return isAdmin;
  }

  //EMPLOYEE
  public boolean isEmployee() {
    return isEmployee;
  }

  public void setId(int Id) {
    this.userRoleId = Id;
  }

  public int getId() {
    return userRoleId;
  }

  public ArrayList getModules() {
    return modules;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  /**
   * Check if this Role is authorized to use the Module with supplied moduleId.
   *
   * @param moduleId, an int specifying the module to check for.
   * @return
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

  public void setRoleId(int roleId) {
    this.roleId = roleId;
  }

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

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getUserId() {
    return userId;
  }

  public static Map<Integer, ArrayList<Role>> loadAllRoles() throws XMLBuilderException {
    Map<Integer, ArrayList<Role>> retval = new HashMap<>();

    try {
      Map<Integer, ArrayList<Role>> cached = ItrCache.get(CACHE_ALL_ROLE);
      if (cached != null) {
        return cached;
      }

      Map<Integer, ArrayList<Module>> mapModules = Module.loadAllModules();

      StringRecordset rs = DBQueries.getProxy().getRoleForUser(null);
      while (!rs.getEOF()) {
        Role aRole = new Role();
        aRole.setName(rs.getField(DBConstants.ROLES_NAME));
        aRole.setRoleId(Integer.parseInt(rs.getField(DBConstants.USERROLES_ROLESID_FK)));
        aRole.setUserId(Integer.parseInt(rs.getField(DBConstants.USERROLES_USERID_FK)));
        aRole.setId(Integer.parseInt(rs.getField(DBConstants.USERROLES_ID_PK)));

        if (aRole.getRoleId() == 1) { // Super Admin
          aRole.setSuperAdmin();
        } else if (aRole.getRoleId() == 2) { //Admin
          aRole.setAdmin();
        } else if (aRole.getRoleId() == 3) { //Employee
          aRole.setEmployee();
        } else if (aRole.getRoleId() == 4) { //Under Consultant
          aRole.setUnderConsultant();
        }
        aRole.modules = mapModules.get(aRole.getRoleId());

        Integer key = aRole.getUserId();
        if (retval.containsKey(key)) {
          retval.put(aRole.getUserId(), new ArrayList<>());
        }
        retval.get(key).add(aRole);

        rs.moveNext();
      }

    } catch (Exception e) {
      IntiroLog.criticalError(Role.class, Role.class.getName() + ".loadAllRoles(): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }

    final int TenHours = 1 * 60 * 60 * 10;
    ItrCache.put(CACHE_ALL_ROLE, retval, TenHours);
    return retval;
  }

  public void load(String userId) throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".load(): Entering");
    }

    //clear the roles before loading new roles.
    clear();

    try {
      StringRecordset rs = DBQueries.getProxy().getRoleForUser(userId);

      if (!rs.getEOF()) {
        setName(rs.getField(DBConstants.ROLES_NAME));
        setRoleId(Integer.parseInt(rs.getField(DBConstants.USERROLES_ROLESID_FK)));
        setUserId(Integer.parseInt(userId));
        setId(Integer.parseInt(rs.getField(DBConstants.USERROLES_ID_PK)));

        if (getRoleId() == 1) { // Super Admin
          setSuperAdmin();
        } else if (getRoleId() == 2) { //Admin
          setAdmin();
        } else if (getRoleId() == 3) { //Employee
          setEmployee();
        } else if (getRoleId() == 4) { //Under Consultant
          setUnderConsultant();
        }
      }

      modules = Module.load(getRoleId());
    } catch (Exception e) {
      IntiroLog.criticalError(getClass(), getClass().getName() + ".load(String userId): ERROR FROM DATABASE, exception = " + e.getMessage());
      throw new XMLBuilderException(e.getMessage());
    }
  }

  public void save() throws XMLBuilderException {
    if (IntiroLog.d()) {
      IntiroLog.detail(getClass(), getClass().getName() + ".save(): Entering");
    }
    if (getId() != -1) {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".save(): updating");
      }
      try {
        DBExecute.getProxy().updateUserRoleConnection(this);
      } catch (Exception e) {
        IntiroLog.criticalError(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception	= " + e.getMessage());
        throw new XMLBuilderException(e.getMessage());
      }
    } else {
      if (IntiroLog.d()) {
        IntiroLog.detail(getClass(), getClass().getName() + ".save(): creating new");
      }
      try {
        StringRecordset rs = DBQueries.getProxy().makeNewUserRoleConnectionAndFetchId(this);

        if (!rs.getEOF()) {
          setId(Integer.parseInt(rs.getField("maxId")));
        }
      } catch (Exception e) {
        IntiroLog.criticalError(getClass(), getClass().getName() + ".save(): ERROR FROM DATABASE, exception	= " + e.getMessage());
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

  void clear() {
    modules = new ArrayList();
    isUnderConsultant = false;
    isEmployee = false;
    isAdmin = false;
    isSuperAdmin = false;
  }
}
