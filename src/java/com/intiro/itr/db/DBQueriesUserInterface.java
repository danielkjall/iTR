package com.intiro.itr.db;

import com.intiro.itr.util.ITRCalendar;
import com.intiro.itr.util.StringRecordset;

public interface DBQueriesUserInterface extends InvocationHandled {

  StringRecordset getWeeksAlreadySubmittedAsStartDate(String userId) throws Exception;

  StringRecordset getRowEntryForUser(String userId, String rowEntryId) throws Exception;

  StringRecordset getRowsInUserWeek(String userId, String fromDate) throws Exception;

  StringRecordset getAllRowsInUserWeek() throws Exception;

  StringRecordset getSubmittedYears(String userId) throws Exception;

  StringRecordset getSubmittedWeeks(String userId, boolean descendingSortOrder, boolean checkIfTheyAreNotApproved) throws Exception;

  StringRecordset getSubmittedWeeks(String userId, String year, boolean descendingSortOrder, boolean checkIfTheyAreNotApproved) throws Exception;

  StringRecordset getSubmittedWeeksThick(String userId, String year) throws Exception;

  StringRecordset getApprovedWeeks(String userId, String year) throws Exception;

  StringRecordset getAllApprovedWeeks(String year) throws Exception;

  StringRecordset getWeeksNeedingSubmit(String userId, ITRCalendar cal, String year, String stopDate) throws Exception;

  StringRecordset getUsersReportedYear(String year) throws Exception;

  StringRecordset loadUserProfile(String userId) throws Exception;

  StringRecordset login(String loginId, String password) throws Exception;

  StringRecordset makeNewUserWeekEntryAndRetriveTheId(String calendarWeekId, String comment) throws Exception;

}
