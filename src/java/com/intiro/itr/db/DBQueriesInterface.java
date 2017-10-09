package com.intiro.itr.db;

import com.intiro.itr.db.vo.ProjectPropertyVO;
import com.intiro.itr.db.vo.CalendarWeekVO;
import com.intiro.itr.logic.activity.Activity;
import com.intiro.itr.logic.company.Company;
import com.intiro.itr.logic.contacts.Contacts;
import com.intiro.itr.logic.email.Email;
import com.intiro.itr.logic.phone.PhoneNumber;
import com.intiro.itr.logic.project.Project;
import com.intiro.itr.logic.project.ProjectActivity;
import com.intiro.itr.logic.project.ProjectMember;
import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.personalization.Role;
import com.intiro.itr.util.personalization.UserProfile;
import java.util.Map;

public interface DBQueriesInterface extends InvocationHandled {

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

  StringRecordset getGeneralReport(String userId, String projectId, String projectCodeId, String fromDate, String toDate) throws Exception;

  StringRecordset getLanguages() throws Exception;

  StringRecordset getModulesForRole(int roleId) throws Exception;

  StringRecordset getMonthlyReport(String userId, String projectId, String projectCodeId, String fromDate, String toDate) throws Exception;

  StringRecordset getWeeksAlreadySubmittedAsStartDate(String userId) throws Exception;

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

  StringRecordset getRowEntryForUser(String userId, String rowEntryId) throws Exception;

  StringRecordset getRowsInUserWeek(String userId, String fromDate) throws Exception;

  StringRecordset getAllRowsInUserWeek() throws Exception;

  StringRecordset getSettings() throws Exception;

  String getProperty(String key) throws Exception;

  Map<String, String> getProperties() throws Exception;

  StringRecordset getSkins() throws Exception;

  StringRecordset getSubmittedYears(String userId) throws Exception;

  StringRecordset getSubmittedWeeks(String userId, boolean descendingSortOrder, boolean checkIfTheyAreNotApproved) throws Exception;

  StringRecordset getSubmittedWeeks(String userId, String year, boolean descendingSortOrder, boolean checkIfTheyAreNotApproved) throws Exception;

  StringRecordset getSubmittedWeeksThick(String userId, String year, boolean descendingSortOrder, boolean checkIfTheyAreNotApproved) throws Exception;

  StringRecordset getApprovedWeeks(String userId, String year) throws Exception;

  StringRecordset getAllApprovedWeeks(String year) throws Exception;

  StringRecordset getWeeksNeedingSubmit(String userId, ITRCalendar cal, String year, String stopDate) throws Exception;

  StringRecordset getTimeType(String id) throws Exception;

  StringRecordset getTimeTypes() throws Exception;

  StringRecordset getUsers(boolean activated) throws Exception;

  StringRecordset getUsersThatNeedApprovel(String userId) throws Exception;

  StringRecordset getUsersReportedYear(String year) throws Exception;

  StringRecordset getVacationReport(String userId, String projectId, String companyId, String year) throws Exception;

  StringRecordset addCompanyAndGetId(Company company) throws Exception;

  StringRecordset addEmailAndGetId(Email email) throws Exception;

  StringRecordset addPhoneNumberAndGetId(PhoneNumber phone) throws Exception;

  StringRecordset loadActivitiesNotInProject(int projectId) throws Exception;

  StringRecordset loadActivity(int id) throws Exception;

  StringRecordset loadAssignedProjectMembers(int projectId) throws Exception;

  StringRecordset loadAvailableProjectMembers(int projectId) throws Exception;

  StringRecordset loadCompany(String companyId) throws Exception;

  StringRecordset loadContact(int contactId) throws Exception;

  StringRecordset loadProjectActivitiesForProject(int projectId) throws Exception;

  StringRecordset loadProjectActivity(int id) throws Exception;

  StringRecordset loadProjectMember(int id) throws Exception;

  StringRecordset loadUserProfile(String userId) throws Exception;

  StringRecordset login(String loginId, String password) throws Exception;

  StringRecordset makeActivityAndFetchId(Activity activity) throws Exception;

  StringRecordset makeNewCommmentAndRetriveTheId(String comment) throws Exception;

  StringRecordset makeNewContactAndFetchId(Contacts contact) throws Exception;

  StringRecordset makeNewProjectActivityAndFetchId(ProjectActivity projectActivity) throws Exception;

  StringRecordset makeNewProjectMemberAndFetchId(ProjectMember projectMember) throws Exception;

  StringRecordset makeNewUserAndFetchId(UserProfile user) throws Exception;

  StringRecordset makeNewUserRoleConnectionAndFetchId(Role role) throws Exception;

  StringRecordset makeNewUserWeekEntryAndRetriveTheId(String calendarWeekId, int weekCommentId) throws Exception;

  StringRecordset makeProjectAndFetchId(Project project) throws Exception;

}
