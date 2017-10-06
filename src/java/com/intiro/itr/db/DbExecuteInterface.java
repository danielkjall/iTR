package com.intiro.itr.db;

import com.intiro.itr.util.logger.LoggerVO;
import com.intiro.itr.logic.activity.Activity;
import com.intiro.itr.logic.company.Company;
import com.intiro.itr.logic.contacts.Contacts;
import com.intiro.itr.logic.email.Email;
import com.intiro.itr.logic.phone.PhoneNumber;
import com.intiro.itr.logic.phone.PhoneRegion;
import com.intiro.itr.logic.project.Project;
import com.intiro.itr.logic.project.ProjectActivity;
import com.intiro.itr.logic.project.ProjectMember;
import com.intiro.itr.logic.weekreport.Row;
import com.intiro.itr.util.personalization.Role;
import com.intiro.itr.util.personalization.UserProfile;
import com.intiro.itr.util.statistics.StatisticsVO;
import java.util.List;

public interface DbExecuteInterface extends InvocationHandled {

  boolean addActivity(Activity activity) throws Exception;

  boolean addCompany(Company company) throws Exception;

  boolean addContacts(Contacts contacts) throws Exception;

  boolean addEmail(Email email) throws Exception;

  boolean addPhoneNumber(PhoneNumber phone) throws Exception;

  boolean addProjectActivity(ProjectActivity projectActivity) throws Exception;

  boolean addProjectMember(ProjectMember projectMember) throws Exception;

  boolean addUserRoleConnection(Role role) throws Exception;

  boolean makeNewComment(String comment) throws Exception;

  boolean makeNewProject(Project project) throws Exception;

  boolean makeNewUserProfile(UserProfile profile) throws Exception;

  boolean makeNewUserWeekId(String calendarWeekId, int weekCommentId) throws Exception;

  boolean changePassword(String userId, String newLoginId, String newPassword) throws Exception;

  boolean deactivateProjectMember(int id) throws Exception;

  boolean deleteActivity(int activityId) throws Exception;

  boolean deleteCompany(int companyId) throws Exception;

  boolean deleteContact(Contacts contact) throws Exception;

  boolean deleteEmail(Email email) throws Exception;

  boolean deletePhoneNumber(PhoneNumber phone) throws Exception;

  boolean deleteProject(int projectId) throws Exception;

  boolean deleteProjectActivity(int id) throws Exception;

  boolean deleteProjectMember(int id) throws Exception;

  boolean deleteRow(Row row) throws Exception;

  boolean deleteUser(int userId) throws Exception;

  boolean insertRow(Row row, int weekReportId) throws Exception;

  void saveLog(List<LoggerVO> list) throws Exception;

  boolean updateActivity(Activity activity) throws Exception;

  boolean updateApprovedInWeek(int userWeekId, boolean status) throws Exception;

  boolean updateComment(int commentId, String comment) throws Exception;

  boolean updateCompany(Company company) throws Exception;

  boolean updateContacts(Contacts contacts) throws Exception;

  boolean updateEmail(Email email) throws Exception;

  boolean updatePhoneNumber(PhoneNumber phone) throws Exception;

  boolean updatePhoneRegion(PhoneRegion region) throws Exception;

  boolean updateProject(Project project) throws Exception;

  boolean updateProjectActivity(ProjectActivity projectActivity) throws Exception;

  boolean updateProjectMember(ProjectMember projectMember) throws Exception;

  boolean updateRow(Row row, int weekReportId) throws Exception;

  boolean updateSubmitInWeek(int userWeekId, boolean status) throws Exception;

  boolean updateUserProfile(UserProfile profile) throws Exception;

  boolean updateUserRoleConnection(Role role) throws Exception;

  boolean updateUserWeekComment(int inWeekReportId, int inWeekCommentId) throws Exception;

  void saveStatistics(StatisticsVO statistics) throws Exception;
}
