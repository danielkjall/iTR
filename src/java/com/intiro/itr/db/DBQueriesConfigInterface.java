package com.intiro.itr.db;

import com.intiro.itr.db.vo.ProjectPropertyVO;
import com.intiro.itr.db.vo.CalendarWeekVO;
import com.intiro.itr.util.StringRecordset;
import java.util.Map;

public interface DBQueriesConfigInterface extends InvocationHandled {

  StringRecordset getActivitiesForProject(int projId, int activityId) throws Exception;

  StringRecordset getAllCompanies() throws Exception;

  StringRecordset getAllPhoneCountries() throws Exception;

  StringRecordset getAllPhoneRegions() throws Exception;

  StringRecordset getAllProjectCodes() throws Exception;

  StringRecordset getAllProjects() throws Exception;

  StringRecordset getAllUsers() throws Exception;

  CalendarWeekVO getCalendarWeek(String fromDate) throws Exception;

  StringRecordset getContacts() throws Exception;

  StringRecordset getEmails(int userId, int contactId, int emailId) throws Exception;

  StringRecordset getAllEmails() throws Exception;

  StringRecordset getLanguages() throws Exception;

  StringRecordset getModulesForRole(int roleId) throws Exception;

  StringRecordset getPhoneCountry(int countryId) throws Exception;

  StringRecordset getPhoneNumbers(int userId, int contactId, int phoneId) throws Exception;

  StringRecordset getAllPhoneNumbers() throws Exception;

  StringRecordset getPhoneRegion(int regionId) throws Exception;

  StringRecordset getProjectCodesForProject(int projId) throws Exception;

  ProjectPropertyVO getProjectProperty(String projId) throws Exception;

  StringRecordset getProjectsForUser(int userId) throws Exception;

  StringRecordset getReportYears() throws Exception;

  StringRecordset getRoleForUser(String userId) throws Exception;

  StringRecordset getRoles() throws Exception;

  StringRecordset getSettings() throws Exception;

  String getProperty(String key) throws Exception;

  Map<String, String> getProperties() throws Exception;

  StringRecordset getSkins() throws Exception;

  StringRecordset getTimeType(String id) throws Exception;

  StringRecordset getTimeTypes() throws Exception;

  StringRecordset getUsers(boolean activated) throws Exception;
}
