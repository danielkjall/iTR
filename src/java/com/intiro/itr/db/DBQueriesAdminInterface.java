package com.intiro.itr.db;

import com.intiro.itr.logic.activity.Activity;
import com.intiro.itr.logic.company.Company;
import com.intiro.itr.logic.contacts.Contacts;
import com.intiro.itr.logic.email.Email;
import com.intiro.itr.logic.phone.PhoneNumber;
import com.intiro.itr.logic.project.Project;
import com.intiro.itr.logic.project.ProjectActivity;
import com.intiro.itr.logic.project.ProjectMember;
import com.intiro.itr.util.StringRecordset;
import com.intiro.itr.util.personalization.Role;
import com.intiro.itr.util.personalization.UserProfile;

public interface DBQueriesAdminInterface extends InvocationHandled {

  StringRecordset getGeneralReport(String userId, String projectId, String projectCodeId, String fromDate, String toDate) throws Exception;

  StringRecordset getMonthlyReport(String userId, String projectId, String projectCodeId, String fromDate, String toDate) throws Exception;

  StringRecordset getUsersThatNeedApprovel(String userId) throws Exception;

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

  StringRecordset makeNewContactAndFetchId(Contacts contact) throws Exception;

  StringRecordset makeNewProjectActivityAndFetchId(ProjectActivity projectActivity) throws Exception;

  StringRecordset makeNewProjectMemberAndFetchId(ProjectMember projectMember) throws Exception;

  StringRecordset makeNewUserAndFetchId(UserProfile user) throws Exception;

  StringRecordset makeNewUserRoleConnectionAndFetchId(Role role) throws Exception;

  StringRecordset makeActivityAndFetchId(Activity activity) throws Exception;

  StringRecordset makeProjectAndFetchId(Project project) throws Exception;

}
