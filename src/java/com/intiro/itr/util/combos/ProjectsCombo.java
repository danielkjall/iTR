package com.intiro.itr.util.combos;

import com.intiro.itr.db.*;
import com.intiro.itr.util.*;
import com.intiro.itr.util.personalization.*;
import com.intiro.itr.util.xml.*;
import com.intiro.itr.util.log.IntiroLog;

public class ProjectsCombo extends XMLCombo {

  int userId = -1;

  /**
   * Constructor I for ProjectsCombo. Creates a combo with a value = "null" and text = "Select project".
   *
   *
   * @param profile the UserProfile of the user.
   */
  public ProjectsCombo(UserProfile profile) throws XMLBuilderException {
    this(profile, "Select project");
  }

  /**
   * Constructor II for ProjectsCombo. Creates a combo with a value = "null" and text = "---------", if makeNullEntry is true.
   *
   * @param profile the UserProfile of the user.
   * @param makeNullEntry a boolean specifying if empty entry should be added.
   */
  public ProjectsCombo(UserProfile profile, boolean makeNullEntry) throws XMLBuilderException {
    super(profile, makeNullEntry);
  }

  /**
   * Constructor III for ProjectsCombo. Creates a combo with a value = "null" and text = nameOnNullEntry.
   *
   * @param profile the UserProfile of the user.
   * @param nameOnNullEntry a String specifying the name on the entry with value "null".
   */
  public ProjectsCombo(UserProfile profile, String nameOnNullEntry) throws XMLBuilderException {
    super(profile, nameOnNullEntry);
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  @Override
  public void load(String valueToBeSelected)
          throws XMLBuilderException {
    try {
      StringRecordset rs = null;

      DBQueriesInterface proxy = DBQueries.getProxy();
      if (userId == -1) {
        rs = proxy.getAllProjects();
      } else {
        rs = proxy.getProjectsForUser(userId);
      }
      while (!rs.getEOF()) {
        addEntry(rs.getField(DBConstants.PROJECT_ID_PK), rs.getField(DBConstants.PROJECT_NAME));
        rs.moveNext();
      }

      setSelectedValue(valueToBeSelected);
      rs.close();
    } catch (Exception e) {
      if (IntiroLog.i()) {
        IntiroLog.info(getClass(),
                getClass().getName() + ".load(): ERROR FROM DATABASE, exception = " + e.getMessage());
      }

      throw new XMLBuilderException(e.getMessage());
    }
  }
}
